package org.ldy.springmvc.model;

import java.util.Date;

/*
 * 通知类，每个员工都可以发布通知
 */
public class Notice {
	private Integer id;
	private String title;  //标题
	private String content;//通知内容
	private Date createDate;//发布日期
	private User user;//发布通知人
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
