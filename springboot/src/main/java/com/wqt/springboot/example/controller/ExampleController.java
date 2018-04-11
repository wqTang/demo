package com.wqt.springboot.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iuShu
 * @date Mar 29, 2018 6:34:16 PM
 */
@RestController
public class ExampleController {

	@RequestMapping("/")
	public String home() {
		return "hello springboot";
	}

}
