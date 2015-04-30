package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author 1
 * Opened from the admin screen
 * Users must have a username and a user id
 */
public class NewUserPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFullName;
	private JTextField textFieldUserID;

	public NewUserPopup(final IControl control, final AdminScreen adminScreen) {
		setModal(true);
		setTitle("Add a new user:");
		setBounds(100, 100, 300, 160);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Full Name:");
			lblNewLabel.setBounds(30, 30, 70, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblUserId = new JLabel("User ID:");
			lblUserId.setBounds(30, 55, 70, 14);
			contentPanel.add(lblUserId);
		}
		{
			textFieldFullName = new JTextField();
			textFieldFullName.setBounds(110, 27, 150, 20);
			contentPanel.add(textFieldFullName);
			textFieldFullName.setColumns(10);
		}
		{
			textFieldUserID = new JTextField();
			textFieldUserID.setBounds(110, 52, 150, 20);
			contentPanel.add(textFieldUserID);
			textFieldUserID.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Create User");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(textFieldFullName.getText().length() < 1)
						{
							JOptionPane.showMessageDialog(null, "No name has been entered!", "Error", 2);
						}
						
						else if(textFieldUserID.getText().length() < 1)
						{
							JOptionPane.showMessageDialog(null, "No user ID has been entered!", "Error", 2);
						}
						
						else
						{
							if(control.addUser(textFieldUserID.getText(), textFieldFullName.getText()))
							{
								//if we successfully login, hide this popup and update the list of displayed users
								setVisible(false);
								adminScreen.updateUsers();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "User ID is already taken!", "Error", 2);
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}