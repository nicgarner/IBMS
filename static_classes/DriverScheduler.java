package static_classes;
import wrapper.*;
import objects.*;

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
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

public class DriverScheduler 
{
	/* Put all the details of a driver into a 2-dimensional
	 * array list. The first dimension is the Driver object 
	 * itself, the second is an Integer array of 3:
	 *	0 is the stretches worked by the driver
	 *	1 is the driver's assigned stretch's end time
	 *	2 is the minutes worked by the driver in the given roster
	 */
	   
	 private static ArrayList <DriverEntry<Driver, Integer[]>> drivers = 
	 new ArrayList <DriverEntry<Driver, Integer[]>> ();
	
	/**
	 * The method takes a two dimensional array list of rosters in which
	 * the first dimension is the day of the week, and the second is the
	 * list of stretches on that day.
	 * @param roster the array list of days and stretches on that day
	 */
	public static void generateSchedule (ArrayList<ArrayList<Stretch>> roster)
	{
		populateTable ();
		DriverComparator comparator = new DriverComparator ();
		for (int day = 0; day < roster.size(); day++)
		{
		
		  // reset driver busy time and stretches worked to 0
		  Iterator <DriverEntry<Driver, Integer[]>>driverIt = drivers.iterator();
		  while (driverIt.hasNext ())
		  {
			  DriverEntry<Driver, Integer[]> driver = driverIt.next();	
			  Integer [] values = driver.getValue ();
			  values[1] = values[0] = 0;
		  }// for
		
		  // sort drivers by time worked so far						
		  Collections.sort (drivers, comparator);		 
		  
		  // assign a driver to each stretch in the current day
			for (int s = 0; s < roster.get(day).size(); s++)
			{
				Stretch stretch = roster.get(day).get(s);
				driverIt = drivers.iterator();
				while (driverIt.hasNext ())
				{
				  // get drivers details into local vars to access more easily
					DriverEntry<Driver, Integer[]> driver = driverIt.next();	
					Integer [] values = driver.getValue ();
					Driver theDriver = driver.getKey();
					
					// check if this driver can work this stretch
					if (values[1] < stretch.startTime() && // not currently driving
   						values[0] < 2 &&  // worked less than two stretches today
   						theDriver.available(stretch.getDate()) &&	// not on holiday!
    					((values[0] == 0) || // either driver hasn't worked yet today or
    					(stretch.startTime() - values[1] >= 60))) // had a break
					{	
					  // assign driver to stretch and update values
						stretch.setDriver (driver.getKey ());		
						values[0]++;
						values[1] = stretch.endTime ();
						values[2] += stretch.duration ();					
						break; // don't need to consider more drivers
					}// if
				}// while
			}// for
		}// for

	}// generateSchedule
	
	
	
	// helper method to populate drivers table
	private static void populateTable ()
	{
		Driver[] allDrivers = Driver.get_drivers ();
		for (int i = 0; i < allDrivers.length; i++)	
		{
			Integer[] array = new Integer[3];
			array[0] = array[1] = array[2] = 0;
			DriverEntry<Driver,Integer[]> pair = 
		         new DriverEntry<Driver, Integer[]> (allDrivers[i], array);
			drivers.add (pair);	
		}// for
	}// populateTable
	
  
  // comparator for sorting drivers by time worked
	private static class DriverComparator implements Comparator
	{
		/**
	 		* Compares two Drivers in order to be able to sort them by
	 		* minues worked per roster for fariness.
	 		* @return -1, 0 or 1 depending on the outcome of the comparison
	 		*/
		public int compare (Object first, Object second)
		{
			DriverEntry <Driver, Integer[]> a = (DriverEntry <Driver, Integer[]>) first;
			DriverEntry <Driver, Integer[]> b = (DriverEntry <Driver, Integer[]>) second;
			Integer [] valuesA = a.getValue ();	
			Integer [] valuesB = b.getValue ();	
			return  valuesA[2].compareTo (valuesB[2]);
		}
		
	}// class
  
  
  // Code is from:
  // http://stackoverflow.com/questions/3110547/java-how-to-create-new-entry-key-value
  final static class DriverEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private V value;

    public DriverEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
  }
}// class

