package deloitte.digital.away.day;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import deloitte.digital.away.day.models.Event;
import deloitte.digital.away.day.service.EventService;
import deloitte.digital.away.day.service.impl.FileEventServiceImpl;
import deloitte.digital.away.exception.EventException;

/**
 * Main file to run the deloitte digital away day .
 * O/p : list of events organized in two teams
 */
public class App {
	public static void main(String[] args) {
		System.out.println("*** Event Organizer **");
		EventService serviceHandler = new FileEventServiceImpl();
		try {
			List<Event> eventList = serviceHandler.readInputfromResouce("test_data.txt");
			List<List<Event>> organizedList = serviceHandler.organizeEvents(eventList, LocalTime.of(9, 00),
					LocalTime.of(12, 0), LocalTime.of(13, 00), LocalTime.of(17, 00), 60, 2);
			serviceHandler.viewOrganizedList(organizedList);
		} catch (EventException e) {
			System.out.println(e);
		}

	}
}
