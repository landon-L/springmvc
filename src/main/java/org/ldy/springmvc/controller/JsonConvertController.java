package org.ldy.springmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.Job;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/json")
public class JsonConvertController {
	private static final Log logger = LogFactory.getLog(JsonConvertController.class);
	
	@RequestMapping(value="/jsonTest")
	public void setJson(@RequestBody Job book, HttpServletResponse response) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		logger.info(mapper.writeValueAsString(book));
		//book.setAuthor("���");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().println(mapper.writeValueAsString(book));
		//���������Ƕ��������ݳɹ����
		response.setStatus(500);
	}
}
