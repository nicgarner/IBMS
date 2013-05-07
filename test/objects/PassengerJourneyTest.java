/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wrapper.database;


/**
 *
 * @author Adam Nogradi
 */
public class PassengerJourneyTest {

   

    public PassengerJourneyTest() {        
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
     * Test of compareTo method, of class PassengerJourney when the first
     * journey's start time is smaller.
     */   
    @Test
    public void testCompareTo1() {
        System.out.println("compareTo with first time smaller");
        PassengerJourney that = new PassengerJourney ();
        that.addStop(new BusStop (802), 800, 1);
        PassengerJourney instance = new PassengerJourney();
        instance.addStop(new BusStop (802), 799, 1);
        int expResult = -1;
        int result = instance.compareTo(that);
        assertEquals(expResult, result);        
    }

    /**
     * Test of compareTo method, of class PassengerJourney when the second
     * journey's start time is smaller.
     */
    @Test
    public void testCompareTo2() {
        System.out.println("compareTo with first time greater");
        PassengerJourney that = new PassengerJourney ();
        that.addStop(new BusStop (802), 799, 1);
        PassengerJourney instance = new PassengerJourney();
        instance.addStop(new BusStop (802), 800, 1);
        int expResult = 1;
        int result = instance.compareTo(that);
        assertEquals(expResult, result);
    }
    /**
     * Test of compareTo method, of class PassengerJourney when the two
     * journeys' start times are equal
     */
    @Test
    public void testCompareTo3() {
        System.out.println("compareTo with equal times");
        PassengerJourney that = new PassengerJourney ();
        that.addStop(new BusStop (802), 800, 1);
        PassengerJourney instance = new PassengerJourney();
        instance.addStop(new BusStop (802), 800, 1);
        int expResult = 0;
        int result = instance.compareTo(that);
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class PassengerJourney
     * Test of return statement when first journey's duration is smaller
     */
    @Test
    public void testCompareTo4() {
        System.out.println("compareTo with first duration smaller");
        PassengerJourney that = new PassengerJourney ();
        that.addStop(new BusStop (802), 800, 1);
        that.addStop(new BusStop (802), 803, 1);
        that.addStop(new BusStop (802), 810, 1);
        PassengerJourney instance = new PassengerJourney();
        instance.addStop(new BusStop (802), 800, 1);
        instance.addStop(new BusStop (802), 803, 1);
        instance.addStop(new BusStop (802), 805, 1);
        int expResult = -1;
        int result = instance.compareTo(that);
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class PassengerJourney 
     * Test of return statement when first journey's duration is greater
     */  
    @Test
    public void testCompareTo5() {
        System.out.println("compareTo with first duration greater");
        PassengerJourney that = new PassengerJourney ();
        that.addStop(new BusStop (802), 800, 1);
        that.addStop(new BusStop (802), 801, 1);
        that.addStop(new BusStop (802), 804, 1);
        that.addStop(new BusStop (802), 807, 1);
        PassengerJourney instance = new PassengerJourney();
        instance.addStop(new BusStop (802), 800, 1);
        instance.addStop(new BusStop (802), 801, 1);
        instance.addStop(new BusStop (802), 808, 1);
        instance.addStop(new BusStop (802), 810, 1);
        int expResult = 1;
        int result = instance.compareTo(that);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuration method (and duration method), of class PassengerJourney
     * 
     */
    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        PassengerJourney instance = new PassengerJourney();
        instance.addStop(new BusStop (802), 300, 1);
        instance.addStop(new BusStop (802), 305, 1);
        int result = instance.getDuration();
        int expResult = 5;
        assertEquals(expResult, result);
    }
}