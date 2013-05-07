package objects;

/**
 *
 * @author garnern1
 */
public class BusStopCombo
{
    private final BusStop busStop;
    public BusStopCombo(BusStop stop)
    {
        busStop = stop;
    }
    public BusStop getBusStop()
    {
        return busStop;
    }
    @Override
    public String toString()
    {
        return busStop.getName();
    }
}