package npa.phonebook;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import npa.phonebook.impl.PhoneBookFactory;
import npa.phonebook.ui.MainForm;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "You did not choose a file!");
				System.exit(0);
				return;
			}
			
			File choosedFile = fileChooser.getSelectedFile();
			String filePath = choosedFile.getAbsolutePath();
			if (choosedFile.isDirectory()) {
				filePath = filePath.concat("phoneBook.dat");
			}
			final PhoneBookKeeper keeper = PhoneBookFactory.getKeeper(filePath);
			final PhoneBookRepository repository = keeper.load();
			final PhoneBookService service = PhoneBookFactory.getService(repository);
	
			final MainForm mainForm = new MainForm("PhoneBook", service);
			mainForm.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent arg0) {
	
					super.windowClosed(arg0);
					try {
						keeper.save(repository);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					System.exit(0);
				}
			});
			mainForm.setVisible(true);
		} catch (Exception ex) {
			JLabel label = new JLabel(ex.toString());
			label.setForeground(Color.RED);
			JOptionPane.showMessageDialog(null, label);
		}
	}

}
