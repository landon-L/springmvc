package org.ldy.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.Notice;
import org.ldy.springmvc.model.User;
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
public class NoticeController {
	@Autowired
	private HrmService hrmService;
	private static final Log logger = LogFactory.getLog(NoticeController.class);
	//首先无论是什么请求都先跳转到查询页面 employee/selectEmployee
	@RequestMapping(value="/notice/selectNotice")
	public String selectNotice(@ModelAttribute Notice notice, @RequestParam(defaultValue="0") int pageIndex, Model model)
	{
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		List<Notice> notices = hrmService.selectNotice(notice, pageModel);
		model.addAttribute("notices", notices);
		model.addAttribute("pageModel",pageModel);
		//返回notice.jsp页面进行显示处理
		return "notice/notice";
	}
	
	@RequestMapping(value="/notice/previewNotice")
	public String previewNotice(Integer id, Model model) {
		Notice notice = hrmService.selectNoticeById(id);
		model.addAttribute("notice", notice);
		return "/notice/previewNotice";
	}
	/**
	 * param id:需要修改用户的id，
	 * param flag:用来表示跳转到修改页面还是提交修改。1表示跳转到修改页面，2表示执行修改操作，更新数据库
	 * return mv对象，当需要执行重定向操作时候需要用到ModelAndView
	 * notice/updatenotice?flag=1&id=${notice.id}
	 */
	@RequestMapping(value="/notice/updateNotice")
	public String updatenotice(@ModelAttribute Notice notice, @RequestParam String flag, Model model)
	{
		//跳转到修改界面
		if (flag.equals("1"))
		{
			//1.根据id查询需要修改的用户
			Notice targer = hrmService.selectNoticeById(notice.getId());
			if (targer == null)
			{
				logger.info("制定要修改的数据找不到,可能是异步操作导致该问题!!!");
			}
			model.addAttribute("notice", targer);
			//2.把查询出的用户设置到model中去，便于修改界面显示需要修改的数据
			return "notice/showUpdateNotice";
		} else {
			//直接更新指定用户
			int res = hrmService.updateNotice(notice);
			if (res != 1)
			{
				logger.info("更新职位失败!");
			}
			//设置客户端重定向，重新执行查询用户的请求			
			return "redirect:/notice/selectNotice";
		}
	}
	
	/**
	 * /notice/removenotice?ids
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/notice/removeNotice")
	public String removenotice(@RequestParam("ids")String ids) throws MySQLIntegrityConstraintViolationException
	{
		String[] idArray = ids.split(",");
		for(String id : idArray)
		{
			//删除指定用户
			hrmService.removeNotice(Integer.parseInt(id));
			//如果违背实体完整性，则会抛出异常
		}				
		return "redirect:/notice/selectNotice";
	}
	
	/**
	 * notice/addnotice?flag=1
	 * @param notice 
	 * @param flag
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/notice/addNotice")
	public String addnotice(@ModelAttribute Notice notice, @RequestParam String flag, HttpSession session, Model model)
	{
		if(flag.equals("1")) {
			//转发到添加用户界面
			return "notice/showAddNotice";
		} else {
			User user = (User)session.getAttribute("userSession");
			notice.setUser(user);
			int res = hrmService.addNotice(notice);
			//添加用户失败
			if (res != 1) {
				//需要考虑一下，添加用户失败，还是返回该页面，是指添加部分错误信息
				logger.info("添加职位失败!");
				model.addAttribute("message", "!");
				return "notice/showAddNotice";
			} else {
				logger.info("添加职位成功");
				return "redirect:/notice/selectNotice";
			}
		}
			
	}
}
