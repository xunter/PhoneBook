package npa.phonebook.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import npa.phonebook.Entry;
import npa.phonebook.PhoneBookRepository;

final class PhoneBookRepositoryImpl implements PhoneBookRepository {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7057185888632525135L;
	private final List<Entry> entries;
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	public PhoneBookRepositoryImpl() {
		this(new ArrayList<Entry>());
	}
	
	public PhoneBookRepositoryImpl(List<Entry> entries) {
		this.entries = entries;
	}

}
