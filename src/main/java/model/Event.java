package model;

import java.sql.Date;

public class Event {
	String username;
	String event;
	Date date;
	public String setEvent(String event){
		this.event = event;
		return this.event;
	}
}
