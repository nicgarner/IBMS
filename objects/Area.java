package objects;
import wrapper.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adam Nogradi
 *
 * Represents an Area in the IBMS System. An area is comprised of an ID, a name,
 * an array of bus stops in it and a flag to sign if there are bus stops in it.
 */

import wrapper.InvalidQueryException;
import wrapper.BusStopInfo;
import java.util.ArrayList;

public class Area {

    // Area properties
    private final int id;
    private final String name;
    private final BusStop[] stopsInArea;
    private static boolean isEmpty;

    /**
     * Create an area given an ID.
     * @param areaID the ID of the area to be constructed
     */
    public Area (int areaID)
    {
        try
        {
            id = areaID;
            name = BusStopInfo.getAreaName(areaID);

            int[] stops = BusStopInfo.getBusStopsInArea(areaID);
            stopsInArea = new BusStop[stops.length];

            if (stops.length != 0)
            {
                for (int i = 0; i < stops.length; i++)
                {
                    stopsInArea[i] = new BusStop(stops[i]);
                }
                isEmpty = false;
            }

            else
                isEmpty = true;

        }

        catch (InvalidQueryException e)
        {
            throw e;
        }
    }// constructor

    /**
     * Creates an area given the area name
     * @param areaName the name of the area to be constructed
     */

    public Area (String areaName)
    {
        try
        {
            name = areaName;
            id = BusStopInfo.findAreaByName(name);

            int[] stops = BusStopInfo.getBusStopsInArea(id);
            stopsInArea = new BusStop[stops.length];

            for (int i = 0; i < stops.length; i++)
            {
                stopsInArea[i] = new BusStop(stops[i]);
            }
        }

        catch (InvalidQueryException e)
        {
            throw e;
        }
    }// constructor

    /**
    * Returns the area id.
    *
    * @return int the area id
    */
    public int getID()
    {
        return id;
    }

   /**
    * Returns the area name
    *
    * @return String the area name
    */

    public String getName()
    {
        return name;
    }

    /**
     * Returns the bus stops in the area
     * @return busStopsInArea the bus stop objects in the area
     */
    public BusStop[] getStopsInArea ()
    {
        return stopsInArea;
    }

    /**
     * Returns only the unique bus stops in the given area
     * @return uniqueArray the unique bus stops
     */
    public BusStop[] getUniqueStops ()
    {
        BusStop[] allStops = getStopsInArea();
        ArrayList <BusStop> uniqueStops = new ArrayList <BusStop> ();
        BusStop[] uniqueArray = null;
        int i = 0;

        if (allStops.length != 0)
        {
            while (i != allStops.length - 1)
            {
                //System.out.println(allStops[i]);
                if (!(allStops[i].getName().equals(allStops[i + 1].getName()))
                        || allStops.length < 3)
                                uniqueStops.add(allStops[i]);
                i++;
            }// while
        if (allStops[allStops.length - 1].getName().equals
                    (allStops[allStops.length - 2].getName()) && allStops.length > 2)
            uniqueStops.add(allStops[allStops.length - 1]);      
           
        uniqueArray = uniqueStops.toArray(new BusStop[uniqueStops.size()]);              
        }// if       

        return uniqueArray;       
    }
    


    /**
     * Get all the areas from the database
     * @return areas and Area array containing all the areas from the database
     */
    public static Area[] getAllAreas()
    {
        int[] areaIDs = BusStopInfo.getAreas();
        Area[] areas = new Area[areaIDs.length];

        for (int i = 0; i < areaIDs.length; i++)
        {
            areas[i] = new Area (areaIDs[i]);
        }
        return areas;
    }

    /**
     * Get all the areas that have bus stops in them from the database
     * @return nonEmptyArray an Area array of the non-empty areas from the database
     */
    public static Area[] getNonEmptyAreas()
    {
        int[] areaIDs = BusStopInfo.getAreas();
        Area[] areas = new Area[areaIDs.length];
        ArrayList<Area> nonEmptyAreas = new ArrayList<Area>();

        for (int i = 0; i < areaIDs.length; i++)
        {
            areas[i] = new Area (areaIDs[i]);

            if (!(areas[i].isEmpty))
                nonEmptyAreas.add(areas[i]);
        }
        Area[] nonEmptyArray = nonEmptyAreas.toArray(new Area[nonEmptyAreas.size()]);
        return nonEmptyArray;
    }



    /**
     * Returns the area's stops.
     * @return string the list of stops in the area
     */
    @Override
    public String toString ()
    {
        String string = name + ":";

        for (int i = 0; i < stopsInArea.length; i++)
        {
          string+= "\n" + stopsInArea[i];
        }
        return string;
    }

    /**
     * Main method for testing only
     */

   /* public static void main (String args[])
    {
        database.openBusDatabase();
        Area[] areas = Area.getAllAreas();
        for (int i = 0; i < areas.length; i++)
            System.out.println(areas[i] + "\n");
    }*/

}//class

    
