<?php  
	$con=mysqli_connect("localhost","root","root","mobile_programming");
	$NTITLE = $_POST['title'];
	$NCONTENT = $_POST['content'];
	$NCATEGORY = $_POST['category'];
	$NIMAGE = $_POST['Image'];
    $UID = $_POST['U_ID'];

	if(mysqli_connect_error($con))
		echo "Failied to connect : " .mysqli_connect_error();
	
	
	$result=mysqli_query($con,  " INSERT into POST(TITLE,CONTENTS,PICTURE,category,U_ID ) values ('$NTITLE','$NCONTENT','$NIMAGE','$NCATEGORY','$UID'); ");
 	
   	mysqli_close($con);

?>  