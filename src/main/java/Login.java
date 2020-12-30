

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		
		//Redirecting Data from Input Form 
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//Checking whether its retrieved or not
		System.out.println("Email : "+email);
		System.out.println("Password : "+password);
		
		//Now checks whether email entered by user is empty. Not checking its valid or not Because we did this in Input form
		if(email.equals("") || password.equals("") ) {
			response.sendRedirect("/getRide/loginfailed.html");//Here i have to enter some missing field login page
		}else {
			//Now Making Connection to DataBase using my sql connector and performing CRUD operation on database using
			//JDBC API(set of Java APIs)
			try {
				//dynamically load the driver's class file into memory,
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				
				//for establishing a connection
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","sardana80");
				
				//Use this for general-purpose access to your database. 
	    		//Useful when you are using static SQL statements at runtime. 
	    		//The Statement interface cannot accept parameters.
				Statement stmt = con.createStatement();
				
				String query = "select * from info";
				ResultSet rs = stmt.executeQuery(query);
				//maintains a cursor that points to the current row in the result set.
				
				//HasMap is used to store key value pair Here key is email and value is password
				//Using HashMap because we know search operation using key is O(1) and insertion is also O(1)
				HashMap<String,String> emailPassword = new HashMap<>();
				
				
				while(rs.next()) {
					emailPassword.put(rs.getString(3), rs.getString(4));//Inserting Key and Value
				}													   // Key = email and value = password
				
				//Just for Testing whether its Contains or not email and password
				System.out.println(emailPassword);
				
				//Now first checking whether its contains particular email and then password
				if(emailPassword.containsKey(email) && password.equals(emailPassword.get(email))) {
					
					//email And Password is correct, now creating HttpSession object and set email attribute we need this further 
					HttpSession session = request.getSession(true);
					session.setAttribute("email", email);
					//Just for Testing When Session is created
					System.out.println("Session Creation Time : "+new Date(session.getCreationTime()));
					response.sendRedirect("/getRide/index2.jsp");//Welcome Page
				}else {
					response.sendRedirect("/getRide/loginfailed.html");//And giving error wrong user name and password
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
