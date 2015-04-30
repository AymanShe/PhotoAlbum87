package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;

/**
 * @author 1
 * This screen is displayed after the advanced search button is clicked from the AlbumsScreen. 
 * The user may specify a string, tags, and a date range to search for.
 */
public class AdvancedSearchPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldStartingDate;
	private JTextField textFieldEndingDate;
	
	private DefaultListModel tagsListModel;
	private JList listTags;
	private ArrayList<Tag> tags;
	
	private IControl control;
	private JTextField textFieldSearchFor;

	/**
	 * Create the dialog.
	 */
	public AdvancedSearchPopup(final IControl control, final AlbumsScreen albumsScreen, String searchFor) {
		tags = new ArrayList<Tag>();
		this.control = control;
		
		setTitle("Advanced Search");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 400, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblSearchingFor = new JLabel("Searching for:");
			GridBagConstraints gbc_lblSearchingFor = new GridBagConstraints();
			gbc_lblSearchingFor.anchor = GridBagConstraints.EAST;
			gbc_lblSearchingFor.insets = new Insets(0, 0, 5, 5);
			gbc_lblSearchingFor.gridx = 0;
			gbc_lblSearchingFor.gridy = 0;
			contentPanel.add(lblSearchingFor, gbc_lblSearchingFor);
		}
		{
			textFieldSearchFor = new JTextField();
			GridBagConstraints gbc_textFieldSearchFor = new GridBagConstraints();
			gbc_textFieldSearchFor.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldSearchFor.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldSearchFor.gridx = 1;
			gbc_textFieldSearchFor.gridy = 0;
			contentPanel.add(textFieldSearchFor, gbc_textFieldSearchFor);
			textFieldSearchFor.setColumns(10);
			textFieldSearchFor.setText(searchFor);
		}
		{
			JLabel lblAnd = new JLabel("to");
			GridBagConstraints gbc_lblAnd = new GridBagConstraints();
			gbc_lblAnd.insets = new Insets(0, 0, 5, 5);
			gbc_lblAnd.gridx = 2;
			gbc_lblAnd.gridy = 1;
			contentPanel.add(lblAnd, gbc_lblAnd);
		}
		{
			JLabel lblNewLabel = new JLabel("Dates between:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 1;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			textFieldStartingDate = new JTextField();
			GridBagConstraints gbc_textFieldStartingDate = new GridBagConstraints();
			gbc_textFieldStartingDate.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldStartingDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldStartingDate.gridx = 1;
			gbc_textFieldStartingDate.gridy = 1;
			contentPanel.add(textFieldStartingDate, gbc_textFieldStartingDate);
			textFieldStartingDate.setColumns(10);
		}
		{
			textFieldEndingDate = new JTextField();
			GridBagConstraints gbc_textFieldEndingDate = new GridBagConstraints();
			gbc_textFieldEndingDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldEndingDate.gridwidth = 2;
			gbc_textFieldEndingDate.insets = new Insets(0, 0, 5, 0);
			gbc_textFieldEndingDate.gridx = 3;
			gbc_textFieldEndingDate.gridy = 1;
			contentPanel.add(textFieldEndingDate, gbc_textFieldEndingDate);
			textFieldEndingDate.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Tags:");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 2;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			JButton btnAddTag = new JButton("Add Tag");
			btnAddTag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showNewTagPopup();
				}
			});
			GridBagConstraints gbc_btnAddTag = new GridBagConstraints();
			gbc_btnAddTag.anchor = GridBagConstraints.SOUTHWEST;
			gbc_btnAddTag.insets = new Insets(0, 0, 5, 0);
			gbc_btnAddTag.gridx = 4;
			gbc_btnAddTag.gridy = 2;
			contentPanel.add(btnAddTag, gbc_btnAddTag);
		}
		{
		}
		{
			JButton btnNewButton_1 = new JButton("Cancel");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			{
				tagsListModel = new DefaultListModel();
				listTags = new JList(tagsListModel);
				GridBagConstraints gbc_list = new GridBagConstraints();
				gbc_list.gridheight = 3;
				gbc_list.gridwidth = 3;
				gbc_list.insets = new Insets(0, 0, 5, 5);
				gbc_list.fill = GridBagConstraints.BOTH;
				gbc_list.gridx = 1;
				gbc_list.gridy = 2;
				contentPanel.add(listTags, gbc_list);
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
				gbc_btnRemoveTag.insets = new Insets(0, 0, 5, 0);
				gbc_btnRemoveTag.gridx = 4;
				gbc_btnRemoveTag.gridy = 3;
				contentPanel.add(btnRemoveTag, gbc_btnRemoveTag);
			}
			GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
			gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
			gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton_1.gridx = 3;
			gbc_btnNewButton_1.gridy = 5;
			contentPanel.add(btnNewButton_1, gbc_btnNewButton_1);
		}
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Photo> photos = new ArrayList<Photo>();
				ArrayList<Photo> photosDate = new ArrayList<Photo>();
				ArrayList<Photo> photosTags = new ArrayList<Photo>();
				
				if(tagsListModel.isEmpty() && textFieldStartingDate.getText().isEmpty() && textFieldEndingDate.getText().isEmpty() && textFieldSearchFor.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "No search terms entered.", "Search", 2);
					return;
				}
				
				if(!textFieldStartingDate.getText().isEmpty() || !textFieldEndingDate.getText().isEmpty())
				{
					Date start;
					Date end;
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
					formatter.setLenient(false);
					
					if(textFieldStartingDate.getText().isEmpty())
					{
						try {
							start = new Date(0); //1970
							end = (Date) formatter.parse(textFieldEndingDate.getText());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Error parsing ending date!", "Search", 2);
							return;
						}
					}
					
					else if(textFieldEndingDate.getText().isEmpty())
					{
						try {
							start = (Date) formatter.parse(textFieldStartingDate.getText());
							end = new Date(); //the current time
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Error parsing starting date!", "Search", 2);
							return;
						}
					}
					
					else
					{
						try {
							start = (Date) formatter.parse(textFieldStartingDate.getText());
							end = (Date) formatter.parse(textFieldEndingDate.getText());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Error parsing dates!", "Search", 2);
							return;
						}
					}
					
					photosDate = control.getPhotosByDate(start, end);
				}
				
				if(!tagsListModel.isEmpty())
				{
					Tag[] t = new Tag[tags.size()];
					tags.toArray(t);
					photosTags = control.getPhotosByTag(t);
				}
				
				//if no tags were entered and only a date range was entered
				if(photosDate.size() > 0 && tagsListModel.isEmpty())
				{
					//if a string to search for was entered
					if(!textFieldSearchFor.getText().isEmpty())
					{
						for(Photo p: photosDate)
						{
							String name = new File(p.getFilePath()).getName();
							if(name.contains(textFieldSearchFor.getText()))
							{
								photos.add(p);
							}
						}
					}
					
					else
					{
						photos = photosDate;
					}
					
					if(photos.size() == 0)
					{
						JOptionPane.showMessageDialog(null, "No results found.", "Search", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					
					SearchPopup popup = new SearchPopup(control, albumsScreen, photos);
					popup.setLocationRelativeTo(null);
					dispose();
					popup.setVisible(true);
				}
				
				//if no dates were entered and only tags were entered
				else if(textFieldStartingDate.getText().isEmpty() && textFieldEndingDate.getText().isEmpty() && !tagsListModel.isEmpty())
				{
					//if a string to search for was entered
					if(!textFieldSearchFor.getText().isEmpty())
					{
						for(Photo p: photosTags)
						{
							String name = new File(p.getFilePath()).getName();
							if(name.contains(textFieldSearchFor.getText()))
							{
								photos.add(p);
							}
						}
					}
					
					else
					{
						photos = photosTags;
					}
					
					if(photos.size() == 0)
					{
						JOptionPane.showMessageDialog(null, "No results found.", "Search", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					SearchPopup popup = new SearchPopup(control, albumsScreen, photos);
					popup.setLocationRelativeTo(null);
					dispose();
					popup.setVisible(true);
				}
				
				if(tagsListModel.isEmpty() && textFieldStartingDate.getText().isEmpty() && textFieldEndingDate.getText().isEmpty() && !textFieldSearchFor.getText().isEmpty())
				{
					for(Photo p: control.getPhotos())
					{
						String name = new File(p.getFilePath()).getName();
						if(name.contains(textFieldSearchFor.getText()))
						{
							photos.add(p);
						}
					}
					
					if(photos.size() == 0)
					{
						JOptionPane.showMessageDialog(null, "No results found.", "Search", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					SearchPopup popup = new SearchPopup(control, albumsScreen, photos);
					popup.setLocationRelativeTo(null);
					dispose();
					popup.setVisible(true);
				}
				
				//date range and tags were entered
				else
				{
					ArrayList<Photo> photos2 = new ArrayList<Photo>();
					
					//See if photos matching the given tags are in the date range
					for(Photo p: photosTags)
					{
						if(photosDate.contains(p))
						{
							photos.add(p);
						}
					}
					
					//if a string to search for was entered
					if(!textFieldSearchFor.getText().isEmpty())
					{
						for(Photo p: photos)
						{
							String name = new File(p.getFilePath()).getName();
							if(name.contains(textFieldSearchFor.getText()))
							{
								photos2.add(p);
							}
						}
					}
					
					else
					{
						photos2 = photos;
					}
					
					if(photos2.size() == 0)
					{
						JOptionPane.showMessageDialog(null, "No results found.", "Search", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						SearchPopup popup = new SearchPopup(control, albumsScreen, photos2);
						popup.setLocationRelativeTo(null);
						dispose();
						popup.setVisible(true);
					}
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 5;
		contentPanel.add(btnNewButton, gbc_btnNewButton);
	}
	
	void updateDisplayedTags()
	{
		tagsListModel.clear();
		for(Tag t: tags){
			tagsListModel.addElement(t.getType() + ":" + t.getValue());
		}
		contentPanel.repaint();
		contentPanel.revalidate();
	}
	
	private void showNewTagPopup()
	{
		NewTagPopup popup = new NewTagPopup(control, this, tags);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}
	
	private void deleteTag()
	{
		if(tags.size() > 0 && listTags.getSelectedIndex() != -1)
		{
			tags.remove(listTags.getSelectedIndex());
		}
		updateDisplayedTags();
	}

}
