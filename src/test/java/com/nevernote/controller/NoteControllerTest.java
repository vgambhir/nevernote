package com.nevernote.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import java.util.Arrays;

import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.nevernote.domain.Note;
import com.nevernote.model.NoteDetail;
import com.nevernote.model.NoteForm;
import com.nevernote.model.NotebookDetail;
import com.nevernote.model.NotebookForm;
import com.nevernote.service.NotebookService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class NoteControllerTest {
	@Autowired
	private NotebookService noteBookSvc;
	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(NoteControllerTest.class);
	private static final String NOTEBOOK_API = "/notebooks";
	private static final String NOTE_API = "/notes";

	@Test
	public void createNote() {
		String name = "NB-3";
		NotebookDetail nDetail = noteBookSvc.create(new NotebookForm(name));
		NoteForm form = new NoteForm("title-1", "body-1", Arrays.asList("t1", "t2"));
		LOG.info("Request : {}  to " + NOTEBOOK_API + " createNote", form);
		ResponseEntity<NoteDetail> responseEntity = restTemplate.postForEntity(NOTEBOOK_API + "/{bId}" + NOTE_API, form,
				NoteDetail.class, nDetail.getId());
		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		NoteDetail resp = responseEntity.getBody();
		assertThat(resp.getTitle(), is(form.getTitle()));
		assertThat(resp.getBody(), is(form.getBody()));
		assertThat(resp.getTags(), hasItems("t1", "t2"));

	}

	@Test
	public void getNoteWithId() {
		String name = "NB-4";
		NotebookDetail bDetail = noteBookSvc.create(new NotebookForm(name));
		NoteForm form = new NoteForm("title-1", "body-1", Arrays.asList("t1", "t2"));
		NoteDetail nDetail = noteBookSvc.addNote(bDetail.getId(), form);

		LOG.info("Request : {}  to " + NOTEBOOK_API + " getNoteWithId", form);
		ResponseEntity<NoteDetail> responseEntity = restTemplate.getForEntity(
				NOTEBOOK_API + "/{bId}" + NOTE_API + "/{nId}", NoteDetail.class, bDetail.getId(), nDetail.getId());
		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		NoteDetail resp = responseEntity.getBody();
		assertThat(resp.getTitle(), is(form.getTitle()));
		assertThat(resp.getBody(), is(form.getBody()));
		assertThat(resp.getTags(), hasItems("t1", "t2"));

	}

	@Test
	public void deletNoteWithId() {
		String name = "NB-5";
		NotebookDetail bDetail = noteBookSvc.create(new NotebookForm(name));
		NoteForm form = new NoteForm("title-1", "body-1", Arrays.asList("t1", "t2"));
		NoteDetail nDetail = noteBookSvc.addNote(bDetail.getId(), form);

		LOG.info("Request : {}  to " + NOTEBOOK_API + " createNote", form);

		ResponseEntity<Void> responseEntity = restTemplate.exchange(NOTEBOOK_API + "/{bid}" + NOTE_API + "/{nId}",
				HttpMethod.DELETE, null, Void.class, bDetail.getId(), nDetail.getId());
		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		assertThat(noteBookSvc.findNoteById(bDetail.getId(), nDetail.getId()), IsNull.nullValue());

	}

	@Test
	public void updateNote() {
		String name = "NB-6";
		NotebookDetail bDetail = noteBookSvc.create(new NotebookForm(name));
		NoteForm form = new NoteForm("title-1", "body-1", Arrays.asList("t1", "t2"));
		NoteDetail nDetail = noteBookSvc.addNote(bDetail.getId(), form);

		NoteForm updNote = new NoteForm("title-2", "body-2", Arrays.asList("t9", "t10"));

		LOG.info("Request : {}  to " + NOTEBOOK_API + " createNote", form);
		ResponseEntity<NoteDetail> responseEntity = restTemplate.postForEntity(
				NOTEBOOK_API + "/{bId}" + NOTE_API + "/{nId}", updNote, NoteDetail.class, bDetail.getId(),
				nDetail.getId());
		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		NoteDetail resp = responseEntity.getBody();
		assertThat(resp.getTitle(), is(updNote.getTitle()));
		assertThat(resp.getBody(), is(updNote.getBody()));
		assertThat(resp.getTags(), hasItems("t9", "t10"));

	}

	@Test
	public void searchNoteWithTag_NoMatching() {
		String name = "NB-9";
		NotebookDetail bDetail = noteBookSvc.create(new NotebookForm(name));
		NoteForm form_1 = new NoteForm("title-1", "body-1", Arrays.asList("t1", "t2"));
		noteBookSvc.addNote(bDetail.getId(), form_1);
		NoteForm form_2 = new NoteForm("title-2", "body-2", Arrays.asList("t3", "t2"));
		noteBookSvc.addNote(bDetail.getId(), form_2);

		LOG.info("Request : {} ,{} to " + NOTEBOOK_API + " searchNote", form_1, form_2);

		ResponseEntity<NotebookDetail> responseEntity = restTemplate.getForEntity(
				NOTEBOOK_API + "/{bId}" + NOTE_API + "?tag={tag}", NotebookDetail.class, bDetail.getId(), "a");

		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		NotebookDetail resp = responseEntity.getBody();
		assertThat(resp.getId(), is(bDetail.getId()));
		assertThat(resp.getName(), is(bDetail.getName()));
		assertThat(resp.getNotes().size(), is(0));

	}

	@Test
	public void searchNoteWithTag_Matching() {
		String name = "NB-9";
		NotebookDetail bDetail = noteBookSvc.create(new NotebookForm(name));
		NoteForm form_1 = new NoteForm("title-1", "body-1", Arrays.asList("t1", "t2"));
		NoteDetail nDetail_1 = noteBookSvc.addNote(bDetail.getId(), form_1);
		NoteForm form_2 = new NoteForm("title-2", "body-2", Arrays.asList("t3", "t2"));
		NoteDetail nDetail_2 = noteBookSvc.addNote(bDetail.getId(), form_2);

		LOG.info("Request : {} ,{} to " + NOTEBOOK_API + " searchNote", form_1, form_2);

		ResponseEntity<NotebookDetail> responseEntity = restTemplate.getForEntity(
				NOTEBOOK_API + "/{bId}" + NOTE_API + "?tag={tag}", NotebookDetail.class, bDetail.getId(), "t2");

		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		NotebookDetail resp = responseEntity.getBody();
		assertThat(resp.getId(), is(bDetail.getId()));
		assertThat(resp.getName(), is(bDetail.getName()));
		assertThat(resp.getNotes().size(), is(2));
		assertThat(resp.getNotes().contains(new Note(nDetail_1.getId(), null, null, null, null, null)), is(true));
		assertThat(resp.getNotes().contains(new Note(nDetail_2.getId(), null, null, null, null, null)), is(true));
	}

}
