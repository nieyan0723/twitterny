package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.kevinsawicki.http.HttpRequest;

import model.Event;
import commands.DB;


@WebServlet("/MyFirstServlet")
public class MyFirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public MyFirstServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String username = request.getParameter("username");
		String date = request.getParameter("date");
		String event = request.getParameter("event");
		PrintWriter pw = response.getWriter();
		pw.println("<br/>got Username:  " + username);
		pw.println("<br/>got Date    :  " + date);
		pw.println("<br/>got Event   :  " + event);
		
		Event event1 = new Event();
		event1.setUsername(username);
		event1.setDate(date);
		event1.setEvent(event);
		DB db = new DB();
		db.saveEvent(event1);
		pw.println("<br/>Your create job is done. :)");
		//String APIToken = "6zhdi1zuikhituz1cm4u1cvpbbw9zfzp";
		//HttpRequest requestsetcrojob = HttpRequest.get("https://www.setcronjob.com/api/cron.add", true, "token", APIToken, "expression", "27 16 * * *",
		//		"url","http://twitterny.herokuapp.com/rest/SetCronJob/runJob?user="+username);
		//int requestsetcrojob = HttpRequest.post("https://www.setcronjob.com/api/cron.add").send("token=6zhdi1zuikhituz1cm4u1cvpbbw9zfzp").code();
		//System.out.println(requestsetcrojob); 
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		try {
			http.sendPost(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}