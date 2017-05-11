package springmvc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldy.springmvc.dao.DeptMapper;
import org.ldy.springmvc.dao.UserMapper;
import org.ldy.springmvc.model.Dept;
import org.ldy.springmvc.model.User;
import org.ldy.springmvc.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext.xml")
public class MapperTest {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private DeptMapper deptMapper;
	
	@Test
	public void selectByLoginNameAndPasswordTest()
	{
		//构造查询参数
		try
		{
			HashMap<String,Object> params = new HashMap<String, Object>();
			params.put("loginname", "admin");
			params.put("password", "123456");
			
			//User user = userMapper.selectByLoginNameAndPassword(params);
			//System.out.println(user);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}		
	}
	
	@Test
	public void selectByIdTest()
	{
		//构造查询参数
		try
		{						
			//User user = userMapper.selectById(2);
			//System.out.println(user);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}		
	}
	
	//TODO 为什么查询不到呢：目前分析认为，username存储内容为汉字，在进行测试时候没有制定数据传输的编码方式，如果以web的方式运行，应该不会出现问题，因为web.xml中已经指定了编码方式
	@Test
	public void selectUserTest()
	{
		//构造查询参数
		try
		{	
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("username", "普通管理员");
			params.put("loginname", "i");
			params.put("status", 1);
			
			PageModel pageModel =new PageModel();
			//pageModel.firstLimitParam = 0;
			//pageModel.pageSize = 5;
			params.put("pageModel", pageModel);
			List<User> userList = userMapper.selectUser(params);
			System.out.println(userList);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}		
	}
	
	@Test
	public void saveUserTest()
	{
		//构造查询参数
		try
		{	
			User user = new User();
			Date date = new Date(System.currentTimeMillis());
			user.setCreateDate(date);
			user.setLoginName("jinhe");
			user.setPassword("");
			user.setStatus(1);
			user.setUserName("超级管理员");
			userMapper.saveUser(user);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}		
	}
	
	@Test
	public void selectWithParamsForDeptTest()
	{
		try
		{
			HashMap<String,Object> params = new HashMap<String,Object>();
			/*params.put("username", "普通管理员");
			params.put("loginname", "i");
			params.put("status", 1);*/
			
			PageModel pageModel =new PageModel();
			//pageModel.firstLimitParam = 0;
			//pageModel.pageSize = 5;
			params.put("pageModel", pageModel);
			List<Dept> deptList = deptMapper.selectByPage(params);
			System.out.println(deptList);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}
	}
}
