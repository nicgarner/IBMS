import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Represents a bus driver in the IBMS system.
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
   * @deprecated  Most required java.util.date functions are now in the 
   * java.util.calendar class, so use the version of this method with
   * calendar objects instead.
   * 
   * Processes a holiday request for a driver and updates database if it is
   * approved.
   *
   * @param  calendar the start date of the requested holiday
   * @param  calendar the end date of the requested holiday
   * @return int[]    array of ints with elements corresponding to each day in
   *                  requested range. 0 means the day couldn't be booked, 1
   *                  means the day was already booked by this driver, 2 means
   *                  the day could be booked. If the array contains at least
   *                  one 0, the holiday request was denied. Otherwise, holidays
   *                  were booked for days containing a 2.
   */
  public int[] request_holiday(Date start_date, Date end_date)
  {
    // call the method with Calendar objects instead of Date objects
    GregorianCalendar start_calendar = new GregorianCalendar();
    GregorianCalendar end_calendar = new GregorianCalendar();
    
    start_calendar.setTime(start_date);
    end_calendar.setTime(end_date);
    
    return request_holiday(start_calendar, end_calendar);
  }
  
  /**
   * Processes a holiday request for a driver and updates database if it is
   * approved.
   *
   * @param  calendar the start date of the requested holiday
   * @param  calendar the end date of the requested holiday
   * @return int[]    array of ints. The first element is 0 if request denied,
   *                  otherwise 1. The remaining elements correspond to each day
   *                  in the requested range, where 0 = no availability, 1 = 
   *                  already booked by this driver, 2 = available.
   */
  public int[] request_holiday(GregorianCalendar start_date,
                               GregorianCalendar end_date)
  {
    // ensure start date isn't after end date and dates are not before today
    if (start_date.after(end_date))
      throw new IllegalArgumentException("Start date must precede end date.");
    GregorianCalendar today = new GregorianCalendar();
    if (start_date.before(today))
      throw new IllegalArgumentException("Dates must be in the future.");
    
    // calculate the number of days in the range
    GregorianCalendar counter = (GregorianCalendar) start_date.clone();
    int length = 1;
    while (counter.before(end_date))
    {
      length++;
      counter.add(GregorianCalendar.DAY_OF_MONTH, 1);
    }
    
    // check for impossible durations
    if (length > holidayAllowance)
      throw new IllegalArgumentException("Range is too great.");
    
    
    // check the number of drivers on holiday on each day between the start
    // and end dates of the requested period
    
    /**
     * Notes:
     *   + Ignores rest days
     *   + Rule for whether holiday on a given day is approved is too simple;
     *     needs to take rostering into account
     *   + DOES take into account days this driver has already got booked off,
     *     but we can only do this at the moment because that's the only
     *     information about unavailability currently being recorded; need to
     *     make availability information more than a boolean field to maintain
     *     this later
     *   + We could return an array of boolean values for each day in the
     *     requested period so that we can report which days are available
     *     for holiday
     *   + Should it be an error if one or both dates are in the past?
     *     (Probably.)
     */
    
    // get array of all drivers so we can check whether they're on holiday
    // on each day in the requested holiday range
    Driver[] drivers = get_drivers();
    
    // counter to represent each day in the range, starting on the start date
    counter = (GregorianCalendar) start_date.clone();
    Date date; // date object required by database wrapper methods
    int[] result = new int[length+1]; // array to store the results
    result[0] = 1;
    
    for (int day = 0; day < length; day++)
    {
      date = counter.getTime();
      
      if (available(date) == false)
        // driver already has this day booked so don't need to continue
        result[day+1] = 1;
      else
      {
        // calculate the number of drivers unavailable on this day
        int unavailable_drivers = 0;
        
        for (int driver = 0; driver < drivers.length; driver++)
          if (drivers[driver].available(date) == false)
            unavailable_drivers++;
        
        // check against the max unavailable for the type of day
        // (20 at the weekend, otherwise 10)
        if (unavailable_drivers >= 20)
        {
          result[day+1] = 0;
          result[0] = 0;
        }
        else if (unavailable_drivers >= 10 && 
                 TimetableInfo.timetableKind(date) ==
                                 TimetableInfo.timetableKind.valueOf("weekday"))
        {
          result[day+1] = 0;
          result[0] = 0;
        }
        else
          result[day+1] = 2;
      }
        
      counter.add(GregorianCalendar.DAY_OF_MONTH, 1);
    }
    
    if (result[0] == 0)
      return result;
    
    int days_to_book = 0;
    // check that driver has enough holiday allowance to book the required days
    for (int day = 0; day < length; day++)
      if (result[day+1] == 2)
        days_to_book++;
        
    if (days_to_book > holidayAllowance - holiday_used())
    {
      result[0] = 0;
      return result;
    }
    
    // holiday is approved, so record in database
    counter = (GregorianCalendar) start_date.clone();
    for (int day = 1; day < result.length; day++)
    {
      if (result[day] == 2)
      {
        date = counter.getTime();
        DriverInfo.setAvailable(key, date, false);
      }
        
      counter.add(GregorianCalendar.DAY_OF_MONTH, 1);
    }
    update_holiday_used(days_to_book);
    
    return result;
  }
  
 
  // updates the number of days of holiday the driver has booked this year
  // can use a negative number to decrease (eg cancelled holiday, or holiday
  // allowance increased)
  private int update_holiday_used(int amount)
  {
    // first get the amount of holiday used so far
    int holiday_used = holiday_used();
    holiday_used += amount;
    
    // ensure that holiday used is non-negative
    if (holiday_used >= 0)
    {
      DriverInfo.setHolidaysTaken(key, holiday_used);
      return holiday_used;
    }
    else
      return -1;
  }
  
  /**
   * Determine whether the driver is available on the specified date.
   *
   * @param   date     the date to check for availability
   * @return  Boolean  true for available, else false
   */
  public Boolean available(Date date)
  {
    return DriverInfo.isAvailable(key, date);
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
}
