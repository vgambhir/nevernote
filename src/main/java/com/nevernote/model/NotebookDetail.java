package com.nevernote.model;

import java.util.List;

import com.nevernote.domain.Note;
import com.nevernote.domain.Notebook;

public class NotebookDetail {

	private Long id;

	private String name;
	private List<Note> notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public NotebookDetail() {
	}

	public NotebookDetail(Long id, String name, List<Note> notes) {
		this.id = id;
		this.name = name;
		this.notes = notes;
	}

	public NotebookDetail(Notebook book) {
		this.id = book.getId();
		this.name = book.getName();
		this.notes = book.getNotes();

	}
}
