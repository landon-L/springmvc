package org.ldy.springmvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public class ConvertController {
	private final static Log logger = LogFactory.getLog(ConvertController.class);
	
	@RequestMapping(value="/{formName}")
	public String loginForm(@PathVariable String formName)
	{
		//���������������̬��תҳ��
		return formName;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@ModelAttribute User user, Model model)
	{
		logger.info("����ע������");
		//user.setLover("������");
		model.addAttribute("user", user);
		return "success";		
	}
}
