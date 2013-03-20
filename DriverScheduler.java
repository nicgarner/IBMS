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
	public static void generateSchedule 
	(ArrayList<ArrayList<Stretch>> roster)
	{
		drivers.clear();
		populateTable ();
		DriverComparator comparator;
		for (int day = 0; day < roster.size(); day++)
		{	
		System.out.println("day " + day);
			
			
			
			System.out.println ("****************Before sort*******************");
			Iterator <DriverEntry<Driver, Integer[]>>driveIt = 
					drivers.iterator();
					int counter = 0;
				while (driveIt.hasNext ())
				{				
				DriverEntry<Driver, Integer[]> driver = driveIt.next();	
				Integer [] values = driver.getValue ();	
				counter++;
			System.out.print 
								(values[2] + " ");	
				}
				System.out.println();
				System.out.println (counter + "\n");
								
								
								
			Collections.sort (drivers, new DriverComparator ());		 
			
			System.out.println ("****************After sort*******************");
			Iterator <DriverEntry<Driver, Integer[]>>drivIt = 
					drivers.iterator();
					counter = 0;
				while (drivIt.hasNext ())
				{				
				DriverEntry<Driver, Integer[]> driver = drivIt.next();	
				Integer [] values = driver.getValue ();	
				counter++;
			System.out.print 
								(values[2] + " ");	
				}
				System.out.println();
				System.out.println (counter + "\n");
			
			
			
			
			
			for (int s = 0; s < roster.get(day).size(); s++)
			{
				Stretch stretch = roster.get(day).get(s);
				Iterator <DriverEntry<Driver, Integer[]>>driverIt = 
					drivers.iterator();
					counter = 0;
				while (driverIt.hasNext ())
				{
					DriverEntry<Driver, Integer[]> driver = driverIt.next();	
					
					Integer [] values = driver.getValue ();	
					counter++;
					if (values[1] < stretch.startTime() && 
						values[0] < 2 &&					
						((stretch.startTime() - values[1] >= 60) ||
						(values[0] == 0))
					 	)
					{	
						stretch.setDriver (driver.getKey ());
						values[1] = stretch.endTime ();		
						values[0]++;
						
						values[2] += stretch.duration ();		 	
						System.out.print (values[2]+" ");					
						break;
					}// if
				}// for
			}// for
		}// for

	}// generateSchedule
	
	
	/**
	 * Populates the table of drivers
	 */
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
	
	/*final class DriverEntry<Driver, Integer[]> 
	            implements Map.Entry<Driver, Integer[]> {
    private final Driver key;
    private Integer[] value;

    public DriverEntry(Driver key, Integer[] value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Driver getKey() {
        return key;
    }

    @Override
    public Integer[] getValue() {
        return value;
    }

    @Override
    public Integer[] setValue(Integer[] value) {
        Integer[] old = this.value;
        this.value = value;
        return old;
    }
}*/

// Code is from http://stackoverflow.com/questions/3110547/java-how-to-create-new-entry-key-value

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
	
	/*public static void main (String[] args)
	{
	database.openBusDatabase ();
		Route[] routes = Route.getAllRoutes();
    GregorianCalendar start = new GregorianCalendar (2013, 8, 01);
    GregorianCalendar end = new GregorianCalendar (2013, 8, 02);
    
    Journey[] a = Timetable.get_journeys(start, end, routes[0]);
    Journey[] b = Timetable.get_journeys(start, end, routes[1]);
    
    ArrayList<ArrayList<Stretch>> roster = Roster.rostering(a, b);
    
    a = Timetable.get_journeys(start, end, routes[2]);
    b = Timetable.get_journeys(start, end, routes[3]);
    
    ArrayList<ArrayList<Stretch>> roster2 = Roster.rostering(a, b);
    
    Roster.merge_rosters(roster, roster2);
    
    BusScheduler.generateSchedule(roster);
    DriverScheduler.generateSchedule(roster);
	}*/
}// class

