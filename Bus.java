/**
 * Creates Bus objects to be used by the bus scheduler. Implemented by Adam.
 */

import java.util.GregorianCalendar;

public class Bus
{
	private int busID;
	private String fleetNo;
	/** 
	 * The constructor creates bus objects given a fleet number.
	 * @param fleet_no the ID of the bus to be created
	 */
	public Bus (int id)
	{
		try
    {
      busID = id;
      fleetNo = BusInfo.busNumber (id);
    }
    catch (InvalidQueryException e) { throw e; }
	}
	
	/**
	 * Gets the available buses that can be used by the scheduler.
	 * @param date the date for which we want to retrieve the availability for
	 * @return an array of the available buses
	 */
	public static Bus[] getAvailableBuses (GregorianCalendar date)
	{
		int[] buses = BusInfo.getBuses();
		Bus[] availableBuses = null;
		
		for (int i = 0; i < buses.length; i++)
		{
			if (BusInfo.isAvailable (buses[i], date.getTime()))
				for (int j = 0; j < buses.length; j++)
					availableBuses[j] = new Bus (buses[i]);
		}
		return availableBuses;
	}// getAvailableBuses
	
	/**
	 * Returns the bus' ID
	 */
	public int getID ()
	{
		return busID;
	}
	
	/**
	 * Returns the bus' fleet number
	 */
	public String getFleetNo ()
	{
		return fleetNo;
	}
	
	public String toString ()
	{
		return "Bus ID: " + busID + " Fleet number: " + fleetNo;
	}
	
	
}// class
