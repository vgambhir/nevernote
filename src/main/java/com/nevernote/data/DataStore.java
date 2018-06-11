package com.nevernote.data;

import java.util.HashMap;
import java.util.Map;

import com.nevernote.domain.Notebook;

public class DataStore {

	private Map<Long, Notebook> bookMap = new HashMap<Long, Notebook>();

	public Notebook save(Notebook book) {
		bookMap.put(book.getId(), book);
		return findbyId(book.getId());

	}

	public Notebook findbyId(Long id) {
		return bookMap.get(id);

	}

	public void deleteById(Long id) {
		bookMap.remove(id);
	}

	public boolean existsById(Long id) {
		return findbyId(id) == null ? false : true;
	}

}
