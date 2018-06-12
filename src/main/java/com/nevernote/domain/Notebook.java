package com.nevernote.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notebook {

	private Long id;
	private String name;

	private Map<Long, Note> noteMap = new HashMap<Long, Note>();
	private Map<String, List<Note>> tagMap = new HashMap<String, List<Note>>();

	public Notebook(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addNote(Note note) {
		noteMap.put(note.getId(), note);
		String[] tagArr = note.getTags();
		if (tagArr == null)
			return;

		for (String tag : tagArr) {
			tag = tag.trim().toLowerCase();
			List<Note> list = tagMap.get(tag);
			if (list == null) {
				list = new ArrayList<Note>();
				tagMap.put(tag, list);
			}

			list.add(note);

		}

	}

	public List<Note> findNotesWithTag(String tag) {
		return tagMap.get(tag.trim().toLowerCase());

	}

	public void updateNote(Note note) {
		deleteNote(note.getId());
		addNote(note);
	}

	public List<Note> searchNotesByTag(String tag) {

		return tagMap.get(tag);

	}

	public List<Note> getNotes() {
		return new ArrayList<Note>(noteMap.values());

	}

	public void deleteNote(Long id) {
		Note delNote = noteMap.remove(id);
		// remove from list of associated tags
		removeFromMappedTag(delNote);

	}

	private void removeFromMappedTag(Note note) {
		String[] tagArr = note.getTags();
		if (tagArr != null) {
			for (String tag : tagArr) {
				List<Note> noteList = tagMap.get(tag);
				noteList.remove(note);
			}

		}

	}

	public Note getNoteById(Long noteId) {
		return noteMap.get(noteId);

	}

}
