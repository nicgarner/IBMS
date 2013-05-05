package test.passengerUI;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wrapper.*;
import objects.*;

/**
 *
 * @author nograda1
 */
public class AreaTest {

    public AreaTest() {
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
     * Test of getID method, of class Area.
     
    @Test
    public void testGetID() {
        System.out.println("getID");
        Area instance = null;
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Area.
     
    @Test
    public void testGetName() {
        System.out.println("getName");
        Area instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStopsInArea method, of class Area.
     
    @Test
    public void testGetStopsInArea() {
        System.out.println("getStopsInArea");
        Area instance = null;
        BusStop[] expResult = null;
        BusStop[] result = instance.getStopsInArea();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of getUniqueStops method, of class Area using the Hayfield area
     */
    @Test
    public void testGetUniqueStopsHayfield() {
        System.out.println("getUniqueStopsHayfield");
        Area instance = new Area(217);
        BusStop[] expResult = new BusStop[1];
        expResult[0] = new BusStop(793);      
        BusStop[] result = instance.getUniqueStops();
        assertEquals(expResult, result);       
        
    }

    /**
     * Test of getUniqueStops method, of class Area using the Compstall area
     */
    @Test
    public void testGetUniqueStopsCompstall() {
        System.out.println("getUniqueStopsCompstall");
        Area instance = new Area(211);
        BusStop[] expResult = null;
        BusStop[] result = instance.getUniqueStops();
        assertEquals(expResult, result);

    }

    /**
     * Test of getUniqueStops method, of class Area using the Stockport area
     */
    @Test
    public void testGetUniqueStopsStockport() {
        System.out.println("getUniqueStopsStockport");
        Area instance = new Area(209);
        BusStop[] expResult = new BusStop[5];
        expResult[0] = new BusStop(782);
        expResult[1] = new BusStop(804);
        expResult[2] = new BusStop(772);
        expResult[3] = new BusStop(771);
        expResult[4] = new BusStop(783);
        BusStop[] result = instance.getUniqueStops();
        assertEquals(expResult, result);

    }
   

    

    /**
     * Test of getAllAreas method, of class Area.
     */
    @Test
    public void testGetAllAreas() {
        System.out.println("getAllAreas");
        int expResult = 13;
        Area[] resultAreas = Area.getAllAreas();
        int result = resultAreas.length;
        assertEquals(expResult, result);       
    }

    /**
     * Test of getNonEmptyAreas method, of class Area.
     */
    @Test
    public void testGetNonEmptyAreas() {
        System.out.println("getNonEmptyAreas");
        int expResult = 11;
        Area[] resultAreas = Area.getNonEmptyAreas();
        int result = resultAreas.length;
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Area.
     
    @Test
    public void testToString() {
        System.out.println("toString");
        Area instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

}