<?php  
	$con=mysqli_connect("localhost","root","root","mobile_programming");
	$NID = $_POST['nid'];
	$NPW = $_POST['npw'];
	$NBR = $_POST['newbir'];
	$EMAIL = $_POST['email'];
	$NUMBER = $_POST['number'];
	$NEWGENDER = $_POST['newgender'];
	
	if($NEWGENDER=="남성")
		$int_gender=1;
	elseif($NEWGENDER=="여성")
		$int_gender=2;

	if(mysqli_connect_error($con))
		echo "Failied to connect : " .mysqli_connect_error();
	

		
	#$result=mysqli_query($con, "INSERT into client (U_ID,UPW,BIR,GENDER,NUMBER,EMAIL) values ('k','a','2021','1','a','a');");
#	$insert = mysqli_query($con, $result);
	$insert = "INSERT into client (U_ID,UPW,BIR,GENDER,NUM,EMAIL) values ('$NID','$NPW','$NBR','$int_gender','$NUMBER','$EMAIL');";
	$result=mysqli_query($con, $insert);
    
 	
   	mysqli_close($con);

?>  
