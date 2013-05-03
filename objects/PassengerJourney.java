package objects;

import objects.BusStop;
import objects.Journey;
import objects.Timetable;
import objects.Service;
import objects.Route;
import objects.PassengerJourney;
import wrapper.database;
import wrapper.BusStopInfo;

import java.util.*;

/**
 * Represents a journey between an origin and a destination in terms of the
 * stops passed and the times. While the object actually contains details
 * of all the stops, the principal method to be use is getJourneys, which
 * returns the details of the changes that need to be made for display to the
 * user.
 *
 * Implemented by Nic.
 */
public class PassengerJourney
{
  private ArrayList<BusStop> stops;
  private ArrayList<Integer> routes;
  private ArrayList<Integer> times;
  
  public PassengerJourney()
  {
    stops = new ArrayList<BusStop>();
    times = new ArrayList<Integer>();
    routes = new ArrayList<Integer>();
  }
  
  /**
   * Add a stop to a journey.
   *
   * @param stop   the bus stop
   * @param time   the time the bus gets to this stop
   * @param route  the id of the route the bus is on (-1 for walking)
   */
  public void addLink(BusStop stop, int time, int route)
  {
    stops.add(stop);
    times.add(time);
    routes.add(route);
  }
  
  /**
   * Gets the journey details in format for displaying in GUI tab;e
   *
   * @return nested array of strings corresponding to the legs in the journey,
   *         each array contains start bus stop, start time, end bus stop,
   *         end time, route numbers, duration and number of stops
   */
  public String[][] getJourneys()
  {
    ArrayList<String[]> legs = new ArrayList<String[]>();
    int lastRoute = -1;
    int stopCounter = 0;
    int startTime = 0;
    for (int i = 0; i < stops.size(); i++)
    {
      if (routes.get(i) == -1)
      {
        // end current leg
        String[] leg = legs.get(legs.size()-1);
        leg[2] = stops.get(i).getName();
        leg[3] = ""+times.get(i);
        leg[5] = ""+duration(startTime, times.get(i));
        leg[6] = ""+stopCounter;
      }
      else if (routes.get(i) != lastRoute)
      {
        // start a new leg
        Route route = new Route(routes.get(i));
        lastRoute = route.getID();
        legs.add(new String[7]);
        
        // populate start stop and time, and service number of the new leg
        String[] leg = legs.get(legs.size()-1);
        leg[0] = stops.get(i).getName();
        leg[1] = ""+times.get(i);
        leg[4] = route.getName();
        stopCounter = 0;
        startTime = times.get(i);
      }
      else
      {
        // its just a stop on a leg so just count it
        stopCounter++;
        lastRoute = routes.get(i);
      }
    }
    String[][] result = new String[legs.size()][7];
    for (int a = 0; a < result.length; a++)
      result[a] = legs.get(a);
    return result;
  }
  
  public int getStartTime()
  {
    return times.get(0);
  }
  
  public int getEndTime()
  {
    return times.get(times.size()-1);
  }
  
  public BusStop getOrigin()
  {
    return stops.get(0);
  }
  
  public BusStop getDestination()
  {
    return stops.get(times.size()-1);
  }
  
  // helper method to take midnight into account when working out
  // journey duration
  private int duration(int startTime, int endTime)
  {
    // if service crosses midnight can't do a simple subtraction
    if (endTime < startTime)
      return 1440 - startTime + endTime;
    else
      return endTime - startTime;
  } // method (duration)
}
