package cs213.photoAlbum.simpleview;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.IBackend;
import cs213.photoAlbum.model.User;

import javax.swing.JButton;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 1
 * The admin may add and delete users here.
 */
public class AdminScreen extends JFrame {

	private JPanel contentPane;
	private LoginScreen login;
	private IBackend backend;
	private IControl control;
	private JList usersList;
	//For JLists you'll need to create a DefaultListModel and create a JList with the DefaultListModel
	//and then add the objects you want to display with the JList to the DefaultListModel
	private DefaultListModel usersListModel;
	
	/**
	 * Create the frame.
	 */
	public AdminScreen(IBackend backend, IControl control, LoginScreen login){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				logout();
			}
		});
		this.backend = backend;
		this.login = login;
		this.control = control;
		
		usersListModel = new DefaultListModel();
		
		setTitle("Admin Mode");
		setBounds(100, 100, 450, 450);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add user");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUser();
			}
		});
		btnNewButton.setBounds(30, 25, 110, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Delete user");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteUser();
			}
		});
		btnNewButton_1.setBounds(30, 59, 110, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Logout");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		btnNewButton_2.setBounds(30, 350, 110, 23);
		contentPane.add(btnNewButton_2);
		
		usersList = new JList(usersListModel);
		usersList.setBounds(169, 25, 240, 348);
		contentPane.add(usersList);
		
		updateUsers();
	}
	
	/**
	 * Updates the list of users displayed.
	 */
	public void updateUsers()
	{
		usersListModel.clear();
		
		if(backend.getUsers().size() > 0)
		{
			for(User u: backend.getUsers().values())
			{
				usersListModel.addElement(u.getUserId());
			}
		}
	}
	
	/**
	 * Saves all changes made to the backend.
	 */
	public void saveChanges()
	{
		try {
			backend.writeData(backend);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving session to disk!");
		}
	}
	
	public void logout()
	{
		this.saveChanges();
		this.setVisible(false);
		this.login.setVisible(true);
	}
	
	public void deleteUser()
	{
		String userID = (String) usersList.getSelectedValue();
		
		if(userID != null && userID.length() > 0)
		{
			int index = usersList.getSelectedIndex();
			this.control.deleteUser(userID);
			usersListModel.removeElementAt(index);
		}
		updateUsers();
	}
	
	public void addUser()
	{
		//show new user popup
		NewUserPopup popup = new NewUserPopup(control, this);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
}