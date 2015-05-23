package com.simulation;

import java.util.PriorityQueue;
import java.util.Queue;



public class Stopage {
	
	private int stopageMark;
	private int timeElapse;
	private int queLength;
	private int total=0;
	private int count=0;
	private PriorityQueue<Integer> qList=new PriorityQueue<Integer>();
	public PriorityQueue<Integer> getqList() {
		return qList;
	}
	public void setqList(PriorityQueue<Integer> qList) {
		this.qList = qList;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private int  max=0;
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getStopageMark() {
		return stopageMark;
	}
	public void setStopageMark(int stopageMark) {
		this.stopageMark = stopageMark;
	}
	public int getLastBusTime() {
		return timeElapse;
	}
	public void setLastBusTime(int timeElapse) {
		this.timeElapse = timeElapse;
	}
	public Stopage(int stopageMark, int timeElapse, int queLength) {
		super();
		this.stopageMark = stopageMark;
		this.timeElapse = timeElapse;
		this.queLength = queLength;
	}
	public int getQueLength() {
		return queLength;
	}
	public void setQueLength(int queLength) {
		this.queLength = queLength;
	}

}
