package com.nevernote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/notebooks")
public class NoteController {
	private final Logger LOG = LoggerFactory.getLogger(NoteController.class);

}
