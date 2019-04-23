# deloitte_digital_away_day_case_study

Language : Java 8
Build tool : Maven 4.0
Junit :   4.7

Problem Statement : 
Deloitte Digital is rewarding their employees in recognition for their hard work for range of successful projects by organising a "Deloitte Digital Away Day". Due to high demand across the firm a number of activities were proposed and a selection of them have been approved. Given there are time constraints, you have been asked to help event organisers by writing a program to accommodate various activities for the day.

Constraints : 
  1.The employees will be divided into various teams and each team will be performing various activities
  2.Activities start from 9am until lunch is served at 12 noon
  3.Activities resume from 1pm and must finish in time for a presentation to be delivered by external speaker on “Staff    Motivation"
  4.The presentation can start no earlier than 4:00 and no later than 5:00
  5.All activity lengths are either in minutes (not hours) or sprint (15 minutes)
  6.Due to high spirit of the team there needs to be no gap between each activity

Input From:digital.away.day/src.resources/test_data.txt

Main File:digital.away.day/src/main/java/deloitte/digital/away/day/App.java

Output:Console [Organized events as per the number of teams]

Flow : 
1. Read data from resource and get list of events 
2. organize these events with there start time ,name and duration based on the numberofteams
3. show organized data on console 

Design:

digital.away.day/src/main/java/deloitte/digital/away/day/models/Event.java : Event bean with its parameters

digital.away.day/src/main/java/deloitte/digital/away/day/service/EventService.java
	methods : public List<Event> readInputfromResouce(String fileName) throws EventException;
			  public List<List<Event>> organizeEvents(List<Event> eventList, LocalTime startTimeMorning, LocalTime endTimeMorning,
															  LocalTime startTimeEvening, LocalTime endTimeEvening, Integer lunchTime, 
															Integer numberofTeams) throws EventException;
			  public void viewOrganizedList(List<List<Event>> organizedList);
					  
digital.away.day/src/main/java/deloitte/digital/away/day/service/impl/FileEventServiceImpl.java: Class implenting EventService interface : overrides the methods on EventService 

digital.away.day/src/main/java/deloitte/digital/away/exception/EventException.java : Custom exception handler
 


