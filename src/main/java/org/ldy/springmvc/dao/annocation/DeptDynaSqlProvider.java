package org.ldy.springmvc.dao.annocation;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.ldy.springmvc.model.Dept;
public class DeptDynaSqlProvider {
	//分页动态查询
	public String selectWithParams(Map<String,Object> params)
	{
		//TODO 了解SQL关键字的实现原理，函数什么意思
		String sql = new SQL(){
			{
				SELECT("*");
				FROM("dept_inf");
				if(params.get("dept") != null){
					Dept dept = (Dept)params.get("dept");
					if(dept.getName() != null && !dept.getName().equals("")){
						WHERE("  name LIKE CONCAT ('%',#{dept.name},'%') ");
					}
				}
			}

		}.toString();
		
		if(params.get("pageModel") != null)
		{
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	/**
	 * 动态查询满足查询参数的总数量
	 * @param params
	 * @return
	 */
	public String count(Dept dept)
	{
		String sql = new SQL(){
			{
				SELECT("count(*)");
				FROM("dept_inf");				
				if(dept.getName() != null && !dept.getName().equals("")){
					WHERE("  name LIKE CONCAT ('%',#{name},'%') ");
				}				
			}
		}.toString();
		return sql;
	}
	
	/**
	 * 动态插入
	 * @param dept
	 * @return
	 */
	public String insertDept(Dept dept)
	{
		String sql = new SQL(){
			{
				INSERT_INTO("dept_inf");
				if(dept.getName() != null && !dept.getName().equals(""))
				{
					VALUES("name","#{name}");
				}
				if(dept.getRemark() != null && !dept.getRemark().equals(""))
				{
					VALUES("remark","#{remark}");
				}
			}
		}.toString();
		return sql;
	}
	
	/**
	 * 动态更新
	 */
	public String updateDept(Dept dept)
	{
		//TODO 此处需要留意set后的空格，以及set 语句需要口号","，这里源码是如何处理的
		return new SQL(){
			{
				UPDATE("dept_inf");
				if(dept.getName() != null)
				{
					SET(" name = #{name}");
				}
				if(dept.getRemark() != null)
				{
					SET(" remark = #{remark}");
				}
				WHERE(" id = #{id}");
			}
		}.toString();
	}
}
