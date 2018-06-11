package com.nevernote.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Notebook {

	private Long id;
	private String name;

	private Map<Long, Note> noteMap = new HashMap<Long, Note>();
	private Map<String, List<Note>> tagMap = new HashMap<String, List<Note>>();

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
			String tagData = tag.trim();
			if (!tagMap.containsKey(tagData)) {
				List<Note> list = new ArrayList<Note>();
				list.add(note);
				tagMap.put(tagData, list);
			} else {
				List<Note> list = tagMap.get(tagData);
				list.add(note);
			}

		}

	}

	public void updateNote(Note note) {

		deleteNote(note.getId());
		addNote(note);
	}

	public List<Note> searchNote(String tag) {

		return tagMap.get(tag);

	}

	public List<Note> getNotes() {
		return new ArrayList<Note>(noteMap.values());

	}

	public void deleteNote(Long id) {
		Note delNote = noteMap.remove(id);
		// remove from list of associated tags
		String[] tagArr = delNote.getTags();
		if (tagArr == null)
			return;

		removeFomMappedTag(delNote);

	}

	private void removeFomMappedTag(Note note) {
		Set<String> tagSet = tagMap.keySet();
		for (String tag : tagSet) {
			List<Note> noteList = tagMap.get(tag);
			noteList.remove(note);
		}

	}

}
