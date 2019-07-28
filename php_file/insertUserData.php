<?php

$link = new mysqli("127.0.0.1", "root", "0000", "android_hw2") or die("連接失敗");

$name = $_POST['name'];
$gender = $_POST['gender'];
$birth = $_POST['birth'];
$height = $_POST['height'];
$weight = $_POST['weight'];

//echo $name;
$sql = "insert into user (name, gender, birth, height, weight) values ('$name','$gender','$birth','$height','$weight')";

$result=mysqli_query($link, $sql);

if (!$result){
    die('insert fail');
}
else{
    echo "success";
} 

$link->close();
?>