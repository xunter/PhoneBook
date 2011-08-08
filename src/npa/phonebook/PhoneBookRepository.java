package npa.phonebook;

import java.io.Serializable;
import java.util.List;

public interface PhoneBookRepository extends Serializable {
	List<Entry> getEntries();
}
