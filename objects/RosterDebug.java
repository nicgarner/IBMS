package objects;

import wrapper.database;
import wrapper.TimetableInfo;
import wrapper.BusStopInfo;
import java.util.GregorianCalendar;
import java.util.ArrayList ;
import java.util.Formatter;
import java.util.* ;
/**
 * Creates stretches for IBMS rostering
 * A class that groups Journeys together to make Stretches. 
 * Not for instantiating!
 * Implemented by Kris & Nic
*/
public class RosterDebug
{   
  public static void main(String args[]) {
   database.openBusDatabase() ;  
   //generate_roster(new GregorianCalendar (2013,04,02), new GregorianCalendar (2013,04,05)) ; 
   Journey[] a = Timetable.get_journeys(new GregorianCalendar (2013,04,02), new GregorianCalendar(2013,04,05), new Route(65));
Journey[] b = Timetable.get_journeys(new GregorianCalendar (2013,04,02), new GregorianCalendar(2013,04,05), new Route(66));
  rostering(a,b) ;

 } 
 
 /**
   * Adds the stretches from one roster into another, so that stretches on
   * the same day are in the same list. The two rosters must correspond to
   * the same period of time; that is they must be the same length and the
   * stretches in the same element in each list must correspond to the same
   * date.
   *
   * @param  originalRoster  the original roster
   * @param  newRoster       the roster to be merged into original roster
   */  
  public static void merge_rosters(ArrayList<ArrayList<Stretch>> originalRoster, 
                                   ArrayList<ArrayList<Stretch>> newRoster)
  {
    for (int d = 0; d < originalRoster.size(); d++)
      originalRoster.get(d).addAll(newRoster.get(d));
  }
    
  /**
   * Main driver class for roster creation. Takes the dates and calls on other
   * classes to complete the full rostering process.
   *
   * @param  start  the start date of the roster
   * @param  end    the end date of the roster
   *
   * @return        the completed roster
   */
  public static ArrayList<ArrayList<Stretch>> generate_roster(
                                   GregorianCalendar start, GregorianCalendar end)
  {
    Route[] routes = Route.getAllRoutes();
    
    Journey[] a = Timetable.get_journeys(start, end, routes[0]);
    Journey[] b = Timetable.get_journeys(start, end, routes[1]);
    
    ArrayList<ArrayList<Stretch>> roster = rostering(a, b);
    
    a = Timetable.get_journeys(start, end, routes[2]);
    b = Timetable.get_journeys(start, end, routes[3]);
    
    ArrayList<ArrayList<Stretch>> roster2 = rostering(a, b);
    
    merge_rosters(roster, roster2);
    
    BusScheduler.generateSchedule(roster);
    DriverScheduler.generateSchedule(roster);
    
    return roster;
  }


  public static boolean hasJourneysA(boolean[] assigned)
  {
    for(int i = 0; i < assigned.length; i++)
     if(!assigned[i]) 
         return true;
  return false ;
  }

  /**
   * Method that groups journeys together to create stretches.
   * Takes 2 timetables as arrays, going from A to B.
   *
   * @param  a[]    Array storing a timetable A
   * @param  b[]    Array storing a timetable B
   *
   * @return roster created stretches for two timetables for one route
   */
  public static ArrayList<ArrayList<Stretch>> rostering(Journey[] a, Journey[] b)
  {
    //boolean arrays for keeping track of assigned journeys
    boolean[] assigned1 = new boolean[a.length];
    boolean[] assigned2 = new boolean[b.length];

    //get date of first journey of specific timetable
    GregorianCalendar date = a[0].getDate();
    
    //Create a 2D ArrayList for storing the roster (days*stretches)
    ArrayList<ArrayList<Stretch>> roster = new ArrayList<ArrayList<Stretch>>();
      
    int day = 0;
    roster.add(new ArrayList<Stretch>());
    roster.get(0).add(new Stretch(a[0].getDate()));
    
    System.out.println("Day 0:");
         
    //while array A containing number of journeys has unassigned journeys,
    //assign Go through the journey pairing process
    while(hasJourneysA(assigned1))
    {
      //for all journeys from timetable a find a suitable b pairing
      for (int j = 0; j < a.length; j++)
      {
        if (date.before(a[j].getDate()))
        {
           System.out.println("Cleanup day "+day+":");
           for(int i = 0 ; i < b.length; i++)
           {
             
             if(!assigned2[i] && a[j-1].getDate().compareTo(b[i].getDate())==0)
             {
                 System.out.print("JourneyB["+i+"] ");
                 roster.get(day).add(new Stretch(b[i].getDate()));
                 roster.get(day).get(roster.get(day).size()-1).addJourney(b[i]);
                 Stretch stretch2 = roster.get(day).get(roster.get(day).size()-1) ;	        
                 System.out.println("S["+(roster.get(day).size()-1)+"]* B("+i+") ");
                 //System.out.println(i) ;
            	 //stretch2.addJourney(b[i]);
            	 assigned2[i] = true ;
                 //System.out.println(stretch2.toString()) ;
             }

            }//for
           
          //start a new day by placing the current journey in the first stretch
          //of the next day
          date = (GregorianCalendar)a[j].getDate().clone();
          day++ ;
          roster.add(new ArrayList<Stretch>());
          roster.get(day).add(new Stretch(a[j].getDate()));
          System.out.println("\n\nDay "+day+":");

        }//if
        
        System.out.print("JourneyA["+j+"] ");
        
        boolean added = false;
        
        //go through all days that are considered
        for (int s = 0; s < roster.get(day).size(); s++)
        {
          Stretch stretch = roster.get(day).get(s);
          
          System.out.print("S["+s+"] ");
          
          //check if adding the current journey complies with the rules
          if (!assigned1[j] && a[j].startTime() >= stretch.endTime() &&
              ((stretch.duration() == 0) || ((stretch.duration() + a[j].duration() 
                           + (a[j].startTime() - stretch.endTime())) <= (5*60))))
            {
            stretch.addJourney(a[j]);
            added = true;
            assigned1[j] = true ;
            
            System.out.print("A("+j+") ");
            
            //go through all the journeys in timetable B to find a suitable
            //pairing for journey from A selected before
            for(int i = 0; i < b.length; i++)
            {
	      //check if adding the current journey complies with the rules
              if (!assigned2[i] && a[j].getDate().compareTo(b[i].getDate())==0  
                  && b[i].startTime() >= stretch.endTime() &&
              (stretch.duration() + b[i].duration() + 
              (b[i].startTime() - stretch.endTime())) <= (5*60))
              {
              System.out.print("B("+i+") ");
	        stretch.addJourney(b[i]);
	        assigned2[i] = true;
	        break;
	      }//if complies with rules
            
            }//for all journeys in array B
            break;
            

          }//if not assigned, current time < start time, stretch < 5hrs
            
        }//for that stretch
          
        //if not added, create a new stretch with journey from A as the first
        //in a new stretch and look for a suitable B pairing
        if (!added)
        {
          
          roster.get(day).add(new Stretch(a[j].getDate()));
          roster.get(day).get(roster.get(day).size()-1).addJourney(a[j]);
          
          System.out.print("S["+(roster.get(day).size()-1)+"]* A("+j+") ");
          
          assigned1[j] = true ;
          Stretch stretch = roster.get(day).get(roster.get(day).size()-1) ;
          
          for(int i = 0; i < b.length; i++)
          {
	    if (!assigned2[i] && a[j].getDate().compareTo(b[i].getDate())==0 
               && b[i].startTime() >= stretch.endTime() &&
            (stretch.duration() + b[i].duration() + 
            (b[i].startTime() - stretch.endTime())) <= (5*60))
            {
            System.out.print("B("+i+") ");
	      stretch.addJourney(b[i]);
	      assigned2[i] = true;
              break;
            }//if
          }//for 
        }//if not added
          System.out.println();
        
      }//for journey length
    }//while has journeys
   
    
   System.out.println("Cleanup day "+day+": ");
   for(int i = 0 ; i < b.length; i++)
   {         
       if(!assigned2[i])
       {
           System.out.print("JourneyB["+i+"] ");
           roster.get(day).add(new Stretch(b[i].getDate()));
           roster.get(day).get(roster.get(day).size()-1).addJourney(b[i]);
           Stretch stretch3 = roster.get(day).get(roster.get(day).size()-1) ;	        

           System.out.println("S["+(roster.get(day).size()-1)+"]* B("+i+") ");
           
           assigned2[i] = true ;
                 
        }
     }//for

   return roster ;
  } //roster

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
}
}
}//Roster class
