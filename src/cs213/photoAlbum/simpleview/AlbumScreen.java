package cs213.photoAlbum.simpleview;

import java.awt.FlowLayout;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 1
 * This screen is displayed after opening an album. The photos within the album are displayed upon launch.
 */
public class AlbumScreen extends JFrame {

	private JPanel contentPane;
	private IControl control;
	private LoginScreen login;
	private Album album;
	private JPanel photosPanel;
	private BufferedImage errorImage;
	private AlbumsScreen albumsScreen;
	private JScrollPane scrollPane;
	int photoClickedCount = 0;
	Photo clickedPhoto = null;
	
	/**
	 * Create the frame.
	 */
	public AlbumScreen(LoginScreen login, IControl control, Album album, AlbumsScreen albumsScreen) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logout();
			}
		});

		//Error image that will be displayed if a photo has an invalid path
		try {
			errorImage = ImageIO.read(new File("data//error.png"));
		} catch (IOException e) {
			
		}
		
		this.login = login;
		this.control = control;
		this.album = album;
		this.albumsScreen = albumsScreen;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblAlbumName = new JLabel("Album name");
		lblAlbumName.setText("Album name: " + album.getAlbumName());
		setTitle("Album " + album.getAlbumName());
		GridBagConstraints gbc_lblAlbumName = new GridBagConstraints();
		gbc_lblAlbumName.anchor = GridBagConstraints.WEST;
		gbc_lblAlbumName.gridwidth = 2;
		gbc_lblAlbumName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbumName.gridx = 0;
		gbc_lblAlbumName.gridy = 0;
		contentPane.add(lblAlbumName, gbc_lblAlbumName);
		
		JButton btnNewButton_1 = new JButton("Slideshow");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSlideshow();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 0;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 5;
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(scrollPane, gbc);
		
		photosPanel = new JPanel();
		photosPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		photosPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setViewportView(photosPanel);
		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 5);
		f.setAlignment(FlowLayout.LEFT);
		photosPanel.setLayout(f);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goBackToAlbumsScreen();
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 2;
		contentPane.add(btnBack, gbc_btnBack);
		
		JButton btnAddPhoto = new JButton("Add Photo");
		btnAddPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNewPhotoPopup();
			}
		});
		
		JButton btnDeletePhoto = new JButton("Remove Photo");
		btnDeletePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedPhoto();
			}
		});
		GridBagConstraints gbc_btnDeletePhoto = new GridBagConstraints();
		gbc_btnDeletePhoto.insets = new Insets(0, 0, 0, 5);
		gbc_btnDeletePhoto.gridx = 3;
		gbc_btnDeletePhoto.gridy = 2;
		contentPane.add(btnDeletePhoto, gbc_btnDeletePhoto);
		GridBagConstraints gbc_btnAddPhoto = new GridBagConstraints();
		gbc_btnAddPhoto.gridx = 4;
		gbc_btnAddPhoto.gridy = 2;
		contentPane.add(btnAddPhoto, gbc_btnAddPhoto);
		updatePhotos();
	}

	/**
	 * Updates the list of photos displayed.
	 */
	public void updatePhotos()
	{
		photosPanel.removeAll();
		drawPhotoThumbnails();
		
		photosPanel.repaint();
		photosPanel.revalidate();
		scrollPane.repaint();
		scrollPane.revalidate();
		contentPane.repaint();
		contentPane.revalidate();
		
		albumsScreen.updateAlbums();
	}
	
	/**
	 * Draws all photos in the album.
	 * TODO make photos clickable
	 */
	public void drawPhotoThumbnails()
	{
		ArrayList<Photo> photos = new ArrayList<Photo>();
		photos = album.getPhotos();
		
		if(photos.size()>0){
			for(final Photo photo: photos){
				BufferedImage img;
				String name = null;
				try {
					File f = new File(photo.getFilePath());
					name = f.getName();
					img=ImageIO.read(f);
					//img = ImageIO.read(new File("data//error.png"));
				} catch (IOException e) {
					img = errorImage;
				}
				
				ImageIcon icon=new ImageIcon(ImageUtil.getScaledImage(img, 100, 100));
				final JButton lbl=new JButton();
				//JLabel lbl=new JLabel();
		        lbl.setSize(100, 100);
		        lbl.setIcon(icon);
		        if(name != null)
		        {
		        	lbl.setText(name);
		        }
		        lbl.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(photoClickedCount == 1 && photo.equals(clickedPhoto))
						{
							showPhotoInfoPopup(photo);
							photoClickedCount = 0;
							return;
						}
						clickedPhoto = photo;
						photoClickedCount = 1;
					}
				});
				photosPanel.add(lbl);	
			}
		}
	}
	
	public void showPhotoInfoPopup(Photo photo)
	{
		PhotoInfoPopup popup = new PhotoInfoPopup(control, album, this, photo);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	/**
	 * Hides this window and makes the albums screen visible.
	 */
	public void goBackToAlbumsScreen()
	{
		albumsScreen.setVisible(true);
		this.dispose();
	}
	
	public void deleteSelectedPhoto()
	{
		if(clickedPhoto != null)
		{
			if(!control.removePhoto(clickedPhoto.getFilePath(), album.getAlbumName()))
			{
				JOptionPane.showMessageDialog(null, "Failed to remove photo!", "Error", 2);
			}
			clickedPhoto = null;
			updatePhotos();
		}
	}
	
	public void showNewPhotoPopup()
	{
		NewPhotoPopup popup = new NewPhotoPopup(control, album, this);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	public void showSlideshow()
	{
		Slideshow popup = new Slideshow(album, this);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	public void logout()
	{
		this.control.logout();
		this.login.setVisible(true);
		this.dispose();
	}
}