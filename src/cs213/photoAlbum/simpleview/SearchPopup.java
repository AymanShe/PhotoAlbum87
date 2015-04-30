package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Photo;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;

/**
 * @author 1
 * Called from the AlbumsScreen
 * Displays the photos found as a result of a search
 */
public class SearchPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	ArrayList<Photo> searchResults;
	private final JScrollPane scrollPane = new JScrollPane();
	private JPanel photosPanel;

	/**
	 * Create the dialog.
	 */
	public SearchPopup(final IControl control, final AlbumsScreen albumsScreen, final ArrayList<Photo> searchResults) {
		
		this.searchResults = searchResults;
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Search results");
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPanel.add(scrollPane, gbc_scrollPane);
		
		photosPanel = new JPanel();
		photosPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		photosPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setViewportView(photosPanel);
		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 5);
		f.setAlignment(FlowLayout.LEFT);
		photosPanel.setLayout(f);
		
		JButton btnNewButton = new JButton("Save results as new album");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				NewAlbumPopup popup = new NewAlbumPopup(control, albumsScreen, searchResults);
				popup.setLocationRelativeTo(null);
				popup.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		contentPanel.add(btnNewButton, gbc_btnNewButton);
		drawPhotoThumbnails();
	}

	public void drawPhotoThumbnails() {
		for (final Photo photo : searchResults) {
			BufferedImage img;
			String name = null;
			try {
				File f = new File(photo.getFilePath());
				name = f.getName();
				img = ImageIO.read(f);
				ImageIcon icon = new ImageIcon(ImageUtil.getScaledImage(img, 100, 100));
				final JButton lbl = new JButton();
				lbl.setSize(100, 100);
				lbl.setIcon(icon);
				if (name != null) {
					lbl.setText(name);
				}
				photosPanel.add(lbl);
			} catch (IOException e) {

			}
		}
	}
}