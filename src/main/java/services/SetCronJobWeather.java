package services;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
//import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;





import model.Event;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import commands.DB;

@Path("/SetCronJob")

public class SetCronJobWeather {
	String APIToken = "6zhdi1zuikhituz1cm4u1cvpbbw9zfzp";
	String consumerKey = "88sBwT9VW6A8vxCrfAkb3Vu3o";
	String consumerSecret = "Uhvkw1DPid132WoCLM9intxM2SgxTKByeUCxqHIYaHbRLjEmgg";
	//int flag ;
	//String event;
	String temp;
	String weathermain;
	String description;
	String location;
	String tempInC ;
	@SuppressWarnings("deprecation")
	@GET
	@Path("/runJobWeather")
	@Produces(MediaType.APPLICATION_JSON)
	public String runJob(@QueryParam("user") String user){
		Twitter twitter = new TwitterFactory().getInstance();
		Status tweetStatus = null;
		AccessToken accessToken = null;
		// flag = 0 ;
		 //event = null;
		
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out.println("The OAuthConsumer has likely already been set");
		}
		try{
			DB db = new DB();
			temp = db.getTemp(user);
			tempInC = Integer.toString(Math.round((Float.parseFloat(temp)-273.15f)*100)/100);
			weathermain = db.getWeathermain(user);
			description = db.getDescription(user);
			location = db.getLocation(user);
			
			accessToken = db.getOAuthToken(user, "twitter");
			twitter.setOAuthAccessToken(accessToken);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		try{
	
				tweetStatus = twitter.updateStatus("Greetings! 12345\nThe temperature in "+location+" is: "+tempInC+"\u00b0"+"C.\nThe main weather is: "+ weathermain + ".\nThe detailed description is: " + description +"." 
						);
			}
		
		catch (TwitterException e) {
		e.printStackTrace();
		}
		if (tweetStatus != null)
			return "Check your Twitter, your tweet has been posted: "
					+ location+" "+Float.toString(Math.round(Float.parseFloat(temp)-273.15f)*100)+" "+weathermain+" "+description + ":D  CurrentTimeMill:"+ System.currentTimeMillis();
		else
			return "BOO! didn't work :(";
	
	}

}
