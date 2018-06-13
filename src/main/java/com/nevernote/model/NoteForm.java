package com.nevernote.model;

import java.util.List;

public class NoteForm {

	private String title;
	private String body;
	private List<String> tags;

	public NoteForm() {
	}

	public NoteForm(String title, String body, List<String> tags) {
		super();
		this.title = title;
		this.body = body;
		this.tags = tags;
	}

	

	@Override
	public String toString() {
		return "NoteForm [title=" + title + ", body=" + body + ", tags=" + tags + "]";
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

}
