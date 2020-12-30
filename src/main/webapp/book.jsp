<!doctype html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

  <title>Confirm Booking</title>
  <link rel="stylesheet" href="css/style.css">
  <link href="https://fonts.googleapis.com/css2?family=Baloo+Bhai+2:wght@500&display=swap" rel="stylesheet">

</head>
<%
	/* Here I m Redirecting the values from session instance and request instance
	to get Parameter carNo*/
	String from =(String)session.getAttribute("from"); 
	String to = (String)session.getAttribute("to");
	Integer passengers =(Integer) session.getAttribute("passengers");
	String email = (String)session.getAttribute("email");
	String carNo = request.getParameter("carNo");
	
	/* System.out.println("From : "+from);
	System.out.println("To :"+to);
	System.out.println("Number of Passengers : "+passengers);
	System.out.println("Email : "+email); */
	/* System.out.println(carNo); */
	
%>
<body>
  <!-- Navbar  -->
  <nav id="navbar">
    <div class="nav-list">
      <div id="logo">
        <img src="/getRide/img/Logo.png" alt="">
      </div>
      <h1> Reise Car</h1>
      <li> <a href="/getRide/index2.html">Home </a></li>
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

  <!-- ----------  Form   -------- -->
  <div class="form-Container">

    <h1> Confirm Booking Details</h1>
    <form method="post" action="/getRide/Book"><!-- Here I have to connect to Servlet -->

      
      <div class="form-gp">
         <label for="from">From : <%=from %></label>
      </div>

      <div class="form-gp">
		<label for="to">To : <%=to %></label>
      </div>
      
      <div class="form-gp">
		<label for="passengers">Seats : <%=passengers %></label>
      </div>
      <div class="form-gp">
		<label for="carNo">Car No. : <%=carNo %></label>
      </div>
      
      <div class="form-gp">
		<input type="hidden" name="carNo" value="<%=carNo%>">
		<!-- <input type="text" name="item-wear" value="Enter your fixed value here"> -->
      </div>
      <!-- <input type="hidden" name="token" value="<?php echo $token; ?>" /> -->
		
    <br></br>
        <div class="submit-btn">
          <input type="submit" value=" Confirm ">  
        </form>    
        

        </div>

    </div>


    </form>

  </div>





  <!-- footer  -->
  <!-- <footer class="container"> 
        <p class="float-right"><a href="#">Back to top</a></p>
        <p>© 2020-2022 Ucoder, Inc. · <a href="#">Privacy</a> · <a href="#">Terms</a></p>
    </footer>  -->

























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