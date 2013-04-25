//package org.jgrapht;
//import org.jgrapht.*;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.alg.DijkstraShortestPath;
import java.util.*;

public class Network
{
  private final DirectedWeightedMultigraph<BusStop,Route> network;
  
  public static void main(String[] args)
  {
    database.openBusDatabase();
    Network network = new Network();
    
    int origin = Integer.parseInt(args[0]);
    int destination = Integer.parseInt(args[1]);
    
    double length = network.find_path(new BusStop(origin), new BusStop(destination));
    System.out.println(length);
  }
  
  public Network()
  {
    network = new DirectedWeightedMultigraph<BusStop,Route>(Route.class);
    
    // build the network from the routes in the database
    Route[] routes = Route.getAllRoutes();
    for (int route = 0; route < routes.length; route++)
    {
      System.out.println("Route: " + routes[route].getID());
      
      BusStop[] stops = routes[route].getStops();
      for (int stop = 0; stop < stops.length; stop++)
      {
        System.out.println("  Stop: " + stops[stop].getId() + " " + stops[stop].getName());
        network.addVertex(stops[stop]);
        if (stop > 0)
          network.addEdge(stops[stop-1], stops[stop], new Route(routes[route].getID()));
      }
    }
    
    // add edges between stops in the same place
    HashSet<BusStop> stops = (HashSet<BusStop>)network.vertexSet();
    Iterator<BusStop> iterator = stops.iterator();
    while (iterator.hasNext())
    {
      
    }
/*    
    BusStop stop1 = new BusStop(770);
    BusStop stop2 = new BusStop(771);
    BusStop stop3 = new BusStop(772);
    BusStop stop4 = new BusStop(773);
    BusStop stop5 = new BusStop(774);
    BusStop stop6 = new BusStop(775);
    BusStop stop7 = new BusStop(776);
    BusStop stop8 = new BusStop(777);
    
    Route route1 = new Route(65);
    Route route2 = new Route(66);
    Route route3 = new Route(66);
    Route route4 = new Route(67);
    Route route5 = new Route(66);
    Route route6 = new Route(66);
    Route route7 = new Route(66);
    
    network.addVertex(stop1);
    network.addVertex(stop2);
    network.addEdge(stop1, stop2, route1);
    network.addVertex(stop3);
    network.addEdge(stop2, stop3, route2);
    network.addVertex(stop4);
    network.addEdge(stop3, stop4, route3);
    network.addVertex(stop5);
    network.addEdge(stop1, stop5, route4);
    network.addVertex(stop6);
    network.addVertex(stop7);
    network.addVertex(stop8);
    network.addEdge(stop3, stop6, route5);
    network.addEdge(stop6, stop7, route6);
    network.addEdge(stop7, stop8, route7);
*/   
  } // constructor
  
  public double find_path(BusStop origin, BusStop destination)
  {
    DijkstraShortestPath<BusStop,Route> path = 
          new DijkstraShortestPath<BusStop,Route>(network, origin, destination);
    double length = path.getPathLength();
    if (length == Double.POSITIVE_INFINITY)
      return -1.0;
    else
      return length;
  }
  
//  public void build_network()

  
}
