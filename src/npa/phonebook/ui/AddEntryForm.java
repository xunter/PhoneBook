package npa.phonebook.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog.ModalExclusionType;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class AddEntryForm extends JDialog {
		
	private int state;
	
	public final static int CANCELED = 0;
	public final static int APPROVED = 1;
	
	public final JPanel pFields = new JPanel(new GridLayout(4,1));
	public final JTextField tfName = new JTextField();
	public final JTextField tfPhoneNumber = new JTextField();

	public final JPanel pButtons = new JPanel(new GridLayout(1,2));
	public final JButton bAdd = new JButton();
	public final JButton bCancel = new JButton();
	
	
	public AddEntryForm(Window owner) {
		super(owner);
		setModal(true);
		setTitle("Add");
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		container.add(pFields, BorderLayout.CENTER);
		container.add(pButtons, BorderLayout.SOUTH);
		
		pFields.add(new JLabel("FullName"));
		pFields.add(tfName);
		pFields.add(new JLabel("PhoneNumber"));
		pFields.add(tfPhoneNumber);
		pButtons.add(bAdd);
		
		
		bAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				state = AddEntryForm.APPROVED;
				setVisible(false);
			}
		});
		bAdd.add(new JLabel("Add"));
		bCancel.add(new JLabel("Cancel"));
		pButtons.add(bCancel);
		
		setSize(200, 150);
		setLocationByPlatform(true);
		
		bCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				state = AddEntryForm.CANCELED;
				tfName.setText("");
				tfPhoneNumber.setText("");
				setVisible(false);
			}
		});

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public int getFormState() {
		return state;
	}
}
