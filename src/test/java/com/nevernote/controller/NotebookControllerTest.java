package com.nevernote.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.nevernote.model.NotebookDetail;
import com.nevernote.model.NotebookForm;
import com.nevernote.service.NotebookService;

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
		NotebookForm form = new NotebookForm("NB-1");
		LOG.info("Request : {}  to " + NOTEBOOK_API + " createNoteBook", form);
		ResponseEntity<NotebookDetail> responseEntity = restTemplate.postForEntity(NOTEBOOK_API , form, NotebookDetail.class);
		LOG.info("Response : {}", responseEntity);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		NotebookDetail resp = responseEntity.getBody();
		assertThat(resp.getName(), is("NB-1"));
		

	}
	@Test
	public void getNoteBook() {
		
		NotebookDetail nDetail = noteBookSvc.create(new NotebookForm("NB-3"));
		String id = Long.toString(nDetail.getId());
//		NotebookForm form = new NotebookForm("NB-2");
//		LOG.info("Request : {}  to " + NOTEBOOK_API + " createNoteBook", form);
//		ResponseEntity<NotebookDetail> responseEntity = restTemplate.postForEntity(NOTEBOOK_API , form, NotebookDetail.class);
//		LOG.info("Response : {}", responseEntity);
//		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
//		NotebookDetail resp = responseEntity.getBody();
//		assertThat(resp.getName(), is("NB-2"));
//		String id = Long.toString(resp.getId());
		ResponseEntity<NotebookDetail> responseEntity1 = restTemplate.getForEntity(NOTEBOOK_API +"/"+id,NotebookDetail.class);
		NotebookDetail resp1 = responseEntity1.getBody();
		assertThat(resp1.getName(), is("NB-3"));
		assertThat(resp1.getId().toString(), is(id));
	}


}
