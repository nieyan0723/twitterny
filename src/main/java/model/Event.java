package model;

//import java.sql.Date;

public class Event {
	String username;
	String event;
	String date;
	//setters
	public void setEvent(String event){
		this.event = event;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setDate(String date){
		this.date = date;
	}
	//getters
	public String getUsername(){
		return username;
	}
	public String getModelevent(){
		return event;
	}
	public String getDate(){
		return date;
	}

}
