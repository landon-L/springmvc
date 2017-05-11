package org.ldy.springmvc.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.Dept;
import org.ldy.springmvc.model.User;
import org.ldy.springmvc.service.HrmService;
import org.ldy.springmvc.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeptController {
	@Autowired
	private HrmService hrmService;
	private static final Log logger = LogFactory.getLog(DeptController.class);
	@RequestMapping(value="dept/selectDept")
	public String selectDept(@ModelAttribute Dept dept, @RequestParam(defaultValue ="0") Integer pageIndex, Model model){
		//TODO 需要仔细分析一下pageIndex的处理方法，为什么不能加RequestParam;--解决：当第一次加载时候请求头中没有pageIndex这个参数就会导致失败，添加默认值就可以搞定了。
		//TODO进一步的，这些参数是怎么获取到的？？？
		PageModel pageModel = new PageModel(); 
//		if (pageIndex == null)
//		{
//			pageIndex = 0;
//		}
		pageModel.setPageIndex(pageIndex);
		List<Dept> depts = hrmService.selectDept(dept, pageModel);
		model.addAttribute("depts", depts);
		model.addAttribute("pageModel", pageModel); 
		return "dept/dept";		
	}
	
	@RequestMapping(value="dept/addDept")
	public String addDept(@ModelAttribute Dept dept, @RequestParam String flag, Model model) {
		if(flag.equals("1")) {
			//转发到添加用户界面
			return "dept/showAddDept";
		} else {
			hrmService.addDept(dept);
			logger.info("添加用户成功");
			return "redirect:/dept/selectDept";
			
			//添加用户失败
//			if (res != 1) {
//				//需要考虑一下，添加用户失败，还是返回该页面，是指添加部分错误信息
//				logger.info("添加用户失败!");
//				model.addAttribute("message", "添加用户失败,可能是该用户已经存在!");
//				return "user/showAddUser";
//			} else {
//				logger.info("添加用户成功");
//				return "redirect:/dept/selectDept";
//			}
		}
	}
	
	@RequestMapping(value="dept/updateDept")
	public String updateDept(@ModelAttribute Dept dept, @RequestParam String flag, Model model) {
		//跳转到修改界面
		if (flag.equals("1"))
		{
			//1.根据id查询需要修改的用户
			Dept targer = hrmService.selectDeptById(dept.getId());
			if (targer == null)
			{
				logger.info("制定要修改的数据找不到,可能是异步操作导致该问题!!!");
				//TODO 1.此处返回一个标志，让前端弹出一个提示框，需要思考如何弹出比较好
				//TODO 2.如果想前段页面不做任何变化，应该如何返回这个结果,更新用户失败也需要在界面提示，如何处理
				return null;
			}
			model.addAttribute("dept", targer);
			//2.把查询出的用户设置到model中去，便于修改界面显示需要修改的数据
			return "dept/showUpdateDept";
		} else {
			//直接更新指定用户
			hrmService.updateDept(dept);
//			if (res != 1)
//			{
//				logger.info("更新用户失败!");
//				return null;
//			}
			//设置客户端重定向，重新执行查询用户的请求			
			return "redirect:/dept/selectDept";
		}
	}
	
	@RequestMapping(value="/dept/removeDept")
	public String removeDept(@RequestParam("ids") String ids)
	{
		String[] idArray = ids.split(",");
		for(String id : idArray)
		{
			//删除指定用户
			hrmService.removeDept(Integer.parseInt(id));
		}		
		return "redirect:/dept/selectDept";
	}

}
