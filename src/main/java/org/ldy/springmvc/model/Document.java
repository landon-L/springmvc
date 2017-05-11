package org.ldy.springmvc.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/*
 * 文档类
 */
public class Document {
	private Integer id;
	private String title;  //标题
	private String fileName;//文件名
	
	//TODO 需要注意的地方，是否可以用二进制流的方式来替代
	private MultipartFile file;//文件
	private String remark;//文件描述
	private Date createDate;//上传日期
	private User user;//上传人
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
	
}
