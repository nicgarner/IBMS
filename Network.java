//package org.jgrapht;
//import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.DijkstraShortestPath;

public class Network
{
  private final DirectedWeightedMultigraph<BusStop,Route> network;
  
  public static void main(String[] args)
  {
    database.openBusDatabase();
    Network network = new Network();
  }
  
  public Network()
  {
    network = new DirectedWeightedMultigraph<BusStop,Route>(Route.class);
    
    BusStop stop1 = new BusStop(770);
    BusStop stop2 = new BusStop(771);
    BusStop stop3 = new BusStop(772);
    BusStop stop4 = new BusStop(773);
    BusStop stop5 = new BusStop(774);
    
    Route route1 = new Route(65);
    Route route2 = new Route(66);
    
    network.addVertex(stop1);
    network.addVertex(stop2);
    network.addEdge(stop1, stop2, route1);
    network.addVertex(stop3);
    network.addEdge(stop2, stop3, route2);
    network.addVertex(stop4);
    network.addEdge(stop3, stop4, route2);
    network.addVertex(stop5);
    
    double length = find_path(stop3, stop4);
    System.out.println(length);
    
  } // constructor
  
  public double find_path(BusStop origin, BusStop destination)
  {
    DijkstraShortestPath<BusStop,Route> path = 
          new DijkstraShortestPath<BusStop,Route>(network, origin, destination);
    return path.getPathLength();
  }
  
//  public void build_network()

  
}
