/**
 * Creates a stretch for the IBMS system. A stretch is a bus and a driver allocated
 * to a journey. Implemented by Adam.
*/

import java.util.GregorianCalendar;
import java.util.Date;
import java.util.ArrayList;

public class Stretch
{
	private Bus bus;
	private Driver driver;
	private ArrayList <Journey> journeys;
	private GregorianCalendar date;
		
	/**
	 * Creates a stretch with a single journey, for use in generating a roster.
	 * BusID and driverID are not set here, they have specific methods.
	 *
	*/
	public Stretch (GregorianCalendar stretchDate)
	{
		bus = null;
		driver = null;
		date = stretchDate ;
		journeys = new ArrayList<Journey>();
        }// constructor
	
	/**
	 * Instatiates a stretch saved in the database.
	 * 
	 * @param  stretchID  the id number of the stretch record in the database
	 */
	public Stretch (int stretchID)
	{
	  int busID = database.busDatabase.find_id("bus_id", "stretch", "stretch_id", 
	                                           stretchID);
	  bus = busID == -1 ? null : new Bus (busID);
	  
	  int driverID = database.busDatabase.find_id("driver_id", "stretch", 
	                                              "stretch_id", stretchID);
	  driver = driverID == -1 ? null : new Driver ("" + driverID);

	  date = new GregorianCalendar();
	  date.setTime(database.busDatabase.get_date("stretch", stretchID, "date"));
	  
	  journeys = new ArrayList<Journey>();
	  int[] journey_ids = database.busDatabase.select_ids("journey_id", "journey", 
	                                                      "stretch_id", stretchID,
	                                                      "journey_id");
	  for (int j = 0; j < journey_ids.length; j++)
	    journeys.add(new Journey(journey_ids[j], date, true));
	}
	
	/**
	 * Puts the journeys related to the stretch into an array.
	 *
	 * @return puts the journey into the list of relevant journeys
	 */
	public Journey[] getJourneys ()
	{
		Journey[] journeyArray = new Journey[journeys.size()];		
		return journeys.toArray (journeyArray);
	}// getJourney
	
	/**
	 * Adds a journey to the array of journeys.
	 */
	public void addJourney (Journey journey)
	{
		journeys.add (journey);
	}// addJourney
	
	/**
	 * Assigns a bus ID to the journey once the buses are scheduled
	 * @param IDToSet the ID of the bus we want to assign to the journey
	*/
	public void setBus (Bus busToSet)
	{
		bus = busToSet;
	}// setBus
	
	/**
	 * Assigns a driver ID to the journey once the drivers are scheduled
	 * @param IDToSet the ID of the driver to be assigned to the journey
	 */	
	public void setDriver (Driver driverToSet)
	{
		driver = driverToSet;
	}// setDriverID
	
	/**
	 * Returns the journey's assigned bus ID
	 * @return the bus ID of the journey
	 */	
	public Bus getBus ()
	{
		return bus;
	}// getBusID
	
	/**
	 * Returns the journey's assigned driver ID
	 * @return the driver's ID of the journey
	 */	
	public Driver getDriver ()
	{
		return driver;
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
	      if(journeys.size() == 0)
                 return 0 ;
              else 
              return journeys.get(0).startTime();
	}// startTime
	
	/**
	 * Returns the end time of the stretch
	 * @return the end time of the last element of the
	 * relevant journeys
	 */
	 public int endTime ()
	 {
	 	if(journeys.size() == 0)
                  return 0 ;
                else {
                int size = journeys.size();
	 	return journeys.get(size - 1).endTime(); 
                }
	 }// endTime
	 
	 /**
	 * Calculates the duration of a stretch
	 * @return the difference between the end time of the last
	 * journey and the start time of the first journey
	 */
	public int duration ()
	{
		int startTime = startTime();
    int endTime = endTime();
    
    // if service crosses midnight can't do a simple subtraction
    if (endTime < startTime)
      return 1440 - startTime + endTime;
    else
      return endTime - startTime;
	}// duration
	
	
	public String toString ()
	{
		return "Start time: " + startTime() + " End time: " + endTime()
		 + " Duration: " + duration() + 
			" Bus: " + bus + " Driver: " + driver;
	}// toString
}// class 
