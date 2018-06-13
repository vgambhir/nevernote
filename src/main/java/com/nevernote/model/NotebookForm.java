package com.nevernote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotebookForm {

	private String name;

	public NotebookForm(String name) {

		this.name = name;
	}

}
