package com.wqt.springboot.book.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wqt.springboot.book.entity.User;

/**
 * Parameter Binding
 * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#invokeHandlerMethod()}
 * 
 * @author iuShu
 * @date Mar 30, 2018 8:34:59 PM
 */
@RestController
public class LoginController {

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@PostMapping("/signIn")
	public void signIn(HttpServletRequest request, HttpServletResponse response, String username, String password)
			throws ServletException, IOException {
		User user = new User();
		user.setNickName("iuShu"); // simulation
		user.setUsername(username);
		user.setPassword(password);
		request.getSession().setAttribute("user", user);
		request.getRequestDispatcher("/book/" + user.getNickName()).forward(request, response);
	}

	@PostMapping("/signUp")
	public void signUp() {
	}

}
