package com.nevernote.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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

import com.nevernote.exception.ErrorDetail;
import com.nevernote.exception.ResourceNotFoundException;
import com.nevernote.model.NotebookDetail;
import com.nevernote.model.NotebookForm;
import com.nevernote.service.NotebookService;
/**
 * Controller level test
 * @author vandana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class NotebookControllerTest {
	@Autowired
	private NotebookService noteBookSvc;
	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(NotebookControllerTest.class);
	private static final String NOTEBOOK_API = "/notebooks";

	@Test
	public void createNoteBook() {
		String name = "NB-3";
		NotebookForm form = new NotebookForm(name);
		LOG.info("Request : {}  to " + NOTEBOOK_API + " createNoteBook", form);
		ResponseEntity<NotebookDetail> responseEntity = restTemplate.postForEntity(NOTEBOOK_API, form,
				NotebookDetail.class);
		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		NotebookDetail resp = responseEntity.getBody();
		assertThat(resp.getName(), is(name));
		assertThat(resp.getId(), IsNull.notNullValue());
		assertThat(resp.getNotes().size(), is(0));

	}

	@Test
	public void getNoteBookWithId() {

		String name = "NB-3";
		NotebookDetail nDetail = noteBookSvc.create(new NotebookForm(name));
		Long id = nDetail.getId();
		ResponseEntity<NotebookDetail> responseEntity = restTemplate.getForEntity(NOTEBOOK_API + "/{id}",
				NotebookDetail.class,id);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		NotebookDetail resp = responseEntity.getBody();
		assertThat(resp.getName(), is(name));
		assertThat(resp.getId(), is(id));
		assertThat(resp.getNotes().size(), is(0));
	}

	@Test(expected = ResourceNotFoundException.class)	
	public void deleteNoteBookWithId() {
		String name = "NB-4";
		NotebookDetail nDetail = noteBookSvc.create(new NotebookForm(name));
		Long id = nDetail.getId();
		ResponseEntity<Void> responseEntity = restTemplate.exchange(NOTEBOOK_API + "/{id}", HttpMethod.DELETE,null,Void.class,id);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		noteBookSvc.findById(id);
		
	}
	@Test(expected = ResourceNotFoundException.class)	
	public void deleteNoteBookWithNonExistentId() {
		Long id = 12L;
		ResponseEntity<Void> responseEntity = restTemplate.exchange(NOTEBOOK_API + "/{id}", HttpMethod.DELETE,null,Void.class,id);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
		noteBookSvc.findById(id);
		
	}
	@Test
	public void getNoteBookWithNonExistentId() {
		Long id = 12L;
		ResponseEntity<ErrorDetail> responseEntity = restTemplate.getForEntity(NOTEBOOK_API + "/{id}",
				ErrorDetail.class,id);
		ErrorDetail resp = responseEntity.getBody();
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
		assertThat(resp.getMessage(), is("No Notebook found with id = "+id));
		
		
		
	}

}
