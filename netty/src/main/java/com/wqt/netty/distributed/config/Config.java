package com.wqt.netty.distributed.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author Wqt
 * @date 2017年12月12日 下午4:21:00
 * @version v1.0
 */
public class Config {

	private int localPort;
	private Map<String, InetAddressHolder> servers;

	public static final String DEFAULT_CONFIG_PATH = "server.properties";
	
	class InetAddressHolder {
		private String host;
		private int port;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

	}

	public Config() {
		this(DEFAULT_CONFIG_PATH);
	}
	
	public Config(String configPath) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(configPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initServer(properties);
	}
	
	private void initServer(Properties properties) {
		properties.getProperty("server.");
	}
	
	public int getLocalPort() {
		return localPort;
	}
}
