/**
 * Creates a stretch for the IBMS system. A stretch is a bus and a driver allocated
 * to a journey. Implemented by Adam.
*/

public class Stretch
{
	// Duration is given in minutes. startTime and endTime are given in minutes
	// past midnight.
	private int duration, busID, driverID, startTime, endTime;
	
	/**
	 * Creates a stretch.
	 * @param start the start time of the stretch in minutes after midnight
	 * @param end the end time of the stretch in minutes after midnight
	*/
	public Stretch (int start, int end)
	{
		busID = -1;
		driverID = -1;
		startTime = start;
		endTime = end;
		duration = startTime - endTime;
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
	
	public String toString ()
	{
		return "Start time: " + startTime + " End time: " + endTime + " Duration: " + duration + 
			" Bus ID: " + busID + " Driver ID: " + driverID;
	}// toString
}// class 
