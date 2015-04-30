package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author 1
 * Users enter a name for a new album here.
 * If this window is opened as a result of a search, the new album will contain the results of the search.
 */
public class NewAlbumPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldAlbumName;

	/**
	 * Create the dialog.
	 */
	public NewAlbumPopup(final IControl control, final AlbumsScreen albumsScreen) {
		setResizable(false);
		setModal(true);
		setTitle("Add a new album:");
		setBounds(100, 100, 300, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblAlbumName = new JLabel("Album name:");
			lblAlbumName.setBounds(30, 34, 80, 14);
			contentPanel.add(lblAlbumName);
		}
		{
			textFieldAlbumName = new JTextField();
			textFieldAlbumName.setBounds(120, 31, 135, 20);
			contentPanel.add(textFieldAlbumName);
			textFieldAlbumName.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Create Album");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(control.createAlbum(textFieldAlbumName.getText()))
						{
							albumsScreen.updateAlbums();
							dispose();
						}
						
						else{
							JOptionPane.showMessageDialog(null, "Album with the same name already exists!", "Error", 2);
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
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public NewAlbumPopup(final IControl control, final AlbumsScreen albumsScreen, final ArrayList<Photo> searchResults) {
		setResizable(false);
		setModal(true);
		setTitle("Add a new album:");
		setBounds(100, 100, 300, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblAlbumName = new JLabel("Album name:");
			lblAlbumName.setBounds(30, 34, 80, 14);
			contentPanel.add(lblAlbumName);
		}
		{
			textFieldAlbumName = new JTextField();
			textFieldAlbumName.setBounds(120, 31, 135, 20);
			contentPanel.add(textFieldAlbumName);
			textFieldAlbumName.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Create Album");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(control.createAlbum(textFieldAlbumName.getText()))
						{
							if(searchResults != null)
							{
								Album a = control.getAlbum(textFieldAlbumName.getText());
								
								for(Photo p: searchResults)
								{
									try {
										control.addPhoto(p.getFilePath(), "", a.getAlbumName());
									} catch (NullPointerException e1) {
										// TODO Auto-generated catch block
										//e1.printStackTrace();
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										//e1.printStackTrace();
									}
								}
							}
							albumsScreen.updateAlbums();
							dispose();
						}
						
						else{
							JOptionPane.showMessageDialog(null, "Album with the same name already exists!", "Error", 2);
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
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}