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
  public void addStop(BusStop stop, int time, int route)
  {
    stops.add(stop);
    times.add(time);
    routes.add(route);
  }
  
  /**
   * Gets the journey details in format for displaying in GUI table
   *
   * @return 2D array of string arrays corresponding to the legs in the journey,
   *         each array contains: start bus stop, start time, end bus stop,
   *         end time, route numbers, duration and number of stops
   */
  public String[][] getJourney()
  {
    ArrayList<String[]> legs = new ArrayList<String[]>();
    
    int lastRoute = -1;
    int stopCounter = 1;
    int startTime = 0;
    
    for (int i = 0; i < stops.size(); i++)
    {
      if (i > 0 && (routes.get(i) == -1 || i == stops.size()-1))
      {
        // end current leg
        String[] leg = legs.get(legs.size()-1);
        leg[2] = stops.get(i).getName();
        leg[3] = ""+Timetable.minutesToTime(times.get(i));
        leg[5] = ""+Timetable.minutesToDuration(duration(startTime, times.get(i)));
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
        leg[1] = ""+Timetable.minutesToTime(times.get(i));
        leg[4] = route.getName();
        stopCounter = 1;
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
  
  @Override
  public String toString()
  {
    String string = "Stop:\t";
    for (int s = 0; s < stops.size(); s++)
      string += stops.get(s).getId() + "\t";
    string += "\nBus:\t";
    for (int r = 0; r < routes.size(); r++)
    {
      if (r == routes.size() - 1)
        string += "arrived";
      else if (routes.get(r) == -1)
        string += "walk\t";
      else
      {
        Route route = new Route(routes.get(r));
        string += route.getName() + "\t";
      }
    }
    string += "\nTime:\t";
    for (int t = 0; t < times.size(); t++)
      string += Timetable.minutesToTime(times.get(t)) + "\t";
    
    return string;
  }
  
  public int getStartTime()
  {
    return times.get(0);
  }
  
  public int getEndTime()
  {
    return times.get(times.size()-1);
  }
  
  public int getDuration()
  {
    return duration(getStartTime(), getEndTime());
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
