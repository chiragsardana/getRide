

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FindRide
 */
public class FindRide extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindRide() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		try {
			//Here I m just redirecting Values from Input form.
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			String date = (String)request.getParameter("date");
			String time = request.getParameter("time");
			String passengers = request.getParameter("passenger");
			//Just Defining the Date Format  and class java.text
			SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//ormatting means converting date to string and parsing means converting string to date.
			
			
//			 if ( passengers.equals("") || from.equals("") ||
//		        		to.equals("") || time.equals("") || date.equals("")) {
//				 //Some field is blank
//				 response.sendRedirect("/getRide/findride.html");
//			 }
			
			String dateTime = date+" "+time;
			//Here I m parsing the String into Date Object using SimpleDateFormat object/instance
			Date d1 = sdfo.parse(dateTime);
			
			
			//For testing Purpose
			System.out.println("From "+from);
			System.out.println("To "+to);
			System.out.println("Date "+date);
			System.out.println("Time "+time);
			System.out.println("Passengers "+passengers);
			
			//Here I m creating Current Date Object/instance for Comparison
			Date currentDate = new Date();  
			
			//for Testing Purpose
	        System.out.println("Current Date and Time : "+sdfo.format(currentDate));
	        System.out.println("User Date and Time : "+sdfo.format(d1));
	        
	        //Getting HttpSession object to check whether the user is logged in or not 
	        //if logged in , then store some values from to dateTime and passengers using
	        //HttpSession.setAttritibute(nameToBeSetted , value) just like key value
	        HttpSession session = request.getSession();
	        
	        
	        //Method checks whether Date is before current date or Some Empty Fields
	        if (session.getAttribute("email") == null||d1.compareTo(currentDate) < 0 ||passengers.equals("") || from.equals("") ||
	        		to.equals("") || time.equals("") || date.equals("")) { 
	        	
	        	
	        	//If Not logged in,user has to login first message occurs
				if(session.getAttribute("email") == null) {
					response.sendRedirect("/getRide/loginfirst.html");//With a Message Login First
				}else {
	            //Here if Any Missing Field is there or date is before Current Date 
	            System.out.println("Entered Date is before Current Date or there is some Blank Entry"); 
	            response.sendRedirect("/getRide/findride.html");//Here One Exception Shown in the Page that Date
				}												//Entered is passed away
	        }else {
	        	//Here I m using RequestDispatcher Object
	        	
	        	//RequestDispatcher interface provides the facility of dispatching the request 
	        	//to another resource it may be html, servlet or jsp.
	        	RequestDispatcher rd = request.getRequestDispatcher("viewride.jsp");
	        	//Here I m setting Attribute, so that we can use in viewride.jsp
	        	//where i need details of from, to, dateTime, No. of passengers
	        	request.setAttribute("from", from);
	        	request.setAttribute("to", to);
	        	request.setAttribute("dateTime", dateTime);
	        	request.setAttribute("passengers", passengers);
	        	rd.forward(request, response);
	        }
	        
	        //One Page required Html View Ride Available
	        
	        
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
