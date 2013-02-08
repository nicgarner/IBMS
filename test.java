public class test
{
  public static void main(String args[])
  {
    database.openBusDatabase();
    int[] buses = BusInfo.getBuses();
    System.out.println("There are " + buses.length + " buses in the database:");
    for (int i = 0; i < buses.length; i++)
      System.out.print(buses[i] + " ");
    System.out.println();
  }
}

