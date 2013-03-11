/**
 * Represents a service in the IBMS system. A service is one scheduled
 * trip of a bus on a timetable; that is, it is one column of a timetable.
 *
 * Implemented by Nic.
 */

public class Service {
  
  // a service has an id and a set of times
  private final int id;
  private final int[] times;
  
  /**
   * Create a service from the id of the service.
   *
   * @param serviceID the id of the service
   */ 
  public Service(int serviceID)
  {
    try
    {
      id = serviceID;
      times = TimetableInfo.getServiceTimes(serviceID);
    }
    catch (InvalidQueryException e) { throw e; }
  } // method (getStartTime)
  
  /**
   * Get the time that the service is scheduled to begin.
   *
   * @return int the number of minutes past midnight when the service will start
   */
  public int startTime()
  {
    return times[0];
  }
  
  /**
   * Get the time that the service is scheduled to end.
   *
   * @return int the number of minutes past midnight when the service will end
   */
  public int endTime()
  {
    int endTime = times[times.length-1];
    
    // if end time is over a day, wrap it round to next day
    if (endTime >= 1440)
    {
      return endTime - 1440;
    }
    else
      return endTime;
      
  } // method (getEndTime)
  
  /**
   * Get the duration of the service.
   *
   * @return int the number of minutes the service lasts
   */
  public int duration()
  {
    int startTime = startTime();
    int endTime = endTime();
    
    // if service crosses midnight can't do a simple subtraction
    if (endTime < startTime)
      return 1440 - startTime + endTime;
    else
      return endTime - startTime;
  } // method (duration)
  
  /**
   * Returns a short string representation of the service.
   *
   * @return String the service id
   */
  public String toString()
  {
    return "" + id;
  } // method (toString)
  
  /**
   * Returns a longer string representation of the service.
   *
   * @return String the service id followed by the times in that service
   */
  public String toFullString()
  {
    String string = toString() + " (";
    for (int time = 0; time < times.length-1; time++)
      string += times[time] + " ";
    string += times[times.length-1] + ")";
    return string;
  } // method (toString)
  
  /**
   * Takes an array of service ids and returns the corresponding array of 
   * services.
   * 
   * @param  service_ids  array of service ids
   * @return Service[]    array of services
   */
  public static Service[] getServices(int[] service_ids)
  {
    Service[] services = new Service[service_ids.length];
    for (int service = 0; service < service_ids.length; service++)
      services[service] = new Service(service_ids[service]);
    return services;
  }
  
  
  /*
  // main method for testing only
  public static void main(String args[])
  {
    database.openBusDatabase();
    TimetableInfo.timetableKind[] kinds = TimetableInfo.timetableKind.values();
    Route[] routes = Route.getAllRoutes();
    
    int[] services = TimetableInfo.getServices(66, kinds[0]);
    for (int i = 0; i < services.length; i++)
    {
      Service service = new Service(services[i]);
      System.out.print(service);
      System.out.print(" Duration: " + service.duration() + " minutes");
      System.out.println();
    }
  }
  */
  
  
  
} // class (Service)

