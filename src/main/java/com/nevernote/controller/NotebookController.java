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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.nevernote.model.NotebookDetail;
import com.nevernote.model.NotebookForm;
import com.nevernote.service.NotebookService;

@RestController
@RequestMapping("notebooks")
public class NotebookController {

	@Autowired
	private NotebookService noteBookSvc;

	private final Logger LOG = LoggerFactory.getLogger(NotebookController.class);

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NotebookDetail> createNoteBook(@RequestBody NotebookForm notebook,
			UriComponentsBuilder ucBuilder) {
		LOG.info("Creating new notebook: {}", notebook);

		NotebookDetail book = noteBookSvc.create(notebook);
		// set id in location
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/notebooks/{id}").buildAndExpand(book.getId()).toUri());
		return new ResponseEntity<NotebookDetail>(book, headers, HttpStatus.CREATED);

	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NotebookDetail> getNoteBook(@PathVariable("id") Long id) {
		LOG.info("Getting notebook with id: {}", id);

		NotebookDetail book = noteBookSvc.findById(id);

		if (book == null) {
			LOG.info("Notebook with id {} not found", id);
			return new ResponseEntity<NotebookDetail>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<NotebookDetail>(book, HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteNoteBook(@PathVariable("id") Long id) {

		LOG.info("Deleting notebook with id: {}", id);
		NotebookDetail book = noteBookSvc.findById(id);
		if (book == null) {
			LOG.info("Unable to delete. Notebook with id {} not found", id);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		noteBookSvc.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);// not ok....vgambhir

	}

}
