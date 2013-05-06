package objects;
import wrapper.*;


import wrapper.InvalidQueryException;
import wrapper.BusStopInfo;

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

  /**
   * Returns the name of the bus stop
   * @return name the bus stop's name
   */
  public String getName ()
  {
       return name;
  }
  
  /**
   * Returns the database id number of the bus stop
   * @return the bus stop's id
   */
  public int getId ()
  {
       return id;
  }
  
  /**
   * Returns true if other bus stop is in the same vicinity as this stop.
   * I.e. you can get between them without catching a bus.
   *
   * @param other the other bus stop
   * @return      true if names (inc. area) are equal and ids are different
   */
  public boolean inVicinity(BusStop other)
  {
    return (this.getName().equals(other.getName()) && 
            this.getId() != other.getId());
  }
  
  @Override
  public boolean equals(Object other)
  {
    boolean result = false;
    if (other instanceof BusStop)
    {
      BusStop that = (BusStop) other;
      result = (this.getName().equals(that.getName()) && this.getId() == that.getId());
    }
    return result;
  }
  
  @Override
  public int hashCode()
  {
    return (41 * (41 + getId()) + name.hashCode());
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

