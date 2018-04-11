package com.wqt.springboot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author iuShu
 * @date Mar 29, 2018 6:35:15 PM
 */
@ComponentScan
@EnableAutoConfiguration
public class ExampleRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExampleRunner.class, args);
	}

}
