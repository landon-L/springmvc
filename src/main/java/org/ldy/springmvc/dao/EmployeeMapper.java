package org.ldy.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.ldy.springmvc.model.Employee;
import org.mybatis.spring.annotation.MapperScan;
@MapperScan
public interface EmployeeMapper {
	Employee selectById(Integer id);
	//这里增、改、删，都返回一个int表示该操作的结果
	int save(Employee employee);
	int update(Employee employee);
	int delById(Integer id);
	List<Employee> selectByPage(Map<String, Object> params);
	int count(Employee employee);
}
