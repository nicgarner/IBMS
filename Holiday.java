import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Provides the method for processing holidays. Implemented by Nic.
 */
public class Holiday
{
  /**
   * Processes a holiday request for a driver and updates database if it is
   * approved.
   *
   * @param  start_date the start date of the requested holiday
   * @param  end_date   the end date of the requested holiday
   * @param  driver     the driver requesting the holiday
   *
   * @return int[]      array of ints. The first element is 0 if request denied,
   *                    otherwise 1. The remaining elements correspond to each 
   *                    day in the requested range, where 0 = no availability, 
   *                    1 = already booked by this driver, 2 = available.
   */
  public static int[] process_request(GregorianCalendar start_date,
                                      GregorianCalendar end_date, Driver driver)
  {
    // ensure start date isn't after end date and dates are not before today
    GregorianCalendar today = new GregorianCalendar();
    if (start_date.before(today))
      throw new IllegalArgumentException("Dates must be in the future.");
    if (start_date.after(end_date))
      throw new IllegalArgumentException("Start date must precede end date.");
    
    // calculate the number of days in the range
    GregorianCalendar counter = (GregorianCalendar) start_date.clone();
    int length = 1;
    while (counter.before(end_date))
    {
      length++;
      counter.add(GregorianCalendar.DAY_OF_MONTH, 1);
    }
    
    // check for impossible durations
    if (length > Driver.holidayAllowance)
      throw new IllegalArgumentException("Date range is too great.");
    
    
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
     *     this later (e.g. illness)
     */
    
    // get array of all drivers so we can check whether they're on holiday
    // on each day in the requested holiday range
    Driver[] drivers = Driver.get_drivers();
    
    // counter to represent each day in the range, starting on the start date
    counter = (GregorianCalendar) start_date.clone();
    Date date; // date object required by database wrapper methods
    int[] result = new int[length+1]; // array to store the results
    result[0] = 1;
    //02/0117
    for (int day = 0; day < length; day++)
    {
      date = counter.getTime();
      
      if (driver.available(date) == false)
        // driver already has this day booked so don't need to continue
        result[day+1] = 1;
      else
      {
        // calculate the number of drivers unavailable on this day
        int unavailable_drivers = 0;
        
        for (int d = 0; d < drivers.length; d++)
          if (drivers[d].available(date) == false)
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
        
    if (days_to_book > Driver.holidayAllowance - driver.holiday_used())
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
        DriverInfo.setAvailable(driver.key(), date, false);
      }
        
      counter.add(GregorianCalendar.DAY_OF_MONTH, 1);
    }
    update_holiday_used(days_to_book, driver);
    
    return result;
  }
  
 
  // updates the number of days of holiday the driver has booked this year
  // can use a negative number to decrease (eg cancelled holiday, or holiday
  // allowance increased)
  private static int update_holiday_used(int amount, Driver driver)
  {
    // first get the amount of holiday used so far
    int holiday_used = driver.holiday_used();
    holiday_used += amount;
    
    // ensure that holiday used is non-negative
    if (holiday_used >= 0)
    {
      DriverInfo.setHolidaysTaken(driver.key(), holiday_used);
      return holiday_used;
    }
    else
      return -1;
  }
  
}
