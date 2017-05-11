package org.ldy.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.ldy.springmvc.model.Document;

public interface DocumentMapper {
	Document selectById(Integer id);
	List<Document> selectAllDocument();
	//这里增、改、删，都返回一个int表示该操作的结果
	Integer save(Document document);
	Integer update(Document document);
	Integer delById(Integer id);
	List<Document> selectByPage(Map<String, Object> params);
	Integer count(Document document);
}