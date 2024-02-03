<?php
    $con=mysqli_connect("localhost","root","root","mobile_programming");
    $POST_NO = $_POST['post_no'];

	if(mysqli_connect_error($con))
     echo "Failied to connect : " .mysqli_connect_error();

$query = "SELECT title,post_no,picture,contents FROM post where post_no='$POST_NO';";
    $result = mysqli_query($con,$query );

    $rowCnt = mysqli_num_rows($result);
    $arr= array();

    for($i=0;$i<$rowCnt;$i++){
        $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
    }

    $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

    echo "$jsonData";
    mysqli_close($con);
?>