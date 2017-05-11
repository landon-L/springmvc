package org.ldy.springmvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldy.springmvc.dao.DeptMapper;
import org.ldy.springmvc.dao.DocumentMapper;
import org.ldy.springmvc.dao.EmployeeMapper;
import org.ldy.springmvc.dao.JobMapper;
import org.ldy.springmvc.dao.NoticeMapper;
import org.ldy.springmvc.dao.UserDao;
import org.ldy.springmvc.model.Dept;
import org.ldy.springmvc.model.Document;
import org.ldy.springmvc.model.Employee;
import org.ldy.springmvc.model.Job;
import org.ldy.springmvc.model.Notice;
import org.ldy.springmvc.model.User;
import org.ldy.springmvc.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 业务逻辑层方法：对控制器传来的参数做简单的校验，同时负责较复杂逻辑的实现
 * @author Administrator
 *
 */
@Service
public class HrmServiceImpl implements HrmService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private DeptMapper deptDao;
	@Autowired
	private EmployeeMapper employeeDao;
	@Autowired
	private JobMapper jobDao;
	@Autowired
	private NoticeMapper noticeDao;
	@Autowired
	private DocumentMapper documentDao;
	@Override
	public User login(String loginname, String password) {
		if(loginname == null || loginname.equals("") || password == null || password.equals(""))
		{
			return null;
		}
		else
		{
			return userDao.login(loginname,password);
		}
	}
	@Override
	public List<User> selectUser(User user, PageModel pageModel) {
		
		int count = userDao.count(user);
		if(count <= 0){
			pageModel.setPageIndex(0);
			pageModel.setRecordCount(0);
			return null;
		}else if (pageModel.getPageIndex() == 0) {
			pageModel.setPageIndex(1);			
		}
		pageModel.setRecordCount(count);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("pageModel", pageModel);

		return userDao.getPage(params);

	}
	@Override
	public User selectUserById(Integer id) {
		
		return userDao.selectUserById(id);
	}
	@Override
	public Integer updateUser(User user) {		
		return userDao.updateUser(user);
	}
	@Override
	public Integer removeUser(Integer id) {
		
		return userDao.removeUser(id);
	}
	@Override
	public Integer addUser(User user) {		
		return userDao.addUser(user);
	}
	@Override
	public List<Dept> selectDept(Dept dept, PageModel pageModel) {
		int count = deptDao.count(dept);
		//有记录则为第一次默认加载时，设置当前页尾第一页
		if ((count > 0) && (pageModel.getPageIndex() == 0)) {
			pageModel.setPageIndex(1);
		}			
		pageModel.setRecordCount(count);
		Map<String, Object> params = new HashMap<>();
		params.put("dept", dept);
		params.put("pageModel", pageModel);
		
		return deptDao.selectByPage(params);
	}
	@Override
	public void addDept(Dept dept) {		
		deptDao.save(dept);
	}
	@Override
	public Dept selectDeptById(Integer id) {		
		return deptDao.selectById(id);
	}
	@Override
	public void updateDept(Dept dept) {		
		deptDao.update(dept);
	}
	@Override
	public Integer removeDept(Integer id) {		
		return deptDao.delById(id);
	}
	
	@Override
	public List<Employee> selectEmployee(Employee employee, PageModel pageModel) {
		int count = employeeDao.count(employee);
		//有记录则为第一次默认加载时，设置当前页尾第一页
		if ((count > 0) && (pageModel.getPageIndex() == 0)) {
			pageModel.setPageIndex(1);
		}			
		pageModel.setRecordCount(count);
		Map<String, Object> params = new HashMap<>();
		params.put("employee", employee);
		params.put("pageModel", pageModel);
		
		return employeeDao.selectByPage(params);
	}
	@Override
	public Integer addEmployee(Employee employee) {
		return employeeDao.save(employee);
	}
	@Override
	public Employee selectEmployeeById(int id) {
		return employeeDao.selectById(id);
	}
	@Override
	public Integer updateEmployee(Employee employee) {
		return employeeDao.update(employee);
	}
	@Override
	public Integer delEmployee(int id) {
		return employeeDao.delById(id);
	}
	@Override
	public List<Job> selectAllJob() {
		
		return jobDao.selectAllJob();
	}
	@Override
	public List<Dept> selectAllDept() {
		return deptDao.selectAllDept();
	}
	@Override
	public Integer addJob(Job job) {
		return jobDao.save(job);
	}
	@Override
	public Integer removeJob(int id) {
		return jobDao.delById(id);
	}
	@Override
	public Integer updateJob(Job job) {
		return jobDao.update(job);
	}
	@Override
	public Job selectJobById(int id) {
		return jobDao.selectById(id);
	}
	@Override
	public List<Job> selectJob(Job job, PageModel pageModel) {
		int count = jobDao.count(job);
		//有记录则为第一次默认加载时，设置当前页尾第一页
		if ((count > 0) && (pageModel.getPageIndex() == 0)) {
			pageModel.setPageIndex(1);
		}			
		pageModel.setRecordCount(count);
		Map<String, Object> params = new HashMap<>();
		params.put("job", job);
		params.put("pageModel", pageModel);
		
		return jobDao.selectByPage(params);
	}
	@Override
	public List<Notice> selectNotice(Notice notice, PageModel pageModel) {
		int count = noticeDao.count(notice);
		//有记录则为第一次默认加载时，设置当前页尾第一页
		if ((count > 0) && (pageModel.getPageIndex() == 0)) {
			pageModel.setPageIndex(1);
		}			
		pageModel.setRecordCount(count);
		Map<String, Object> params = new HashMap<>();
		params.put("notice", notice);
		params.put("pageModel", pageModel);
		
		return noticeDao.selectByPage(params);
	}
	@Override
	public Notice selectNoticeById(Integer id) {
		return noticeDao.selectById(id);
	}
	@Override
	public Integer updateNotice(Notice notice) {
		return noticeDao.update(notice);
	}
	@Override
	public Integer removeNotice(Integer id) {
		return noticeDao.delById(id);		
	}
	@Override
	public Integer addNotice(Notice notice) {
		return noticeDao.save(notice);
	}
	@Override
	public List<Document> selectDocument(Document document, PageModel pageModel) {
		int count = documentDao.count(document);
		//有记录则为第一次默认加载时，设置当前页尾第一页
		if ((count > 0) && (pageModel.getPageIndex() == 0)) {
			pageModel.setPageIndex(1);
		}			
		pageModel.setRecordCount(count);
		Map<String, Object> params = new HashMap<>();
		params.put("document", document);
		params.put("pageModel", pageModel);
		
		return documentDao.selectByPage(params);
	}
	@Override
	public Document selectDocumentById(Integer id) {
		return documentDao.selectById(id);
	}
	@Override
	public Integer updateDocument(Document document) {
		return documentDao.update(document);
	}
	@Override
	public Integer removeDocument(Integer id) {
		return documentDao.delById(id);
	}
	@Override
	public Integer addDocument(Document document) {
		return documentDao.save(document);
	}

}
