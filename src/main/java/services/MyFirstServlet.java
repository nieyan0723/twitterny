package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/MyFirstServlet")
public class MyFirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public MyFirstServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String username = request.getParameter("Username");
		String date = request.getParameter("Date");
		String event = request.getParameter("Event");
		PrintWriter pw = response.getWriter();
		pw.println("<br/>got Username:" + username);
		pw.println("<br/>got Date:" + date);
		pw.println("<br/>got Event:" + event);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}