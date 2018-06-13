package com.nevernote.model;

import java.util.List;

import com.nevernote.domain.Note;
import com.nevernote.domain.Notebook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotebookDetail {

	private Long id;
	private String name;
	private List<Note> notes;

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
