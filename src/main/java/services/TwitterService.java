package services;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

//import model.Event;
import commands.DB;
//import commands.GetEvent;

@Path("/twitter")
public class TwitterService {
	String consumerKey = "88sBwT9VW6A8vxCrfAkb3Vu3o";
	String consumerSecret = "Uhvkw1DPid132WoCLM9intxM2SgxTKByeUCxqHIYaHbRLjEmgg";
	String event = null;
	
	@GET
	@Path("/request")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAuthentication(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @QueryParam("user") String user) {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthAccessToken(null);
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out.println("The OAuthConsumer has likely already been set");
		}

		try {
			RequestToken requestToken = twitter.getOAuthRequestToken();
			
			request.getSession().setAttribute("requestToken", requestToken);
			request.getSession().setAttribute("username", user);
			response.sendRedirect(requestToken.getAuthorizationURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@GET
	@Path("/success")
	@Produces(MediaType.APPLICATION_JSON)
	public String success(@QueryParam("oauth_token") String otoken,
			@QueryParam("oauth_verifier") String oauth_verifier,
			@Context HttpServletRequest request) {
		Twitter twitter = new TwitterFactory().getInstance();
		Status tweetStatus = null;
		AccessToken accessToken = null;
		RequestToken requestToken = null;
		String user = null;
		
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out
					.println("The OAuthConsumer has likely already been set, ignore");
		}
		try {
			requestToken = (RequestToken) request.getSession().getAttribute(
					"requestToken");
			if (requestToken == null)
				throw new Exception();
		} catch (Exception e1) {
			return "Could not find valid Request Token";
		}
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken,
					oauth_verifier);
		} catch (TwitterException e1) {
			return "Could not find valid token";
		}
		try {
			DB db = new DB();
			user = (String) request.getSession().getAttribute("username");
			db.saveOAuthToken(accessToken.getToken(), user, "twitter",
					accessToken.getTokenSecret());
			event = db.getEvent(user);
		} catch (Exception e) {
			System.out.println("Could not store access token to DB");
		}
		// twitter action need do the post 
		 try {
			tweetStatus = twitter.updateStatus("Test From Heroku "
					+ event);
		} catch (TwitterException e) {
			e.printStackTrace();
		} 
		if (tweetStatus != null)
			return "Check your Twitter, your tweet has been posted: "
					+ event;
		else
			return "BOO! didn't work"; 
	}

	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	public String success(@QueryParam("user") String user) {
		Twitter twitter = new TwitterFactory().getInstance();
		Status tweetStatus = null;
		AccessToken accessToken = null;
		//String event = null;
		
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out.println("The OAuthConsumer has likely already been set");
		}
		try {
			DB db = new DB();
			accessToken = db.getOAuthToken(user, "twitter");
			//event = db.getEvent(user);
			twitter.setOAuthAccessToken(accessToken);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			tweetStatus = twitter.updateStatus(event);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		if (tweetStatus != null)
			return "Check your Twitter, your tweet has been posted: "
					+ event;
		else
			return "BOO! didn't work";
	}

	@POST
	@Path("/createEvent")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public void createEvents(@QueryParam("username") String username, @QueryParam("date") Date date, @QueryParam("event") String event) {
		String username1 = username;
		Date date1 = date;
		String event1 = event;
		DB db = new DB();
		DB.saveEvent(username1, date1, event1);
	}










}
