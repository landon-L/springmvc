package org.ldy.springmvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataBindingController {
	private static final Log logger = LogFactory.getLog(DataBindingController.class);
	
	@RequestMapping(value="/pathVariableTeset/{userID}")
	public String pathVariableTest(@PathVariable int userID, Model model)
	{
		logger.info("测试路径变量");
		model.addAttribute("userID", userID);
		return "first";
	}
	
	@RequestMapping(value="/requestHeaderTest")
	public String requestHeaderTest(@RequestHeader(value="Accept") String [] accepts, 
			@RequestHeader(value="User-Agent") String userAgent,
			Model model)
	{
		logger.info("通过RequestHeader 测试");
		for(String accept : accepts)
		{
			logger.info(accept);
		}
		model.addAttribute("header", userAgent);
		return "first";
	}
	@RequestMapping(value="/cookieValueTest")
	 public String cookieValueTest(@CookieValue(value="JSESSIONID", defaultValue="ldy test") String sessionID,
			 Model model)
	 {
		 logger.info("通过cookieValue 测试" +sessionID);
		 model.addAttribute("cookie", sessionID);
		 return "first";
	 }
}
