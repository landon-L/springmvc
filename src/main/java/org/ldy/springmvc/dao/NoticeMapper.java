package org.ldy.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.ldy.springmvc.model.Job;
import org.ldy.springmvc.model.Notice;

public interface NoticeMapper {
	Notice selectById(Integer id);
	List<Notice> selectAllNotice();
	//这里增、改、删，都返回一个int表示该操作的结果
	Integer save(Notice notice);
	Integer update(Notice notice);
	Integer delById(Integer id);
	List<Notice> selectByPage(Map<String, Object> params);
	Integer count(Notice notice);
}