package com.nevernote.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoteForm {

	private String title;
	private String body;
	private List<String> tags;

	public NoteForm(String title, String body, List<String> tags) {
		this.title = title;
		this.body = body;
		this.tags = tags;
	}

}
