package gui.staff;
import objects.*;
import static_classes.*;
import wrapper.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.*;	

/* Dani and Adam */

public class Login extends JFrame implements ActionListener
{
	// Make the Login and Menu objects usable for other classes
	// so it can be displayed/hidden from anywhere
	private static Login login;
	//public static Menu theMenu;
	public static DriverInterface driverInterface;
	public static SupervisorInterface supervisorInterface;
	// 2 textfields for the login
	private final JTextField username = new JTextField(10);
  //private final JPasswordField password = new JPasswordField(10);
	private JLabel warningJLabel;
	private Driver driver;
  
  // Constructor
  public Login ()
  {
  	setTitle ("Login");
  	
  	Container contents = getContentPane();
  	contents.setLayout (new BorderLayout());
  	
  	// Add login fields to a grid
    JPanel loginPanel = new JPanel (new GridLayout (0, 2));
    contents.add (loginPanel, BorderLayout.NORTH);
    
    loginPanel.add (new JLabel ("Username: "));
    loginPanel.add (username);
    
    // nic trying to add code so that button responds to enter key
/*  username.addKeyListener(new KeyListener()
    {
      public void keyPressed(KeyEvent e)
      {
        if (e.getKeyChar() == KeyEvent.VK_ENTER)
          actionPerformed (e);
      }
    });
*/    
    //loginPanel.add (new JLabel ("Password:"));
    //loginPanel.add (password);
    
    JButton loginButton = new JButton ("Login");
    contents.add (loginButton, BorderLayout.CENTER);
    loginButton.addActionListener(this);
    
    warningJLabel = new JLabel (" ");
    contents.add (warningJLabel, BorderLayout.SOUTH);
    
    // Open the DB
    database.openBusDatabase();
    setDefaultCloseOperation (EXIT_ON_CLOSE);
    pack();
  }// constructor
  
  // Process login details using the database
  public void actionPerformed (ActionEvent event)
  {  	  	
    // On click of Login button    
    // check to see if controller is loggin in, otherwise
    // try to create a new Driver object if the ID is found in the DB
    try
    {
      if (username.getText().toLowerCase().equals("supervisor") ||
          username.getText().toLowerCase().equals("s"))
        displaySupervisorInterface();
      else
      {
        driver = new Driver (username.getText());
        displayDriverInterface();
      }
    }// try
    
    catch (InvalidQueryException exception)
    {
    	warningJLabel.setText ("Error: invalid driver ID");
    }// catch    
    
  }// actionPerformed
  
  private void displayDriverInterface ()
  {
  	driverInterface = new DriverInterface(driver);
  	
  	// Position the window to the middle of the screen
  	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		driverInterface.setLocation(dim.width/2-
		driverInterface.getSize().width/2,
		dim.height/2-driverInterface.getSize().height/2);
		
  	// Pressing the Login button will hide the login
  	// app and open the menu
  	driverInterface.setVisible (true);  	
  	driverInterface.setDefaultCloseOperation (EXIT_ON_CLOSE);
  	login.setVisible (false);  	
  } // displayDriverInterface
  
  private void displaySupervisorInterface ()
  {
  	supervisorInterface = new SupervisorInterface();
  	
  	// Position the window to the middle of the screen
  	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		supervisorInterface.setLocation(dim.width/2-
		supervisorInterface.getSize().width/2,
		dim.height/2-supervisorInterface.getSize().height/2);
		
  	// Pressing the Login button will hide the login
  	// app and open the menu
  	supervisorInterface.setVisible (true);  	
  	supervisorInterface.setDefaultCloseOperation (EXIT_ON_CLOSE);
  	login.setVisible (false);  	
  } // displaySupervisorInterface
  
  public static void main (String[] args)
  {
  	login = new Login();
  	// Display login window in the middle of the screen
  	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		login.setLocation(dim.width/2-login.getSize().width/2,
		dim.height/2-login.getSize().height/2);
		
  	login.setVisible (true);
  }// main
}
