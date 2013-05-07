package objects;

/**
 *
 * @author garnern1
 */
public class LiveJourney
{
    private final Journey journey;
    private final int status;
    
    
    public LiveJourney(Journey journey, int status)
    {
        this.journey = journey;
        this.status = status;
    }
    public Service getService()
    {
        return journey.getService();
    }
    public int getStatus()
    {
        return status;
    }
    public Journey getJourney()
    {
        return journey;
    }
}