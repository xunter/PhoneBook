package npa.phonebook;

public class EntryAlreadyExistsException extends Exception {
	private final Entry entry;

	public EntryAlreadyExistsException(Entry entry) {
		super();
		this.entry = entry;
	}

	public EntryAlreadyExistsException(Entry entry, String message) {
		super(message);
		this.entry = entry;
	}

	public Entry getEntry() {
		return entry;
	}
}
