package npa.phonebook.ui;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import npa.phonebook.Entry;
import npa.phonebook.EntryAlreadyExistsException;
import npa.phonebook.EntryNotExistsException;
import npa.phonebook.PhoneBookService;

public class EntriesTableModel implements TableModel {
	private final PhoneBookService phoneBookService;
	private final String[] headers = { "Number", "FullName", "PhoneNumber" };
	
	private final List<WeakReference<TableModelListener>> listeners = Collections.synchronizedList(new ArrayList<WeakReference<TableModelListener>>());
	
	public EntriesTableModel(PhoneBookService phoneBookService) {
		this.phoneBookService = phoneBookService;
	}
	
	public void addTableModelListener(TableModelListener listener) {
		WeakReference<TableModelListener> weakListener = new WeakReference<TableModelListener>(listener);
		listeners.add(weakListener);
	}

	public Class<?> getColumnClass(int colIndex) {
		return headers[colIndex].getClass();
	}

	public int getColumnCount() {
		return headers.length;
	}

	public String getColumnName(int colIndex) {
		return headers[colIndex];
	}

	public int getRowCount() {
		return phoneBookService.getEntries().size();
	}

	public Object getValueAt(int rowIndex, int colIndex) {
		int counter = 0;
		for (Entry entry : phoneBookService.getEntries()) {
			if (counter++ == rowIndex) {
				switch (colIndex) {
					case 0: return String.valueOf(counter);
					case 1: return entry.getName();
					case 2: return entry.getPhoneNumber();
					default: return null;
				}
			}
		}
		return null;
	}

	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener arg0) {
		int removeIndex = -1;
		int index = 0;
		for (WeakReference<TableModelListener> weakListener : listeners) {
			TableModelListener listener = weakListener.get();
			if (listener != null && listener.equals(arg0)) {
				removeIndex = index;
			}
			index++;
		}
		if (removeIndex != -1) {
			listeners.remove(removeIndex);
		}
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {

	}
	
	public boolean addEntry(String name, String phoneNumber) {
		try {
			phoneBookService.addEntry(new Entry(name, phoneNumber));
			notifyListeners(new TableModelEvent(this));
			return true;
		} catch (EntryAlreadyExistsException ex) {
			return false;
		}
	}
	
	public boolean removeEntry(Entry entry) {
		try {
			phoneBookService.removeEntry(entry);
			notifyListeners(new TableModelEvent(this));
			return true;
		} catch (EntryNotExistsException ex) {
			return false;
		}
	}
	
	public void removeEntries(int[] rowIndexes) {
		try {
			Set<Entry> entriesForRemove = new HashSet<Entry>(rowIndexes.length);
			int counter = 0;
			for (Entry entry : phoneBookService.getEntries()) {
				if (Arrays.binarySearch(rowIndexes, counter++) != -1) {
					entriesForRemove.add(entry);
				}
			}
			for (Entry entry : entriesForRemove) {
				try {
					phoneBookService.removeEntry(entry);
				} catch (EntryNotExistsException e) {
					e.printStackTrace();
				}					
			}
		} finally {
			notifyListeners(new TableModelEvent(this));
		}
	}
	
	public boolean removeEntryAt(int index) {
		try {
			phoneBookService.removeEntryAt(index);
			notifyListeners(new TableModelEvent(this, index));
			return true;
		} catch (EntryNotExistsException ex) {
			return false;
		}
	}
	
	public void notifyListeners(TableModelEvent tableModelEvent) {
		for (WeakReference<TableModelListener> weakListener : listeners) {
			TableModelListener listener = weakListener.get();
			if (listener != null) {
				listener.tableChanged(tableModelEvent);
			}
		}
	}
}
