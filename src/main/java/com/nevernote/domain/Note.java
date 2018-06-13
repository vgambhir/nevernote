package com.nevernote.domain;

import java.util.List;
import java.util.Objects;

public class Note {

	private Long id;
	private String title;
	private String body;
	private List<String> tags;
	private Long createdDate;
	private Long lastModifiedDate;

	public Note(Long id, String title, String body, List<String> tags, Long createdDate, Long lastModifiedDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public Note() {
	}

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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public Long getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Note) {
			Note other = (Note) obj;
			return Objects.equals(this.getId(), other.getId());
		}
		return false;
	}

}
