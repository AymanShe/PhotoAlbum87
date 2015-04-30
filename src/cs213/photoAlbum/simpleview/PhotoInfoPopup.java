package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author 1
 * Displayed after double clicking on a photo in AlbumScreen
 * Displays some information related to the photo, as well as a resized photo.
 */
public class PhotoInfoPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel imagePanel;
	private Photo photo;
	private IControl control;
	private Album album;
	private JLabel lblPhotoName;
	private JList listTags;
	private JLabel lblCaption;
	private JLabel lblDate;
	private DefaultListModel tagsListModel;
	private AlbumScreen albumScreen;
	
	/**
	 * Create the dialog.
	 */
	public PhotoInfoPopup(IControl control, Album album, AlbumScreen albumScreen, Photo photo) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				//updateDisplayedPhoto();
			}
		});
		
		this.photo = photo;
		this.control = control;
		this.album = album;
		this.albumScreen = albumScreen;
		
		setModal(true);
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//setBounds(0, 0, screenSize.width, screenSize.height);
		
		setBounds(100, 100, 500, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{452, 0, 0, 29, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			imagePanel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 2;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			contentPanel.add(imagePanel, gbc_panel);
		}
		{
			lblPhotoName = new JLabel("Photo name:");
			GridBagConstraints gbc_lblPhotoName = new GridBagConstraints();
			gbc_lblPhotoName.gridwidth = 2;
			gbc_lblPhotoName.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhotoName.gridx = 0;
			gbc_lblPhotoName.gridy = 1;
			contentPanel.add(lblPhotoName, gbc_lblPhotoName);
		}
		{
			JLabel lblTags = new JLabel("Tags:");
			GridBagConstraints gbc_lblTags = new GridBagConstraints();
			gbc_lblTags.anchor = GridBagConstraints.WEST;
			gbc_lblTags.insets = new Insets(0, 0, 5, 0);
			gbc_lblTags.gridx = 1;
			gbc_lblTags.gridy = 2;
			contentPanel.add(lblTags, gbc_lblTags);
		}
		{
			tagsListModel = new DefaultListModel();
			listTags = new JList(tagsListModel);
			GridBagConstraints gbc_listTags = new GridBagConstraints();
			gbc_listTags.insets = new Insets(0, 0, 5, 0);
			gbc_listTags.gridheight = 3;
			gbc_listTags.fill = GridBagConstraints.BOTH;
			gbc_listTags.gridx = 1;
			gbc_listTags.gridy = 3;
			contentPanel.add(listTags, gbc_listTags);
		}
		{
			lblCaption = new JLabel("Caption:");
			GridBagConstraints gbc_lblCaption = new GridBagConstraints();
			gbc_lblCaption.anchor = GridBagConstraints.EAST;
			gbc_lblCaption.insets = new Insets(0, 0, 5, 5);
			gbc_lblCaption.gridx = 0;
			gbc_lblCaption.gridy = 4;
			contentPanel.add(lblCaption, gbc_lblCaption);
		}
		{
			lblDate = new JLabel("Date taken:");
			GridBagConstraints gbc_lblDate = new GridBagConstraints();
			gbc_lblDate.anchor = GridBagConstraints.EAST;
			gbc_lblDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblDate.gridx = 0;
			gbc_lblDate.gridy = 5;
			contentPanel.add(lblDate, gbc_lblDate);
		}
		{
			JButton btnEdit = new JButton("Edit");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					showEditPhotoPopup();
				}
			});
			GridBagConstraints gbc_btnEdit = new GridBagConstraints();
			gbc_btnEdit.anchor = GridBagConstraints.EAST;
			gbc_btnEdit.gridx = 1;
			gbc_btnEdit.gridy = 6;
			contentPanel.add(btnEdit, gbc_btnEdit);
		}
		
		updateDisplayedPhoto();
	}
	
	/**
	 * Updates the photo information displayed
	 * Called after changing a photo's attributes (i.e. renaming it)
	 */
	void updateDisplayedPhoto()
	{
		
		
		try {
			BufferedImage img;
			
			File file = new File(photo.getFilePath());
			
			//sets the title of the window to be the file name
			setTitle(file.getName());
			
			img=ImageIO.read(file);
			//TODO image resizing
			
			Rectangle r = this.getBounds();
			int y = img.getHeight();
			int x = img.getWidth();
			//int y = r.height - 200;
			//int x = r.width - 30;
			
			if(r.height - 200 < y)
			{
				y = r.height - 200;
			}
			
			if(r.width - 30 < x)
			{
				x = r.width - 30;
			}
			
			ImageIcon icon=new ImageIcon(ImageUtil.getScaledImage(img, x, y));
			JLabel lbl=new JLabel();
			lbl.setSize(imagePanel.getWidth(), imagePanel.getWidth());
			lbl.setIcon(icon);
			imagePanel.add(lbl);
			
			lblPhotoName.setText(file.getName());
			lblCaption.setText("Caption: " + photo.getCaption());
			lblDate.setText("Date: " + photo.getDate().toString());
		} 
		catch (IOException e) {
			lblPhotoName.setText("Unable to load file information.");
		}

		tagsListModel.clear();
		for(Tag t: photo.getTags()){
			tagsListModel.addElement(t.getType() + ":" + t.getValue());
		}
		
		imagePanel.repaint();
		imagePanel.revalidate();
		contentPanel.repaint();
		contentPanel.revalidate();
		
		albumScreen.updatePhotos();
	}
	
	private void showEditPhotoPopup()
	{
		EditPhotoPopup popup = new EditPhotoPopup(control, photo, album, this);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}

}
