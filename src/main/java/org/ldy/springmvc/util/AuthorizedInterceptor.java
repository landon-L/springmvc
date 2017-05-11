package org.ldy.springmvc.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ldy.springmvc.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthorizedInterceptor implements HandlerInterceptor{
	// 定义不需要拦截的请求
	private static final String[] IGNORE_URI ={"/loginForm", "/login", "/404.html"};

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//默认用户没有登陆
		boolean flag =false;
		//获得请求的serveletPath
		String servletPath = request.getServletPath();
		for (String s : IGNORE_URI) {
			if (servletPath.contains(s)){
				flag = true;
				break;
			}
		}
		//需要拦截
		if (!flag)
		{
			//获取session中的用户
			User user = (User) request.getSession().getAttribute("userSession");
			if (user == null) {
				request.setAttribute("message", "请先登录再访问网站!");
				request.getRequestDispatcher("loginForm").forward(request, response);
				return flag;
			} else {
				flag = true;
			}
		}		
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
