package com.nevernote.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevernote.data.DataStore;
import com.nevernote.domain.Notebook;
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

	public NotebookDetail findById(Long id) {

		Notebook book = dataStore.findbyId(id);

		if (book == null) {
			LOG.info("Notebook with id {} not found", id);
			return null;
		}
		return new NotebookDetail(book.getId(), book.getName(), book.getNotes());

	}
	
	public void deleteById(Long id) {
		dataStore.deleteById(id);

}

}
