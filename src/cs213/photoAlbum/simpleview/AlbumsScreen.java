package cs213.photoAlbum.simpleview;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JList;
import javax.swing.JLabel;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * @author 1
 * This screen is displayed after logging in. The list of albums belonging to the user is displayed.
 */
public class AlbumsScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldSearch;
	private IControl control;
	private LoginScreen login;
	private DefaultListModel albumsListModel;
	JList albumsList;
	private AlbumScreen albumScreen;
	int clickCount = 0;

	/**
	 * Create the frame.
	 */
	public AlbumsScreen(final LoginScreen login, final IControl control) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logout();
			}
		});
		this.login = login;
		this.control = control;
		
		
		setTitle("Albums");
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblLoggedInAs = new JLabel("Logged in as");
		lblLoggedInAs.setText("Logged in as " + control.getActiveUser().getUserId());
		GridBagConstraints gbc_lblLoggedInAs = new GridBagConstraints();
		gbc_lblLoggedInAs.anchor = GridBagConstraints.WEST;
		gbc_lblLoggedInAs.gridwidth = 2;
		gbc_lblLoggedInAs.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoggedInAs.gridx = 0;
		gbc_lblLoggedInAs.gridy = 0;
		contentPane.add(lblLoggedInAs, gbc_lblLoggedInAs);
		
		textFieldSearch = new JTextField();
		GridBagConstraints gbc_textFieldSearch = new GridBagConstraints();
		gbc_textFieldSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearch.gridwidth = 2;
		gbc_textFieldSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSearch.gridx = 2;
		gbc_textFieldSearch.gridy = 0;
		contentPane.add(textFieldSearch, gbc_textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Show search pop-up
				showSearchPopup();
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.EAST;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 4;
		gbc_btnSearch.gridy = 0;
		contentPane.add(btnSearch, gbc_btnSearch);
		
		JButton btnAdvanced = new JButton("Advanced");
		btnAdvanced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Show advanced search pop-up
				showAdvancedSearchPopup();
			}
		});
		GridBagConstraints gbc_btnAdvanced = new GridBagConstraints();
		gbc_btnAdvanced.anchor = GridBagConstraints.EAST;
		gbc_btnAdvanced.gridwidth = 2;
		gbc_btnAdvanced.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdvanced.gridx = 3;
		gbc_btnAdvanced.gridy = 1;
		contentPane.add(btnAdvanced, gbc_btnAdvanced);
		
		albumsListModel = new DefaultListModel();
		albumsList = new JList(albumsListModel);
		albumsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(clickCount > 0){
					clickCount = 0;
					displayAlbumScreen();
				}
				else{
					clickCount++;
				}
			}
		});
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 5;
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 2;
		contentPane.add(albumsList, gbc_list);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Go back to login screen
				logout();
			}
		});
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.anchor = GridBagConstraints.WEST;
		gbc_btnLogout.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogout.gridx = 0;
		gbc_btnLogout.gridy = 3;
		contentPane.add(btnLogout, gbc_btnLogout);
		
		JButton btnDelete = new JButton("Delete Album");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Delete selected album
				deleteAlbum();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 2;
		gbc_btnDelete.gridy = 3;
		contentPane.add(btnDelete, gbc_btnDelete);
		
		JButton btnCreate = new JButton("Create Album");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Album creation popup
				showNewAlbumPopup();
			}
		});
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 3;
		gbc_btnCreate.gridy = 3;
		contentPane.add(btnCreate, gbc_btnCreate);
		
		JButton btnEdit = new JButton("Edit Album");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Edit album popup
				showEditAlbumPopup();
			}
		});
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEdit.gridx = 4;
		gbc_btnEdit.gridy = 3;
		contentPane.add(btnEdit, gbc_btnEdit);
		updateAlbums();
	}
	
	/**
	 * Updates the list of albums displayed.
	 */
	public void updateAlbums()
	{
		clickCount = 0;
		albumsListModel.clear();
		
		if(this.control.listAlbums().size() > 0)
		{
			for(Album a: this.control.listAlbums())
			{
				albumsListModel.addElement("Album name: " + a.getAlbumName()+ ", Photos: " + a.getPhotos().size() + ", Dates: " 
						+ a.getStartDate() + " - " + a.getEndDate());
			}
		}
	}
	
	/**
	 * Hides this window and displays the selected album.
	 */
	public void displayAlbumScreen()
	{
		ArrayList<Album> albums = this.control.listAlbums();
		Album album = albums.get(albumsList.getSelectedIndex());
		
		//this is to prevent someone from spamclicking the list and creating many album windows
		if(albumScreen == null || !albumScreen.isVisible())
		{
			albumScreen = new AlbumScreen(this.login, this.control, album, this);
			this.setVisible(false);
			albumScreen.setVisible(true);
		}
	}
	
	public void showNewAlbumPopup()
	{
		NewAlbumPopup popup = new NewAlbumPopup(control, this);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	private void showSearchPopup() {
		if(textFieldSearch.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "No search term entered.", "Search", 2);
			return;
		}
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		String searchTerm = textFieldSearch.getText();
		
		for(Photo p: control.getPhotos())
		{
			String name = new File(p.getFilePath()).getName();
			if(name.contains(searchTerm))
			{
				photos.add(p);
			}
		}
		
		if(photos.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "No results found.", "Search", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		SearchPopup popup = new SearchPopup(control, this, photos);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	private void showAdvancedSearchPopup() {
		// TODO Auto-generated method stub
		AdvancedSearchPopup popup = new AdvancedSearchPopup(control, this, textFieldSearch.getText());
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	private void deleteAlbum() {
		// TODO Auto-generated method stub
		if(this.control.listAlbums().size() > 0 && albumsList.getSelectedIndex() != -1)
		{
			ArrayList<Album> albums = this.control.listAlbums();
			Album album = albums.get(albumsList.getSelectedIndex());
			control.deleteAlbum(album.getAlbumName());
			updateAlbums();
		}
	}
	
	private void showEditAlbumPopup() {
		// TODO Auto-generated method stub
		if(this.control.listAlbums().size() > 0 && albumsList.getSelectedIndex() != -1)
		{
			ArrayList<Album> albums = this.control.listAlbums();
			Album album = albums.get(albumsList.getSelectedIndex());
			EditAlbumPopup popup = new EditAlbumPopup(control, this, album);
			popup.setLocationRelativeTo(null);
			popup.setVisible(true);
		}
	}

	public void logout()
	{
		this.control.logout();
		this.login.setVisible(true);
		this.dispose();
	}
}