package com.nevernote.domain;

import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Note {

	private Long id;
	private String title;
	private String body;
	private List<String> tags;
	private Long createdDate;
	private Long lastModifiedDate;

	public Note(Long id, String title, String body, List<String> tags, Long createdDate, Long lastModifiedDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Note) {
			Note other = (Note) obj;
			return Objects.equals(this.getId(), other.getId());
		}
		return false;
	}

}
