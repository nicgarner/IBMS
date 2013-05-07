package objects;

import wrapper.TimetableInfo;
import wrapper.database;
import wrapper.BusStopInfo;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Provides methods for working with timetables. Not for instantiating.
 *
 * Implemented by Nic.
 */
public class Timetable
{
  /**
   * Returns an array of Journey objects to represent all the bus journeys to be
   * made between the specified dates on the specified route. This is the first
   * stage of the rostering process.
   *
   * @param  start_date  the start date of the roster
   * @param  end_date    the final date of the roster
   * @oaran  route       the route of the journeys to get
   * @return             array of journey objects
   */
  public static Journey[] get_journeys(GregorianCalendar start_date, 
                                       GregorianCalendar end_date, Route route)
  {
    // validate the input dates
    //GregorianCalendar today = new GregorianCalendar();
    //if (start_date.before(today))
      //throw new IllegalArgumentException("Dates must be in the future.");
    if (start_date.after(end_date))
      throw new IllegalArgumentException("Start date must precede end date.");
    
    // array list to store the journeys as we create them, beacuse we don't
    // know how many there'll be at the start
    ArrayList<Journey> journeys = new ArrayList<Journey>();
    
    // iterate over each day between start date and end date
    for (GregorianCalendar day = (GregorianCalendar)start_date.clone();
         !day.after(end_date); day.add(GregorianCalendar.DAY_OF_MONTH, 1))
    {
      // get the relevant service ids and create journey objects for them
      int[] services = TimetableInfo.getServices(route.getID(), day.getTime());
      for (int service = 0; service < services.length; service++)
        journeys.add(new Journey(services[service], day));
    }
    
    // return journeys as an array
    Journey[] journeys_array = new Journey[journeys.size()];
    return journeys.toArray(journeys_array);
  }
  
  /**
   * Method that simulates to impose delays, cancellations of services.
   * ~60% of journeys on time, ~10% cancelled, ~30% late. Possible delays have
   * a bound from 1 to 30 minutes. Delays, cancellations selected randomly.
   */   
   public static int sim()
   {
     int delayTime = 1 + (int)(Math.random() * ((68 - 1) + 1)) ;
     
     return delayTime ;
     
   }//sim

   public static int getCurTime()
   {
     int minute, hour, pastMidnight ;
     GregorianCalendar curTime = new GregorianCalendar() ;
     minute = curTime.get(GregorianCalendar.MINUTE) ;
     hour = curTime.get(GregorianCalendar.HOUR) ;
     pastMidnight = (hour * 60) + minute ;
     return pastMidnight ;
   }
   /**
    * Method that simulates times of an array of Journeys
    * and applying delays, cancellations
    */
   public static LiveJourney[] getAlterTimes(Route route)
   {
     GregorianCalendar curTime = new GregorianCalendar() ;
     getCurTime() ;

     ArrayList<LiveJourney> simJourneys = new ArrayList<LiveJourney>();
     
     //Array storing the original journeys and times 
     Journey[] journeys = get_journeys(curTime, curTime, route) ;
          
     //iterate over all the journeys on the selected day
     for (int i = 0; i < journeys.length; i++)
     {
        //select the journeys we want to apply simulation to
        //Only journeys that have already started can have data changed
        if (journeys[i].startTime() < getCurTime())
        {
           Service curService = journeys[i].getService() ;
           //get times for current journey
           int[] origTimes = curService.getTimes() ;
           int simForJourney = sim() ;
           
           //Check if journey cancelled and alter starting time, so we can
           //later identify the cancelled journeys. Stored in an array list
           if (simForJourney > 30 && simForJourney < 34)
           {
              //origTimes[0] = 2000 ;
              LiveJourney journey = new LiveJourney(journeys[i], -1);
              simJourneys.add(journey) ;
           }//if
           
           //If the journey is late, apply the delay to every timing point
           //and add the new times to the journey, store it in an array list
           else if (simForJourney < 31)
           {
              LiveJourney journey2 = new LiveJourney(journeys[i], simForJourney) ;
              simJourneys.add(journey2) ;
           }//else if
           
           //if journey on time, don't simulate, just add original times to
           //the array list
           else
           {
              LiveJourney journey3 = new LiveJourney(journeys[i], 0 );
              simJourneys.add(journey3) ;
           }//else
        }//if

        else if (journeys[i].startTime() < (getCurTime() + 90))
        {
           LiveJourney journey4 = new LiveJourney(journeys[i], 0 );
           simJourneys.add(journey4) ;
        }
     }//for
   
    // return journeys as an array
    LiveJourney[] journeys_array = new LiveJourney[simJourneys.size()];
    return simJourneys.toArray(journeys_array);
   }//getAlterTimes
   
  /*
    public static Journey[] getJourneys(BusStop stop, GregorianCalendar date,
                                            Route route)
        {
          int minute, hour, pastMidnight ;
          GregorianCalendar curTime = new GregorianCalendar() ;
          minute = curTime.get(Calendar.MINUTE) ;
          hour = curTime.get(Calendar.HOUR) ;
          pastMidnight = (hour * 60) + minute ;

          ArrayList<Journey> journeys = new ArrayList<Journey>();
          
          GregorianCalendar day = (GregorianCalendar)date.clone()
         
          //iterate over current day 
          while(!day.after(date))
          {
            int[] services = TimetableInfo.getServices(route.getID(), day.getTime());
            for (int service = 0; service < services.length; service++)
                journeys.add(new Journey(services[service], day));
          }
  */
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
    
    if (hour > 23)
      hour -= 24;
    
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
 	  int day  = minutes / 1440;
 	  int hour = (minutes - day*1440) / 60;
    int min  = minutes - day*1440 - hour*60;
    
    if (day > 0)
      return day + "d " + hour + "h " + min + "m";
    else if (hour > 0)
      return hour + "h " + min + "m";
    else
      return min + "m ";
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
      throw new IllegalArgumentException("Invalid date format, please check and try again.");

    // split on / and check there's exactly three parts
    String[] dateComponents = string.split ("/");
    if (dateComponents.length != 3)
      throw new IllegalArgumentException("Invalid date format, please check and try again.");

    // parse the parts as ints
    int day = Integer.parseInt(dateComponents[0]);
    int month = Integer.parseInt(dateComponents[1]);
    int year = Integer.parseInt(dateComponents[2]);

    // do some sanity checking on the ints (this isn't perfect!)
    if (day < 1 || day > 31 || month < 1 || month > 12)
      throw new IllegalArgumentException("Invalid date format, please check and try again.");

    // create the date
    GregorianCalendar date = new GregorianCalendar (year, month-1, day);
    return date;
  }

  /**
   * Returns an integer number of minutes past midnight from hour and minutes.
   *
   * @param  hours    the hours in the time
   * @param  minutes  the minutes in the time
   * @return          number of minutes past midnight that represents this time
   */
  public static int parseTime(int hours, int minutes)
  {
    // check hours and minutes within limits
    if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59)
      throw new IllegalArgumentException("Invalid time format, please check and try again.");
    return hours*60 + minutes;
  }
  
  /**
   * Generates some statistics about a roster.
   *
   * @param  roster  the roster to be inspected
   * @return         a string containing the statistics
   */
  public static String roster_statistics(ArrayList<ArrayList<Stretch>> roster)
  {
    int total_drivers = 70;
  
    int days = roster.size();
    
    int[] stretches_per_day = new int[days];
    int[] journeys_per_day = new int[days];
    int[] drivers_per_day = new int[days];
    
    // hours worked in this roster by each driver
    int[] drivers = new int[total_drivers];
    
    int total_stretches = 0;
    int total_journeys = 0;
    int driving_time = 0;
    int working_time = 0;
    
    for (int d = 0; d < days; d++)
    {
      ArrayList<Stretch> stretches = roster.get(d);
      stretches_per_day[d] = stretches.size();
      total_stretches += stretches.size();
      
      // to count number of unique drivers used today
      int[] daily_drivers = new int[total_drivers];
      
      for (int s = 0; s < stretches.size(); s++)
      {
        Stretch stretch = stretches.get(s);
        Journey[] journeys = stretch.getJourneys();
        journeys_per_day[d] += journeys.length;
        total_journeys += journeys.length;
        working_time += stretch.duration();
        
        for (int j = 0; j < journeys.length; j++)
          driving_time += journeys[j].duration();
        
        if (stretch.getDriver() != null)
        {
          int driver = stretch.getDriver().key() - 2012;
          drivers[driver] += stretch.duration();
          daily_drivers[driver] = 1;
        }
        
      }
      
      for (int dr = 0; dr < total_drivers; dr++)
        drivers_per_day[d] += daily_drivers[dr];
    }
    
    // count total number of drivers used (assigned some work) and min and
    // max hours worked
    int drivers_used = 0;
    int max_time = drivers[0];
    int min_time = Integer.MAX_VALUE;
    
    for (int dr = 0; dr < drivers.length; dr++)
    {
      if (drivers[dr] > 0)
      {
        drivers_used++;
        if (drivers[dr] < min_time)
          min_time = drivers[dr];
      }
      if (drivers[dr] > max_time)
        max_time = drivers[dr];
    }
        
    double dead_time = (double)(working_time - driving_time)*100 / working_time;
    DecimalFormat dp2 = new DecimalFormat("0.0");
    
    
    // generate output
    String string = "This " + days + " day roster contains " + total_journeys + 
                    " journeys in " + total_stretches + " stretches.\n";
    string += "Total work time: " +
              Timetable.minutesToDuration(working_time) + "\n";
    string += "Total driving time: " +
              Timetable.minutesToDuration(driving_time) + "\n";
    string += "Total dead time: " +
              Timetable.minutesToDuration(working_time - driving_time) + " (" +
              dp2.format(dead_time) + "%)\n";
    string += "Average stretch length: " + 
              Timetable.minutesToDuration(working_time/total_stretches) + "\n";         
    string += "Total drivers used: " + drivers_used + "\n\n";
    
    for (int d = 0; d < days; d++)
    {
      string += "Day " + (d+1) + ": ";
      string += drivers_per_day[d] + (drivers_per_day[d] == 1 ? " driver," : " drivers, ");
      string += stretches_per_day[d] + (stretches_per_day[d] == 1 ? " stretch," : " stretches, ");
      string += journeys_per_day[d] + (journeys_per_day[d] == 1 ? " journey.\n" : " journeys.\n");
    }
    string += "\n";
    string += "Average time worked by a driver: " +
              Timetable.minutesToDuration(working_time/drivers_used) + "\n";
    string += "Most hours worked by one driver: " + 
              Timetable.minutesToDuration(max_time) + "\n";
    string += "Fewest hours worked by one driver: " + 
              Timetable.minutesToDuration(min_time) + "\n";
    return string;
  }
  
  /**
   * Outputs full timetable information for all routes.
   */
  public static void print_all(boolean printServiceNames)
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
          
          if (printServiceNames)
          {
            int[] serviceNames = TimetableInfo.getServices(routes[route], kinds[kind]);
            System.out.print("                                                     ");
            for (int s = 0; s <serviceNames.length; s++)
              System.out.format("%5d  ", serviceNames[s]);
            System.out.println();
          }
          
          int skipped = 0;
          for (int stop = 0; stop < stops.length; stop++)
          {
            System.out.format("  %2s: %-42s", (stop+1), 
                              BusStopInfo.getFullName(stops[stop]));
            if (BusStopInfo.isTimingPoint(stops[stop]))
              System.out.print(" (T)  ");
            else
              System.out.print("      ");
            
            for (int service = 0; service < services.length; service++)
            {
              if (times[service].length > stop - skipped)
              {
                if (BusStopInfo.isTimingPoint(stops[stop]))
                { 
                  //int hours = times[service][stop-skipped] / 60;
                  //int minutes = times[service][stop-skipped] - hours*60;
                  //if (minutes >= 10)
                    //System.out.format("%2d:%2d  ", hours, minutes);
                  //else
                    //System.out.format("%2d:0%d  ", hours, minutes);
                  System.out.format("%s  ", 
                                   minutesToTime(times[service][stop-skipped]));
                }
                else
                  System.out.print("       ");
              }
              else
                System.out.format("  --   ");
            }
            if (!BusStopInfo.isTimingPoint(stops[stop]))
              skipped++;  
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
