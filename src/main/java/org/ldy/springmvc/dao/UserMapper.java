package org.ldy.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ldy.springmvc.model.User;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserMapper {
	//根据用户名和密码来查询用户 Map params
	User selectByLoginNameAndPassword(@Param("loginname")String loginname, @Param("password")String password);
	//根据id来查询用户
	User selectUserById(Integer id);
	//根据id删除用户
	int delUserById(Integer id);
	//动态修改用户
	int updateUser(User user);
	
	/**
	 * 根据用户名和用户状态进行分页查询
	 * @param params 包含三个参数，一个username，一个status，还有一个pageModel对象
	 * @return
	 */
	List<User> selectUser(Map<String,Object> params);
	//根据参数查询用户总数
	Integer selectUserCount(User user);
	//新增用户
	int saveUser(User user);
	 
}
