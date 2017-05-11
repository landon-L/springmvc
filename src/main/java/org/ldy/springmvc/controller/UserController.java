package org.ldy.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.User;
import org.ldy.springmvc.service.HrmService;
import org.ldy.springmvc.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	private static final Log logger = LogFactory.getLog(UserController.class);
	@Autowired
	private HrmService hrmService;
	
	//没有匹配的处理方法就占到对应的页面
	@RequestMapping(value="/{formName}")
	public String loginForm(@PathVariable String formName)
	{
		return formName;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(@RequestParam("loginname")String loginname, @RequestParam("password")String password, HttpSession session, ModelAndView mv)
	{
		User user = hrmService.login(loginname,password);
		if(user != null)
		{
			session.setAttribute("userSession", user);
			//重定向到main页面，登陆请求结束，这里还需要视图解析器统一处理，前缀以及后缀
			mv.setViewName("redirect:/main");
		}
		else
		{
			mv.addObject("message", "登录名或密码错误！请重新输入");
			mv.setViewName("forward:/loginForm");
		}
		return mv;
	}
	/**
	 * 根据用户名和用户状态进行用户查询,
	 * @return
	 */
	@RequestMapping(value="/user/selectUser")
	public String selectUser(@ModelAttribute User user, @RequestParam(defaultValue="0") int pageIndex, Model model)
	{
		//TODO 需要思考第一次进来，参数如何处理？？？？？？？？ 当第一次加载页面时候，对象类型的参数默认为NULL
		//TODO 如果输入数据不合法，会照成description The request sent by the client was syntactically incorrect.的错误，这时候需要在前段加入js校验
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		List<User> users = hrmService.selectUser(user, pageModel);
		model.addAttribute("users", users);
		model.addAttribute("pageModel",pageModel);
		//返回user.jsp页面进行显示处理
		return "user/user";
	}
	/**
	 * param id:需要修改用户的id，
	 * param flag:用来表示跳转到修改页面还是提交修改。1表示跳转到修改页面，2表示执行修改操作，更新数据库
	 * return mv对象，当需要执行重定向操作时候需要用到ModelAndView
	 * user/updateUser?flag=1&id=${user.id}
	 */
	@RequestMapping(value="/user/updateUser")
	public String updateUser(@ModelAttribute User user, @RequestParam String flag, Model model)
	{
		//跳转到修改界面
		if (flag.equals("1"))
		{
			//1.根据id查询需要修改的用户
			User targer = hrmService.selectUserById(user.getId());
			if (targer == null)
			{
				logger.info("制定要修改的数据找不到,可能是异步操作导致该问题!!!");
				//TODO 1.此处返回一个标志，让前端弹出一个提示框，需要思考如何弹出比较好
				//TODO 2.如果想前段页面不做任何变化，应该如何返回这个结果,更新用户失败也需要在界面提示，如何处理
				return null;
			}
			model.addAttribute("user", targer);
			//2.把查询出的用户设置到model中去，便于修改界面显示需要修改的数据
			return "user/showUpdateUser";
		} else {
			//直接更新指定用户
			int res = hrmService.updateUser(user);
			if (res != 1)
			{
				logger.info("更新用户失败!");
				return null;
			}
			//设置客户端重定向，重新执行查询用户的请求			
			return "redirect:/user/selectUser";
		}
	}
	
	/**
	 * /user/removeUser?ids
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/removeUser")
	public String removeUser(@RequestParam("ids")String ids)
	{
		String[] idArray = ids.split(",");
		for(String id : idArray)
		{
			//删除指定用户
			hrmService.removeUser(Integer.parseInt(id));
		}				
		return "redirect:/user/selectUser";
	}
	
	/**
	 * user/addUser?flag=1
	 * @param user 
	 * @param flag
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/addUser")
	public String addUser(@ModelAttribute User user, @RequestParam String flag, Model model)
	{
		if(flag.equals("1")) {
			//转发到添加用户界面
			return "user/showAddUser";
		} else {
			int res = hrmService.addUser(user);
			//添加用户失败
			if (res != 1) {
				//需要考虑一下，添加用户失败，还是返回该页面，是指添加部分错误信息
				logger.info("添加用户失败!");
				model.addAttribute("message", "添加用户失败,可能是该用户已经存在!");
				return "user/showAddUser";
			} else {
				logger.info("添加用户成功");
				return "redirect:/user/selectUser";
			}
		}
			
	}
}
