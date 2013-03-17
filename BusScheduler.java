/**
 * Assigns a bus to the given stretch. Concurrent stretches cannot
 * have the same bus assigned to them and each stretch must have a bus
 * assigned to it. Implemented by Adam.
 */

import java.util.GregorianCalendar;
import java.util.Date;
import java.util.ArrayList;

public class BusScheduler 
{
	// Arrays for available bus IDs and the buses' assigned stretchs' 
	//end times
	private static Bus[] availableBuses;
	private static int[] busEndTime;
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
			availableBuses = Bus.getAvailableBuses
			 (roster.get(day).get(0).getDate());
			 
			 busEndTime = new int[availableBuses.length];
			
			for (int s = 0; s < roster.get(day).size(); s++)
			{
				Stretch stretch = roster.get(day).get(s);
				
				for (int bus = 0; bus < availableBuses.length; bus++)
				{
					if (busEndTime[bus] < stretch.startTime())
					{
						stretch.setBus (availableBuses[bus]);
						busEndTime[bus] = stretch.endTime ();
						
						break;
					}
				}
			}
		}
	}// generateSchedule
	
	
}// class

