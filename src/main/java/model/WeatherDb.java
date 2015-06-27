package model;

public class WeatherDb {
	String username;
	String temp;
	String weathermain;
	String description;
	String location;
	//setters
	public void setTemp(String temp){
		this.temp = temp;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setWeathermain(String weathermain){
		this.weathermain = weathermain;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setLocation(String location){
		this.location = location;
	}
	//getters
	public String getUsername(){
		return username;
	}
	public String getTemp(){
		return temp;
	}
	public String getWeathermain(){
		return weathermain;
	}
	public String getDescription(){
		return description;
	}
	public String getLocation(){
		return location;
	}
}
