package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author 1
 * This window is displayed when the user clicks the add photo button in AlbumScreen.
 * The user specifies the path and the optional caption of the photo.
 */
public class NewPhotoPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldPhotoPath;
	private JTextField textFieldCaption;

	/**
	 * Create the dialog.
	 */
	public NewPhotoPopup(final IControl control, final Album album, final AlbumScreen albumScreen) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Add a new photo:");
		setBounds(100, 100, 300, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPhotoPath = new JLabel("Photo file path:");
			lblPhotoPath.setBounds(10, 30, 85, 14);
			contentPanel.add(lblPhotoPath);
		}
		
		textFieldPhotoPath = new JTextField();
		textFieldPhotoPath.setBounds(105, 27, 150, 20);
		contentPanel.add(textFieldPhotoPath);
		textFieldPhotoPath.setColumns(10);
		
		JLabel lblCaption = new JLabel("Caption:");
		lblCaption.setBounds(10, 55, 85, 14);
		contentPanel.add(lblCaption);
		
		textFieldCaption = new JTextField();
		textFieldCaption.setBounds(105, 52, 150, 20);
		contentPanel.add(textFieldCaption);
		textFieldCaption.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Add Photo");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//Captions are optional
						if(textFieldPhotoPath.getText().isEmpty())
						{
							JOptionPane.showMessageDialog(null, "No file path specified.", "Error", 2);
							return;
						}
						
						try{
							String absolutePath = new File(textFieldPhotoPath.getText()).getAbsoluteFile().getAbsolutePath();		
							if(control.addPhoto(absolutePath, textFieldCaption.getText(), album.getAlbumName()))
							{
								dispose();
								albumScreen.updatePhotos();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Photo " + textFieldPhotoPath.getText() + " already exists in this album.", "Error", 2);
							}
						}
						catch (NullPointerException ex)
						{
							//System.out.println("Album " + albumName + " does not exist");
						} 
						catch (FileNotFoundException ex) {
							JOptionPane.showMessageDialog(null, "Photo " + textFieldPhotoPath.getText() + " does not exist.", "Error", 2);
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