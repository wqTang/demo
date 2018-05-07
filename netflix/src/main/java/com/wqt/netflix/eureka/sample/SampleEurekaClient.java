package com.wqt.netflix.eureka.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

/**
 * Sample Eureka client that discoverys the example service using Eureka and
 * sends requests.
 * 
 * In this example, the program tries to get the example from the EurekaClient,
 * and then makes a REST call to a supported service endpoint.
 * 
 * @author iuShu
 * @date May 2, 2018 2:36:58 PM
 */
public class SampleEurekaClient {

	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;

	private static synchronized ApplicationInfoManager initializeApplicationInfoManager(
			EurekaInstanceConfig instanceConfig) {
		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
		}

		return applicationInfoManager;
	}

	private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager,
			EurekaClientConfig clientConfig) {
		if (eurekaClient == null) {
			eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
		}

		return eurekaClient;
	}

	public void sendRequestToServiceUsingEureka(EurekaClient eurekaClient) {
		// initialize the client
		// this is the virtual ip address for the example service to talk to as
		// defined in 'sample-eureka-service.properties'
		String vipAddress = "sample.mydomain.net";

		InstanceInfo nextServerInfo = null;
		nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);

		System.out.println("Found an instance of example service to talk to from eureka: "
				+ nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

		System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
		System.out.println("override: " + nextServerInfo.getOverriddenStatus());

		// connect to server
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(nextServerInfo.getHostName(), nextServerInfo.getPort()));
		} catch (IOException e) {
			System.err.println("Could not connect to the server " + nextServerInfo.getHostName() + ":"
					+ nextServerInfo.getPort());
		}

		// send a request to server and waiting response
		try {
			String request = "FOO " + new Date() + "\n";
			System.out.println("Connected to server. Sending a request: " + request);

			PrintStream out;
			out = new PrintStream(socket.getOutputStream());
			out.print(request);

			System.out.println("Waiting for server response...");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str = br.readLine();
			if (str != null) {
				System.out.println("Received response from server: " + str);
				System.out.println("Exiting the client. Demo over..");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SampleEurekaClient sampleEurekaClient = new SampleEurekaClient();

		// create the client
		ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
		EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

		// use the client
		sampleEurekaClient.sendRequestToServiceUsingEureka(eurekaClient);

		// shutdown the client
		eurekaClient.shutdown();
	}
}
