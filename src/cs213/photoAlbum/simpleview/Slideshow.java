package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 1
 * Displays a slideshow of the photos within an album
 * Called from the AlbumScreen
 */
public class Slideshow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ArrayList<Photo> photos;
	JLabel lblPicture;
	
	JLabel lblPhotoName;
	JLabel lblPhotoDate;
	JLabel lblPhotoCaption;
	
	int index = 0;

	/**
	 * Create the dialog.
	 */
	public Slideshow(final Album album, final AlbumScreen albumScreen) {
		setAlwaysOnTop(true);
		photos = album.getPhotos();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
		});
		setTitle("Photo Name");
		setModal(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setBounds(0, 0, screenSize.width, screenSize.height);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		lblPicture = new JLabel("");
		GridBagConstraints gbc_lblPicture = new GridBagConstraints();
		gbc_lblPicture.gridheight = 2;
		gbc_lblPicture.gridwidth = 3;
		gbc_lblPicture.weighty = 1.0;
		gbc_lblPicture.weightx = 1.0;
		gbc_lblPicture.insets = new Insets(0, 0, 5, 5);
		gbc_lblPicture.gridx = 0;
		gbc_lblPicture.gridy = 0;
		contentPanel.add(lblPicture, gbc_lblPicture);
		
		lblPhotoName = new JLabel("Photo Name:");
		GridBagConstraints gbc_lblPhotoName = new GridBagConstraints();
		gbc_lblPhotoName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhotoName.gridx = 1;
		gbc_lblPhotoName.gridy = 2;
		contentPanel.add(lblPhotoName, gbc_lblPhotoName);
		
		JButton btnLeft = new JButton("Prev");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO left button
				if(index-- >= 0)
				{
					updateDisplayedPhoto(index);
				}
				else
				{
					index = photos.size() - 1;
					
				}
				updateDisplayedPhoto(index);
			}
		});
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.anchor = GridBagConstraints.WEST;
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.gridx = 0;
		gbc_btnLeft.gridy = 3;
		contentPanel.add(btnLeft, gbc_btnLeft);
		
		lblPhotoCaption = new JLabel("Photo Caption:");
		GridBagConstraints gbc_lblPhotoCaption = new GridBagConstraints();
		gbc_lblPhotoCaption.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhotoCaption.gridx = 1;
		gbc_lblPhotoCaption.gridy = 3;
		contentPanel.add(lblPhotoCaption, gbc_lblPhotoCaption);
		
		JButton btnRight = new JButton("Next");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO right button
				if(index++ < photos.size())
				{
					updateDisplayedPhoto(index);
				}
				else
				{
					index = 0;
					updateDisplayedPhoto(index);
				}
				updateDisplayedPhoto(index);
			}
		});
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.anchor = GridBagConstraints.EAST;
		gbc_btnRight.insets = new Insets(0, 0, 5, 0);
		gbc_btnRight.gridx = 2;
		gbc_btnRight.gridy = 3;
		contentPanel.add(btnRight, gbc_btnRight);
		
		lblPhotoDate = new JLabel("Date taken:");
		GridBagConstraints gbc_lblPhotoDate = new GridBagConstraints();
		gbc_lblPhotoDate.insets = new Insets(0, 0, 0, 5);
		gbc_lblPhotoDate.gridx = 1;
		gbc_lblPhotoDate.gridy = 4;
		contentPanel.add(lblPhotoDate, gbc_lblPhotoDate);
		updateDisplayedPhoto(index);
	}
	
	private void updateDisplayedPhoto(int index)
	{
		//TODO text below the photo
		
		if(photos.size()>0 && index < photos.size() && index >= 0){
			try {
				BufferedImage img;
				Photo photo = photos.get(index);
				File file = new File(photo.getFilePath());
				
				img=ImageIO.read(file);
				setTitle(file.getName());
				ImageIcon icon=new ImageIcon(img);
				lblPicture.setIcon(icon);
				
				lblPhotoName.setText("Photo Name: " + file.getName());
				lblPhotoDate.setText("Photo Caption: " +photos.get(index).getCaption());
				lblPhotoCaption.setText("Date taken: " +photos.get(index).getDate());
			} 
			catch (IOException e) {
				
			}
		}		
		
		contentPanel.repaint();
		contentPanel.revalidate();
	}
}