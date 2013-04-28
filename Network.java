import org.jgrapht.*;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.alg.DijkstraShortestPath;
import java.util.*;

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
    //System.out.println(length);
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
