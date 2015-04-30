package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * @author 1
 * Adds a tag to an arraylist of tags (for searching photos by tags) or a photo.
 */
public class NewTagPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldTagValue;
	private JTextField textFieldTagType;

	/**
	 * Create the dialog.
	 */
	public NewTagPopup(final IControl control, final Photo photo, final EditPhotoPopup editPhotoPopup) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Add a new tag:");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 300, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tag value:");
		lblNewLabel.setBounds(10, 11, 80, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tag type:");
		lblNewLabel_1.setBounds(10, 36, 80, 14);
		contentPanel.add(lblNewLabel_1);
		
		textFieldTagValue = new JTextField();
		textFieldTagValue.setBounds(100, 8, 160, 20);
		contentPanel.add(textFieldTagValue);
		textFieldTagValue.setColumns(10);
		
		textFieldTagType = new JTextField();
		textFieldTagType.setBounds(100, 33, 160, 20);
		contentPanel.add(textFieldTagType);
		textFieldTagType.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Add tag");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldTagType.getText().length() == 0 || textFieldTagValue.getText().length() == 0)
						{
							JOptionPane.showMessageDialog(null, "Both tag type and value must be specified!", "Error", 2);
							return;
						}
						
						if(textFieldTagType.getText().contains(":") || textFieldTagValue.getText().contains(":"))
						{
							JOptionPane.showMessageDialog(null, "Colons cannot be used!", "Error", 2);
							return;
						}
						
						if(control.addTag(photo.getFilePath(), textFieldTagType.getText(), textFieldTagValue.getText()))
						{
							editPhotoPopup.updateDisplayedTags();
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Tag with the given value and type already exists for photo!", "Error", 2);
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
	
	public NewTagPopup(final IControl control, final AdvancedSearchPopup advancedSearchPopup, final ArrayList<Tag> tags) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Add a new tag:");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 300, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tag value:");
		lblNewLabel.setBounds(10, 11, 80, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tag type:");
		lblNewLabel_1.setBounds(10, 36, 80, 14);
		contentPanel.add(lblNewLabel_1);
		
		textFieldTagValue = new JTextField();
		textFieldTagValue.setBounds(100, 8, 160, 20);
		contentPanel.add(textFieldTagValue);
		textFieldTagValue.setColumns(10);
		
		textFieldTagType = new JTextField();
		textFieldTagType.setBounds(100, 33, 160, 20);
		contentPanel.add(textFieldTagType);
		textFieldTagType.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Add tag");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldTagType.getText().length() == 0 || textFieldTagValue.getText().length() == 0)
						{
							JOptionPane.showMessageDialog(null, "Both tag type and value must be specified!", "Error", 2);
							return;
						}
						
						if(textFieldTagType.getText().contains(":") || textFieldTagValue.getText().contains(":"))
						{
							JOptionPane.showMessageDialog(null, "Colons cannot be used!", "Error", 2);
							return;
						}
						
						if(!tags.contains(new Tag(textFieldTagType.getText(), textFieldTagValue.getText())))
						{
							tags.add(new Tag(textFieldTagType.getText(), textFieldTagValue.getText()));
							advancedSearchPopup.updateDisplayedTags();
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Tag with the given value and type already exists!", "Error", 2);
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
