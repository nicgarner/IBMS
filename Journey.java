import java.util.Date;
import java.util.GregorianCalendar;

/** 
 * Creates a journey for IBMS rostering. A journey is a combination of a date 
 * and a service. The combination of a service and a date will give a unique 
 * pair, called a journey. Implemented by Adam and Nic.
*/

public class Journey 
{
	private Service service;
	private GregorianCalendar date;
	
	/**
	 * Represents a journey in the system. 
	 * @param serviceID the service's ID from the database (a column of a timetable)
	 * @param date the journey's date
	 */
	public Journey (int serviceID, GregorianCalendar journeyDate)
	{
		service = new Service(serviceID);
		date = (GregorianCalendar) journeyDate.clone();
	}// constructor
	
	/**
	 * Gets the service ID of the journey
	 * @return the service ID of the journey
	 */	
	public Service getService ()
	{
		return service;
	}// getJourneyID
	
	/**
	 * Gets the date of the journey
	 * @return the date of the journey
	 */	
	public GregorianCalendar getDate ()
	{
		return date;
	}// getJourneyID
	
	/**
	 * Gets the start time of the journey.
	 * @return the start time of the journey
	 */
  public int startTime()
  {
    return service.startTime();
  }
	
	/**
	 * Gets the end time of the journey.
	 * @return the end time of the journey
	 */
  public int endTime()
  {
    return service.endTime();
  }
	
	/**
	 * Gets the duration of the journey in minutes.
	 * @return the duration of the journey in minutes
	 */
  public int duration()
  {
    return service.duration();
  }
	
	/**
	 * Returns a string representation of the journey.
	 * @return string representation of the journey
	 */
	public String toString ()
	{
		return "Service: " + service + ", " +
		       "Date: " + Timetable.dateToString(date);
	}// toString
	
	/**
	 * Returns a more detailed string representation of the journey.
	 * @return string representation of the journey
	 */
	public String toFullString ()
	{
		return "Service: " + service.toFullString() + ", " +
		       "Date: " + Timetable.dateToString(date);
	}// toString
	
}// class
