import java.util.Formatter;

public class Timetable
{
  public static void main(String args[])
  {
    database.openBusDatabase();
    
    // get the routes
    int[] routes = BusStopInfo.getRoutes();
    for (int route = 0; route < routes.length; route++)
    {
      // print the name of the route
      System.out.println("Route: " + BusStopInfo.getRouteName(routes[route]));
      
      // get the bus stops on the route
      int[] stops = BusStopInfo.getBusStops(routes[route]);
      
      
      /* debugging only*/
      // get the timing points on the route
      int[] tp = TimetableInfo.getTimingPoints(routes[route]);
      System.out.print(tp.length + " timing points on this route: ");
      for (int i = 0; i < tp.length; i++)
        System.out.print(tp[i] + " " + BusStopInfo.getFullName(tp[i])+", ");
      System.out.println();
      
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
                System.out.format("%4s ", times[service][stop]);
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
