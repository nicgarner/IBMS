/**
 * Represents a bus stop in the IBMS system.
 *
 * Implemented by Nic.
 */

public class BusStop {

  // a bus stop has a name, id number and boolean flag for timing point
  private final int id;
  private final String name;
  private boolean timingPoint;
  
  /**
   * Create a bus stop from the id of the bus stop.
   *
   * @param busStopID the id of the bus stop
   */
  public BusStop(int busStopID)
  {
    try
    {
      id = busStopID;
      name = BusStopInfo.getFullName(busStopID);
      timingPoint = false;
    }
    catch (InvalidQueryException e) { throw e; }
  }
  
  /**
   * Sets whether the bus stop is a timing point.
   *
   * @param value whether the bus stop is a timing point
   */
  public void setTimingPoint(boolean value)
  {
    timingPoint = value;
    return;
  }
  
  /**
   * Returns a string representation of the bus stop.
   *
   * @return boolean whether the bus stop is a timing point
   */
  public boolean isTimingPoint()
  {
    return timingPoint;
  }
  
  /**
   * Returns a string representation of the bus stop.
   *
   * @return String the name of the bus stop appended with an asterisk if
   *                it is a timing point
   */
  public String toString()
  {
    String string = name;
    if (timingPoint)
      string += " *";
    return string;
  }
  
  /*
  // main method for testing only
  public static void main(String args[])
  {
    database.openBusDatabase();
    BusStop stop1 = new BusStop(770);
    BusStop stop2 = new BusStop(771);
    stop2.setTimingPoint(true);
    System.out.println(stop1);
    System.out.println(stop2);
    BusStop stop3 = new BusStop(1);
  }
  */
  
}

