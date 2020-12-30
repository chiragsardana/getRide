

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SignUp
 */
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
//    static Pattern compile(String regex)	compiles the given regex and returns the instance of the Pattern.
//    Matcher matcher(CharSequence input)	creates a matcher that matches the given input with the pattern.
//   	boolean matches()	test whether the regular expression matches the pattern
    public static boolean isValidEmail(String email) 
	{ 
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +  //part before @
				"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
 
		Pattern pat = Pattern.compile(emailRegex);//final Pattern Class  
		if (email == null) //Check whether it contains a value or not
			return false; 
		return pat.matcher(email).matches(); //It return boolean (checks whether email is valid or not)
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
    //Java Database Connectivity (JDBC) is an application programming interface (API) 
    //for the programming language Java, which defines how a client may access a database.
    
    //For making connection with data base we are using mysql connector api 
    //dependency of the connector we have written in pom.xml(Project Oriented Model)
    //It first ridirect from data from mysql database
    
    public boolean isAlreadyEmail(String email) {
    	//Creating ArrayList to Store Emails From Database
    	ArrayList<String> emailList = new ArrayList<>();
    	try {
    		
    		//Here I m creating Connection with Database
    		
    		//dynamically load the driver's class file into memory, 
    		Class.forName("com.mysql.cj.jdbc.Driver");//JDBC Driver Name
         
    		//for establishing a connection
    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","sardana80");
         
    		
    		//Use this for general-purpose access to your database. 
    		//Useful when you are using static SQL statements at runtime. 
    		//The Statement interface cannot accept parameters.
    		Statement stmt = con.createStatement();
    		
    		String query = "select * from info";
			ResultSet rs = stmt.executeQuery(query);
			
			//ResultSet object maintains a cursor that points to the 
			//current row in the result set.
			
			while(rs.next()) {
				emailList.add(rs.getString(3));//emailList (ArrayList<String> contains all emails id's)) 
			}								  
			
			
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	//For Testing Purpose
    	System.out.println("The Email List : "+emailList);//Just for testing Purpose
    	
    	//Now Checks whether email Entered by user is Already Present or not
    	if(emailList.contains(email)) {//now checks in the ArrayList whether it contains the particular email that
			return true;				//is entered by user in our database
		}
    	return false;
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
		
		
		//Now we are getting the Form value which user has written in Form
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("sname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNo = request.getParameter("phone");
		String gender = request.getParameter("myGender");
		
		//Here I m testing whether i successfully retrieved valus which are written in the form
		System.out.println("First Name : "+firstName);
		System.out.println("Last Name : "+lastName);
		System.out.println("Email : "+email);
		System.out.println("Password: "+password);
		System.out.println("Phone No. : "+phoneNo);
		System.out.println("Gender : "+gender);
		
		//It is just only for testing whether Any field is empty or null.
		System.out.println(firstName.equals(""));
		System.out.println(lastName.equals(""));
		System.out.println(isValidEmail(email));
		System.out.println(password.equals(""));
		System.out.println(isValidPhoneNo(phoneNo));
		System.out.println(gender == null);
		System.out.println(isAlreadyEmail(email));
		
		
//		if User Don't Enter any info or Enter any inalid details
		if(firstName.equals("") || lastName.equals("") ||
				!isValidEmail(email) || password.equals("") ||
				!isValidPhoneNo(phoneNo) || gender == null || isAlreadyEmail(email)) {
			System.out.println("Some Fields are Empty");
			response.sendRedirect("/getRide/signupfailed.html");//Here we have to Enter new Page So that it shows Error in Another Page
		}else {
			
			//Now connect to Database And inserting values into them
			try {
				
				//dynamically load the driver's class file into memory, 
	    		//which automatically registers it.
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				//for establishing a connection
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","sardana80");
				//Just an sql insert query
				String insertQuery = "insert into info values ('"+firstName+"', '"+lastName+"', '"+email+"', '"+password+"', '"+phoneNo+"','"+gender+"')";
				
				//Use this for general-purpose access to your database. 
	    		//Useful when you are using static SQL statements at runtime. 
	    		//The Statement interface cannot accept parameters.
				Statement stmt = con.createStatement();
				int record = stmt.executeUpdate(insertQuery);
				if(record == 1) {
					//Now Creating HttpSession Object to Manage Login And Logout Session
					//Now setting Attributes which we required further
					HttpSession session = request.getSession();
					session.setAttribute("fname", firstName);
					session.setAttribute("sname", lastName);
					session.setAttribute("email", email);
					session.setAttribute("password", password);
					session.setAttribute("phone", phoneNo);
					session.setAttribute("myGender", gender);
					session.invalidate();
					//For Testing to check whether Registration is done or not
					System.out.println("Registration Doned Succesfully");
					response.sendRedirect("/getRide/index1.html");//Ridirect to Home Page
				}
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
