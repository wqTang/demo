package com.wqt.netflix.eureka.sample;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

/** 
 * Sample Eureka service that registers with Eureka to receive and process requests.
 * This example just receive one request and exits once it receives the request after processing it.
 * 
 * @author iuShu 
 * @date May 2, 2018 1:23:50 PM
 */
public class SampleEurekaService {
	
	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;
	
	private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
		}
		
		return applicationInfoManager;
	}

	private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
		if (eurekaClient == null)
			eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
		
		return eurekaClient;
	}
	
	public static void main(String[] args) {
		DynamicPropertyFactory configInstance = DynamicPropertyFactory.getInstance();
		ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
		EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
		
		SampleServiceBase sampleServiceBase = new SampleServiceBase(applicationInfoManager, eurekaClient, configInstance);
		try {
			sampleServiceBase.start();
		} finally {
			sampleServiceBase.stop();
		}
	}

}
