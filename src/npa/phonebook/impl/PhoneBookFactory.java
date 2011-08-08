package npa.phonebook.impl;

import npa.phonebook.PhoneBookKeeper;
import npa.phonebook.PhoneBookRepository;
import npa.phonebook.PhoneBookService;

public class PhoneBookFactory {
	public static PhoneBookKeeper getKeeper(String filePath) {
		return new PhoneBookKeeperImpl(filePath);
	}

	public static PhoneBookService getService(PhoneBookRepository repository) {
		return new PhoneBookServiceImpl(repository);
	}
	
	public static PhoneBookRepository getEmptyRepository() {
		return new PhoneBookRepositoryImpl();
	}
}
