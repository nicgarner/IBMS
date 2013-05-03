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
import java.util.*;
import java.util.GregorianCalendar;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Network
{
  static boolean verbose = false;
  private final DirectedWeightedMultigraph<BusStop,Edge> network;
  
  public static void main(String[] args)
  {
    verbose = true;
    database.openBusDatabase();
    Network network = new Network();
    
    BusStop origin = new BusStop(Integer.parseInt(args[0]));
    BusStop destination = new BusStop(Integer.parseInt(args[1]));
    GregorianCalendar day = new GregorianCalendar(2013, 3, 29);
    int time = 840; // 14:00
    
    network.journeys(origin, destination, time, day);
  }
  
  /**
   * Builds a graph representing the network with no edge weights.
   */
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
        {
          network.addEdge(stops[stop-1], stops[stop], new Edge(routes[route].getID()));
          Edge edge = network.getEdge(stops[stop-1], stops[stop]);
          network.setEdgeWeight(edge, 0.0);
        }
        
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
            Edge edge = network.getEdge(stops[stop], busStop);
            network.setEdgeWeight(edge, 5.0);
            network.addEdge(busStop, stops[stop], new Edge(-1));
            edge = network.getEdge(busStop, stops[stop]);
            network.setEdgeWeight(edge, 5.0);
          }
        } // while (nodes in graph)
      } // for (stops on route)
    } // for (routes)
    if (verbose)
      System.out.println();
  } // constructor
  
  public void journeys(BusStop origin, BusStop destination, int time,
                       GregorianCalendar date)
  {
    // get possible routes for the journey
    KShortestPaths<BusStop,Edge> pathsCalculator = 
                           new KShortestPaths<BusStop,Edge>(network, origin, 3);
    ArrayList<GraphPath<BusStop,Edge>> paths = 
      (ArrayList<GraphPath<BusStop,Edge>>)pathsCalculator.getPaths(destination);
    
    // work out the first stop and its route
    BusStop first = first_stop(paths.get(0));
    Route route = new Route(BusStopInfo.getRoutes(first.getId())[0]);
    int[] times = timesAtStop(first, date, time, 60);
    
    for (int p = 0; p < paths.size(); p++)
      print_path(paths.get(p));
    
    // get times for the journeys
    for (int t = 0; t < times.length; t++)
      for (int p = 0; p < paths.size(); p++)
      {
        PassengerJourney journey = new PassengerJourney();
        
      }
  }
  
  public void print_path(GraphPath<BusStop,Edge> path)
  {
    ArrayList<Edge> edges = (ArrayList<Edge>)path.getEdgeList();
    ArrayList<BusStop> stops = (ArrayList<BusStop>)Graphs.getPathVertexList(path);
    for (int s = 0; s < edges.size(); s++)
    {
      System.out.print(stops.get(s).getId() + " " + stops.get(s).getName());
      System.out.println(" (" + edges.get(s) + ") ->");
    }
    System.out.println(stops.get(stops.size()-1).getId() + " " + stops.get(stops.size()-1).getName() + "\n");
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
    int stopPosition = stopPositionInRoute(stop, route);
    
    // get the times from the journeys based on the stop position
    ArrayList<Integer> possibleTimes = new ArrayList<Integer>();
    Journey[] journeys = Timetable.get_journeys(date, date, route);
    for (int j = 0; j < journeys.length; j++)
    {
      Service service = journeys[j].getService();
      int[] times = service.getTimes();
      if (stopPosition < times.length)
      {
        if (times[stopPosition] > time && times[stopPosition] < time + duration)
          possibleTimes.add(times[stopPosition]);
      }
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
   * @param  route  the route to look in
   * @return        the position of the stop on the route
   */
  public static int stopPositionInRoute(BusStop stop, Route route)
  {
    // work out where the stop is in the service
    BusStop[] stops = route.getStops();
    int stopPosition = 0;
    while (!stops[stopPosition].equals(stop))
      stopPosition++;
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
