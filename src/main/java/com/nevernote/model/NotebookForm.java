package com.nevernote.model;

public class NotebookForm {

	private String name;

	public NotebookForm() {
	}

	public NotebookForm(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "NotebookForm [name=" + name + "]";
	}

}
