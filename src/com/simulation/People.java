package com.simulation;

public class People {
	private int id;
	private long startTime=0;
	private int start=-1;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	private int destination=-1;
	private long endTime=0;

}
