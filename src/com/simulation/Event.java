package com.simulation;

public class Event {
	
	private int eventType=-1;
	private int eventStartTime=0;
	private int eventEndTime=0;
	public int getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(int eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public int getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(int eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public Event(int eventType) {
		super();
		this.eventType = eventType;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

}
