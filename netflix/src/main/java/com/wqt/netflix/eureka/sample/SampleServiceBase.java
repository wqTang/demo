package com.wqt.netflix.eureka.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.EurekaClient;

/**
 * An example service (that can be initialized in a variety of ways) that
 * registers with eureka and listens for REST calls on port 8001.
 * 
 * @author iuShu
 * @date May 2, 2018 12:52:23 PM
 */
@Singleton
public class SampleServiceBase {

	private final ApplicationInfoManager applicationInfoManager;
	private final EurekaClient eurekaClient;
	private final DynamicPropertyFactory configInstance;

	@Inject
	public SampleServiceBase(ApplicationInfoManager applicationInfoManager, EurekaClient eurekaClient,
			DynamicPropertyFactory configInstance) {
		this.applicationInfoManager = applicationInfoManager;
		this.eurekaClient = eurekaClient;
		this.configInstance = configInstance;
	}

	@PostConstruct
	public void start() {
		// A good practice is to register as STARTING and only change status to UP
		// after the service is ready to receive traffic
		System.out.println("Registering service to Eureka with STARTING status");
		applicationInfoManager.setInstanceStatus(InstanceStatus.STARTING); // initialize status

		System.out.println("Simulating service initialization by sleeping for 2 seconds...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// ignore
		}

		// Now we change our status to UP
		System.out.println("Done sleeping, now changing status to UP");
		applicationInfoManager.setInstanceStatus(InstanceStatus.UP);
		waitForRegistrationWithEureka(eurekaClient);
		System.out.println("Service started and ready to process requests...");

		// started to process requests
		try {
			int myServingPort = applicationInfoManager.getInfo().getPort();
			ServerSocket serverSocket = new ServerSocket(myServingPort);
			final Socket socket = serverSocket.accept();
			System.out.println("Client got an connected and ready to process it...");
			processRequest(socket);
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void stop() {
		if (eurekaClient == null)
			return;

		System.out.println("Shutting down server. Sample over.");
		eurekaClient.shutdown();
	}

	private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
		// listen on Virtual IP Address specific in configuration.
		String vipAddress = configInstance.getStringProperty("eureka.vipAddress", "sampleservice.mydomain.net").get();
		InstanceInfo nextServerInfo = null;
		while (nextServerInfo == null) {
			try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Throwable e) {
            	System.out.println(">>> failure: " + e);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
		}
	}

	private void processRequest(final Socket socket) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = br.readLine();
			if (line != null)
				System.out.println("Received a request from the example client: " + line);

			String response = "BAR " + new Date();
			System.out.println("Sending the response to the client: " + response);

			PrintStream out = new PrintStream(socket.getOutputStream());
			out.print(response);
		} catch (IOException e) {
			System.err.println("Error processing requests");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
