package services;

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

import java.sql.Date;

@Path("/SetCronJob")

public class SetCronJobService {
	String APIToken = "6zhdi1zuikhituz1cm4u1cvpbbw9zfzp";
	String consumerKey = "88sBwT9VW6A8vxCrfAkb3Vu3o";
	String consumerSecret = "Uhvkw1DPid132WoCLM9intxM2SgxTKByeUCxqHIYaHbRLjEmgg";
	int flag ;
	String event;
	@GET
	@Path("/runJob")
	@Produces(MediaType.APPLICATION_JSON)
	public String runJob(@QueryParam("user") String user){
		Twitter twitter = new TwitterFactory().getInstance();
		Status tweetStatus = null;
		AccessToken accessToken = null;
		 flag = 0 ;
		 event = null;
		
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out.println("The OAuthConsumer has likely already been set");
		}
		try{
			DB db = new DB();
			String date = db.getEventDate(user);
			compareDate(date);
			accessToken = db.getOAuthToken(user, "twitter");
			twitter.setOAuthAccessToken(accessToken);
			event = db.getEvent(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(flag == 1)
		{
		try{
	
				tweetStatus = twitter.updateStatus("Test From SetCronJob "
						+ event);
			}
		
		catch (TwitterException e) {
		e.printStackTrace();
		}
		if (tweetStatus != null)
			return "Check your Twitter, your tweet has been posted: "
					+ event;
		else
			return "BOO! didn't work";
		}
		else 
			return "Oh It's not today! :)";
	}
	
	public void compareDate(String date){
		DB db = new DB();
		Date date1 = db.getSystemDate();
		Date date2 = Date.valueOf(date);
    	if(date1.equals(date2)){
    		flag = 1;
    	}	
    	else flag = 0;
	}
		
}