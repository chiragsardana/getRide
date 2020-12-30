<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<%@ page import = "java.io.*,java.util.*,java.sql.Connection,java.sql.DriverManager,java.sql.ResultSet,java.sql.Statement,java.text.SimpleDateFormat" %>
<%
   		try{
   			/* import java.text.SimpleDateFormat;import java.util.Date; */
   			/* Here we are using request instance to get value from InputForm */
   			String from =(String) request.getAttribute("from");
   			String to =(String) request.getAttribute("to");
   			String dateTime =(String) request.getAttribute("dateTime");
   			String passengers =(String) request.getAttribute("passengers");
   			//For Just Testing Purpose.
   			System.out.println(from+" "+to+" "+dateTime+" "+passengers);
   			
   			//Now Setting Some Details Using HttpSession Object so that we can use it
   			//in Book Servlet
   			session.setAttribute("passengers", Integer.parseInt(passengers));
   			session.setAttribute("from",from );
   			session.setAttribute("to", to);
   			//Date Entered by User 
   			//The java.text.SimpleDateFormat class provides methods to format and 
   			//parse date and time in java. The SimpleDateFormat is a concrete class 
   			//for formatting and parsing date which inherits java.text.DateFormat class.
   			SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
   			
   			//Parsing String to Date Object
   			Date enteredDate = sdfo.parse(dateTime);
   			
   
   			
   			//dynamically load the driver's class file into memory, 
   			Class.forName("com.mysql.cj.jdbc.Driver");
   			//for establishing a connection
    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","sardana80");
         	
    		//Use this for general-purpose access to your database. 
    		//Useful when you are using static SQL statements at runtime. 
    		//The Statement interface cannot accept parameters.
    		Statement stmt = con.createStatement();
    		
    		String query = "select * from trip where fromWhere ='"+from+"' AND toWhere ='"+to+"'";
    		//ResultSet object maintains a cursor that points to the 
			//current row in the result set.
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Entered Date : "+sdfo.format(enteredDate));
			
   			
			//Here we can use custom object to Store carNo, dateTime and seats
			//But I m using 3 ArrayList to Store Values
			ArrayList<String> carNoList= new ArrayList<>();
			ArrayList<String> dateTimeList= new ArrayList<>();
			ArrayList<Integer> seatsList= new ArrayList<>();
			while(rs.next()){
				//Here I m parsing as In Database, I m storing it as String
				Integer seatsAvailable = Integer.parseInt(rs.getString(5));
				
				//Parsing String into Date Object/instance
				Date dateAvailable = sdfo.parse(rs.getString(4));
				System.out.println("Available Date : "+sdfo.format(dateAvailable));
				
				//Checking Whether Date is after the Available Date
				if((seatsAvailable >= Integer.parseInt(passengers)) && (enteredDate.compareTo(dateAvailable) < 0)){
					carNoList.add(rs.getString(1));
					dateTimeList.add(rs.getString(4));
					seatsList.add(seatsAvailable);
				}
				
			}
			//Here we are checking Size of List if its zero that means no ride available
			//Here we can use the size of dateTimeList or seatsList as our Choice
			if(carNoList.size() == 0){
				response.sendRedirect("/getRide/norideavailable.html");
			}
%>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>View Ride</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Baloo+Bhai+2:wght@500&display=swap" rel="stylesheet">

</head>
<body>
    <nav id="navbar">
        <div class="nav-list">
            <div id="logo">
                <img src="/getRide/img/Logo.png" alt="">
            </div>
            <h1> Reise Car</h1>
            <li> <a href="/getRide/index2.jsp">Home </a></li>
            <li> <a href="#service">Service </a></li>
            <li> <a href="#client-section">Clients </a></li>
            <li> <a href="#about">About us </a></li>
            <li> <a href="#contact">Contact us </a></li>

        </div>
        <div class="rightnav">


            <li class="item"><a href="/getRide/Logout">Log Out</a> </li>
            <li class="item"><a href="">Update Profile</a> </li>
        </div>
    </nav>


    <div class="form-Container">

        <h1> VIEW AVAILABLE RIDE </h1>
  		<table border = '1' width='100%'>
		  <tr>
		    <th>Car No.</th>
		    <th>From</th>
		    <th>To</th>
		    <th>Date and Time</th>
		    <th>Seats Available</th>
		  </tr>
		  <tr>
		  <% 
		  for(int i = 0;i < carNoList.size();i++){
			  %>
            <tr>
                <td><%=carNoList.get(i) %></td>
                <td><%=from %></td>
                <td><%=to %></td>
                <td><%=dateTimeList.get(i) %></td>
                <td><%=seatsList.get(i) %></td>
            </tr>
            <%
            }
		  System.out.println(carNoList+" "+dateTimeList+" "+seatsList);
            %>
		  </tr>
	</table>

    </div>

 <div class="form-Container">

    <h1> Do You Want to Book!!!</h1>
    <form method="post" action="/getRide/book.jsp" >
    
    
    
      <div class="nav-container-2_2">
      <div class="Start" style="font-family: 'Montserrat', sans-serif;">
      <!--  margin-left: 45px; -->
        <select name="carNo" id="carNo">
          <option value="">Select Car No. </option>
		<%for(int i = 0;i < carNoList.size();i++) { %>
          <option value="<%=carNoList.get(i) %>"><%=carNoList.get(i) %></option>
		<%} %>
        </select>
      </div>
      
      
      
        <div class="submit-btn">
          <input type="submit" value=" Submit Now ">  
        </form>    
        
<%
	}catch(Exception e){
		e.printStackTrace();
	} %>
        </div>

    </div>


    </form>

  </div>


    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <!--
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-q2kxQ16AaE6UbzuKqyBE9/u/KzioAlnx2maXQHiDX9d4/zp8Ok3f+M7DPm+Ib6IU" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-pQQkAEnwaBkjpqZ8RU1fF1AKtTcHJwFl3pblpTlHXybJjHpMYo79HY3hIi4NKxyj" crossorigin="anonymous"></script>
    -->
</body>





</html>