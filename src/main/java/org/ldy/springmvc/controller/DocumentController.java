package org.ldy.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldy.springmvc.model.Document;
import org.ldy.springmvc.model.User;
import org.ldy.springmvc.service.HrmService;
import org.ldy.springmvc.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class DocumentController {
	@Autowired
	private HrmService hrmService;
	private static final Log logger = LogFactory.getLog(DocumentController.class);
	//首先无论是什么请求都先跳转到查询页面 employee/selectEmployee
	@RequestMapping(value="/document/selectDocument")
	public String selectDocument(@ModelAttribute Document document, @RequestParam(defaultValue="0") int pageIndex, Model model)
	{
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		List<Document> documents = hrmService.selectDocument(document, pageModel);
		model.addAttribute("documents", documents);
		model.addAttribute("pageModel",pageModel);
		//返回document.jsp页面进行显示处理
		return "document/document";
	}
	
	/**
	 * param id:需要修改用户的id，
	 * param flag:用来表示跳转到修改页面还是提交修改。1表示跳转到修改页面，2表示执行修改操作，更新数据库
	 * return mv对象，当需要执行重定向操作时候需要用到ModelAndView
	 * document/updatedocument?flag=1&id=${document.id}
	 */
	@RequestMapping(value="/document/updateDocument")
	public String updatedocument(@ModelAttribute Document document, @RequestParam String flag, Model model)
	{
		//跳转到修改界面
		if (flag.equals("1"))
		{
			//1.根据id查询需要修改的用户
			Document targer = hrmService.selectDocumentById(document.getId());
			if (targer == null)
			{
				logger.info("制定要修改的数据找不到,可能是异步操作导致该问题!!!");
			}
			model.addAttribute("document", targer);
			//2.把查询出的用户设置到model中去，便于修改界面显示需要修改的数据
			return "document/showUpdateDocument";
		} else {
			//直接更新指定用户
			int res = hrmService.updateDocument(document);
			if (res != 1)
			{
				logger.info("更新职位失败!");
			}
			//设置客户端重定向，重新执行查询用户的请求			
			return "redirect:/document/selectDocument";
		}
	}
	
	/**
	 * /document/removedocument?ids
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/document/removeDocument")
	public String removedocument(@RequestParam("ids")String ids) throws MySQLIntegrityConstraintViolationException
	{
		String[] idArray = ids.split(",");
		for(String id : idArray)
		{
			//删除指定用户
			hrmService.removeDocument(Integer.parseInt(id));
			//如果违背实体完整性，则会抛出异常
		}				
		return "redirect:/document/selectDocument";
	}
	
	/**
	 * document/adddocument?flag=1
	 * @param document 
	 * @param flag
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="/document/addDocument")
	public String adddocument(@ModelAttribute Document document, String flg, HttpSession session, Model model) throws IllegalStateException, IOException
	{
		if(flg.equals("1")) {
			//转发到添加用户界面
			return "/document/showAddDocument";
		} else {
			// 上传文件路径
			String path = session.getServletContext().getRealPath("/upload/");
			System.out.println(path);
			// 上传文件名
			String fileName = document.getFile().getOriginalFilename();
			System.out.println(fileName);
			 // 将上传文件保存到一个目标文件当中
			document.getFile().transferTo(new File(path+File.separator+ fileName));
			
			// 插入数据库
			// 设置fileName
			document.setFileName(fileName);
			// 设置关联的User对象
			User user = (User) session.getAttribute("userSession");
			document.setUser(user);
			// 插入数据库
			hrmService.addDocument(document);
			// 返回
			return "redirect:/document/selectDocument";			
		}			
	}
	
	@RequestMapping(value="/document/downLoad")
	public ResponseEntity<byte[]> download(Integer id, HttpSession session) throws IOException {
		Document target = hrmService.selectDocumentById(id);
		String fileName =target.getFileName();
		//获取上传文件路径
		String path = session.getServletContext().getRealPath("/upload");
		//创建要下载的file对象
		File file = new File(path+File.separator+fileName);
		//如果文件不存在
		if (!file.exists()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		HttpHeaders headers = new HttpHeaders();
		//解决中文文件名乱码问题
		String downloadFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		//通知浏览器以attachment打开图片
		headers.setContentDispositionFormData("attachment", downloadFileName);
		//以二进制流的方式进行文件的下载
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//返回201
		return new ResponseEntity<>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);		
	}
}
