package objects;

import objects.BusStop;
import objects.Journey;
import objects.Timetable;
import objects.Service;
import objects.Route;
import objects.PassengerJourney;
import wrapper.database;
import wrapper.BusStopInfo;

import org.jgrapht.*;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;
import java.util.GregorianCalendar;

public class Network
{
  static boolean verbose = false;
  private final DirectedWeightedMultigraph<BusStop,Edge> network;
  
  public static void main(String[] args)
  {
    verbose = true;
    database.openBusDatabase();
    Network network = new Network();
    
    if (args.length == 0)
      System.exit(0);
    
    BusStop origin = new BusStop(Integer.parseInt(args[0]));
    BusStop destination = new BusStop(Integer.parseInt(args[1]));
    GregorianCalendar day = new GregorianCalendar(Integer.parseInt(args[4]), Integer.parseInt(args[3]) - 1, Integer.parseInt(args[2]));
    int time = Integer.parseInt(args[5]);
    
    PassengerJourney[] journeys = network.journeys(origin, destination, time, day);
    
    System.out.println("Showing journeys between " + origin.getName() + 
                       " and " + destination.getName() + "\nleaving on " + 
                       Timetable.dateToString(day) + " between " + 
                       Timetable.minutesToTime(time) + " and " + 
                       Timetable.minutesToTime(time + 60) + "\n");
    for (int j = 0; j < journeys.length; j++)
    {
      //System.out.println("\n"+journeys[j]+"\n");
      String[][] journey = journeys[j].getJourney();
      for (int l = 0; l < journey.length; l++)
        System.out.println(journey[l][0] +  "\t" + journey[l][1] + " \t" +
                           journey[l][2] + " \t" + journey[l][3] + " \t" +
                           journey[l][4] + " \t" + journey[l][5] + " \t" +
                           journey[l][6]);
      System.out.println("Total duration: " + Timetable.minutesToDuration(journeys[j].getDuration()));
      System.out.println();
    }
  }
  
  /**
   * Builds a graph representing the network.
   */
  public Network()
  {
    network = new DirectedWeightedMultigraph<BusStop,Edge>(Edge.class);
    
    // build the network from the routes in the database
    Route[] routes = Route.getAllRoutes();
    for (int route = 0; route < routes.length; route++)
    {
      if (verbose)
        System.out.println("Route: " + routes[route].getName() + " (id: " + routes[route].getID() + ")");
      
      BusStop[] stops = routes[route].getStops();
      for (int stop = 0; stop < stops.length; stop++)
      {
        if (verbose)
          System.out.println("  Stop: " + stops[stop].getId() + " " + stops[stop].getName());
        
        // add the stop, and connect it to the previous one on the route
        network.addVertex(stops[stop]);
        
        if (stop > 0)
        {
          Edge edge = new Edge(routes[route].getID());
          network.addEdge(stops[stop-1], stops[stop], edge);
          network.setEdgeWeight(edge, 10.0);
        }
        
        // add edges between stops with the same area and name
        Set<BusStop> allStops = (Set<BusStop>)network.vertexSet();
        Iterator<BusStop> iterator = allStops.iterator();
        while (iterator.hasNext())
        {
          BusStop busStop = iterator.next();
          if (busStop.inVicinity(stops[stop]))
          {
            Edge edge = new Edge(-1);
            network.addEdge(stops[stop], busStop, edge);
            network.setEdgeWeight(edge, 30.0);
            edge = new Edge(-1);
            network.addEdge(busStop, stops[stop], edge);
            network.setEdgeWeight(edge, 30.0);
          }
        } // while (nodes in graph)
      } // for (stops on route)
    } // for (routes)
    if (verbose)
      System.out.println();
  } // constructor
  
  public PassengerJourney[] journeys(BusStop origin, BusStop destination,
                                         int startTime, GregorianCalendar date)
  {
    assert(network.containsVertex(origin));
    
    // get possible routes for the journey
    KShortestPaths<BusStop,Edge> pathsCalculator = 
                           new KShortestPaths<BusStop,Edge>(network, origin, 2);
    ArrayList<GraphPath<BusStop,Edge>> paths = (ArrayList<GraphPath<BusStop,Edge>>)pathsCalculator.getPaths(destination);
    
    for (int p = 0; p < paths.size(); p++)
      print_path(paths.get(p));
    
    ArrayList<PassengerJourney> journeys = new ArrayList<PassengerJourney>();
    
    for (int p = 0; p < paths.size(); p++)
    { 
      // reject path if it's the same as the previous path
      if (p > 0)
        if (comparePaths(paths.get(p), paths.get(p-1)))
        {
          System.out.println("Path " + p + " rejected as duplicate");
          continue;
        }
        
      System.out.print("Path " + p + ": ");
      
      ArrayList<Edge> edges = (ArrayList<Edge>)paths.get(p).getEdgeList();
      ArrayList<BusStop> stops = (ArrayList<BusStop>)Graphs.getPathVertexList(paths.get(p));
      
      // work out the first stop and the times buses depart it
      BusStop first = first_stop(paths.get(p));
      int[] times = timesAtStop(first, date, startTime, 60);
      
      for (int t = 0; t < times.length; t++)
        System.out.print(Timetable.minutesToTime(times[t]) + ", ");
      System.out.println();
      
      // get times for the journeys
      time: for (int t = 0; t < times.length; t++)
      {
        int lastTime = times[t]; // keep track of the time we got to each stop 
        boolean start = false;   // we don't count the first stops if they're
                                 // "walking" stops
        PassengerJourney journey = new PassengerJourney();
        
        // get the time for each stop
        stops: for (int s = 0; s < stops.size(); s++)
        {
          if (!start)
            // skip over the first stop(s) if they're walking stops
            if (stops.get(s).equals(first) && edges.get(s).getRoute() != -1)
              start = true;
          if (start)
          {
            // get the time of the next bus at this stop
            int time = nextTimeAtStop(stops.get(s), date, lastTime);
            if (time < 0)
            {
              // reject the whole journey if we didn't find the time
              System.out.println("  Time " + t + " rejected as impossible");
              continue time;
            }
            // System.out.println("    Stop " + stops.get(s).getId() + " " + 
               //                stops.get(s).getName() + " (" + s + "/" + 
               //                stops.size() + ") added");
            if (s >= edges.size())
              journey.addStop(stops.get(s), time, -1);
            else
            {
              journey.addStop(stops.get(s), time, edges.get(s).getRoute());
              if (edges.get(s).getRoute() == -1)
                lastTime = time + 10; // allow ten minutes to change buses
              else
                lastTime = time;
            }
            if (stops.get(s).inVicinity(destination))
              // don't count final stops if they're "walking" stops
              start = false;
          }
        }
        System.out.println("  Time " + t + " completed");
        journeys.add(journey);
      }
      System.out.println();
    }
    
    // sort, convert to array and return
    PassengerJourney[] result = new PassengerJourney[journeys.size()];
    for (int i = 0; i < result.length; i++)
      result[i] = journeys.get(i);
    Arrays.sort(result);
    return result;
  }
  
  // helper method to print a journey's stops in detail
  private void print_path(GraphPath<BusStop,Edge> path)
  {
    ArrayList<Edge> edges = (ArrayList<Edge>)path.getEdgeList();
    ArrayList<BusStop> stops = (ArrayList<BusStop>)Graphs.getPathVertexList(path);
    
    System.out.println("Weight: " + path.getWeight());
    for (int s = 0; s < edges.size(); s++)
    {
      System.out.print(stops.get(s).getId() + " " + stops.get(s).getName());
      System.out.println(" (" + edges.get(s) + ") ->");
    }
    System.out.println(stops.get(stops.size()-1).getId() + " " + stops.get(stops.size()-1).getName() + "\n");
  }
  
  /**
   * Gets the time that a bus is next scheduled to arrive at a bus stop.
   *
   * @param  stop
   * @param  date
   * @param  time
   */
  public int nextTimeAtStop(BusStop stop, GregorianCalendar date, int time)
  {
    Route route = new Route(BusStopInfo.getRoutes(stop.getId())[0]);
    int stopPosition = stopPositionInRoute(stop);
    
    Journey[] journeys = Timetable.get_journeys(date, date, route);
    for (int j = 0; j < journeys.length; j++)
    {
      Service service = journeys[j].getService();
      int[] times = service.getTimes();
      if (stopPosition < times.length)
        if (times[stopPosition] >= time)
          return times[stopPosition];
    }
    return -1;
  }
  
  /**
   * Gets the times that buses are scheduled to arrive at a bus stop during
   * a particular time period.
   *
   * @param  stop      the bus stop in question
   * @param  date      the date under consideration
   * @param  time      the desired start time
   * @param  duration  the amount of time to look for buses
   * @return           int array of times buses are timetabled to arrive
   */
  public int[] timesAtStop(BusStop stop, GregorianCalendar date, int time, int duration)
  {
    Route route = new Route(BusStopInfo.getRoutes(stop.getId())[0]);
    int stopPosition = stopPositionInRoute(stop);
    
    // get the times from the journeys based on the stop position
    ArrayList<Integer> possibleTimes = new ArrayList<Integer>();
    Journey[] journeys = Timetable.get_journeys(date, date, route);
    for (int j = 0; j < journeys.length; j++)
    {
      Service service = journeys[j].getService();
      int[] times = service.getTimes();
      if (stopPosition < times.length)
        if (times[stopPosition] >= time && times[stopPosition] <= time + duration)
          possibleTimes.add(times[stopPosition]);
    }
    
    // convert to array and return
    int[] result = new int[possibleTimes.size()];
    for (int i = 0; i < result.length; i++)
      result[i] = possibleTimes.get(i);
      
    return result;
  }
  
  /**
   * Finds the position of a bus stop in a route. The first position is 0.
   *
   * @param  stop   the bus stop
   * @return        the position of the stop on the route
   */
  public static int stopPositionInRoute(BusStop stop)
  {
    // work out where the stop is in the service
    Route route = new Route(BusStopInfo.getRoutes(stop.getId())[0]);
    BusStop[] stops = route.getStops();
    int s = 0;
    int stopPosition = 0;
    while (!stops[s].equals(stop))
    {
      if (stops[s].isTimingPoint())
        stopPosition++;
      s++;
    }
    return stopPosition;
  }
  
  /**
   * Identifies which bus stop is the correct one to wait at for a
   * the journey between the two specified stops
   *
   * @param   origin       the requested origin bus stop
   * @param   destination  the requested destination bus stop
   * @return               the first actual bus stop on the journey
   */
  public BusStop first_stop(GraphPath<BusStop,Edge> graphPath)
  {
    Graph<BusStop,Edge> graph = graphPath.getGraph();
    ArrayList<Edge> legs = (ArrayList<Edge>)graphPath.getEdgeList();
    
    if (legs.get(0).getRoute() == -1)
      return Graphs.getOppositeVertex(graph, legs.get(0), graphPath.getStartVertex());
    else
      return graphPath.getStartVertex();
  }
  
  // compares two paths to see if they are equal when walking between stops
  // at the start and end is discounted
  private boolean comparePaths(GraphPath<BusStop,Edge> a, GraphPath<BusStop,Edge> b)
  {
    ArrayList<Edge> edgesA = (ArrayList<Edge>)a.getEdgeList();
    ArrayList<Edge> edgesB = (ArrayList<Edge>)b.getEdgeList();
    
    int startA, startB, endA, endB;
    startA = startB = endA = endB = -1;
    
    for (int s = 0; s < edgesA.size(); s++)
      if (edgesA.get(s).getRoute() > -1)
      {
        startA = s;
        break;
      }
    for (int s = 0; s < edgesB.size(); s++)
      if (edgesB.get(s).getRoute() > -1)
      {
        startB = s;
        break;
      }
    for (int s = edgesA.size() - 1; s >= 0 ; s--)
      if (edgesA.get(s).getRoute() > -1)
      {
        endA = s + 1;
        break;
      }
    for (int s = edgesB.size() - 1; s >= 0 ; s--)
      if (edgesB.get(s).getRoute() > -1)
      {
        endB = s + 1;
        break;
      }
    
    ArrayList<BusStop> stopsA = (ArrayList<BusStop>)Graphs.getPathVertexList(a);
    ArrayList<BusStop> stopsB = (ArrayList<BusStop>)Graphs.getPathVertexList(b);
    
    return stopsA.subList(startA, endA).equals(stopsB.subList(startB, endB));
  }
  
  private double find_path(BusStop origin, BusStop destination)
  {
    System.out.println("Paths between " + origin + " and " + destination);
   
    DijkstraShortestPath<BusStop,Edge> path = 
        new DijkstraShortestPath<BusStop,Edge>(network, origin, destination);
    
    
    GraphPath<BusStop,Edge> graphPath = path.getPath();
    Graph<BusStop,Edge> graph = graphPath.getGraph();
    ArrayList<Edge> legs = (ArrayList<Edge>)graphPath.getEdgeList();
    Iterator<Edge> iterator = legs.iterator();
    
    BusStop stop = graphPath.getStartVertex();
    
    while (iterator.hasNext())
    {
      Edge leg = iterator.next();
      BusStop opposite = Graphs.getOppositeVertex(graph, leg, stop);
      System.out.println(stop + " to " + opposite + " (" + leg.getRoute() +")");
      stop = opposite;
    }
    
    System.out.println();
    
    double length = path.getPathLength();
    if (length == Double.POSITIVE_INFINITY)
      return -1.0;
    else
      return length;
  }
  
  // represents an edge in a graph representing the network
  public class Edge extends DefaultWeightedEdge
  {
    private final int route;
    public Edge(int route_id)
    {
      route = route_id;
    }
    @Override
    public String toString()
    {
      return ""+route;
    }
    public int getRoute()
    {
      return route;
    }
  }

  
}
