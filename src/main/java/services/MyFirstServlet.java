package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.MalformedURLException;

//import net.aksingh.owmjapis.CurrentWeather;
//import net.aksingh.owmjapis.OpenWeatherMap;




import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import model.Event;
import model.WeatherDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import commands.DB;




@WebServlet("/MyFirstServlet")
public class MyFirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public MyFirstServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String weather_id = null;
	    String weather_main = null ;
	    String weather_description = null;
	    String weather_icon = null;
	    String urlstring = null;
		String username = request.getParameter("username");
		String date = request.getParameter("date");
		String event = request.getParameter("event");
		String location = request.getParameter("location");
		PrintWriter pw = response.getWriter();
		pw.println("<br/>got Username:  " + username);
		pw.println("<br/>got Date    :  " + date);
		pw.println("<br/>got Event   :  " + event);
		pw.println("<br/>got Location   :  " + location); 
		
		//OpenWeatherMap owm = new OpenWeatherMap("77e9ead2b934d798bb55b68a04f97dc2");
		//CurrentWeather cwd = owm.currentWeatherByCityName(location);
		
	
		String urllocation = "http://api.openweathermap.org/data/2.5/weather?q="+location.replaceAll("\\s","%20")+"&APPID=77e9ead2b934d798bb55b68a04f97dc2";
		//urlstring = URLEncoder.encode(urllocation,"UTF-8");
		URL url = new URL(urllocation);
		
		// read from the URL
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext())
	        str += scan.nextLine();
	    scan.close();
	    
	    // build a JSON object
	    JSONObject jsonobj = new JSONObject(str);
		String temp = Double.toString(jsonobj.getJSONObject("main").getDouble("temp"));
		JSONArray arr = jsonobj.getJSONArray("weather");
		for (int i = 0; i < arr.length(); i++)
		{
		    weather_id = Integer.toString(arr.getJSONObject(i).getInt("id"));
		    weather_main = arr.getJSONObject(i).getString("main");
		    weather_description = arr.getJSONObject(i).getString("description");
		    weather_icon = arr.getJSONObject(i).getString("icon");
		
		}
		
		
		WeatherDb weather1= new WeatherDb();
		weather1.setUsername(username);
		weather1.setTemp(temp);
		
		weather1.setWeathermain(weather_main);
		weather1.setDescription(weather_description);
		weather1.setLocation(location);
		DB weatherdb = new DB();
		weatherdb.saveWeatherDb(weather1);
		
		Event event1 = new Event();
		event1.setUsername(username);
		event1.setDate(date);
		event1.setEvent(event);
		DB db = new DB();
		db.saveEvent(event1);
		pw.println("<br/>Your create job is done. :)");
   
		
		
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		try {
			http.sendPostEvent(username);
			http.sendPostWeather(username);
			weatherdb.deleteWeather(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}*/

}