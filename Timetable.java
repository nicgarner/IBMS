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
    
    Journey[] journeys = get_journeys(new GregorianCalendar(2013,02,18), 
                                      new GregorianCalendar(2013,02,18));
    
    GregorianCalendar date = journeys[0].getDate();
    ArrayList<ArrayList<Stretch>> roster = new ArrayList<ArrayList<Stretch>>();
    
    int day = 0;
    roster.add(new ArrayList<Stretch>());
    roster.get(0).add(new Stretch(journeys[0]));
    
    // put the journeys into stretches
    for (int j = 1; j < journeys.length; j++)
    {
      if (date.before(journeys[j].getDate()))
      {
        // start a new day by placing the current journey in the first stretch
        // of the next day
        date = (GregorianCalendar)journeys[j].getDate().clone();
        day++;
        roster.add(new ArrayList<Stretch>());
        roster.get(day).add(new Stretch(journeys[j]));
      }
      else
      {
        boolean added = false;
        for (int s = 0; s < roster.get(day).size(); s++)
        {
          Stretch stretch = roster.get(day).get(s);
          if (journeys[j].startTime() > stretch.endTime() &&
              (stretch.duration() + journeys[j].duration() + 
              (journeys[j].startTime() - stretch.endTime())) <= (5*60))
          {
            stretch.addJourney(journeys[j]);
            added = true;
            break;
          }
          
        }
        if (!added)
        {
          roster.get(day).add(new Stretch(journeys[j]));
        }
        
      }
    }

/*    
    // test bus assignment
    System.out.println(print_roster(roster));
    BusScheduler.generateSchedule(roster);
    System.out.println(print_roster(roster));
    
    // test driver assignment
    System.out.println(print_roster(roster));
    DriverScheduler.generateSchedule(roster);
git ad
    System.out.println(print_roster(roster));
*/    
    
    System.out.println(print_roster(roster));
    //save_roster(roster);
    
    
    
    //ArrayList<ArrayList<Stretch>> roster2 = load_roster(new GregorianCalendar(2013,02,24), new GregorianCalendar(2013,02,24));
    //System.out.println(print_roster(roster2));
    
    //Stretch stretch = new Stretch(1);
    //System.out.println(stretch);
    
  } // main method (testing only)
  
  
  /**
   * Returns a (long!) string representation of a roster in a heirarchical form
   * (day/stretch/journey) showing stretches in each day, the driver, bus and 
   * length of each stretch and the times of each journey in each stretch.
   *
   * NOTE: should be moved to the Roster class!
   * 
   * @param  roster  a 2D array list of stretches
   * @param          string representation of roster
   */
  public static String print_roster(ArrayList<ArrayList<Stretch>> roster)
  {
    String string = "";
    
    // for each day in the roster...
    for (int d = 0; d < roster.size(); d++)
    {
      // print out the date and the number of stretches on that date
      GregorianCalendar date = roster.get(d).get(0).getDate();
      string += Timetable.dateToString(date) + " has " + roster.get(d).size() + 
                 " stretches.\n";
      
      // for each stretch in the day...
      for (int s = 0; s < roster.get(d).size(); s++)
      {
        // print the details of the stretch
        Stretch stretch = roster.get(d).get(s);
        string += "   Stretch " + (s+1) + ": ";
        string += "Driver: " + stretch.getDriver() + ", ";
        string += "Bus: " + stretch.getBus() + ", ";
        string += Timetable.minutesToDuration(stretch.duration()) + "\n";
        
        // print out the details of each journey in the stretch
        Journey[] journys = stretch.getJourneys();
        for (int j = 0; j < journys.length; j++)
        {
          Service service = journys[j].getService();
          string += "      Service " + service + ": ";
          string += Timetable.minutesToTime(service.startTime()) + " - ";
          string += Timetable.minutesToTime(service.endTime()) + "\n";
        } // for (journey)
        
      } // for (each stretch)
      
    } // for (each day)
    
    return string;
    
  } // method (print roster)
  
  
  /**
   * Recovers the details of a roster from the database for the given time
   * period.
   *
   * NOTE: should be moved to the Roster class!
   * 
   * @param  start_date  the desired start date
   * @param  end_date    the desired end date
   * @param              a 2D array list of stretches
   */
  public static ArrayList<ArrayList<Stretch>> load_roster(
                       GregorianCalendar start_date, GregorianCalendar end_date)
  {
    // validate the input dates
    if (start_date.after(end_date))
      throw new IllegalArgumentException("Start date must precede end date.");
    
    ArrayList<ArrayList<Stretch>> roster = new ArrayList<ArrayList<Stretch>>();
    int d = 0; // used to access each day of the "outer" ArrayList
    
    // iterate over each day between start date and end date
    for (GregorianCalendar day = (GregorianCalendar)start_date.clone();
         !day.after(end_date); day.add(GregorianCalendar.DAY_OF_MONTH, 1))
    {
      
      
      // get the stretch ids for the current day's stretches and use them
      // to instantiate stretch objects
      int[] stretches = database.busDatabase.select_ids("stretch_id", "stretch", 
                                                        "date", day.getTime(),
                                                        "stretch_id");
      // only continue if there are some stretches on this day
      if (stretches.length > 0)
      {
        // create a list for the current day's stretches
        roster.add(new ArrayList<Stretch>());
        
        // add each stretch to the list
        for (int s = 0; s < stretches.length; s++)
          roster.get(d).add(new Stretch(stretches[s]));
        
        // increment the day counter ready for the next day
        d++;
        
      } // if (stretches were found for today)
      
    } // for (each day)
    
    // throw an error if no data was returned
    if (roster.size() == 0)
      throw new IllegalArgumentException("No roster information was found " +
                                         "between the specified dates.");
    
    return roster;
    
  } // method (load roster)
  
  
  /**
   * Inserts the details of a roster into the database.
   *
   * NOTE: should be moved to the Roster class!
   * 
   * @param  roster  a 2D array list of stretches
   * @param          true if success, false if failure
   */
  public static boolean save_roster(ArrayList<ArrayList<Stretch>> roster)
  { 
    // for each day in the roster...
    for (int d = 0; d < roster.size(); d++)
    {
      // for each stretch in the day...
      for (int s = 0; s < roster.get(d).size(); s++)
      {
        // insert stretch details and get back the stretch id created
        Stretch stretch = roster.get(d).get(s);
        
        // if stretch doesn't have a bus/driver assigned, enter -1 in database
        int bus_id = stretch.getBus() == null ? -1 : stretch.getBus().getID();
        int driver_id = stretch.getDriver() == null ? -1 : stretch.getDriver().getID();
        
        int stretch_id = database.busDatabase.new_record_return_id("stretch",
          new Object[][] {
            { "date",      stretch.getDate().getTime() },
            { "bus_id",    bus_id },
            { "driver_id", driver_id } } );
                
        // insert each journey's details
        Journey[] journeys = stretch.getJourneys();
        for (int j = 0; j < journeys.length; j++)
          database.busDatabase.new_record("journey",
            new Object[][] {
              { "stretch_id", stretch_id },
              { "service_id", journeys[j].getService().toString() } } );
          
      } // for (each stretch)
      
    } // for (each day)
    
    return true;
    
  } // method (save roster)
  
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
   * @return           services in the timetable
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
   * @return         string representation of the given date
   */
  public static String dateToString(GregorianCalendar date)
  {
    return  date.get(GregorianCalendar.DAY_OF_MONTH) + "/" + 
		       (date.get(GregorianCalendar.MONTH)+1) + "/" +
 	 		      date.get(GregorianCalendar.YEAR);
 	}
 	
 	/**
 	 * Returns the string representation of a time specified as the number of
 	 * minutes past midnight.
 	 *
 	 * @param  minutes  the number of minutes past midnight
 	 * @return          string representation of the given time
 	 */
 	public static String minutesToTime(int minutes)
 	{
 	  int hour = minutes/60;
    int min  = minutes - hour*60;
    
    if (hour < 10)
    {
      if (min < 10)
        return "0"+hour+":0"+min;
      else
        return "0"+hour+":"+min;
    }
    else
    {
      if (min < 10)
        return hour+":0"+min;
      else
        return hour+":"+min;
    }
 	}
 	
 	/**
 	 * Returns the a string representing a number of minutes as minutes and hours.
 	 *
 	 * @param  minutes  the number of minutes
 	 * @return          string representation of the given duration
 	 */
 	public static String minutesToDuration(int minutes)
 	{
 	  int hour = minutes/60;
    int min  = minutes - hour*60;
    
    return hour + "h " + min + "m";
 	}
  
  /**
   * Returns a calendar date from a string.
   *
   * @param  string  the input string
   * @return         calendar object matching the input string
   */
  public static GregorianCalendar parseDate(String string)
  {
    // check the input string isn't too long to be a date
    if (string.length() > 10)
 			throw new IllegalArgumentException 
 			                     ("Invalid date format, please check and try again.");
 	  
 	  // split on / and check there's exactly three parts
 		String[] dateComponents = string.split ("/");
 		if (dateComponents.length != 3)
 			throw new IllegalArgumentException 
 			                     ("Invalid date format, please check and try again.");	
 	  
 	  // parse the parts as ints
 	  int day = Integer.parseInt(dateComponents[0]);
 	  int month = Integer.parseInt(dateComponents[1]);
 	  int year = Integer.parseInt(dateComponents[2]);
 	  
 	  // do some sanity checking on the ints (this isn't perfect!)
 	  if (day < 1 || day > 31 || month < 1 || month > 12)
 	    throw new IllegalArgumentException 
 			                     ("Invalid date format, please check and try again.");
 	  
 	  // create the date
 	  GregorianCalendar date = new GregorianCalendar (year, month-1, day);
 	  
 	  return date;
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
