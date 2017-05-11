package org.ldy.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.ldy.springmvc.dao.annocation.DeptDynaSqlProvider;
import org.ldy.springmvc.model.Dept;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 尝试使用注解的方式来进行动态sql的编写，这样在遇到一些复杂sql时候能够更方便的处理
 * @author 李东洋
 *
 */
@MapperScan
public interface DeptMapper {
	//分页查询部分
	@SelectProvider(type=DeptDynaSqlProvider.class,method="selectWithParams")
	List<Dept> selectByPage(Map<String,Object> params);
	@SelectProvider(type=DeptDynaSqlProvider.class,method="count")
	Integer count(Dept dept);
	//这里不需要配置返回值类型？？
	@Select("select * from dept_inf order by id desc")
	List<Dept> selectAllDept();
	@Select("select * from dept_inf where id = #{id}")
	Dept selectById(int id);
	@Delete("delete from dept_inf where id = #{id}")
	int delById(int id);
	
	@SelectProvider(type=DeptDynaSqlProvider.class,method="insertDept")
	void save(Dept dept);
	@SelectProvider(type=DeptDynaSqlProvider.class,method="updateDept")
	void update(Dept dept);
}
