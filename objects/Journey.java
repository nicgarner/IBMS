package objects;

import wrapper.database;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;

/** 
 * Creates a journey for IBMS rostering. A journey is a combination of a date 
 * and a service. The combination of a service and a date will give a unique 
 * pair, called a journey. Implemented by Adam, Nic and Kris
*/

public class Journey
{
	private Service service;
	private GregorianCalendar date;
	
	/**
	 * Represents a journey in the system. 
	 * @param  serviceID  the service's ID from the database (a column of a timetable)
	 * @param  date       the journey's date
	 */
	public Journey (int serviceID, GregorianCalendar journeyDate)
	{
		service = new Service(serviceID);
		date = (GregorianCalendar) journeyDate.clone();
	}// constructor
	
	/**
	 * Instantiates a journey saved in the database.
	 * @oaram  journeyID    the id of the journey record in the database
	 * @oaram  journeyDate  the date of the journey
	 * @param  value        distinguishes between the two otherwise identical
	 *                      constructor signitures
	 */
	public Journey (int journeyID, GregorianCalendar journeyDate, boolean value)
	{
		this(database.busDatabase.find_id("service_id", "journey", 
	                                    "journey_id", journeyID), journeyDate);
	}// constructor
	
	/**
         * Method for selecting relevant bus stop to display real time info
         * and applying delays, cancellations
         
        public static Journey[] getJourneys(BusStop stop, GregorianCalendar date,
                                            Route route)
        {
          int minute, hour, pastMidnight ;
          GregorianCalendar curTime = new GregorianCalendar() ;
          minute = curTime.get(Calendar.MINUTE) ;
          hour = curTime.get(Calendar.HOUR) ;
          pastMidnight = (hour * 60) + minute ;

          ArrayList<Journey> journeys = new ArrayList<Journey>();
          
          GregorianCalendar day = (GregorianCalendar)date.clone()
         
          //iterate over current day 
          while(!day.after(date))
          {
            int[] services = TimetableInfo.getServices(route.getID(), day.getTime());
            for (int service = 0; service < services.length; service++)
                journeys.add(new Journey(services[service], day));
          }
          
          Journey[] journeys_array = new Journey[journeys.size()];
          journeys.toArray(journeys_array);
        
          int stopPosition = Network.stopPositionInRoute(stop) ;
          for (int i = 0; i < journeys_array.length; i++)
          {
             journeys_array[i].getService; 
          }
}
          
     */   
        /**
	 * Gets the service ID of the journey
	 * @return the service ID of the journey
	 */	
	public Service getService ()
	{
		return service;
	}// getJourneyID

    public static String view_RT_info(BusStop stop,Route route)
    {
      LiveJourney[] liveJourneys = Timetable.getAlterTimes(route) ;
      int stopPosition = Network.stopPositionInRoute(stop) ;
      String string = "" ;

      for (int i = 0; i < liveJourneys.length; i++)
      {
         LiveJourney liveJourney = liveJourneys[i];
         Service curService = liveJourney.getService() ;
         //get times for current journey
         int[] times = curService.getTimes();
         int late ;

         if (((stopPosition < times.length) && ((Timetable.getCurTime()+60) > times[stopPosition])) && (Timetable.getCurTime() < times[stopPosition]))
         {
            
            int status = liveJourneys[i].getStatus() ;
            if (status == -1) {
             System.out.println("check2") ;
               string +=  "The " + route.getName() + " service is cancelled due to " +
                    "forcasted snow and icy conditions! We apologize for the inconvenience.\n" ; }
            else if (status == 0)
            {
               
               string += "The " + route.getName() + " service is scheduled to arrive at " +
                     (Timetable.minutesToTime(times[stopPosition])) + "\n" ;
            }
                else
            {
              late = times[stopPosition] + liveJourneys[i].getStatus() ;
              string += "The " + route.getName() + " service is late by " + liveJourneys[i].getStatus() + " minutes due to cows crossing the road and is due to arrive at " +
                     Timetable.minutesToTime(late) + " We apologize for the inconvenience! \n" ;
            }
         }//if
       }//for
      return string ;
    }//view_RT_info

       public static void printtest()
  {
     Route route = new Route(68) ;
     //LiveJourney[] journeys = Timetable.getAlterTimes(route) ;

     for(int i = 0; i < 8; i++)
     {
         System.out.println(i) ;
     }
  }
    

        /**
	 * Gets the date of the journey
	 * @return the date of the journey
	 */	
	public GregorianCalendar getDate ()
	{
		return date;
	}// getJourneyID
	
	/**
	 * Gets the start time of the journey.
	 * @return the start time of the journey
	 */
        public int startTime()
        {
          return service.startTime();
        }
	
	/**
	 * Gets the end time of the journey.
	 * @return the end time of the journey
	 */
        public int endTime()
        {
          return service.endTime();
        }
	
	/**
	 * Gets the duration of the journey in minutes.
	 * @return the duration of the journey in minutes
	 */
        public int duration()
        {
          return service.duration();
        }
	
	/**
	 * Returns a string representation of the journey.
	 * @return string representation of the journey
	 */
	public String toString ()
	{
		return "Service: " + service + ", " +
		       "Date: " + Timetable.dateToString(date);
	}// toString
	
	/**
	 * Returns a more detailed string representation of the journey.
	 * @return string representation of the journey
	 */
	public String toFullString ()
	{
		return "Service: " + service.toFullString() + ", " +
		       "Date: " + Timetable.dateToString(date) + ", " +
		       "Duration " + Timetable.minutesToDuration(duration());
	}// toString
	
}// class
