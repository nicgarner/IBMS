import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* Dani and Adam */

public class DriverInterface extends JFrame implements ActionListener
{
		
	// Define a JTextArea for the results to be displayed
	private final JTextArea resultTextArea = new JTextArea ();
	private final JTextArea welcomeTextArea = new JTextArea (20, 50);
		
	// Create the buttons, fields and objects to be used in the class
	private JButton holidaysButton, timetablesButton, brokenBusButton;
	private JButton shiftsButton;
	
	private JLabel requestHolidayMessage;
	private JButton holidayNextButton;
	private JTextField startDateField;
	private JTextField endDateField;
	
	private JLabel driverLabel = new JLabel ("");
	private JLabel messageLabel = new JLabel ("");
	
	private Driver driver;
	
	private Container contents = getContentPane();
		
	// Constructor
	public DriverInterface (Driver user)
  {
  	driver = user;
  	setTitle ("Bus driver interface");  	

  	contents.setLayout (new BorderLayout());
  	
  	// Create a menu panel on the top part of the window
  	JPanel menuPanel = new JPanel (new GridLayout (2, 1));
    contents.add (menuPanel, BorderLayout.NORTH);
    
    JPanel messagePanel = new JPanel (new GridLayout (1, 2));
    menuPanel.add (messagePanel);
    
    // Display the remaining days left for the driver
    driverLabel.setText ("Logged in as: " + driver.name());
    messagePanel.add (driverLabel);
    
    messageLabel.setHorizontalAlignment (JLabel.RIGHT);
    messagePanel.add (messageLabel);
        
    JPanel buttonsPanel = new JPanel (new GridLayout (1, 4));
		menuPanel.add (buttonsPanel);
    
    // Place the menu buttons into a grid
    holidaysButton = new JButton ("Manage holidays");
		buttonsPanel.add (holidaysButton);
		holidaysButton.addActionListener(this);
    
    shiftsButton = new JButton ("My shifts");
    shiftsButton.setEnabled(false);
		buttonsPanel.add (shiftsButton);
		shiftsButton.addActionListener(this);
    
    timetablesButton = new JButton ("Timetables");
    timetablesButton.setEnabled(false);
		buttonsPanel.add (timetablesButton);
		timetablesButton.addActionListener(this);
    
    brokenBusButton = new JButton ("Report broken bus");
    brokenBusButton.setEnabled(false);
		buttonsPanel.add (brokenBusButton);
		brokenBusButton.addActionListener(this);
		
		// Display welcome message
    contents.add (welcomeTextArea, BorderLayout.CENTER);
    welcomeTextArea.setEnabled (false);
    welcomeTextArea.setDisabledTextColor (Color.black);
    welcomeTextArea.append ("Welcome, " + driver.name() + "! " +
                            "Please choose one of the above options.");
        
    setDefaultCloseOperation (EXIT_ON_CLOSE);
    pack();
 } // constructor
 
	
	// Query the DB for the driver's remaining days
	private int remainingDays ()
	{
		int daysLeft = Driver.holidayAllowance - 
		driver.holiday_used();
		return daysLeft;
	}// remainingDays
 
 public void actionPerformed (ActionEvent event)
 {	 	
 	// On pressing the holidays button, the database is queried
 	// and the appropriate booked and past holidays are displayed.
 	if (event.getSource() == holidaysButton)
 	{
 		// Remove the welcome text area
 		contents.remove (welcomeTextArea);
		
		// Display the driver's remaining holidays
    messageLabel.setText ("Remaining holidays: " + remainingDays());
    		
		// Place the input fields and buttons on the left
		// and the results on the right
		JPanel holidayPanel = new JPanel (new GridLayout (1, 2));	
		contents.add (holidayPanel, BorderLayout.CENTER);
		
		JPanel holidayRequestPanel = new JPanel ();
		holidayRequestPanel.setLayout (new BoxLayout(holidayRequestPanel,
				BoxLayout.Y_AXIS));
		holidayPanel.add (holidayRequestPanel);
		
		JLabel requestHolidayLabel = new JLabel (
		                                "<html>Request new holiday<br><br></html>");
		requestHolidayLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		holidayRequestPanel.add (requestHolidayLabel);
		
		requestHolidayMessage = new JLabel ("<html>Please enter the dates and " +
		                                    "click Submit.<br><br><br></html>");
		requestHolidayMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
		holidayRequestPanel.add (requestHolidayMessage);
		
		Dimension requestPanelSize = new Dimension (100, 53);
		
		// Start and end date fields 
		JPanel startDatePanel = new JPanel ();
		startDatePanel.setLayout (new BoxLayout (startDatePanel, BoxLayout.Y_AXIS));
		startDatePanel.setPreferredSize (requestPanelSize);
		startDatePanel.setMaximumSize (requestPanelSize);
		//startDatePanel.add (Box.Filler (10, 10, 10));
		holidayRequestPanel.add (startDatePanel);
		JLabel startDateLabel = new JLabel ("Start date: ");
		startDatePanel.add (startDateLabel);
		startDateField = new JTextField (10);
		startDatePanel.add (startDateField);
		JLabel startHintLabel = new JLabel ("<html>dd/mm/yyyy<br><br></html>");
		startHintLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
		startDatePanel.add (startHintLabel);
		
		JPanel endDatePanel = new JPanel ();
		endDatePanel.setLayout (new BoxLayout (endDatePanel, BoxLayout.Y_AXIS));
		endDatePanel.setPreferredSize (requestPanelSize);
		endDatePanel.setMaximumSize (requestPanelSize);
		holidayRequestPanel.add (endDatePanel);
		JLabel endDateLabel = new JLabel ("End date: ");
		endDatePanel.add (endDateLabel);
		endDateField = new JTextField (10);
		endDatePanel.add (endDateField);
		JLabel endHintLabel = new JLabel ("<html>dd/mm/yyyy<br><br></html>");
		endHintLabel.setFont(new Font("SansSerif", Font.PLAIN,9));
		endDatePanel.add (endHintLabel);
		
		// Submit button
		holidayNextButton = new JButton("Submit");
		holidayRequestPanel.add (holidayNextButton);
		holidayNextButton.addActionListener(this);
		
		// Results panel on the right side
		holidayPanel.add (resultTextArea);		
		resultTextArea.setEnabled (false);
    resultTextArea.setDisabledTextColor (Color.black);
 		
 	}// if
 	
 	else if (event.getSource() == holidayNextButton)
 	{
 	// Verify the correctness of the input dates
 	try
 	{ 		
 		String startDateString = startDateField.getText();
 		String endDateString = endDateField.getText();
 		
 		if (startDateString.length() > 10 || endDateString.length() > 10)
 			throw new IllegalArgumentException 
 			                     ("Invalid date format, please check and try again.");
 			
 		String[] startDateComponents = startDateString.split ("/");
 		String[] endDateComponents = endDateString.split ("/");
 		
 		if (startDateComponents.length != 3 || endDateComponents.length != 3)
 			throw new IllegalArgumentException 
 			                     ("Invalid date format, please check and try again.");	
 			
 	  GregorianCalendar startDate = new GregorianCalendar (
 	                                Integer.parseInt (startDateComponents[2]), 
 	                                Integer.parseInt (startDateComponents[1]) - 1,
 	                                Integer.parseInt (startDateComponents[0]));
 	  GregorianCalendar endDate = new GregorianCalendar (
 	                                  Integer.parseInt (endDateComponents[2]), 
 	                                  Integer.parseInt (endDateComponents[1]) - 1,
 	                         	        Integer.parseInt (endDateComponents[0]));
    int[] holidayRequestResult = driver.request_holiday (startDate, endDate);
 	 
 	  // Display error message if the request is denied
 	  if (holidayRequestResult[0] == 0)
 	  {
 	 		String message = "<html>Sorry, your request could not be approved.<br>";
 	 		message += "More information is provided to the right.<br><br></html>";
 	 		requestHolidayMessage.setText(message);
 	 		
 	 		resultTextArea.setText ("");
 	 		resultTextArea.append ("Sorry, your request could not be approved.\n" +
 	 		                  "The list below shows the result of your request.\n\n");
 	 		
 	 		boolean insufficient_allowance = true;
 	 		
 	 		GregorianCalendar date = (GregorianCalendar) startDate.clone();
 	 		for (int day = 1; day < holidayRequestResult.length; day++)
 	 		{
 	 		  resultTextArea.append (date.get(Calendar.DAY_OF_MONTH) + "/");
 	 		  resultTextArea.append ((date.get(Calendar.MONTH)+1) + "/");
 	 		  resultTextArea.append (date.get(Calendar.YEAR) + " - ");
 	 		  switch (holidayRequestResult[day])
 	 		  {
 	 		    case 0: resultTextArea.append ("unavailable for booking\n");
 	 		            insufficient_allowance = false; break;
 	 		    case 1: resultTextArea.append ("already booked by you\n"); break;
 	 		    case 2: resultTextArea.append ("available for booking\n"); break;
 	 		    default: break;
 	 		  }
 	 		
 	 		  date.add(GregorianCalendar.DAY_OF_MONTH, 1);
 	 		}
 	 		
 	 		if (insufficient_allowance)
 	 		  resultTextArea.append ("\nYou have insufficient holiday allowance\n " +
 	 		                         "to book this holiday.");
 	 		
 	  }// if
 	 
 	  // If the request is approved, display a message and
 	  // update the remaining days
 	  else
 	  {
  		requestHolidayMessage.setText(
  		                   "<html>Your request was approved.<br><br><br></html>");
  		
  		resultTextArea.setText ("");
  		int remaining_days = remainingDays();
 	 		resultTextArea.append ("Your holiday request was approved.\n\n" + 
 	 		                       "Holiday from " + startDateString + " to " +
 	 		                       endDateString + " has \nbeen booked for you.\n\n" +
 	 		                       "You have " + remaining_days + " days remaining.");
 	 		messageLabel.setText ("Remaining holidays: " + remaining_days);
 	 		
 	  }// else
 	}// try
 	
 	// report any errors that occurred
	catch (Exception e)
	{
		resultTextArea.setText ("");
		requestHolidayMessage.setText("<html>"+e.getMessage()+"<br><br><br>");
	}//catch
 	 
 	}// else if
 	
 	// On pressing the timetables button, the timetable for
 	// the driver is displayed
 	else if (event.getSource() == timetablesButton)
 	{
 		
 	}// else if
 	
 	// On pressing the broken bus button, the supervisor is
 	// informed
 	else if (event.getSource() == brokenBusButton)
 	{
 		
 	}// else if
 	
 	else if (event.getSource() == shiftsButton)
 	{
 		
 	}// else if
 }// actionPerformed

    
}// class DriverInterface
