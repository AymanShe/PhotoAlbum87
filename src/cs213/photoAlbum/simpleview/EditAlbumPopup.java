package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

/**
 * EditAlbum is the gui which gets called when
 *           the edit button is presses on either the
 *           Albums screen or the Album screen  
 * 
 * 
 * @author 2
 * 
 * 
 * Called from the AlbumsScreen
 */
public class EditAlbumPopup extends JDialog {

	//private final JPanel contentPanel = new JPanel();
	
	Album album;
	AlbumScreen albumScreen;
	AlbumsScreen albumsScreen;
	private IControl control;
	private JLabel titleLabel = new JLabel("Edit Album", JLabel.CENTER);
    private JLabel nameLabel = new JLabel("Name:");
    
    private JTextField textField1 = new JTextField(18);
    
    private JButton saveButton = new JButton("Save");
    private JButton cancelButton = new JButton("Cancel");

	/**
	 * Create the dialog.
	 */
	public EditAlbumPopup(final IControl control, final AlbumsScreen albumsScreen, final Album album) {
		
		setTitle("Edit Album");   	
    	setModal(true);
    	
    	this.album = album;
    	this.albumsScreen = albumsScreen;
    	
    	this.control = control;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    	textField1.setText(album.getAlbumName() );
    	
		makeLayout();
		
	}
		
	/**
	    * makeLayout setups the layout and adds the components
	    * 
	    * 
	    */
	    private void makeLayout() {
	    	
	    	
	    	
	    	
			setSize(325, 275);
			setLocationRelativeTo(null);
			
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    	
	    	//setBounds(100, 100, 300, 160);
	    	
	    	GridBagLayout layout = new GridBagLayout();
		 	setLayout(layout);
			GridBagConstraints gbc = new GridBagConstraints();
			
			
		   
		    //reference:
		    //GridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight, 
		    //                   double weightx, double weighty, int anchor, int fill,
		    //                   Insets insets, int ipadx, int ipady)
	    	
			//	add title label	
		    add(titleLabel, new GridBagConstraints( 0, 0, GridBagConstraints.REMAINDER, 2,
		    		                                0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		    		                                getTitleInsets(), 0, 0 ));
		    
		    // add name label    
		    add(nameLabel, new GridBagConstraints( 0, 3, 1, 1,
		    		                               0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		    		                               getInsets(), 0, 0));
		    
		    // add text field 1 	    
		    add(textField1, new GridBagConstraints( 1, 3, GridBagConstraints.REMAINDER, 2, 
		    		                                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		    		                                getInsets(), 0, 0));
		    
		    // setup action listener and add save button 	    
		    saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					
					
					// calls method to change the album name
					inputAlbumName();
					
					dispose();
					
				}
			});
		    
		    add(saveButton, new GridBagConstraints( 1, 8, 1, 2,
		    										0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		    										getInsets(), 0, 0));
		    
		    
		    
		    // setup action listener and add cancel button	    
		    cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//receiver.setOk(false);
					
					dispose();
					//setVisible(false);
					
					/*if(albumScreen != null ) {
			    		
			    		albumScreen.setVisible(true);
			    	}	
					else
					{
						albumsScreen.setVisible(true);
						
					}*/
					
					
									
				}
			});
				    
		    add(cancelButton, new GridBagConstraints( 4, 8, 1, 2,
		    										  0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
		    										  getInsets(), 0, 0));
		    
		    
	    }
	    
	    /**
	     * getInsets is the normal insets
	     * @return Insets
	     */
	    public Insets getInsets() {
			return  new Insets(10, 10, 10, 10);
		}
	    
	    /**
	     * getTitleInseets are used for the title only
	     * 
	     * @return Insets
	     */
	    public Insets getTitleInsets() {
	    	return  new Insets(10, 10, -50, 0);    	
	    }
	    
	    /**
	     * prompts user for new album name,
	     * then changes it
	     * 
	     * 
	     */
	    public void inputAlbumName() {
	    	//Prompts user for new album name
			
			//Note for my partner:
			//public static void showMessageDialog(Component parentComponent,
	        //Object message,
	        //String title,
	        //int messageType) //2 for warning error
	    	//int messageType = JOptionPane.QUESTION_MESSAGE;
			//String albumName = (String) JOptionPane.showInputDialog(this, "Input the new name for the Album", "Get Album Name", messageType);
			
			
			//dispose();
			//albumScreen.dispose();
			
			// set album's name
			album.setAlbumName(textField1.getText());
			
			
			// get null pointer error,
			albumsScreen.updateAlbums();
			
			
			
	    	
	    	
	    }	
	    
	   
		
}