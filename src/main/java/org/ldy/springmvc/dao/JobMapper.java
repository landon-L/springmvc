package org.ldy.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.ldy.springmvc.model.Job;
import org.mybatis.spring.annotation.MapperScan;
@MapperScan
public interface JobMapper {
	Job selectById(int id);
	List<Job> selectAllJob();
	//这里增、改、删，都返回一个int表示该操作的结果
	int save(Job job);
	int update(Job job);
	int delById(int id);
	List<Job> selectByPage(Map<String, Object> params);
	int count(Job job);
}
