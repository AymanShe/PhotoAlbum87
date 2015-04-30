package cs213.photoAlbum.simpleview;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.control.IControl;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.IBackend;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author 1
 * The first window that is displayed.
 * The admin user or regular users may login here. Regular users see the albums screen, while admins see the admin screen.
 */
public class LoginScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private IBackend backend;
	private IControl control;
	private AdminScreen adminScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
					//Center the window
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginScreen() {
		backend = new Backend();
		backend = backend.readSessionData();
		control = new Control(backend);
		
		setTitle("Login");
		setBounds(new Rectangle(0, 0, 300, 150));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(300, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					loginHandler();
				}
			}
		});
		textField.setBounds(102, 25, 150, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(25, 28, 67, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginHandler();
			}
		});
		btnLogin.setBounds(164, 76, 89, 23);
		contentPane.add(btnLogin);
	}
	
	public void loginHandler()
	{
		String args = textField.getText();
		
		if(args.length() == 0)
		{
			//Note for my partner:
			//public static void showMessageDialog(Component parentComponent,
            //Object message,
            //String title,
            //int messageType) //2 for warning error
			JOptionPane.showMessageDialog(null, "Please enter a username.", "Error", 2);
		}
		
		else if(args.equals("admin"))
		{
			adminScreen = new AdminScreen(backend, control, this);	
			adminScreen.setLocationRelativeTo(null);
			adminScreen.setVisible(true);
			this.setVisible(false);
		}
		
		else
		{
			if(control.login(args))
			{
				 //Display Albums window
				AlbumsScreen albumsScreen = new AlbumsScreen(this, control);
				albumsScreen.setVisible(true);
				this.setVisible(false);
			}
			else
			{
				//Display user does not exist message
				
				//Note for my partner:
				//public static void showMessageDialog(Component parentComponent,
                //Object message,
                //String title,
                //int messageType) //2 for warning error
				JOptionPane.showMessageDialog(null, "User does not exist!", "Error", 2);
			}
		}
		
	}
}