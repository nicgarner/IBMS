package objects;


import wrapper.TimetableInfo;
import wrapper.InvalidQueryException;
import wrapper.BusStopInfo;

/**
 * Represents a route in the IBMS system. A route is comprised of a number
 * of bus stops in a particular order. A subset of these bus stops are timing
 * points on the route.
 *
 * Implemented by Nic.
 */

public class Route {

  // a route has an id, a name and an array of bus stops
  private final int id;
  private final String name;
  private final BusStop[] route;
  
  /**
   * Create a route from the id of the route
   *
   * @param routeID the id of the route
   */
  public Route(int routeID)
  {
    try
    {
      id = routeID;
      name = BusStopInfo.getRouteName(routeID);
      
      // get the bus stops and timing points on the route
      int[] stops = BusStopInfo.getBusStops(routeID);
      int[] timingPoints = TimetableInfo.getTimingPoints(routeID);
      
      // populate route (a bus stops array) with the data
      int timingPoint = 0;
      route = new BusStop[stops.length];
      for (int stop = 0; stop < stops.length; stop++)
      {
        route[stop] = new BusStop(stops[stop]);
        if (timingPoint < timingPoints.length &&
            stops[stop] == timingPoints[timingPoint])
        {
          route[stop].setTimingPoint(true);
          timingPoint++;
        }
      }
    }
    catch (InvalidQueryException e) { throw e; }
  }
  
  /**
   * Returns the route id.
   *
   * @return int the route id
   */
  public int getID()
  {
    return id;
  }

  /**
   * Returns the name of the route
   * @return name the name of the route
   */
  public String getName ()
  {
      return name;
  }


  /**
   * Returns a string representation of the route.
   *
   * @return String multiline string containing the name of the route followed
   *                by each bus stop
   */
  public String toString()
  {
    String string = name + ":";
    for (int stop = 0; stop < route.length; stop++)
      string += "\n" + route[stop];
    return string;
  }
  
  /**
   * Returns the stops on this route.
   *
   * @result  array of bus stops on this route
   */
  public BusStop[] getStops()
  {
    return route;
  }
  
  @Override
  public boolean equals(Object other)
  {
    boolean result = false;
    if (other instanceof Route)
    {
      Route that = (Route) other;
      result = this.getID() == that.getID();
    }
    return result;
  }
  
  @Override
  public int hashCode()
  {
    return (41 * (41 + getID()));
  }
  
  /**
   * Returns an array containing all the routes in the database.
   *
   * @return Route[] the routes in the database
   */
  public static Route[] getAllRoutes()
  {
    int[] route_ids = BusStopInfo.getRoutes();
    Route[] routes = new Route[route_ids.length];
    for (int route = 0; route < route_ids.length; route++)
    {
      routes[route] = new Route(route_ids[route]);
    }
    return routes;
  }
  
  /*
  // main method for testing only
  public static void main(String args[])
  {
    database.openBusDatabase();
    Route[] routes = Route.getAllRoutes();
    for (int route = 0; route < routes.length; route++)
      System.out.println(routes[route] + "\n");
  }
  */

}

