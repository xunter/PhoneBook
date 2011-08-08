package npa.phonebook;

import java.util.Collection;

public interface PhoneBookService {
	Collection<Entry> getEntries();
	void addEntry(Entry entry) throws EntryAlreadyExistsException;
	void removeEntry(Entry entry) throws EntryNotExistsException;
	void removeEntryAt(int index) throws EntryNotExistsException;
}
