package npa.phonebook.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import npa.phonebook.PhoneBookService;

public class MainForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7217572899882931529L;

	private final PhoneBookService phoneBookService;
	
	public JTable entriesTable;
	
	public final JMenuBar menuBar = new JMenuBar();
	
	public final JMenu fileMenu = new JMenu("File");
	public final JMenuItem exitMenuItem = new JMenuItem("Exit");
	public final JMenu editMenu = new JMenu("Edit");
	
	public final JMenuItem addEntryMenuItem = new JMenuItem("Add entry");
	public final JMenuItem removeEntryMenuItem = new JMenuItem("Remove entry");
    public final JMenu helpMenu = new JMenu("Help");
	public final JMenuItem aboutMenu = new JMenuItem("About");
	
	public MainForm(String title, PhoneBookService phoneBookService) {
		this.phoneBookService = phoneBookService;
		setTitle(title);
		
		setJMenuBar(menuBar);
		initializeMenuBar();
		
		getContentPane().setLayout(new BorderLayout());
		//Container contentPane = getContentPane();
		//contentPane.setLayout(new BorderLayout());
		//contentPane.add(entriesTable, BorderLayout.CENTER);
				
		initializeEntriesTable();
		pack();
		setMinimumSize(getSize());
		setSize(500, 300);
		setLocationByPlatform(true);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	protected void initializeEntriesTable() {
		final EntriesTableModel entriesTableModel = new EntriesTableModel(phoneBookService);		
		entriesTable = new JTable(entriesTableModel);
		
		entriesTable.createDefaultColumnsFromModel();
		entriesTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(entriesTable);

		getContentPane().add(scrollPane, BorderLayout.CENTER);

		addEntryMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				AddEntryForm addEntryForm = new AddEntryForm(MainForm.this);
				addEntryForm.setVisible(true);

				if (addEntryForm.getFormState() == AddEntryForm.APPROVED) {
					entriesTableModel.addEntry(addEntryForm.tfName.getText(), addEntryForm.tfPhoneNumber.getText());
				}
			}
		});
		
		removeEntryMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int[] selectedRows = entriesTable.getSelectedRows();				
				if (selectedRows.length == 1) {
					entriesTableModel.removeEntryAt(entriesTable.getSelectedRow());
				} else if (selectedRows.length > 1) {
					entriesTableModel.removeEntries(selectedRows);					
				}
			}
		});
	}
	
	protected void initializeMenuBar() {
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		fileMenu.add(exitMenuItem);
		
		editMenu.add(addEntryMenuItem);
		editMenu.add(removeEntryMenuItem);
		
		helpMenu.add(aboutMenu);
		
		exitMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		
		aboutMenu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainForm.this, "Powered by Nazarov P.A.");
			}
		});
		
		
	}
}