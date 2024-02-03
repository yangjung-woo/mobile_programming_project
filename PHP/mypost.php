<?php  
	$con=mysqli_connect("localhost","root","root","mobile_programming");
	$UID = $_POST['uid'];
	
	
	if(mysqli_connect_error($con))
		echo "Failied to connect : " .mysqli_connect_error();
	
	$result=mysqli_query($con, "select title,post_no,client.U_ID,BIR,GENDER,NUM,EMAIL from post,client where post.u_id=client.u_id and client.u_id='$UID'");
    	$rowCnt= mysqli_num_rows($result);
   	$arr= array();
 

  	 for($i=0;$i<$rowCnt;$i++){
     	 	$row= mysqli_fetch_array($result, MYSQLI_ASSOC);
	        	$arr[$i]= $row;      
    	}
  	 
      	$jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
      	echo "$jsonData";
 	
   	mysqli_close($con);

?>  