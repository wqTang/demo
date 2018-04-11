package com.wqt.springboot.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author iuShu
 * @date Mar 29, 2018 7:15:37 PM
 */
@SpringBootApplication
public class BookRunner {

	public static void main(String[] args) {
		// set this properties to available use the springboot-actuator for all user.
		System.setProperty("management.security.enabled", "false");
		
		SpringApplication.run(BookRunner.class, args);
	}

}
