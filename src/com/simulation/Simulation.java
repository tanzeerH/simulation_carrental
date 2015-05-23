package com.simulation;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import sun.text.normalizer.UProperty;

public class Simulation {

	private Stopage stopage1;
	private Stopage stopage2;
	private Stopage stopage3;
	private Random random1 = new Random(15);
	private Random random2 = new Random(12);
	private Random random3 = new Random(65);
	private Random random4 = new Random(876);
	private Random random5 = new Random(34);
	private Random random6 = new Random(344);
	int totalTime = 0;
	int totalPeople = 2;
	int max_in_bus=0;
	int total_load_count=0;
	int total_in_bus=0;
	int total_loop=0;
	int max_time_in_loop=0;
	int min_time_in_loop=60*60*100;
	int total_loop_time=0;
	Comparator<Event> myComparator = new Comparator<Event>() {

		@Override
		public int compare(Event o1, Event o2) {
			if (o1.getEventEndTime() > o2.getEventEndTime())
				return 1;
			else
				return -1;
		}
	};
	ArrayList<People> peopleList = new ArrayList<People>();

	PriorityQueue<Event> queue;

	public Simulation() {
		initStopages();
		init();
	}

	public void simulate() {
		// System.out.println("Simulation Called");
		while (queue.size() > 0) {
			Event curEvent = queue.peek();
			System.out.println("EvenType: " + curEvent.getEventType());
			totalTime = curEvent.getEventStartTime();
			System.out.println("Total Time: " + totalTime);
			unLoadPeople(curEvent);
			loadPeople(curEvent);
			scheduleNextEvent(curEvent);
			if (totalTime > 60 * 60 * 15)
			{
				printResults();
				break;
			}

		}
	}
	private void printResults()
	{
		System.out.println("avg at 1:"+stopage1.getTotal()/stopage1.getCount());
		System.out.println("max:"+stopage1.getMax());
		
		System.out.println("avg at 2:"+stopage2.getTotal()/stopage2.getCount());
		System.out.println("max:"+stopage2.getMax());
		
		System.out.println("avg at 3:"+stopage3.getTotal()/stopage3.getCount());
		System.out.println("max:"+stopage3.getMax());
		
		System.out.println("avg in bus:"+total_in_bus/total_load_count);
		System.out.println("max:"+max_in_bus);
		
		System.out.println("avg in loop time:"+total_loop_time/total_loop);
		System.out.println("max:"+max_time_in_loop);
		System.out.println("min:"+min_time_in_loop);
	}

	private void scheduleNextEvent(Event curEvent) {
		Event nextEvent = null;
		if (curEvent.getEventType() == Constants.EVENT_TYPE_DEPARTURE_3) {
			stopage3.setLastBusTime(totalTime);
			nextEvent = new Event(Constants.EVENT_TYPE_ARRIVAL_1);
			nextEvent.setEventStartTime(totalTime + 9 * 60);
		} else if (curEvent.getEventType() == Constants.EVENT_TYPE_ARRIVAL_1) {
			nextEvent = new Event(Constants.EVENT_TYPE_DEPARTURE_1);
			nextEvent.setEventStartTime(totalTime);

		} else if (curEvent.getEventType() == Constants.EVENT_TYPE_DEPARTURE_1) {
			stopage1.setLastBusTime(totalTime);
			nextEvent = new Event(Constants.EVENT_TYPE_ARRIVAL_2);
			nextEvent.setEventStartTime(totalTime + 2 * 60);

		} else if (curEvent.getEventType() == Constants.EVENT_TYPE_ARRIVAL_2) {
			nextEvent = new Event(Constants.EVENT_TYPE_DEPARTURE_2);
			nextEvent.setEventStartTime(totalTime);

		} else if (curEvent.getEventType() == Constants.EVENT_TYPE_DEPARTURE_2) {
			stopage2.setLastBusTime(totalTime);
			nextEvent = new Event(Constants.EVENT_TYPE_ARRIVAL_3);
			nextEvent.setEventStartTime(totalTime + 9 * 60);

		} else if (curEvent.getEventType() == Constants.EVENT_TYPE_ARRIVAL_3) {
			total_loop++;
			
			int tempTime=totalTime-stopage3.getLastBusTime();
			if(tempTime>max_time_in_loop)
				max_time_in_loop=tempTime;
			if(tempTime<min_time_in_loop)
				min_time_in_loop=tempTime;
			total_loop_time+=tempTime;
			nextEvent = new Event(Constants.EVENT_TYPE_DEPARTURE_3);
			nextEvent.setEventStartTime(totalTime);

		}
		setQueueLength(nextEvent);
		queue.add(nextEvent);

	}

	private void loadPeople(Event e) {
		if (e.getEventType() == Constants.EVENT_TYPE_ARRIVAL_1) {
			int peopleInQueue = stopage1.getQueLength();
			System.out.println("Queue lengt at 1:"+peopleInQueue);
			int uCount = getLoadPeopleCount(peopleInQueue, 3, e);
			stopage1.setQueLength(stopage1.getQueLength() - uCount);

		} else if (e.getEventType() == Constants.EVENT_TYPE_ARRIVAL_2) {
			int peopleInQueue = stopage2.getQueLength();
			System.out.println("Queue lengt at 2:"+peopleInQueue);
			int uCount = getLoadPeopleCount(peopleInQueue, 3, e);
			stopage2.setQueLength(stopage2.getQueLength() - uCount);

		} else if (e.getEventType() == Constants.EVENT_TYPE_ARRIVAL_3) {
			int peopleInQueue = stopage3.getQueLength();
			System.out.println("Queue lengt at 3:"+peopleInQueue);
			int uCount = getLoadPeopleCount(peopleInQueue, -1, e);
			stopage3.setQueLength(stopage3.getQueLength() - uCount);
		}
		total_load_count++;
		total_in_bus+=totalPeople;
		if(totalPeople>max_in_bus)
			max_in_bus=totalPeople;

	}

	private void unLoadPeople(Event e) {
		if (e.getEventType() == Constants.EVENT_TYPE_ARRIVAL_1) {
			getUnloadPeopleTime(1);
		} else if (e.getEventType() == Constants.EVENT_TYPE_ARRIVAL_2) {
			getUnloadPeopleTime(2);
		} else if (e.getEventType() == Constants.EVENT_TYPE_ARRIVAL_3) {
			getUnloadPeopleTime(3);
		}

	}

	private int getLoadPeopleCount(int qLength, int des, Event e) {
		System.out.println("total people:" + totalPeople);
		int toLoad = 20 - totalPeople;
		int time = 0;
		if (qLength <= toLoad) {
			for (int i = 0; i < qLength; i++) {
				int x = random5.nextInt(9) + 15;
				time += x;
			}
			totalPeople += qLength;
			totalTime += time;
			addPeopleQueue(qLength, des, e);
			return qLength;
		} else {
			for (int i = 0; i < toLoad; i++) {
				int x = random5.nextInt(9) + 15;
				time += x;
			}
			totalPeople += toLoad;
			totalTime += time;
			addPeopleQueue(toLoad, des, e);
			return toLoad;
		}

	}

	private void getUnloadPeopleTime(int des) {
		int count = 0;
		int time = 0;
		int size = peopleList.size();
		for (int i = 0; i < size; i++) {
			if (peopleList.get(i).getDestination() == des) {
				peopleList.remove(i);
				int x = random4.nextInt(9) + 16;
				totalPeople--;
				time += x;
				i--;
				size--;
				count++;
			}
		}
		totalTime += time;
		System.out.println("Unloaded People at Stopage " + des + " :" + count + " Time Taken: " + time);
	}

	private void setQueueLength(Event event) {
		if (event.getEventType() == Constants.EVENT_TYPE_ARRIVAL_1) {
			int start = stopage1.getLastBusTime();
			int intervalFromLastDeparture = event.getEventStartTime() - start;
			int length = generateQuelength(12, intervalFromLastDeparture);
			stopage1.setQueLength(stopage1.getQueLength() + length);
			stopage1.setCount(stopage1.getCount()+1);
			stopage1.setTotal(stopage1.getTotal()+stopage1.getQueLength());
			if(stopage1.getQueLength()>stopage1.getMax())
				stopage1.setMax(stopage1.getQueLength());
			// addPeopleQueue(length, 3, event);

		} else if (event.getEventType() == Constants.EVENT_TYPE_ARRIVAL_2) {
			int start = stopage2.getLastBusTime();
			int intervalFromLastDeparture = event.getEventStartTime() - start;
			int length = generateQuelength(10, intervalFromLastDeparture);
			stopage2.setQueLength(stopage2.getQueLength() + length);
			stopage2.setCount(stopage2.getCount()+1);
			stopage2.setTotal(stopage2.getTotal()+stopage2.getQueLength());
			if(stopage2.getQueLength()>stopage2.getMax())
				stopage2.setMax(stopage2.getQueLength());
			// addPeopleQueue(length, 3, event);

		} else if (event.getEventType() == Constants.EVENT_TYPE_ARRIVAL_3) {
			int start = stopage3.getLastBusTime();
			int intervalFromLastDeparture = event.getEventStartTime() - start;
			int length = generateQuelength(24, intervalFromLastDeparture);
			stopage3.setQueLength(stopage3.getQueLength() + length);
			stopage3.setCount(stopage3.getCount()+1);
			stopage3.setTotal(stopage3.getTotal()+stopage3.getQueLength());
			if(stopage3.getQueLength()>stopage3.getMax())
				stopage3.setMax(stopage3.getQueLength());
			// -1 means have to genarate through random
			// addPeopleQueue(length, -1, event);

		}
	}

	private void addPeopleQueue(int count, int destination, Event e) {
		if (destination == -1) {
			for (int i = 0; i < count; i++) {
				People people = new People();
				int rand = getPersonProbability();
				if (rand <= 583) {
					people.setDestination(1);
					// people.setId(intitalPopultion++);

				} else {
					people.setDestination(2);
					// people.setId(intitalPopultion++);
				}
				people.setStartTime(e.getEventStartTime());
				peopleList.add(people);
			}
		} else {

			for (int i = 0; i < count; i++) {
				People people = new People();
				people.setDestination(destination);
				people.setStartTime(e.getEventStartTime());
				peopleList.add(people);
			}
		}
	}

	private int generateQuelength(int beta, int time) {
		int count = 0;
		int timeElapsed = 0;
		while (true) {
			double x = -1;
			if (beta == 14)
				x = -random1.nextInt(100) / beta;
			else if (beta == 10)
				x = -random2.nextInt() / beta;
			else
				x = -random3.nextInt() / beta;

			x /= -1;

			int interval = (int) (Math.exp(x) / 14);
			timeElapsed += interval;
			count++;
			// System.out.println("elapsed"+timeElapsed);
			if (timeElapsed > time)
				break;
		}

		// System.out.println("queue length:"+count);
		return count;
	}

	private int getPersonProbability() {
		return random6.nextInt(1000);
	}

	private void init() {
		// System.out.println("init");
		totalTime = 5 * 60;
		int intitalPopultion = 2;
		totalPeople = 2;
		queue = new PriorityQueue<Event>(2, myComparator);
		Event initialEvent = new Event(Constants.EVENT_TYPE_DEPARTURE_3);
		initialEvent.setEventStartTime(totalTime);
		queue.add(initialEvent);

		for (int i = 0; i < intitalPopultion; i++) {
			int rand = getPersonProbability();
			System.out.println("rand: " + rand);
			if (rand <= 583) {
				People people = new People();
				people.setDestination(1);
				peopleList.add(people);
				// people.setId(intitalPopultion++);

			} else {
				People people = new People();
				people.setDestination(2);
				peopleList.add(people);
				// people.setId(intitalPopultion++);
			}
		}
	}

	private void initStopages() {
		stopage1 = new Stopage(1, 0, 0);
		stopage2 = new Stopage(2, 0, 0);
		stopage3 = new Stopage(3, 0, 0);
	}

}
