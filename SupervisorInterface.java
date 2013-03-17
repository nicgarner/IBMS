import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;

/**
 * Provides the user interface for the supervisor.
 *
 * Implemented by Nic, based on DriverInterface.
 */
public class SupervisorInterface extends JFrame implements ActionListener
{
		
	// Define a JTextArea for the results to be displayed
	private final JTextArea resultTextArea = new JTextArea (20, 30);
	private final JTextArea welcomeTextArea = new JTextArea (20, 50);
		
	// Create the buttons, fields and objects to be used in the class
	private JButton rostersButton, timetablesButton, driversButton, otherButton;
	
	private JLabel generateRosterMessage;
	private JButton viewRosterButton;
	private JButton generateRosterButton;
	private JButton approveRosterButton;
	private JTextField viewStartDateField;
	private JTextField viewEndDateField;
	private JTextField startDateField;
	private JTextField endDateField;
	
	private JLabel driverLabel = new JLabel ("");
	private JLabel messageLabel = new JLabel ("");
	private JLabel viewMessage = new JLabel ("");
	
	private Container contents = getContentPane();
		
	// Constructor
	public SupervisorInterface ()
  {
  	setTitle ("Supervisor interface");

  	contents.setLayout (new BorderLayout());
		contents.setMaximumSize(new Dimension(570,490));
		contents.setPreferredSize(new Dimension(570,490));
  	
  	// Create a menu panel on the top part of the window
  	JPanel menuPanel = new JPanel (new GridLayout (2, 1));
    contents.add (menuPanel, BorderLayout.NORTH);
    
    JPanel messagePanel = new JPanel (new GridLayout (1, 2));
    menuPanel.add (messagePanel);
    
    // Display the remaining days left for the driver
    driverLabel.setText ("Logged in as supervisor");
    messagePanel.add (driverLabel);
    
    messageLabel.setHorizontalAlignment (JLabel.RIGHT);
    messagePanel.add (messageLabel);
        
    JPanel buttonsPanel = new JPanel (new GridLayout (1, 4));
		menuPanel.add (buttonsPanel);
    
    // Place the menu buttons into a grid
    rostersButton = new JButton ("Manage rosters");
		buttonsPanel.add (rostersButton);
		rostersButton.addActionListener(this);
    
    driversButton = new JButton ("Manage drivers");
    driversButton.setEnabled(false);
		buttonsPanel.add (driversButton);
		driversButton.addActionListener(this);
    
    timetablesButton = new JButton ("Timetables");
    timetablesButton.setEnabled(false);
		buttonsPanel.add (timetablesButton);
		timetablesButton.addActionListener(this);
    
    otherButton = new JButton ("Other");
    otherButton.setEnabled(false);
		buttonsPanel.add (otherButton);
		otherButton.addActionListener(this);
		
		// Display welcome message
    contents.add (welcomeTextArea, BorderLayout.CENTER);
    welcomeTextArea.setEnabled (false);
    welcomeTextArea.setDisabledTextColor (Color.black);
    welcomeTextArea.append ("Welcome, supervisor! " +
                            "Please choose one of the above options.");
        
    setDefaultCloseOperation (EXIT_ON_CLOSE);
    pack();
 } // constructor
 
 public void actionPerformed (ActionEvent event)
 {	 	
 	// On pressing the holidays button, the database is queried
 	// and the appropriate booked and past holidays are displayed.
 	if (event.getSource() == rostersButton)
 	{
 		// Remove the welcome text area
 		contents.remove (welcomeTextArea);
		
		// Display the driver's remaining holidays
    // messageLabel.setText ("Remaining holidays: " + remainingDays());
    		
		// Place the input fields and buttons on the left
		// and the results on the right
		JPanel rosterPanel = new JPanel();
		rosterPanel.setLayout(new BorderLayout());
		contents.add(rosterPanel, BorderLayout.CENTER);
		
		JLabel manageRosterLabel = new JLabel ("Manage Rosters");
		manageRosterLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		rosterPanel.add(manageRosterLabel, BorderLayout.NORTH);
		
		JPanel rosterOptionsPanel = new JPanel();
		rosterOptionsPanel.setLayout(new BorderLayout());
		rosterPanel.add(rosterOptionsPanel, BorderLayout.WEST);
		
		JPanel viewRosterPanel = new JPanel();
		viewRosterPanel.setLayout(new BorderLayout());
		rosterOptionsPanel.add(viewRosterPanel, BorderLayout.NORTH);
    
    JPanel viewRosterMessagePanel = new JPanel();
    viewRosterMessagePanel.setLayout(new GridLayout(2,1));
    viewRosterPanel.add(viewRosterMessagePanel, BorderLayout.NORTH);
    
    JLabel viewRosterLabel = new JLabel("<html><br>View an existing roster</html>");
		viewRosterLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		viewRosterMessagePanel.add(viewRosterLabel);
		
		viewMessage.setText("<html>Enter dates for the desired <br>" +
		                    "period and click View.</html>");
		viewMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
		viewMessage.setMaximumSize(new Dimension(80,50));
		viewMessage.setPreferredSize(new Dimension(80,50));
    viewRosterMessagePanel.add(viewMessage);
    
    Dimension requestPanelSize = new Dimension (90, 53);
    // Start and end date fields 
		JPanel viewStartDatePanel = new JPanel ();
		viewStartDatePanel.setLayout (new BoxLayout (viewStartDatePanel, BoxLayout.Y_AXIS));
		viewStartDatePanel.setPreferredSize (requestPanelSize);
		viewStartDatePanel.setMaximumSize (requestPanelSize);
		viewRosterPanel.add (viewStartDatePanel, BorderLayout.WEST);
		JLabel viewStartDateLabel = new JLabel ("Start date: ");
		viewStartDatePanel.add (viewStartDateLabel);
		viewStartDateField = new JTextField (10);
		viewStartDatePanel.add (viewStartDateField);
		JLabel viewStartHintLabel = new JLabel ("<html>dd/mm/yyyy<br><br></html>");
		viewStartHintLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
		viewStartDatePanel.add (viewStartHintLabel);
		
		JPanel viewEndDatePanel = new JPanel ();
		viewEndDatePanel.setLayout (new BoxLayout (viewEndDatePanel, BoxLayout.Y_AXIS));
		viewEndDatePanel.setPreferredSize (requestPanelSize);
		viewEndDatePanel.setMaximumSize (requestPanelSize);
		viewRosterPanel.add (viewEndDatePanel, BorderLayout.EAST);
		JLabel viewEndDateLabel = new JLabel ("End date: ");
		viewEndDatePanel.add (viewEndDateLabel);
		viewEndDateField = new JTextField (10);
		viewEndDatePanel.add (viewEndDateField);
		JLabel viewEndHintLabel = new JLabel ("<html>dd/mm/yyyy<br><br></html>");
		viewEndHintLabel.setFont(new Font("SansSerif", Font.PLAIN,9));
		viewEndDatePanel.add (viewEndHintLabel);
		
		JPanel viewRosterButtonsPanel = new JPanel();
    viewRosterButtonsPanel.setLayout(new BorderLayout());
    viewRosterPanel.add (viewRosterButtonsPanel, BorderLayout.SOUTH);
		
		// View roster button
		viewRosterButton = new JButton("View");
		viewRosterButtonsPanel.add (viewRosterButton, BorderLayout.WEST);
		viewRosterButton.addActionListener(this);
    
    JPanel generateRosterPanel = new JPanel();
		generateRosterPanel.setLayout(new BorderLayout());
		rosterOptionsPanel.add(generateRosterPanel, BorderLayout.CENTER);
    
    JPanel generateRosterMessagePanel = new JPanel();
    generateRosterMessagePanel.setLayout(new GridLayout(2,1));
    generateRosterPanel.add(generateRosterMessagePanel, BorderLayout.NORTH);
    
    JLabel generateRosterLabel = new JLabel("<html><br>Generate a new roster</html>");
		generateRosterLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		generateRosterMessagePanel.add(generateRosterLabel);
		
		JLabel generateMessage = new JLabel("<html>Please enter the dates for the" +
		                                    "<br> new roster and click Generate." +
		                                    "<br><br></html>");
		generateMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
    generateRosterMessagePanel.add(generateMessage);
    
    // Start and end date fields 
		JPanel startDatePanel = new JPanel ();
		startDatePanel.setLayout (new BoxLayout (startDatePanel, BoxLayout.Y_AXIS));
		startDatePanel.setPreferredSize (requestPanelSize);
		startDatePanel.setMaximumSize (requestPanelSize);
		//startDatePanel.add (Box.Filler (10, 10, 10));
		generateRosterPanel.add (startDatePanel, BorderLayout.WEST);
		JLabel startDateLabel = new JLabel ("Start date: ");
		startDatePanel.add (startDateLabel);
		startDateField = new JTextField (10);
		startDatePanel.add (startDateField);
		JLabel startHintLabel = new JLabel ("<html>dd/mm/yyyy<br><br><br><br></html>");
		startHintLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
		startDatePanel.add (startHintLabel);
		
		JPanel endDatePanel = new JPanel ();
		endDatePanel.setLayout (new BoxLayout (endDatePanel, BoxLayout.Y_AXIS));
		endDatePanel.setPreferredSize (requestPanelSize);
		endDatePanel.setMaximumSize (requestPanelSize);
		generateRosterPanel.add (endDatePanel, BorderLayout.EAST);
		JLabel endDateLabel = new JLabel ("End date: ");
		endDatePanel.add (endDateLabel);
		endDateField = new JTextField (10);
		endDatePanel.add (endDateField);
		JLabel endHintLabel = new JLabel ("<html>dd/mm/yyyy<br><br><br><br></html>");
		endHintLabel.setFont(new Font("SansSerif", Font.PLAIN,9));
		endDatePanel.add (endHintLabel);
    
    JPanel generateRosterButtonsPanel = new JPanel();
    generateRosterButtonsPanel.setLayout(new BorderLayout());
    generateRosterPanel.add (generateRosterButtonsPanel, BorderLayout.SOUTH);
    
    generateRosterMessage = new JLabel("");
		generateRosterMessage.setMaximumSize(new Dimension(80,50));
		generateRosterMessage.setPreferredSize(new Dimension(80,50));
		generateRosterMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
    generateRosterButtonsPanel.add(generateRosterMessage, BorderLayout.NORTH);
    
    // Generate roster button
		generateRosterButton = new JButton("Generate");
		generateRosterButtonsPanel.add (generateRosterButton, BorderLayout.WEST);
		generateRosterButton.addActionListener(this);
		
		// Approve roster button
		approveRosterButton = new JButton("Approve");
		approveRosterButton.setEnabled(false);
		generateRosterButtonsPanel.add (approveRosterButton, BorderLayout.EAST);
		approveRosterButton.addActionListener(this);
		
		// Results panel on the right side
		JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
		resultScrollPane.setMaximumSize(new Dimension(100,400));
		resultScrollPane.setPreferredSize(new Dimension(100,400));
		rosterPanel.add(resultScrollPane, BorderLayout.EAST);
		resultTextArea.setEnabled (false);
    resultTextArea.setDisabledTextColor (Color.black);
		
		pack();

 	}// else if
 	
 	else if (event.getSource() == viewRosterButton)
 	{
    try
    {
      viewMessage.setText("<html>Enter dates for the desired <br>" +
		                      "period and click View.</html>");
      
      String startString = viewStartDateField.getText();
      String endString = viewEndDateField.getText();
      
      GregorianCalendar start = Timetable.parseDate(startString);
      GregorianCalendar end = Timetable.parseDate(endString);
      
      ArrayList<ArrayList<Stretch>> roster = Timetable.load_roster(start, end);
      resultTextArea.setText(Timetable.print_roster(roster));
    }
    catch (Exception e)
    {
      resultTextArea.setText("");
  		viewMessage.setText("<html>"+e.getMessage()+"</html>");
  		
  		// disable approve button
  		approveRosterButton.setEnabled(false);
    }
      
 	}// else if

 	
 	else if (event.getSource() == generateRosterButton)
 	{
    try
    {
      viewMessage.setText("<html>Enter dates for the desired <br>" +
		                      "period and click View.</html>");
      
      String startString = startDateField.getText();
      String endString = endDateField.getText();
      
      GregorianCalendar start = Timetable.parseDate(startString);
      GregorianCalendar end = Timetable.parseDate(endString);
      
      Journey[] journeys = Timetable.get_journeys(start, end);
      ArrayList<ArrayList<Stretch>> roster = Timetable.generate_roster(journeys);
      BusScheduler.generateSchedule(roster);
      DriverScheduler.generateSchedule(roster);
      resultTextArea.setText(Timetable.print_roster(roster));
      
      // enable the approve button
      approveRosterButton.setEnabled(true);
    }
    catch (Exception e)
    {
      resultTextArea.setText("");
  		generateRosterMessage.setText("<html>"+e.getMessage()+"</html>");
  		
  		// disable approve button
  		approveRosterButton.setEnabled(false);
    }
      
 	}// else if

 	// On pressing the timetables button, the timetable for
 	// the driver is displayed
 	else if (event.getSource() == timetablesButton)
 	{
 		
 	}// else if
 	
 	// On pressing the broken bus button, the supervisor is
 	// informed
 	else if (event.getSource() == driversButton)
 	{
 		
 	}// else if
 	
 	else if (event.getSource() == otherButton)
 	{
 		
 	}// else if
 }// actionPerformed

    
}// class DriverInterface
