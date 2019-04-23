/**
 * 
 */
package deloitte.digital.away.day.models;

import java.time.LocalTime;

/**
 * Model class for event
 * 
 * @author suman
 *
 */
public class Event {

	static String SPRINT = "sprint";

	public Event(String name, LocalTime startTime, LocalTime endTime, Integer duration) {
		super();
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
	}

	private String name;

	private LocalTime startTime;

	private LocalTime endTime;

	private Integer duration;

	public Event(String name, Integer duration) {
		super();
		this.name = name;
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(this.startTime + " ").append((this.getStartTime().getHour() < 12 ? "am" : "pm"))
				.append(" : " + this.getName() + " ").append((this.duration == 15 ? SPRINT : this.duration + "min"));
		return buffer.toString();
	}

}
