package com.wqt.netflix.eureka.sample.guice;

import com.google.inject.AbstractModule;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.guice.EurekaModule;
import com.netflix.governator.InjectorBuilder;
import com.netflix.governator.LifecycleInjector;
import com.wqt.netflix.eureka.sample.SampleServiceBase;

/** 
 * Sample Eureka service that registers with Eureka to receive and process requests, using EurekaModule.
 * 
 * @author iuShu 
 * @date May 2, 2018 1:36:30 PM
 */
public class SampleEurekaGovernatedService {

	// IOC config (using google Guice)
	static class SampleServiceModule extends AbstractModule {
		
		@Override
		protected void configure() {
			bind(SampleServiceBase.class).asEagerSingleton();
		}
	}
	
	private static LifecycleInjector init() {
		System.out.println("Creating Injector for example service");
		
		LifecycleInjector injector = InjectorBuilder.fromModules(new EurekaModule(), new SampleServiceModule())
				.overrideWith(new AbstractModule() {
					@Override
					protected void configure() {
						DynamicPropertyFactory configInstance = DynamicPropertyFactory.getInstance();
						bind(DynamicPropertyFactory.class).toInstance(configInstance);
						
						// the default impl of EurekaInstanceConfig is CloudInstanceConfig, which we only want in an 
						// AWS environment. Here we override that by binding MyDataCenterInstanceConfig to EurekaInstanceConfig
						bind(EurekaInstanceConfig.class).to(MyDataCenterInstanceConfig.class);
						
						// (DiscoveryClient optional bindings) bind the optional event bus
						// bind(EventBus.class).to(EventBusImpl.class).in(Scope.SINGLETON);
					}
				}).createInjector();
		
		System.out.println("Done creating the injector");
		return injector;
	}
	
	public static void main(String[] args) {
		LifecycleInjector injector = null;
		
		try {
			injector = init();
			injector.awaitTermination();
		} catch (InterruptedException e) {
            System.err.println("Error starting the sample service: " + e);
			e.printStackTrace();
		} finally {
			if (injector != null)
				injector.close();
		}
	}
	
}
