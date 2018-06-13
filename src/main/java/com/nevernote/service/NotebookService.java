package com.nevernote.service;

import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevernote.domain.Note;
import com.nevernote.domain.Notebook;
import com.nevernote.exception.ResourceNotFoundException;
import com.nevernote.model.NoteDetail;
import com.nevernote.model.NoteForm;
import com.nevernote.model.NotebookDetail;
import com.nevernote.model.NotebookForm;
import com.nevernote.store.DataStore;

/**
 * Provides service layer
 * 
 * @author vandana
 *
 */
@Service
public class NotebookService {

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private DataStore dataStore;
	private final Logger LOG = LoggerFactory.getLogger(NotebookService.class);

	public NotebookDetail create(NotebookForm bookForm) {
		Notebook book = new Notebook(idGenerator.getNext(), bookForm.getName());
		dataStore.save(book);
		return new NotebookDetail(book);

	}

	public NotebookDetail findById(Long notebookId) {

		Notebook book = dataStore.findbyId(notebookId);

		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			throw new ResourceNotFoundException("No Notebook found with id = " + notebookId);
		}
		return new NotebookDetail(book);

	}

	public void deleteById(Long id) {
		dataStore.deleteById(id);
	}

	public NoteDetail addNote(Long notebookId, NoteForm noteForm) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			throw new ResourceNotFoundException("No Notebook found with id = " + notebookId);
		}
		long currTime = System.currentTimeMillis();
		Note note = new Note(idGenerator.getNext(), noteForm.getTitle(), noteForm.getBody(), noteForm.getTags(),
				currTime, currTime);
		book.addNote(note);
		return new NoteDetail(note);
	}

	public NoteDetail findNoteById(Long notebookId, Long noteId) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			throw new ResourceNotFoundException("No Notebook found with id = " + notebookId);
		}

		Note note = book.getNoteById(noteId);
		if (note == null) {
			LOG.info("Note with id {} not found", noteId);
			throw new ResourceNotFoundException("No Note found with id = " + noteId);
		}

		return new NoteDetail(note);
	}

	public NoteDetail deleteNoteById(Long notebookId, Long noteId) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			throw new ResourceNotFoundException("No Notebook found with id = " + notebookId);
		}
		Note note = book.getNoteById(noteId);
		if (note == null) {
			LOG.info("Note with id {} not found", noteId);
			throw new ResourceNotFoundException("No Note found with id = " + noteId);
		}
		book.deleteNote(noteId);
		return new NoteDetail(note);
	}

	/**
	 * Update is being carried out by delete+create operations. Note might have
	 * multiple attributes. Instead of figuring out the changes and updating
	 * existing note, this is a simpler approach
	 * 
	 * @param notebookId
	 * @param noteId
	 * @param noteForm
	 * @return
	 */
	public NoteDetail updateNote(Long notebookId, Long noteId, NoteForm noteForm) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			throw new ResourceNotFoundException("No Notebook found with id = " + notebookId);
		}
		Note currNote = book.getNoteById(noteId);
		if (currNote == null) {
			LOG.info("Note with id {} not found", noteId);
			throw new ResourceNotFoundException("No Note found with id = " + noteId);
		}
		Note newNote = new Note(noteId, noteForm.getTitle(), noteForm.getBody(), noteForm.getTags(),
				currNote.getCreatedDate(), System.currentTimeMillis());
		book.updateNote(newNote);
		return new NoteDetail(newNote);
	}

	public NotebookDetail findNotesWith(Long notebookId, String tag) {

		Notebook book = dataStore.findbyId(notebookId);
		if (book == null) {
			LOG.info("Notebook with id {} not found", notebookId);
			throw new ResourceNotFoundException("No Notebook found with id = " + notebookId);
		}

		Set<Note> noteSet = book.findNotesWithTag(tag);

		if (noteSet == null)
			return new NotebookDetail(book.getId(), book.getName(), new ArrayList<Note>());

		return new NotebookDetail(book.getId(), book.getName(), new ArrayList<Note>(noteSet));

	}

}
