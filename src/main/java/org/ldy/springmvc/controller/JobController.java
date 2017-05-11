package org.ldy.springmvc.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.Job;
import org.ldy.springmvc.service.HrmService;
import org.ldy.springmvc.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class JobController {
	@Autowired
	private HrmService hrmService;
	private static final Log logger = LogFactory.getLog(JobController.class);
	//首先无论是什么请求都先跳转到查询页面 employee/selectEmployee
	@RequestMapping(value="/job/selectJob")
	public String selectJob(@ModelAttribute Job job, @RequestParam(defaultValue="0") int pageIndex, Model model)
	{
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		List<Job> jobs = hrmService.selectJob(job, pageModel);
		model.addAttribute("jobs", jobs);
		model.addAttribute("pageModel",pageModel);
		//返回job.jsp页面进行显示处理
		return "job/job";
	}
	
	/**
	 * param id:需要修改用户的id，
	 * param flag:用来表示跳转到修改页面还是提交修改。1表示跳转到修改页面，2表示执行修改操作，更新数据库
	 * return mv对象，当需要执行重定向操作时候需要用到ModelAndView
	 * job/updatejob?flag=1&id=${job.id}
	 */
	@RequestMapping(value="/job/updateJob")
	public String updatejob(@ModelAttribute Job job, @RequestParam String flag, Model model)
	{
		//跳转到修改界面
		if (flag.equals("1"))
		{
			//1.根据id查询需要修改的用户
			Job targer = hrmService.selectJobById(job.getId());
			if (targer == null)
			{
				logger.info("制定要修改的数据找不到,可能是异步操作导致该问题!!!");
			}
			model.addAttribute("job", targer);
			//2.把查询出的用户设置到model中去，便于修改界面显示需要修改的数据
			return "job/showUpdateJob";
		} else {
			//直接更新指定用户
			int res = hrmService.updateJob(job);
			if (res != 1)
			{
				logger.info("更新职位失败!");
			}
			//设置客户端重定向，重新执行查询用户的请求			
			return "redirect:/job/selectJob";
		}
	}
	
	/**
	 * /job/removejob?ids
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/job/removeJob")
	public String removejob(@RequestParam("ids")String ids) throws MySQLIntegrityConstraintViolationException
	{
		String[] idArray = ids.split(",");
		for(String id : idArray)
		{
			//删除指定用户
			hrmService.removeJob(Integer.parseInt(id));
			//如果违背实体完整性，则会抛出异常
		}				
		return "redirect:/job/selectJob";
	}
	
	/**
	 * job/addjob?flag=1
	 * @param job 
	 * @param flag
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/job/addJob")
	public String addjob(@ModelAttribute Job job, @RequestParam String flag, Model model)
	{
		if(flag.equals("1")) {
			//转发到添加用户界面
			return "job/showAddJob";
		} else {
			int res = hrmService.addJob(job);
			//添加用户失败
			if (res != 1) {
				//需要考虑一下，添加用户失败，还是返回该页面，是指添加部分错误信息
				logger.info("添加职位失败!");
				model.addAttribute("message", "添加职位失败,可能是该职位已经存在!");
				return "job/showAddJob";
			} else {
				logger.info("添加职位成功");
				return "redirect:/job/selectJob";
			}
		}
			
	}
}
