<?php

$link = new mysqli("127.0.0.1", "root", "0000", "android_hw2") or die("連接失敗");

$id = $_POST['id'];

$sql = "select * from user where id=".$id;
$result=mysqli_query($link, $sql, MYSQLI_STORE_RESULT);
$arr = array();

if (!$result){
    die('no data');
}
else{
    while($row = mysqli_fetch_array($result)){
        $count=count($row); 
        for($i=0;$i<$count;$i++){ 
            unset($row[$i]);//刪除冗餘資料 
        }
//         print_r($row['name']);
        array_push($arr,$row);
    }
    echo json_encode($arr,JSON_UNESCAPED_UNICODE);
} 

$link->close();
?>