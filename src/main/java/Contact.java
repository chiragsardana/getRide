

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Contact
 */
public class Contact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Contact() {
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
		
		//Here I m redirecting values from Input Form.
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("sname");
		String email = request.getParameter("email");
		String message = request.getParameter("message");
		
		//For Testing Purpose
		System.out.println("First Name : "+firstName);
		System.out.println("Last Name : "+lastName);
		System.out.println("Email : "+email);
		System.out.println("Message : "+message);
		
//		Intially I m thinking of File Handling Here.
//		It is always better to use Database.
//		File file = new File("");
//		System.out.println(file.getAbsoluteFile());
//		if (file.exists()) 
//            System.out.println("Exists"); 
//        else
//            System.out.println("Does not Exists");
		try {
			//Now Connect a DataBase
			
			//dynamically load the driver's class file into memory, 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//for establishing a connection
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","sardana80");
			
			
			String insertQuery = "insert into contactus values ('"+firstName+"', '"+lastName+"', '"+email+"','"+message+"' )";
			
			//Use this for general-purpose access to your database. 
    		//Useful when you are using static SQL statements at runtime. 
    		//The Statement interface cannot accept parameters.
			Statement stmt = con.createStatement();
			int record = stmt.executeUpdate(insertQuery);
			if(record == 1) {
				//Just for Testing
				System.out.println("Entry Added.");
				//Here we can add one html page which shows entry added here we have to add one condition to Check whether it is login or logut 
				response.sendRedirect("/getRide/contact.html");
			}else {
				//In Contact us no error is Take place because we have not entered any condition
				System.out.println("There is Some Error in Updating");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
