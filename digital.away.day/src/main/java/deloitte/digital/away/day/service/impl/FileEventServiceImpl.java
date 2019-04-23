package deloitte.digital.away.day.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import deloitte.digital.away.day.models.Event;
import deloitte.digital.away.day.service.EventService;
import deloitte.digital.away.exception.EventException;

/**
 * Class implementing EventService to read data from text files and organize them.
 * 
 * @author suman
 *
 */
public class FileEventServiceImpl implements EventService {

	private static final String SPRINT = "sprint";
	static String REGEX = "^[A-Za-z0-9][A-Za-z0-9& -]* (([0-9]+min)|sprint)$";
	
	/*
	 * (non-Javadoc)
	 * @see deloitte.digital.away.day.service.EventService#readInputfromResouce(java.lang.String)
	 */
	public List<Event> readInputfromResouce(String fileName) throws EventException{
		List<Event> eventsList = new ArrayList<Event>();
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());

			Stream<String> lines = Files.lines(path);
			Iterator<String> iterator = lines.iterator();
			while (iterator.hasNext()) {
				String line = iterator.next();
				if (Pattern.matches(REGEX, line)) {
					String eventName = line.substring(0, line.lastIndexOf(" "));
					Integer duration = this.getMinutes(line.substring(line.lastIndexOf(" ") + 1));
					eventsList.add(new Event(eventName, duration));
				} else {
					lines.close();
					throw new EventException("File Resource is not in correct format ,line :"+line);
				}
			}
			lines.close();

		} catch (Exception ex) {
			throw new EventException("There is a problem loading file");
		}
		return eventsList;
	}

	/**
	 * @param min
	 * @return integer val used as duration for from the string 
	 * @throws EventException
	 */
	private Integer getMinutes(String min) throws EventException{
		Integer result = 0;
		if (SPRINT.equals(min))
			result = 15;
		else
			result = Integer.valueOf(min.substring(0, min.length() - 3));

		if (result <= 0 || result > 60) {
			 throw new EventException("Not a valid Duration : " +result);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see deloitte.digital.away.day.service.EventService#organizeTasks(java.util.List, java.time.LocalTime, java.time.LocalTime, java.time.LocalTime, java.time.LocalTime, java.lang.Integer, java.lang.Integer)
	 */
	public List<List<Event>> organizeEvents(List<Event> eventList, LocalTime startTimeMorning, LocalTime endTimeMorning,
			LocalTime startTimeEvening, LocalTime endTimeEvening, Integer lunchTime, Integer numberofTeams) throws EventException {
		long elapsedMinutes = Duration.between(startTimeMorning, endTimeEvening).toMinutes();
		List<List<Event>> organizedList = new ArrayList<List<Event>>();
		while (numberofTeams != 0) {
			long timeRemaing = elapsedMinutes;
			LocalTime startTime = startTimeMorning;
			List<Event> events = new LinkedList<Event>();
			boolean lunchDone = false;
			while (timeRemaing > 0) {
				eventList.removeAll(events);
				if (eventList.size() > 0) {
					Event maxDurationTask = this.getMaxDurationEvent(eventList);
					LocalTime endTime = startTime.plusMinutes(maxDurationTask.getDuration());
					if (endTime.compareTo(endTimeMorning) < 0) {
						Event e = this.createTask(maxDurationTask, startTime, endTime);
						events.add(e);
						startTime = endTime;
						timeRemaing = timeRemaing - e.getDuration();
					} else if (endTime.compareTo(endTimeMorning) == 0 && !lunchDone) {
						Event e = this.createTask(maxDurationTask, startTime, endTime);
						events.add(e);
						startTime = endTime;
						timeRemaing = timeRemaing - e.getDuration();
						Event lunchEvent = this.createTask("Lunch", endTimeMorning,
								endTimeMorning.plusMinutes(lunchTime), lunchTime);
						events.add(lunchEvent);
						startTime = lunchEvent.getStartTime().plusMinutes(lunchEvent.getDuration());
						lunchDone = true;
						timeRemaing = timeRemaing - lunchEvent.getDuration();
					} else if (endTime.compareTo(endTimeMorning) > 0 && !lunchDone) {
						Event minDurationTask = this.getMinDurationEvent(eventList);
						endTime = startTime.plusMinutes(minDurationTask.getDuration());
						if (endTime.compareTo(endTimeMorning) < 0) {
							Event e = this.createTask(minDurationTask, startTime, endTime);
							events.add(e);
							startTime = endTime;
							timeRemaing = timeRemaing - e.getDuration();
						} else {
							Event lunchEvent = this.createTask("Lunch", endTimeMorning,
									endTimeMorning.plusMinutes(lunchTime), lunchTime);
							events.add(lunchEvent);
							startTime = lunchEvent.getStartTime().plusMinutes(lunchEvent.getDuration());
							lunchDone = true;
							timeRemaing = timeRemaing - lunchEvent.getDuration();
						}
					} else if (lunchDone && endTime.compareTo(endTimeEvening) < 0) {
						Event e = this.createTask(maxDurationTask, startTime, endTime);
						events.add(e);
						startTime = endTime;
						timeRemaing = timeRemaing - e.getDuration();
					} else if (lunchDone && endTime.compareTo(endTimeEvening) == 0) {
						Event e = this.createTask(maxDurationTask, startTime, endTime);
						events.add(e);
						startTime = endTime;
						timeRemaing = timeRemaing - e.getDuration();
						events.add(createTask("Speech", endTimeEvening, endTimeEvening.plusMinutes(20), 20));
						break;
					} else if (lunchDone && endTime.compareTo(endTimeEvening) > 0) {
						Event minDurationTask = this.getMinDurationEvent(eventList);
						endTime = startTime.plusMinutes(minDurationTask.getDuration());
						if (endTime.compareTo(endTimeEvening) < 0) {
							Event e = this.createTask(minDurationTask, startTime, endTime);
							events.add(e);
							startTime = endTime;
							timeRemaing = timeRemaing - e.getDuration();
						} else {
							events.add(createTask("Speech", endTimeEvening, endTimeEvening.plusMinutes(20), 20));
							break;
						}
					}
				} else {
					if (!lunchDone) {
						events.add(
								createTask("Lunch", endTimeMorning, endTimeMorning.plusMinutes(lunchTime), lunchTime));
					}
					LocalTime endTime = endTimeEvening.plusMinutes(20);
					events.add(createTask("Speech", endTimeEvening, endTime, 20));
					break;
				}
			}
			organizedList.add(events);
			eventList.removeAll(events);
			numberofTeams--;
		}
		return organizedList;
	}

	/**
	 * @param task
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Event createTask(Event task, LocalTime startTime, LocalTime endTime) {
		task.setStartTime(startTime);
		task.setEndTime(endTime);
		return task;
	}

	/**
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param duration
	 * @return event
	 */
	private Event createTask(String name, LocalTime startTime, LocalTime endTime, Integer duration) {
		Event e = new Event(name, startTime, endTime, duration);
		return e;
	}

	/**
	 * @param eventList
	 * @return  event with maximum duration from a list of events
	 */
	private Event getMaxDurationEvent(List<Event> eventList) {
		Comparator<Event> comparator = Comparator.comparing(Event::getDuration);
		Event maxDurationEvent = eventList.stream().max(comparator).get();
		return maxDurationEvent;
	}

	/**
	 * @param eventList
	 * @return event with minimum duration from a list of event
	 */
	private Event getMinDurationEvent(List<Event> eventList) {
		Comparator<Event> comparator = Comparator.comparing(Event::getDuration);
		Event minDurationEvent = eventList.stream().min(comparator).get();
		return minDurationEvent;
	}

	/* (non-Javadoc)
	 * @see deloitte.digital.away.day.service.EventService#printOrganizedList(java.util.List)
	 */
	@Override
	public void viewOrganizedList(List<List<Event>> organizedList) {
		for (int i = 0; i < organizedList.size(); i++) {
			System.out.println("Team " + (i + 1)+" =>");
			for (Event e : organizedList.get(i)) {
				System.out.println(e.toString());
			}
		}
	}

}
