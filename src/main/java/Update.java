

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Update
 */
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
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
	
	public static boolean isValidPhoneNo(String s) 
	{ 
	      
	    // The given argument to compile() method  
	    // is regular expression. With the help of  
	    // regular expression we can validate mobile 
	    // number.  
	    // 1) Begins with 0 or 91 
	    // 2) Then contains 7 or 8 or 9. 
	    // 3) Then contains 9 digits 
	    Pattern p = Pattern.compile("(91||0)?[6-9][0-9]{9}"); 
	  
	    // Pattern class contains matcher() method 
	    // to find matching between given number  
	    // and regular expression 
	    Matcher m = p.matcher(s); 
	    return (m.find() && m.group().equals(s)); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		//HttpSession object is Created to Get Attribute Email.
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("email");
		//Getting Attribute From Update.html using InputForm
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("sname");
		String phoneNo = request.getParameter("phone");
		String gender = request.getParameter("myGender");
		
		try {
			//Checks Whethers If any Field is blank or not
			if(firstName.equals("") || lastName.equals("") || gender == null || !isValidPhoneNo(phoneNo)) {
				response.sendRedirect("/getRide/updatefailed.html");
			}else {
				
				//Making Connection with Database
				
				//dynamically load the driver's class file into memory.
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				//for establishing a connection
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","sardana80");
				
				//Use this for general-purpose access to your database. 
	    		//Useful when you are using static SQL statements at runtime. 
	    		//The Statement interface cannot accept parameters.
				Statement stmt = con.createStatement();
				String query = "update info set firstName = '"+firstName+"'"+", lastName = '"+lastName+"', phoneNumber = '"+phoneNo+"', gender = "+Integer.parseInt(gender)+" where email = '"+email+"'";
				int record = stmt.executeUpdate(query);
				
				if(record == 1) {
					//Send Ridirect Saying that Updation Done
					System.out.println("Profile Updated");
					response.sendRedirect("/getRide/updatesuccessfully.html");
				}else {
					//Update Failed
					System.out.println("Send Back to Update Profile Html Page");
					response.sendRedirect("/getRide/updatefailed.html");
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
