

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Book
 */
public class Book extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Book() {
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
		//Here  I m creating the Object of HttpSession for session management
		HttpSession session = request.getSession();
		String from =(String)session.getAttribute("from"); 
		String to = (String)session.getAttribute("to");
		Integer passengers =(Integer) session.getAttribute("passengers");
		String email = (String)session.getAttribute("email");
		String carNo = request.getParameter("carNo");
		//Just for Testing Purpose
		System.out.println("From : "+from);
		System.out.println("To : "+to);
		System.out.println("Number of Passengers : "+passengers);
		System.out.println("Email : "+email);
		System.out.println("Car No. : "+carNo);
		
		try {
			//Here I m making connect with Database using JDBC API's
			
			//dynamically load the driver's class file into memory, 
    		//which automatically registers it.
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
			//for establishing a connection
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","sardana80");
			
			//Use this for general-purpose access to your database. 
    		//Useful when you are using static SQL statements at runtime. 
    		//The Statement interface cannot accept parameters.
			Statement stmt = con.createStatement();
			
			//First I Have to Redirect Data to Check Available Seats
			String ridirectQuery = "select * from trip where carNo = '"+carNo+"' AND fromWhere = '"+from+"' AND toWhere ='"+to+"'";
			ResultSet rs = stmt.executeQuery(ridirectQuery);
			//ResultSet object maintains a cursor that points to the current row in the result set.
			Integer seats = null;
			while(rs.next()) {
				//Ridirecting Available Seats
				seats = Integer.parseInt(rs.getString(5));
			}
			//Just an Update query
			String updateQuery = "update trip " +"SET seats = '"+(seats-passengers)+"' WHERE  carNo = '"+carNo+"'";
			int record = stmt.executeUpdate(updateQuery);
			
			if(record == 1) {
				System.out.println("You Booked Successfully");//Add Here Html page That shows you Booked Ticket Successfully
				response.sendRedirect("/getRide/bookedsuccessfully.html");
			}else {
				System.out.println("There is Some Error");
				//Give Try Again Page Error
				response.sendRedirect("/getRide/findrideafterlogin.html");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
