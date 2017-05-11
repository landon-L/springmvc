package org.ldy.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.ldy.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserDao用来处理和用户相关的数据库层操作
 * @author Administrator
 *
 */
@Component
public class UserDao {
	@Autowired
	private UserMapper userMapper;
	//private Map<String,Object> params = new HashMap<String ,Object>();
	public User login(String loginname, String password)
	{	
		//params.put("loginname", loginname);
		//params.put("password", password);
		//return userMapper.selectByLoginNameAndPassword(params);
		return userMapper.selectByLoginNameAndPassword(loginname, password);
	}

	public int count(User user) {		
		return userMapper.selectUserCount(user);
	}

	public List<User> getPage(Map<String, Object> params) {
		return userMapper.selectUser(params);
	}
	
	public User selectUserById(int id)
	{
		return userMapper.selectUserById(id);
	}
	
	public int updateUser(User user)
	{
		return userMapper.updateUser(user);
	}

	public int removeUser(Integer id) {		
		return userMapper.delUserById(id);
	}

	public int addUser(User user) {		
		return userMapper.saveUser(user);
	}
}
