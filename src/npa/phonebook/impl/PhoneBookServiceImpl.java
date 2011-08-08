package npa.phonebook.impl;

import java.util.Collection;
import java.util.Collections;

import npa.phonebook.Entry;
import npa.phonebook.EntryAlreadyExistsException;
import npa.phonebook.EntryNotExistsException;
import npa.phonebook.PhoneBookRepository;
import npa.phonebook.PhoneBookService;

public class PhoneBookServiceImpl implements PhoneBookService {
	private final PhoneBookRepository repository;
	
	public PhoneBookServiceImpl(PhoneBookRepository phoneBookRepository) {
		this.repository = phoneBookRepository;
	}
	
	public Collection<Entry> getEntries() {
		return Collections.unmodifiableList(repository.getEntries());
	}

	public void addEntry(Entry entry) throws EntryAlreadyExistsException {
		if (repository.getEntries().contains(entry)) {
			throw new EntryAlreadyExistsException(entry);
		}
		repository.getEntries().add(entry);
	}

	public void removeEntry(Entry entry) throws EntryNotExistsException {
		Entry existsEntry = null;
		for (Entry loopEntry : repository.getEntries()) {
			if (entry.equals(loopEntry)) {
				existsEntry = loopEntry;
			}
		}
		if (existsEntry == null || !repository.getEntries().remove(entry)) {
			throw new EntryNotExistsException();
		}
		
	}

	@Override
	public void removeEntryAt(int index) throws EntryNotExistsException {
		Entry removedEntry = repository.getEntries().remove(index);
		if (removedEntry == null) {
			throw new EntryNotExistsException();
		}
	}

}
