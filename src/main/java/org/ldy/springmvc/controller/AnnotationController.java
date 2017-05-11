package org.ldy.springmvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnnotationController {
	private static final Log logger = LogFactory.getLog(HelloController.class);
	@RequestMapping(value="/helloAnno")
	public ModelAndView helloAnnotation()
	{
		logger.info("helloAnnotation is ������");		
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "helloAnnotation!!!");
		mv.setViewName("/WEB-INF/views/first.jsp");
		return mv;
	}
}
