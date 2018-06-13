package com.nevernote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.nevernote.model.NoteDetail;
import com.nevernote.model.NoteForm;
import com.nevernote.model.NotebookDetail;
import com.nevernote.service.NotebookService;

@RestController
@RequestMapping("notebooks")
public class NoteController {

	@Autowired
	private NotebookService noteBookSvc;
	private final Logger LOG = LoggerFactory.getLogger(NoteController.class);

	@PostMapping(value = "/{notebookId}/notes", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NoteDetail> createNote(@PathVariable("notebookId") Long notebookId,
			@RequestBody NoteForm noteForm, UriComponentsBuilder ucBuilder) {
		LOG.info("Creating new note: {}", noteForm);

		// find notebook exists or not find by notebook id
		NoteDetail noteDetail = noteBookSvc.addNote(notebookId, noteForm);
		if (noteDetail == null) {
			LOG.info("Unable to create. Notebook with id {} not found", notebookId);
			return new ResponseEntity<NoteDetail>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				ucBuilder.path("/notebook/" + notebookId + "/notes/{id}").buildAndExpand(noteDetail.getId()).toUri());
		return new ResponseEntity<NoteDetail>(noteDetail, headers, HttpStatus.CREATED);
	}

	@GetMapping(value = "/{notebookId}/notes/{noteId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NoteDetail> getNoteBook(@PathVariable("notebookId") Long notebookId,
			@PathVariable("noteId") Long noteId) {

		NoteDetail n = noteBookSvc.findNoteById(notebookId, noteId);
		if (n == null) {
			LOG.info("Unable to get Notebook with id {}  or note wiht id {} ", notebookId, noteId);
			return new ResponseEntity<NoteDetail>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<NoteDetail>(n, HttpStatus.OK);

	}

	@DeleteMapping(value = "/{notebookId}/notes/{noteId}")
	public ResponseEntity<Void> deleteNoteBook(@PathVariable("notebookId") Long notebookId,
			@PathVariable("noteId") Long noteId) {

		LOG.info("deleting notebook with id: {}", noteId);
		NoteDetail ndto = noteBookSvc.deleteNoteById(notebookId, noteId);
		if (ndto == null) {
			LOG.info("Unable to delete. Note with id {} not found", noteId);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

	@PostMapping(value = "/{notebookId}/notes/{noteId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NoteDetail> updateNote(@PathVariable("notebookId") Long notebookId,
			@PathVariable("noteId") Long noteId, @RequestBody NoteForm noteForm) {
		NoteDetail ndto = noteBookSvc.updateNote(notebookId, noteId, noteForm);
		if (ndto == null) {
			LOG.info("Unable to delete. Note with id {} not found", noteId);
			return new ResponseEntity<NoteDetail>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<NoteDetail>(ndto, HttpStatus.OK);

	}

	@GetMapping(value = "/{notebookId}/notes", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NotebookDetail> getNotesWithTags(@PathVariable("notebookId") Long notebookId,
			@RequestParam("tag") String tag) {

		NotebookDetail notebookDetail = noteBookSvc.findNotesWith(notebookId, tag);
		if (notebookDetail == null) {
			LOG.info("Unable to get Notebook with id {}    ", notebookId);
			return new ResponseEntity<NotebookDetail>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<NotebookDetail>(notebookDetail, HttpStatus.OK);

	}

}
