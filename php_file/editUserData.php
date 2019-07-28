<?php

$link = new mysqli("127.0.0.1", "root", "0000", "android_hw2") or die("連接失敗");

$id = $_POST['id'];
$name = $_POST['name'];
$gender = $_POST['gender'];
$birth = $_POST['birth'];
$height = $_POST['height'];
$weight = $_POST['weight'];

$sql = "update user set name='$name', gender='$gender', birth='$birth', height='$height', weight='$weight' where id=".$id;

$result=mysqli_query($link, $sql);

if (!$result){
    die('update fail');
}
else{
    echo "success";
}

$link->close();
?>