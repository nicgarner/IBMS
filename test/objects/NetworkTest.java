/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import java.util.GregorianCalendar;
import objects.Network.Edge;
import org.jgrapht.GraphPath;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wrapper.database;
import org.jgrapht.*;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;


/**
 *
 * @author Adam Nogradi
 */
public class NetworkTest {

    public NetworkTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        database.openBusDatabase();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }   

    /**
     * Test of nextTimeAtStop method, of class Network.
     * Test case: Bus stop ID 794 (Birch Vale, Grouse Hotel), current date and
     * 6:40 as time
     */
    @Test
    public void testNextTimeAtStop() {
        System.out.println("nextTimeAtStop");
        BusStop stop = new BusStop (794);
        GregorianCalendar date = new GregorianCalendar (2013, 4, 8);
        int time = 400;
        Network instance = new Network();
        int expResult = 401;
        int result = instance.nextTimeAtStop(stop, date, time);
        assertEquals(expResult, result);        
    }

    /**
     * Test of timesAtStop method, of class Network.
     * Test case: Bus stop ID 794 (Birch Vale, Grouse Hotel), current date,
     * 6:40 as time and a 60-minute period
     */
    @Test
    public void testTimesAtStop() {
        System.out.println("timesAtStop");
        BusStop stop = new BusStop (794);
        GregorianCalendar date = new GregorianCalendar (2013, 4, 8);
        int time = 400;
        int duration = 60;
        Network instance = new Network();
        int[] expResult = {401, 434};
        int[] result = instance.timesAtStop(stop, date, time, duration);
        //for (int i = 0; i < result.length; i++)
        //System.out.println (result[i]);
        for (int i = 0; i < result.length; i++)
        assertEquals(expResult[i], result[i]);
    }

    /**
     * Test of stopPositionInRoute method, of class Network.
     * Test case: Bus stop ID 794 (Birch Vale, Grouse Hotel)
     */
    @Test
    public void testStopPositionInRoute() {
        System.out.println("stopPositionInRoute");
        BusStop stop = new BusStop (794);
        int expResult = 4;
        int result = Network.stopPositionInRoute(stop);
        assertEquals(expResult, result);        
    }

    /**
     * Test of first_stop method, of class Network.
     * Test case: Stockport bus staion where the passenger has to walk
     * to the appropriate stop.
     */
    @Test
    public void testFirst_stop() {
        System.out.println("first_stop");
        Network instance = new Network ();
        DirectedWeightedMultigraph<BusStop,Edge> graph = new DirectedWeightedMultigraph<BusStop,Edge>(Edge.class);

        // build the network from the routes in the database
        Route[] routes = Route.getAllRoutes();
        for (int route = 0; route < routes.length; route++)
        {
          BusStop[] stops = routes[route].getStops();
          for (int stop = 0; stop < stops.length; stop++)
          {

            // add the stop, and connect it to the previous one on the route
            graph.addVertex(stops[stop]);

            if (stop > 0)
            {
              Network.Edge edge = instance.new Edge(routes[route].getID());
              graph.addEdge(stops[stop-1], stops[stop], edge);
              graph.setEdgeWeight(edge, 10.0);
            }

            // add edges between stops with the same area and name
            Set<BusStop> allStops = (Set<BusStop>)graph.vertexSet();
            Iterator<BusStop> iterator = allStops.iterator();
            while (iterator.hasNext())
            {
              BusStop busStop = iterator.next();
              if (busStop.inVicinity(stops[stop]))
              {
                Network.Edge edge = instance.new Edge(-1);
                graph.addEdge(stops[stop], busStop, edge);
                graph.setEdgeWeight(edge, 30.0);
                edge = instance.new Edge(-1);
                graph.addEdge(busStop, stops[stop], edge);
                graph.setEdgeWeight(edge, 30.0);
              }
            } // while (nodes in graph)
          } // for (stops on route)
        } // for (routes)

        BusStop origin = new BusStop (770);
        BusStop destination = new BusStop (796);

        KShortestPaths<BusStop,Edge> pathsCalculator =
                       new KShortestPaths<BusStop,Edge>(graph, origin, 2);

    ArrayList<GraphPath<BusStop,Edge>> paths = (ArrayList<GraphPath<BusStop,Edge>>)pathsCalculator.getPaths(destination);
    GraphPath<BusStop, Edge> graphPath = paths.get(0);    
        BusStop expResult = new BusStop(804);
        BusStop result = instance.first_stop(graphPath);
        assertEquals(expResult, result);        
    }

}