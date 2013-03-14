/**
 * Creates a stretch for the IBMS system. A stretch is a bus and a driver allocated
 * to a journey. Implemented by Adam.
*/

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;

public class Stretch
{
	private int busID, driverID;
	private Journey[] journeys;
	private GregorianCalendar date;
		
	/**
	 * Creates a stretch.
	 * @param start the start time of the stretch in minutes after midnight
	 * @param end the end time of the stretch in minutes after midnight
	*/
	public Stretch 	(Journey journey)
	{
		busID = -1;
		driverID = -1;
		date = journey.getDate();
	}// constructor
	
	/**
	 * Puts the journeys related to the stretch into an array.
	 @param a Journey object from the loop of the Roster class
	 @return puts the journey into the list of relevant journeys
	 */
	public Journey[] getJourneys (Journey journey)
	{
		ArrayList<Journey> journeysList = new ArrayList<Journey>();
		journeys = new Journey[journeysList.size()];
		return journeysList.toArray (journeys);
	}// getJourney
	
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
	 * Returns the date of the stretch
	 * @return the date of the stretch it is called by
	 */
	public GregorianCalendar getDate ()
	{
		return date;
	}// getDate
	
	/**
	 * Gets the start time of the stretch
	 * @return the start time of the first element of the
	 * relevant journeys
	 */
	public int startTime ()
	{
		return journeys[0].startTime();
	}// startTime
	
	/**
	 * Returns the end time of the stretch
	 * @return the end time of the last element of the
	 * relevant journeys
	 */
	 public int endTime ()
	 {
	 	return journeys[journeys.length - 1].endTime();
	 }// endTime
	 
	 /**
	 * Calculates the duration of a stretch
	 * @return the difference between the end time of the last
	 * journey and the start time of the first journey
	 */
	public int duration ()
	{
		return startTime () - endTime ();
	}// duration
	
	
	public String toString ()
	{
		return "Start time: " + startTime() + " End time: " + endTime()
		 + " Duration: " + duration() + 
			" Bus ID: " + busID + " Driver ID: " + driverID;
	}// toString
}// class 
