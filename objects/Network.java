package objects;

import objects.BusStop;
import objects.Journey;
import objects.Timetable;
import objects.Service;
import objects.Route;
import wrapper.database;
import wrapper.BusStopInfo;
import org.jgrapht.*;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.alg.DijkstraShortestPath;
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
    
    int origin = Integer.parseInt(args[0]);
    int destination = Integer.parseInt(args[1]);
    
    double length = network.find_path(new BusStop(origin), new BusStop(destination));
    
    BusStop first = network.first_stop(new BusStop(origin), new BusStop(destination));
    Route route = new Route(BusStopInfo.getRoutes(first.getId())[0]);
    int stopPosition = stopPositionInRoute(first, route);
    
    GregorianCalendar today = new GregorianCalendar(2013, 3, 29);
    int time = 840; // 14:00
    
    System.out.println("Start at stop " + first.getId() + ", which is " + stopPosition + "th on route " + route.getID());
    
    ArrayList<Integer> possibleTimes = new ArrayList<Integer>();
    Journey[] journeys = Timetable.get_journeys(today, today, route);
    for (int j = 0; j < journeys.length; j++)
    {
      Service service = journeys[j].getService();
      int[] times = service.getTimes();
      if (stopPosition < times.length)
      {
        if (times[stopPosition] > time && times[stopPosition] < time + 60)
          possibleTimes.add(times[stopPosition]);
      }
    }
    
    System.out.println("On " + Timetable.dateToString(today) + " from " + Timetable.minutesToTime(time) + " possible departure times are:");
    for (int i = 0; i < possibleTimes.size(); i++)
      System.out.print(Timetable.minutesToTime(possibleTimes.get(i)) + ", ");
    System.out.println();
    
    
    
    //System.out.println(length);
  }
  
  public static int stopPositionInRoute(BusStop stop, Route route)
  {
    // work out where the stop is in the service
    BusStop[] stops = route.getStops();
    int stopPosition = 0;
    while (!stops[stopPosition].equals(stop))
      stopPosition++;
    return stopPosition;
  }
  
  public Network()
  {
    network = new DirectedWeightedMultigraph<BusStop,Edge>(Edge.class);
    
    // build the network from the routes in the database
    Route[] routes = Route.getAllRoutes();
    for (int route = 0; route < routes.length; route++)
    {
      if (verbose)
        System.out.println("Route: " + routes[route].getID());
      
      BusStop[] stops = routes[route].getStops();
      for (int stop = 0; stop < stops.length; stop++)
      {
        if (verbose)
          System.out.println("  Stop: " + stops[stop].getId() + " " + stops[stop].getName());
        
        // add the stop, and connect it to the previous one on the route
        network.addVertex(stops[stop]);
        
        if (stop > 0)
          network.addEdge(stops[stop-1], stops[stop], new Edge(routes[route].getID()));
        
        // add edges between stops with the same area and name
        Set<BusStop> allStops = (Set<BusStop>)network.vertexSet();
        Iterator<BusStop> iterator = allStops.iterator();
        while (iterator.hasNext())
        {
          BusStop busStop = iterator.next();
          if (busStop.inVicinity(stops[stop]))
          {
            //if (verbose)
              //System.out.println("        Matches " + busStop.getId() + " " + busStop.getName());
            network.addEdge(stops[stop], busStop, new Edge(-1));
            network.addEdge(busStop, stops[stop], new Edge(-1));
          }
        } // while (nodes in graph)
      } // for (stops on route)
    } // for (routes)
    if (verbose)
      System.out.println();
  } // constructor
  
  /**
   * Identifies which bus stop is the correct one to wait at for a
   * the journey between the two specified stops
   *
   * @param   origin       the requested origin bus stop
   * @param   destination  the requested destination bus stop
   * @return               the first actual bus stop on the journey
   */
  public BusStop first_stop(BusStop origin, BusStop destination)
  {
    DijkstraShortestPath<BusStop,Edge> path = 
           new DijkstraShortestPath<BusStop,Edge>(network, origin, destination);
    
    GraphPath<BusStop,Edge> graphPath = path.getPath();
    Graph<BusStop,Edge> graph = graphPath.getGraph();
    ArrayList<Edge> legs = (ArrayList<Edge>)graphPath.getEdgeList();
    
    if (legs.get(0).getRoute() == -1)
      return Graphs.getOppositeVertex(graph, legs.get(0), graphPath.getStartVertex());
    else
      return graphPath.getStartVertex();
  }
  
  public double find_path(BusStop origin, BusStop destination)
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
  
  public class Edge
  {
    public final int route;
    //public final int start;
    //public final int duration;
    
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
  
//  public void build_network()

  
}
