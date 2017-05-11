package org.ldy.springmvc.service;

import java.util.List;

import org.ldy.springmvc.model.Dept;
import org.ldy.springmvc.model.Document;
import org.ldy.springmvc.model.Employee;
import org.ldy.springmvc.model.Job;
import org.ldy.springmvc.model.Notice;
import org.ldy.springmvc.model.User;
import org.ldy.springmvc.util.PageModel;

public interface HrmService {
	User login(String loginname,String password);

	List<User> selectUser(User user, PageModel pageModel);

	User selectUserById(Integer id);

	Integer updateUser(User user);

	Integer removeUser(Integer id);

	Integer addUser(User user);

	List<Dept> selectDept(Dept dept, PageModel pageModel);

	void addDept(Dept dept);

	Dept selectDeptById(Integer id);

	void updateDept(Dept dept);

	Integer removeDept(Integer id);

	List<Employee> selectEmployee(Employee employee, PageModel pageModel);

	Integer addEmployee(Employee employee);

	Employee selectEmployeeById(int id);

	Integer updateEmployee(Employee employee);

	Integer delEmployee(int id);

	List<Job> selectAllJob();

	List<Dept> selectAllDept();

	Integer addJob(Job job);

	Integer removeJob(int parseInt);

	Integer updateJob(Job job);

	Job selectJobById(int id);

	List<Job> selectJob(Job job, PageModel pageModel);

	List<Notice> selectNotice(Notice notice, PageModel pageModel);

	Notice selectNoticeById(Integer id);

	Integer updateNotice(Notice notice);

	Integer removeNotice(Integer parseInt);

	Integer addNotice(Notice notice);

	List<Document> selectDocument(Document document, PageModel pageModel);

	Document selectDocumentById(Integer id);

	Integer updateDocument(Document document);

	Integer removeDocument(Integer parseInt);

	Integer addDocument(Document document);


}
