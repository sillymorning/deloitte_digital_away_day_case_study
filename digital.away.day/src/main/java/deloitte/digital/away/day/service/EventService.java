/**
 * 
 */
package deloitte.digital.away.day.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import deloitte.digital.away.day.models.Event;
import deloitte.digital.away.exception.EventException;

/**
 * Service for Events operations such as reading ,
 * organizing and printing the organization possible
 * 
 * @author suman
 *
 */
public interface EventService {

	/** Read the input from the resource.
	 * 
	 * @param fileName
	 * @return list of events which are provided by the resource (considering file or any other source)
	 * @throws EventException
	 */
	public List<Event> readInputfromResouce(String fileName) throws EventException;

	/** Organize the events based of the number of teams and time-table available 
	 * 
	 * @param eventList
	 * @param startTimeMorning
	 * @param endTimeMorning
	 * @param startTimeEvening
	 * @param endTimeEvening
	 * @param lunchTime
	 * @param numberofTeams
	 * @return events list with there teams
	 * @throws EventException
	 */
	public List<List<Event>> organizeEvents(List<Event> eventList, LocalTime startTimeMorning, LocalTime endTimeMorning,
			LocalTime startTimeEvening, LocalTime endTimeEvening, Integer lunchTime, Integer numberofTeams) throws EventException;

	/**Used to render the output
	 * 
	 * @param organizedList
	 */
	public void viewOrganizedList(List<List<Event>> organizedList);
}
