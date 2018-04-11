package com.wqt.springboot.book.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wqt.springboot.book.entity.User;

/**
 * @author iuShu
 * @date Mar 30, 2018 8:25:45 PM
 */
public class LoginInterceptor implements HandlerInterceptor {

	private static final String[] IGNORE_LIST = { "/login", "/signIn", "/signUp", "/error" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String reqPath = request.getServletPath();
		System.out.println("[preHandle] requestPath: " + reqPath);

		boolean ignore = false;
		for (int i = 0; i < IGNORE_LIST.length; i++) {
			if (IGNORE_LIST[i].equals(reqPath)) {
				ignore = true;
				break;
			}
		}

		if (ignore)
			return true;

		User user = (User) request.getSession().getAttribute("user");
		if (user != null)
			return true;

		System.out.println("[preHandle] unauthentication");
		request.getRequestDispatcher("/login").forward(request, response);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("[postHandler] handler: " + handler.getClass().getName());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("[afterCompletion] handler: " + handler);
		System.out.println("[afterCompletion] exception: " + ex);
	}

}
