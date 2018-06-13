package com.nevernote.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notebook {

	private Long id;
	private String name;

	private Map<Long, Note> noteMap = new HashMap<Long, Note>();
	private Map<String, Set<Note>> tagMap = new HashMap<String, Set<Note>>();

	public Notebook(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addNote(Note note) {
		noteMap.put(note.getId(), note);
		List<String> tagList = note.getTags();
		if (tagList == null)
			return;

		for (String tag : tagList) {
			tag = tag.trim().toLowerCase();
			Set<Note> set = tagMap.get(tag);
			if (set == null) {
				set = new HashSet<Note>();
				tagMap.put(tag, set);
			}

			set.add(note);
		}
	}

	public Set<Note> findNotesWithTag(String tag) {
		return tagMap.get(tag.trim().toLowerCase());
	}

	public void updateNote(Note note) {
		deleteNote(note.getId());
		addNote(note);
	}

	public Set<Note> searchNotesByTag(String tag) {
		return tagMap.get(tag);
	}

	public List<Note> getNotes() {
		return new ArrayList<Note>(noteMap.values());

	}

	public void deleteNote(Long id) {
		Note delNote = noteMap.remove(id);
		// remove from tag to notes map
		removeFromMappedTag(delNote);

	}

	private void removeFromMappedTag(Note note) {
		List<String> tagList = note.getTags();
		if (tagList != null) {
			for (String tag : tagList) {
				tagMap.get(tag).remove(note);
			}
		}
	}

	public Note getNoteById(Long noteId) {
		return noteMap.get(noteId);

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
		if (obj instanceof Notebook) {
			Notebook other = (Notebook) obj;
			return Objects.equals(this.getId(), other.getId());
		}
		return false;
	}
}
