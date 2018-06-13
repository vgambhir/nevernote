package com.nevernote.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.nevernote.domain.Notebook;

/**
 * In memory Data Store
 * 
 * @author vandana
 *
 */
@Component
public class DataStore {

	private Map<Long, Notebook> bookMap = new ConcurrentHashMap<Long, Notebook>();

	/**
	 * Save Notebook
	 * 
	 * @param book
	 */
	public void save(Notebook book) {
		bookMap.put(book.getId(), book);
	}

	/**
	 * Find Notebook with id
	 * 
	 * @param id
	 * @return
	 */
	public Notebook findbyId(Long id) {
		return bookMap.get(id);

	}

	/**
	 * Delete Notebook with id
	 * 
	 * @param id
	 */
	public void deleteById(Long id) {
		bookMap.remove(id);
	}

	/**
	 * Find if a notebook with id exists or not
	 * 
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return findbyId(id) == null ? false : true;
	}

}
