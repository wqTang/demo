package com.wqt.netflix.ribbon.client;

import java.net.URI;

import org.apache.commons.configuration.AbstractConfiguration;

import com.netflix.client.ClientFactory;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;

/**
 * Ribbon <br>
 * 	+ ribbon-httpClient (actually used the jersey Client) <br>
 * 		- default config: @see {@link DefaultClientConfigImpl}
 * 		- default client: @see {@link RestClient}
 * 		- default loadBalancer: @see {@link ZoneAwareLoadBalancer}
 * 
 * @author iuShu
 * @date Apr 28, 2018 6:58:32 PM
 */
@SuppressWarnings("deprecation")
public class DemoRibbon {

	public static final String CUR_CLIENT = "sample-client";
	public static final String CUR_NAMESPACE = "ribbon";

	public static void main(String[] args) throws Exception {
		ConfigurationManager.loadPropertiesFromResources("com/wqt/netflix/ribbon/sample-client.properties");

		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		System.out.println("config class: " + config.getClass().getName());
		String listOfServers = CUR_CLIENT + "." + CUR_NAMESPACE + "." + CommonClientConfigKey.ListOfServers.key();
		System.out.println("list of servers: " + config.getProperty(listOfServers));

		RestClient restClient = (RestClient) ClientFactory.getNamedClient(CUR_CLIENT);

		ILoadBalancer lb = restClient.getLoadBalancer();
		System.out.println("loadBalancer class name: " + lb.getClass().getName());

		HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build();
		for (int i = 0; i < 20; i++) {
			HttpResponse response = restClient.executeWithLoadBalancer(request);
			System.out.println("Status code for: " + response.getStatus() + " " + response.getRequestedURI());
		}

		ZoneAwareLoadBalancer loadBalancer = (ZoneAwareLoadBalancer) restClient.getLoadBalancer();
		System.out.println("load balancer:\n" + loadBalancer.getLoadBalancerStats());
	
		/**
		 * dynamic change the servers list
		 * 
		 * Eureka provides service discovery functionality 
		 * and can be integrated with ribbon to provide dynamic list of servers.
		 */
		config.setProperty(listOfServers, "www.github.com:80,www.jd.com:80,www.csdn.net:80");
		System.out.println("\nchanging servers ...\n");

		Thread.sleep(6); // Wait util server list is refreshed (2 seconds refresh interval defined in properties file)

		for (int i = 0; i < 20; i++) {
			HttpResponse response = restClient.executeWithLoadBalancer(request);
			System.out.println("Status code for: " + response.getStatus() + " " + response.getRequestedURI());
		}

		System.out.println("load balancer:\n" + loadBalancer.getLoadBalancerStats());
	}

}
