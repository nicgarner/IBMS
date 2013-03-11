import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.ArrayList;

/**
 * Provides methods for working with timetables. Not for instantiating.
 *
 * Implemented by Nic.
 */
public class Timetable
{
  
  // for testing only
  public static void main(String args[])
  {
    database.openBusDatabase();
    Journey[] journeys = get_journeys(new GregorianCalendar(2013,02,17), 
                                      new GregorianCalendar(2013,02,23));
    System.out.println(journeys.length + " journeys were created.");
    for (int j = 0; j < journeys.length; j++)
      System.out.println(journeys[j]);
  }
  
  /**
   * Returns an array of Journey objects to represent all the bus journeys to be
   * made between the specified dates. This is the first stage of the rostering
   * process.
   *
   * @param  start_date  the start date of the roster
   * @param  end_date    the final date of the roster
   * @return Journey[]   array of journey objects
   */
  public static Journey[] get_journeys(GregorianCalendar start_date, 
                                       GregorianCalendar end_date)
  {
    // validate the input dates
    GregorianCalendar today = new GregorianCalendar();
    if (start_date.before(today))
      throw new IllegalArgumentException("Dates must be in the future.");
    if (start_date.after(end_date))
      throw new IllegalArgumentException("Start date must precede end date.");
    
    // array list to store the journeys as we create them, beacuse we don't
    // know how many there'll be at the start
    ArrayList<Journey> journeys = new ArrayList<Journey>();
    
    // get the routes
    Route[] routes = Route.getAllRoutes();
    
    // iterate over each day between start date and end date
    for (GregorianCalendar day = (GregorianCalendar)start_date.clone();
         !day.after(end_date); day.add(GregorianCalendar.DAY_OF_MONTH, 1))
    {
      // iterate over each route
      for (int route = 0; route < routes.length; route++)
      {
        // get the relevant service ids and create journey objects for them
        int[] service_ids = 
                TimetableInfo.getServices(routes[route].getID(), day.getTime());
        for (int service = 0; service < service_ids.length; service++)
          journeys.add(new Journey(service_ids[service], day));
      }
    }
    
    // return journeys as an array
    Journey[] journeys_array = new Journey[journeys.size()];
    return journeys.toArray(journeys_array);
  }
  
  /**
   * Returns the services for the the given route for the given day.
   *
   * @param  route     the route the timetable is for
   * @param  date      the day the timetable is for
   * @return Service[] services in the timetable
   */
  public static Service[] get_services(Route route, GregorianCalendar date)
  {
    int[] service_ids = TimetableInfo.getServices(route.getID(),date.getTime());
    Service[] services = Service.getServices(service_ids);
    return services;
  }
  
  /**
   * Returns a string represenation of a calendar object.
   *
   * @param  date    the calendar object
   * @return String  string representation of the given date
   */
  public static String dateToString(GregorianCalendar date)
  {
    return  date.get(GregorianCalendar.DAY_OF_MONTH) + "/" + 
		       (date.get(GregorianCalendar.MONTH)+1) + "/" +
 	 		      date.get(GregorianCalendar.YEAR);
 	}
  
  /**
   * Outputs full timetable information for all routes.
   */
  public static void print_all()
  {
    database.openBusDatabase();
    
    // get the routes
    int[] routes = BusStopInfo.getRoutes();
    for (int route = 0; route < routes.length; route++)
    {
      // print the name of the route
      System.out.println("Route: " + BusStopInfo.getRouteName(routes[route]) +
                         " (id:" + routes[route] + ")");
      
      // get the bus stops and timing points on the route
      int[] stops = BusStopInfo.getBusStops(routes[route]);
      int[] tp = TimetableInfo.getTimingPoints(routes[route]);
      
      // get the services of each kind (weekdays, saturday, Sunday) on the route
      TimetableInfo.timetableKind[] kinds = TimetableInfo.timetableKind.values();
      for (int kind = 0; kind < kinds.length; kind++)
      {
        int[] services = TimetableInfo.getServices(routes[route], kinds[kind]);
        if (services.length > 0)
        {
          // get the times of all the services
          int[][] times = new int[services.length][stops.length];
          for (int service = 0; service < services.length; service++)
            times[service] = TimetableInfo.getServiceTimes(routes[route], 
                                                           kinds[kind],
                                                           service);
          // print the timetable
          System.out.println("  " + kinds[kind] + " ("+stops.length+" stops, "+
                             services.length+" services)");
          for (int stop = 0; stop < stops.length; stop++)
          {
            System.out.format("  %2s: %-42s", (stop+1), 
                              BusStopInfo.getFullName(stops[stop]));
            if (BusStopInfo.isTimingPoint(stops[stop]))
              System.out.print(" (T)  ");
            else
              System.out.print("      ");
            
            /*
              if (stop == 0)
                for (int service = 0; service < services.length; service++)
                  System.out.format("%4s ", services[service]);
              if (stop == 1)
                for (int service = 0; service < services.length; service++)
                  System.out.format("%4s ", times[service].length);
            //}
            //else */
            for (int service = 0; service < services.length; service++)
              if (times[service].length > stop)
              { 
                int hours = times[service][stop] / 60;
                int minutes = times[service][stop] - hours*60;
                if (minutes >= 10)
                  System.out.format("%2d:%2d ", hours, minutes);
                else
                  System.out.format("%2d:0%d ", hours, minutes);
              }
              else
                System.out.format(" --  ");
             
            
            System.out.println();
          } // for (print timetable)
          
        }
        else
          System.out.println("    No services");
        System.out.println("\n");
      }
      System.out.println("\n");
    } // for (routes)
  } // method (main)
} // class (Timetable)
