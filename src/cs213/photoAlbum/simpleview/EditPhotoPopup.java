package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;

/**
 * @author 1
 * This window is displayed upon clicking the edit button within the photo info window.
 * The user may modify attributes of the photo here.
 */
public class EditPhotoPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldName;
	private JTextField textFieldCaption;
	private JTextField textFieldPhotoPath;
	private DefaultListModel tagsListModel;
	private JList listTags;
	private Photo photo;
	private IControl control;
	JComboBox comboBoxToAlbum;
	PhotoInfoPopup photoInfoPopup;
	
	File file;

	/**
	 * Create the dialog.
	 */
	public EditPhotoPopup(final IControl control, final Photo photo, final Album album, final PhotoInfoPopup photoInfoPopup) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.photo = photo;
		this.control = control;
		this.photoInfoPopup = photoInfoPopup;
		setTitle("Editing " + photo.getFilePath());
		file = new File(photo.getFilePath()).getAbsoluteFile();		
		
		setModal(true);
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Photo path:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			textFieldPhotoPath = new JTextField();
			textFieldPhotoPath.setEditable(false);
			textFieldPhotoPath.setText(photo.getFilePath());
			GridBagConstraints gbc_textFieldPhotoPath = new GridBagConstraints();
			gbc_textFieldPhotoPath.gridwidth = 3;
			gbc_textFieldPhotoPath.insets = new Insets(0, 0, 5, 0);
			gbc_textFieldPhotoPath.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldPhotoPath.gridx = 1;
			gbc_textFieldPhotoPath.gridy = 0;
			contentPanel.add(textFieldPhotoPath, gbc_textFieldPhotoPath);
			textFieldPhotoPath.setColumns(10);
		}
		{
			JLabel lblPhotoName = new JLabel("Photo Name:");
			GridBagConstraints gbc_lblPhotoName = new GridBagConstraints();
			gbc_lblPhotoName.anchor = GridBagConstraints.WEST;
			gbc_lblPhotoName.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhotoName.gridx = 0;
			gbc_lblPhotoName.gridy = 1;
			contentPanel.add(lblPhotoName, gbc_lblPhotoName);
		}
		{
			textFieldName = new JTextField();
			textFieldName.setText(file.getName());
			GridBagConstraints gbc_textFieldName = new GridBagConstraints();
			gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldName.gridx = 1;
			gbc_textFieldName.gridy = 1;
			contentPanel.add(textFieldName, gbc_textFieldName);
			textFieldName.setColumns(10);
		}
		{
			JLabel lblPhotoCaption = new JLabel("Photo Caption:");
			GridBagConstraints gbc_lblPhotoCaption = new GridBagConstraints();
			gbc_lblPhotoCaption.anchor = GridBagConstraints.WEST;
			gbc_lblPhotoCaption.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhotoCaption.gridx = 0;
			gbc_lblPhotoCaption.gridy = 2;
			contentPanel.add(lblPhotoCaption, gbc_lblPhotoCaption);
		}
		{
			textFieldCaption = new JTextField();
			textFieldCaption.setText(photo.getCaption());
			GridBagConstraints gbc_textFieldCaption = new GridBagConstraints();
			gbc_textFieldCaption.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldCaption.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldCaption.gridx = 1;
			gbc_textFieldCaption.gridy = 2;
			contentPanel.add(textFieldCaption, gbc_textFieldCaption);
			textFieldCaption.setColumns(10);
		}
		{
			JLabel lblPhotoTags = new JLabel("Photo Tags:");
			GridBagConstraints gbc_lblPhotoTags = new GridBagConstraints();
			gbc_lblPhotoTags.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblPhotoTags.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhotoTags.gridx = 0;
			gbc_lblPhotoTags.gridy = 3;
			contentPanel.add(lblPhotoTags, gbc_lblPhotoTags);
		}
		{
			tagsListModel = new DefaultListModel();
			listTags = new JList(tagsListModel);
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.gridheight = 2;
			gbc_list.insets = new Insets(0, 0, 5, 5);
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 1;
			gbc_list.gridy = 3;
			contentPanel.add(listTags, gbc_list);
		}
		{
			JButton btnAddTag = new JButton("Add Tag");
			btnAddTag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showNewTagPopup();
				}
			});
			GridBagConstraints gbc_btnAddTag = new GridBagConstraints();
			gbc_btnAddTag.anchor = GridBagConstraints.NORTHWEST;
			gbc_btnAddTag.insets = new Insets(0, 0, 5, 5);
			gbc_btnAddTag.gridx = 2;
			gbc_btnAddTag.gridy = 3;
			contentPanel.add(btnAddTag, gbc_btnAddTag);
		}
		{
			JButton btnRemoveTag = new JButton("Remove Tag");
			btnRemoveTag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteTag();
				}
			});
			GridBagConstraints gbc_btnRemoveTag = new GridBagConstraints();
			gbc_btnRemoveTag.anchor = GridBagConstraints.NORTHWEST;
			gbc_btnRemoveTag.insets = new Insets(0, 0, 5, 5);
			gbc_btnRemoveTag.gridx = 2;
			gbc_btnRemoveTag.gridy = 4;
			contentPanel.add(btnRemoveTag, gbc_btnRemoveTag);
		}
		{
			JButton btnCancel = new JButton("Close");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			{
				JLabel lblMovePhoto = new JLabel("Move Photo to:");
				GridBagConstraints gbc_lblMovePhoto = new GridBagConstraints();
				gbc_lblMovePhoto.anchor = GridBagConstraints.EAST;
				gbc_lblMovePhoto.insets = new Insets(0, 0, 5, 5);
				gbc_lblMovePhoto.gridx = 0;
				gbc_lblMovePhoto.gridy = 5;
				contentPanel.add(lblMovePhoto, gbc_lblMovePhoto);
			}
			{
				comboBoxToAlbum = new JComboBox();
				GridBagConstraints gbc_comboBoxToAlbum = new GridBagConstraints();
				gbc_comboBoxToAlbum.insets = new Insets(0, 0, 5, 5);
				gbc_comboBoxToAlbum.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBoxToAlbum.gridx = 1;
				gbc_comboBoxToAlbum.gridy = 5;
				contentPanel.add(comboBoxToAlbum, gbc_comboBoxToAlbum);
			}
			GridBagConstraints gbc_btnCancel = new GridBagConstraints();
			gbc_btnCancel.anchor = GridBagConstraints.SOUTHEAST;
			gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
			gbc_btnCancel.gridx = 2;
			gbc_btnCancel.gridy = 6;
			contentPanel.add(btnCancel, gbc_btnCancel);
		}
		{
			JButton btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//check if any photos in the album already have that name
					
					String temp = "";
					
					if(file.getParentFile() != null){
						temp = file.getParentFile().getAbsolutePath();
					}
					
					File newFilePath = new File(temp + File.separatorChar + textFieldName.getText());
					
					if(file.renameTo(newFilePath))
					{
						photo.setFilePath(newFilePath.getAbsolutePath());
						photoInfoPopup.updateDisplayedPhoto();
					}
					
					else
					{
						JOptionPane.showMessageDialog(null, "Error renaming file!", "Error", 2);
						return;
					}
					
					if(comboBoxToAlbum.getSelectedItem() != null)
					{
						try{
							if(control.movePhoto(photo.getFilePath(), album.getAlbumName(), (String) comboBoxToAlbum.getSelectedItem())){

							}
							else
							{
								JOptionPane.showMessageDialog(null, "Error moving file!", "Error", 2);
							}
						}
						catch(NullPointerException ex)
						{
							JOptionPane.showMessageDialog(null, "Error moving file!", "Error", 2);
						}
						catch(IllegalArgumentException ex)
						{
							JOptionPane.showMessageDialog(null, "The photo already exists in the destination album.", "Error", 2);
						}
					}
					
					//save the new caption
					photo.setCaption(textFieldCaption.getText());
					photoInfoPopup.updateDisplayedPhoto();
					//dispose();
				}
			});
			GridBagConstraints gbc_btnSave = new GridBagConstraints();
			gbc_btnSave.anchor = GridBagConstraints.SOUTHEAST;
			gbc_btnSave.gridx = 3;
			gbc_btnSave.gridy = 6;
			contentPanel.add(btnSave, gbc_btnSave);
			updateDisplayedTags();
			
			comboBoxToAlbum.addItem(null);
			for(Album a: control.listAlbums())
			{
				comboBoxToAlbum.addItem(a.getAlbumName());
			}
		}
	}
	
	private void showNewTagPopup()
	{
		NewTagPopup popup = new NewTagPopup(control, photo, this);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	private void deleteTag()
	{
		if(photo.getTags().size() > 0 && listTags.getSelectedIndex() != -1)
		{
			String[] tokens = tagsListModel.getElementAt(listTags.getSelectedIndex()).toString().split(":");
			control.deleteTag(photo.getFilePath(), tokens[0], tokens[1]);
			updateDisplayedTags();
		}
	}
	
	void updateDisplayedTags()
	{
		tagsListModel.clear();
		for(Tag t: photo.getTags()){
			//if(!tagsListModel.contains(new String(t.getType() + ":" + t.getValue()))){
				tagsListModel.addElement(t.getType() + ":" + t.getValue());
			//}
		}
		
		photoInfoPopup.updateDisplayedPhoto();
		contentPanel.repaint();
		contentPanel.revalidate();
	}
}
