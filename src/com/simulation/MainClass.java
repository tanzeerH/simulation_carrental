package com.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class MainClass {
	
	long totalTime=0;
	int totalPeople=0;
	static Comparator<Event> myComparator=new Comparator<Event>() {

		@Override
		public int compare(Event o1, Event o2) {
			if(o1.getEventEndTime()>o2.getEventEndTime())
			return 1;
			else return -1;
		}
	};
	ArrayList<People> peopleList=new ArrayList<>();

	static PriorityQueue<Event>  queue;
	
	public static void main(String[] args) {
		
		int intitalPopultion=2;
		queue=new PriorityQueue<>(2,myComparator);
		Event initialEvent=new Event(Constants.EVENT_TYPE_DEPARTURE_3);
		initialEvent.setEventStartTime(0);
		queue.add(initialEvent);
		while (queue.size()>0) {
			Event curEvent=queue.peek();
			
		}
		for(int i=0;i<intitalPopultion;i++)
		{
			int rand=getPersonProbability();
			if(rand<=583)
			{
				People people=new People();
				people.setDestination(1);
				people.setId(intitalPopultion++);
				
			}
			else{
				People people=new People();
				people.setDestination(2);
				people.setId(intitalPopultion++);
			}
		}
		
	}
	private static int getPersonProbability()
	{
		Random random=new Random(1000);
		return random.nextInt();
	}
}
