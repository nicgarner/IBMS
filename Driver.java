import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;

/**
 * Represents a bus driver in the IBMS system. Implemented by Nic.
 */
public class Driver
{
  public static final int holidayAllowance = 25;
  
  // a driver has a name and an id number
  private String name;
  private String id_number;
  
  // he also has a key which is used to identify him in the database
  // this remains internal to the class
  private int key;
  
  /**
   * Instantiates an object representing a driver record in the database.
   *
   * @param  id  the drivers unique identification number
   */
  public Driver(String id) throws InvalidQueryException
  {
    try
    {
      name = DriverInfo.getName(DriverInfo.findDriver(id));
      key = DriverInfo.findDriver(id);
      id_number = id;
    }
    catch (InvalidQueryException e) { throw e; }
  }
  
  /**
   * Returns the number of days of holiday the driver has booked this year, 
   * both in the past as well as the future.
   *
   * @return  the number of days of holiday used
   */ 
  public int holiday_used()
  {
    return DriverInfo.getHolidaysTaken(key);
  }
  
  /**
   * Determines whether a holiday can be taken by this driver.
   * 
   * @param  start_date the start date of the requested holiday
   * @param  end_date   the end date of the requested holiday
   *
   * @return int[]      array of ints. The first element is 0 if request denied,
   *                    otherwise 1. The remaining elements correspond to each 
   *                    day in the requested range, where 0 = no availability, 
   *                    1 = already booked by this driver, 2 = available.
   */
  public int[] request_holiday(GregorianCalendar start_date,
                                      GregorianCalendar end_date)
  {
    return Holiday.process_request(start_date, end_date, this);
  }
  
  /**
   * Returns the driver's name.
   *
   * @return  the driver's name
   */
  public String name()
  {
    return name;
  }
  
  /**
   * Returns the driver's ID number.
   *
   * @return  the driver's ID number
   */
  public String id_number()
  {
    return id_number;
  }
  
  /**
   * Returns the driver's key.
   *
   * @return  the driver's key
   */
  public int key()
  {
    return key;
  }
  
  /**
   * Determine whether the driver is available on the specified date.
   *
   * @param   date     the date to check for availability
   * @return  Boolean  true for available, else false
   */
  public Boolean available(GregorianCalendar date)
  {
    return DriverInfo.isAvailable(key, date.getTime());
  }
    
  
  public String toString()
  {
    return "Name: " + name + ", ID number: " + id_number;
  }
  
  /**
   * Get all the drivers in the database.
   *
   * @return  an array of driver objects
   */
  public static Driver[] get_drivers()
  {
    int[] driver_ids = DriverInfo.getDrivers();
    Driver[] drivers = new Driver[driver_ids.length];
    for (int driver = 0; driver < drivers.length; driver++)
      drivers[driver] = new Driver(DriverInfo.getNumber(driver_ids[driver]));
    return drivers;
  }
  
  /**
   * Get all the drivers from the database who are available on the given day.
   *
   * @param   date   the date to check for availability
   * @return         an array of drivers available on the date
   */
  public static Driver[] get_available_drivers(GregorianCalendar date)
  {
    // get all the drivers
    Driver[] drivers = get_drivers();
    
    // list to store the available drivers as we find them
    ArrayList<Driver> available_drivers = new ArrayList<Driver>();
    
    // look at all the drivers and add them to the list if they're available
    for (int driver = 0; driver < drivers.length; driver++)
      if (drivers[driver].available(date))
        available_drivers.add(drivers[driver]);
        
    // return drivers as an array
    Driver[] drivers_array = new Driver[available_drivers.size()];
    return available_drivers.toArray(drivers_array);
    
  } // method (get available drivers)
  
  
  // for testing only
  public static void main (String args[])
  {
    database.openBusDatabase();
    Driver[] drivers = get_available_drivers(new GregorianCalendar(2013,03,15));
    System.out.println(drivers.length + " drivers are available on 15 April.");
  }
}
