/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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
 * @author zarkovy1
 */
public class TimetableTest {

    public TimetableTest() {
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
     * Test of get_services method, of class Timetable.
     * @ author zarkovy1
     */
    @Test
    public void testGet_services() {
        System.out.println("get_services");
        Route route = new Route(67);
        GregorianCalendar date = new GregorianCalendar(2013, 5, 13);
        Service[] expResult = new Service[20];
        expResult[0] = new Service(6460);
        expResult[1] = new Service(6461);
        expResult[2] = new Service(6462);
        expResult[3] = new Service(6463);
        expResult[4] = new Service(6464);
        expResult[5] = new Service(6465);
        expResult[6] = new Service(6466);
        expResult[7] = new Service(6467);
        expResult[8] = new Service(6468);
        expResult[9] = new Service(6469);
        expResult[10] = new Service(6470);
        expResult[11] = new Service(6471);
        expResult[12] = new Service(6472);
        expResult[13] = new Service(6473);
        expResult[14] = new Service(6474);
        expResult[15] = new Service(6475);
        expResult[16] = new Service(6476);
        expResult[17] = new Service(6477);
        expResult[18] = new Service(6478);
        expResult[19] = new Service(6479);
        Service[] result = Timetable.get_services(route, date);
        assertEquals(expResult, result);
    }

 
}