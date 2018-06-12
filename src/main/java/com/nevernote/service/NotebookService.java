package com.nevernote.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevernote.data.DataStore;
import com.nevernote.domain.Note;
import com.nevernote.domain.Notebook;
import com.nevernote.model.NoteDetail;
import com.nevernote.model.NoteForm;
import com.nevernote.model.NotebookDetail;
import com.nevernote.model.NotebookForm;

@Service
public class NotebookService {

	@Autowired
	private CounterGen gen;

	@Autowired
	private DataStore dataStore;
	private final Logger LOG = LoggerFactory.getLogger(NotebookService.class);

	public NotebookDetail create(NotebookForm bookForm) {
		Notebook book = new Notebook(gen.getNext(), bookForm.getName());
		book = dataStore.save(book);
		return new NotebookDetail(book.getId(), book.getName(), book.getNotes());

	}

	public NotebookDetail findById(Long notebookId) {

		Notebook book = dataStore.findbyId(notebookId);

		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			return null;
		}
		return new NotebookDetail(book.getId(), book.getName(), book.getNotes());

	}

	public void deleteById(Long id) {
		dataStore.deleteById(id);

	}

	public NoteDetail addNote(Long notebookId, NoteForm noteForm) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			return null;
		}
		long currTime = System.currentTimeMillis();
		Note note = new Note(gen.getNext(), noteForm.getTitle(), noteForm.getBody(), noteForm.getTags(), currTime,
				currTime);
		book.addNote(note);
		return new NoteDetail(note.getId(), note.getTitle(), note.getBody(), note.getTags(), note.getCreatedDate(),
				note.getLastModifiedDate());
	}

	public NoteDetail findNoteById(Long notebookId, Long noteId) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			return null;
		}

		Note note = book.getNoteById(noteId);
		if (note == null) {
			LOG.info("Note with id {} not found", noteId);
			return null;
		}

		return new NoteDetail(note.getId(), note.getTitle(), note.getBody(), note.getTags(), note.getCreatedDate(),
				note.getLastModifiedDate());
	}

	public NoteDetail deleteNoteById(Long notebookId, Long noteId) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			return null;
		}
		Note note = book.getNoteById(noteId);
		if (note == null) {
			LOG.info("Note with id {} not found", noteId);
			return null;
		}
		book.deleteNote(noteId);

		return new NoteDetail(note.getId(), note.getTitle(), note.getBody(), note.getTags(), note.getCreatedDate(),
				note.getLastModifiedDate());
	}

	public NoteDetail updateNote(Long notebookId, Long noteId, NoteForm noteForm) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			return null;
		}
		Note currNote = book.getNoteById(noteId);
		if (currNote == null) {
			LOG.info("Note with id {} not found", noteId);
			return null;
		}
		Note newNote = new Note(noteId, noteForm.getTitle(), noteForm.getBody(), noteForm.getTags(),
				currNote.getCreatedDate(), System.currentTimeMillis());
		book.updateNote(newNote);

		return new NoteDetail(newNote.getId(), newNote.getTitle(), newNote.getBody(), newNote.getTags(),
				newNote.getCreatedDate(), newNote.getLastModifiedDate());
	}

	public List<NoteDetail> findNotesWith(Long notebookId, String tag) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			return null;
		}

		List<Note> noteList = book.findNotesWithTag(tag);

		if (noteList == null)
			return new ArrayList<NoteDetail>();

		return mapToNoteDetail(noteList);

	}

	private List<NoteDetail> mapToNoteDetail(List<Note> noteList) {
		List<NoteDetail> nDetailList = new ArrayList<NoteDetail>();
		for (Note n : noteList) {
			nDetailList.add(new NoteDetail(n.getId(), n.getTitle(), n.getBody(), n.getTags(), n.getCreatedDate(),
					n.getLastModifiedDate()));
		}
		return nDetailList;

	}

}
