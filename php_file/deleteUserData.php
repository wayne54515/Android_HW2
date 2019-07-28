<?php

$link = new mysqli("127.0.0.1", "root", "0000", "android_hw2") or die("連接失敗");

$id = $_POST['id'];

$sql = "delete from user where id=".$id;
$result=mysqli_query($link, $sql, MYSQLI_STORE_RESULT);
$arr = array();

if (!$result){
    die('delete fail');
}
else{
    echo "success";
} 

$link->close();
?>