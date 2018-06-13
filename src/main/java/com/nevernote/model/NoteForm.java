package com.nevernote.model;

import java.util.Arrays;

public class NoteForm {

	private String title;
	private String body;
	private String[] tags;

	public NoteForm() {
	}

	public NoteForm(String title, String body, String[] tags) {
		super();
		this.title = title;
		this.body = body;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "NoteForm [title=" + title + ", body=" + body + ", tags=" + Arrays.toString(tags) + "]";
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

}
