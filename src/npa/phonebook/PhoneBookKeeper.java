package npa.phonebook;

import java.io.IOException;

public interface PhoneBookKeeper {
	void save(PhoneBookRepository phoneBookRepository) throws IOException;
	PhoneBookRepository load() throws IOException;
}
