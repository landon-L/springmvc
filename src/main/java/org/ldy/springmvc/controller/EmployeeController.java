package org.ldy.springmvc.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.Dept;
import org.ldy.springmvc.model.Employee;
import org.ldy.springmvc.model.Job;
import org.ldy.springmvc.service.HrmService;
import org.ldy.springmvc.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {
	@Autowired
	private HrmService hrmService;
	//TODO 需要了解一下日志工厂的实现方式
	private static Log logger = LogFactory.getLog(EmployeeController.class);
	
	@RequestMapping(value="/employee/selectEmployee")
	public String selectEmployee(@ModelAttribute Employee employee, @RequestParam(defaultValue="0") int pageIndex,
			@RequestParam(defaultValue="0") Integer job_id, @RequestParam(defaultValue="0") Integer dept_id, Model model)	{
		//生成关联对象实体
		this.genericAssociation(job_id, dept_id, employee);
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		
		List<Employee> employees = hrmService.selectEmployee(employee, pageModel);
		//同时在数据展示区域还有jobs，和depts的下拉框，需要数据填充
		List<Job> jobs = hrmService.selectAllJob();
		List<Dept> depts = hrmService.selectAllDept();
		
		model.addAttribute("employees", employees);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("depts", depts);
		model.addAttribute("jobs", jobs);		
		
		return "employee/employee";
	}
	
	@RequestMapping(value="/employee/addEmployee")
	public String addEmployee(@ModelAttribute Employee employee, @RequestParam String flag,
			@RequestParam(defaultValue="0")Integer job_id, @RequestParam(defaultValue="0")Integer dept_id, Model model)	{
		//跳转到增加页面
		if(flag.equals("1"))	{
			//准备数据
			List<Job> jobs = hrmService.selectAllJob();
			List<Dept> depts = hrmService.selectAllDept();
			model.addAttribute("depts", depts);
			model.addAttribute("jobs", jobs);			
			return "employee/showAddEmployee";
		} else {
			//注意生成关联对象
			this.genericAssociation(job_id, dept_id, employee);
			int res = hrmService.addEmployee(employee);
			//添加用户失败
			if (res != 1) {
				//需要考虑一下，添加用户失败，还是返回该页面，是指添加部分错误信息
				logger.info("添加职工失败!");
				model.addAttribute("message", "添加职工失败,可能是该职工已经存在!");
				return "employee/showAddEmployee";
			} else {
				logger.info("添加职工成功");
				return "redirect:/employee/selectEmployee";
			}			
		}
	}
	
	@RequestMapping(value="/employee/updateEmployee")
	public String updateEmployee(@ModelAttribute Employee employee, @RequestParam String flag,
			@RequestParam(defaultValue="0")Integer job_id, @RequestParam(defaultValue="0")Integer dept_id, Model model)	{
		//跳转到修改界面，这里需要注意，数据的现实问题：1，下拉框显示所有供选项2.显示需要修改的数据
		if (flag.equals("1"))
		{	
			//展示所有供选择的数据
			List<Job> jobs = hrmService.selectAllJob();
			List<Dept> depts = hrmService.selectAllDept();
			model.addAttribute("depts", depts);
			model.addAttribute("jobs", jobs);
			
			//1.根据id查询需要修改的用户
			Employee targer = hrmService.selectEmployeeById(employee.getId());
			if (targer == null)
			{
				logger.info("制定要修改的数据找不到,可能是异步操作导致该问题!!!");
				//TODO 1.此处返回一个标志，让前端弹出一个提示框，需要思考如何弹出比较好
				//TODO 已解决 2.如果想前段页面不做任何变化，应该如何返回这个结果,更新用户失败也需要在界面提示，如何处理
				model.addAttribute("message", "更新职工信息失败,请检查网络!");
				//TODO 不刷新当前页面，该如何处理，直接返回改页面是不是可以
				return "employee/employee";
			}
			model.addAttribute("employee", targer);
			//2.把查询出的用户设置到model中去，便于修改界面显示需要修改的数据
			return "employee/showUpdateEmployee";
		} else {
			//直接更新指定用户,生成关联对象
			this.genericAssociation(job_id, dept_id, employee);
			int res = hrmService.updateEmployee(employee);
			if (res != 1)
			{
				logger.info("更新职工信息失败!");
				model.addAttribute("message", "更新职工信息失败,请检查网络!");
				return "employee/showUpdateEmployee";
			}
			//设置客户端重定向，重新执行查询用户的请求			
			return "redirect:/employee/selectEmployee";
		}
	}
	
	@RequestMapping(value="/employee/removeEmployee")
	public String removeEmployee(@RequestParam("ids")String ids) {
		// 分解id字符串
		String[] idArray = ids.split(",");
		for(String id : idArray){
			// 根据id删除员工
			int res = hrmService.delEmployee(Integer.parseInt(id));
			if (res != 1)
			{
				logger.info("删除职工id="+id+"信息失败，请检查主外键关系");
				//不刷新页面,如下方法不行
				return "/employee/employee";
			}
		}
		
		return "redirect:/employee/selectEmployee";
	}
	
	/**
	 * 由于部门和职位在Employee中是对象关联映射，
	 * 所以不能直接接收参数，需要创建Job对象和Dept对象
	 * */
	private void genericAssociation(Integer job_id,
			Integer dept_id,Employee employee){
		if(job_id != null){
			Job job = new Job();
			job.setId(job_id);
			employee.setJob(job);
		}
		if(dept_id != null){
			Dept dept = new Dept();
			dept.setId(dept_id);
			employee.setDept(dept);
		}
	}
}
