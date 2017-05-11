package org.ldy.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller {
	private static final Log logger = LogFactory.getLog(HelloController.class);
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.info("handleRequest ±»µ÷ÓÃ");
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "hello springmvc");
		mv.setViewName("/WEB-INF/views/first.jsp");
		return mv;
	}
	

}
