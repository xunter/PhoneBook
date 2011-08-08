package npa.phonebook.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import npa.phonebook.PhoneBookKeeper;
import npa.phonebook.PhoneBookRepository;

public class PhoneBookKeeperImpl implements PhoneBookKeeper {
	private final String filePath;
	
	public PhoneBookKeeperImpl(String filePath) {
		super();
		this.filePath = filePath;
	}

	public void save(PhoneBookRepository phoneBookRepository) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		if (!file.canWrite()) {
			throw new IOException("Can not write to the file: "+filePath);
		}
		ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		outputStream.writeObject(phoneBookRepository);
		outputStream.close();
	}

	public PhoneBookRepository load() throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			return PhoneBookFactory.getEmptyRepository();
		}
		if (!file.canRead()) {
			throw new IOException("Can not read from the file: "+filePath);
		}
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			try {
				PhoneBookRepository repository = (PhoneBookRepositoryImpl)inputStream.readObject();
				return repository;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new IOException(e);
			}	
		} finally {
			if (inputStream != null) {
				inputStream.close();		
			}
		}
	}

	protected String getFilePath() {
		return filePath;
	}

}
