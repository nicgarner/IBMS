import java.util.Date;

/** 
 *Creates a stretch for IBMS rostering. The combination of a journey
 * and a date will give a unique pair, a stretch. Implemented by Adam.
*/

public class Stretch 
{
	private int busID, driverID, journey;
	private Date journeyDate;
	
	/**
	 * Represents a stretch in the system. 
	 * @param journeyID the journey's ID from the database (a column of a timetable)
	 * @param date the journey's date
	 */
	public Stretch (int journeyID, Date date)
	{
		busID = -1;
		driverID = -1;
		journey = journeyID;
		journeyDate = date;
	}// constructor
	
	/**
	 * Assigns a bus ID to the stretch once the buses are scheduled
	 * @param IDToSet the ID of the bus we want to assign to the stretch
	*/
	public void setBusID (int IDToSet)
	{
		busID = IDToSet;
	}// setBusID
	
	/**
	 * Assigns a driver ID to the stretch once the drivers are scheduled
	 * @param IDToSet the ID of the driver to be assigned to the stretch
	 */	
	public void setDriverID (int IDToSet)
	{
		driverID = IDToSet;
	}// setDriverID
	
	/**
	 * Returns the stretch's assigned bus ID
	 * @return the bus ID of the stretch
	 */	
	public int getBusID ()
	{
		return busID;
	}// getBusID
	
	/**
	 * Returns the stretch's assigned driver ID
	 * @return the driver's ID of the stretch
	 */	
	public int getDriverID ()
	{
		return driverID;
	}// getDriverID
	
	/**
	* Gets the journey ID of the stretch
	* @return the journey ID of the stretch
	*/	
	public int getJourneyID ()
	{
		return journey;
	}// getJourneyID
	
	
	public String toString ()
	{
		return "Journey ID: " + journey + " Journey date: " + journeyDate
		+ "\nDriver ID: " + driverID + "Bus ID: " + busID;
	}// toString
	
}// class
