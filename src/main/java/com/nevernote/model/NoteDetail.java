package com.nevernote.model;

import java.util.List;

import com.nevernote.domain.Note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoteDetail {

	private Long id;
	private String title;
	private String body;
	private List<String> tags;
	private long createDate;
	private long lastModifiedDate;

	public NoteDetail(Long id, String title, String body, List<String> tags, long createDate, long lastModifiedDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public NoteDetail(Note note) {
		this.id = note.getId();
		this.title = note.getTitle();
		this.body = note.getBody();
		this.tags = note.getTags();
		this.createDate = note.getCreatedDate();
		this.lastModifiedDate = note.getLastModifiedDate();
	}

}
