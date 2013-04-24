/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adam Nogradi
 *
 * Represents an Area in the IBMS System. An area is comprised of an ID, a name
 * and an array of bus stops in it.
 */
public class Area {

    private final int id;
    private final String name;
    private final BusStop[] stopsInArea;

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

    