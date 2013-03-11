import java.util.Date;
import java.util.GregorianCalendar;

/** 
 * Creates a journey for IBMS rostering. A journey is a combination of a time and a service. 
 * The combination of a journey
 * and a date will give a unique pair, a stretch. Implemented by Adam.
*/

public class Journey 
{
	private int busID, driverID, service;
	private GregorianCalendar journeyDate;
	
	/**
	 * Represents a journey in the system. 
	 * @param serviceID the service's ID from the database (a column of a timetable)
	 * @param date the journey's date
	 */
	public Journey (int serviceID, GregorianCalendar date)
	{
		busID = -1;
		driverID = -1;
		service = serviceID;
		journeyDate = (GregorianCalendar) date.clone();
	}// constructor
	
	/**
	 * Assigns a bus ID to the journey once the buses are scheduled
	 * @param IDToSet the ID of the bus we want to assign to the journey
	*/
	public void setBusID (int IDToSet)
	{
		busID = IDToSet;
	}// setBusID
	
	/**
	 * Assigns a driver ID to the journey once the drivers are scheduled
	 * @param IDToSet the ID of the driver to be assigned to the journey
	 */	
	public void setDriverID (int IDToSet)
	{
		driverID = IDToSet;
	}// setDriverID
	
	/**
	 * Returns the journey's assigned bus ID
	 * @return the bus ID of the journey
	 */	
	public int getBusID ()
	{
		return busID;
	}// getBusID
	
	/**
	 * Returns the journey's assigned driver ID
	 * @return the driver's ID of the journey
	 */	
	public int getDriverID ()
	{
		return driverID;
	}// getDriverID
	
	/**
	* Gets the service ID of the journey
	* @return the service ID of the journey
	*/	
	public int getServiceID ()
	{
		return service;
	}// getJourneyID
	
	
	public String toString ()
	{
		return "Service ID: " + service + ", Journey date: " + 
		       Timetable.dateToString(journeyDate) + ", Driver ID: " + driverID + 
		       ", Bus ID: " + busID;
	}// toString
	
}// class
