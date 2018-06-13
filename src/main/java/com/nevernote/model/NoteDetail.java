package com.nevernote.model;

public class NoteDetail {

	private Long id;
	public NoteDetail(Long id, String title, String body, String[] tags, long createDate, long lastModifiedDate) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public NoteDetail() {}
	private String title;
	private String body;
	private String[] tags;
	private long createDate;
	private long lastModifiedDate;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	public long getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	
	

}
