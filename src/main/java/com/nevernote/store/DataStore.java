package com.nevernote.store;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.nevernote.domain.Notebook;

@Repository
public class DataStore {

	private Map<Long, Notebook> bookMap = new HashMap<Long, Notebook>();

	public void save(Notebook book) {
		bookMap.put(book.getId(), book);
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
