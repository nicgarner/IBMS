/**
 * Assigns a driver to the given stretch. A driver can
 * work at most 2 stretches per day and only if he/she
 * is available (so do not have a holiday booked).
 * For fairness, the worked hours are tracked to help
 * rostering. Implemented by Adam.
 */

import java.util.GregorianCalendar;
import java.util.Date;
import java.util.ArrayList;

public class DriverScheduler 
{
	/* Arrays for available driver IDs, the number of stretches
	   they worked on the given day and the end times of their
	   stretches
	*/
	private static Driver[] availableDrivers;
	private static int[] stretchesWorked;
	private static int[] driverEndTime;
	
	/**
	 * The method takes a two dimensional array list of rosters in which
	 * the first dimension is the day of the week, and the second is the
	 * list of stretches on that day.
	 * @param roster the array list of days and stretches on that day
	 */
	public static void generateSchedule 
	(ArrayList<ArrayList<Stretch>> roster)
	{
		for (int day = 0; day < roster.size(); day++)
		{
			availableDrivers = Driver.get_available_drivers
			 (roster.get(day).get(0).getDate());
			 
			 stretchesWorked = new int[availableDrivers.length];
			 driverEndTime = new int[availableDrivers.length];
			
			for (int s = 0; s < roster.get(day).size(); s++)
			{
				Stretch stretch = roster.get(day).get(s);
				
				for (int driver = 0; driver < availableDrivers.length; driver++)
				{
					if (driverEndTime[driver] < stretch.startTime() && 
						stretchesWorked[driver] < 2 &&					
						((driverEndTime[driver] - stretch.startTime() >= 60) ||
						(stretchesWorked[driver] == 0))
					 	)
					{
						stretch.setDriver (availableDrivers[driver]);
						driverEndTime[driver] = stretch.endTime ();		
						stretchesWorked[driver]++;						
						break;
					}
				}
			}
		}
	}// generateSchedule
	
}// class

