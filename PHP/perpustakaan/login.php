<?php
 error_reporting(0);
 require_once('koneksi.php');
if($_SERVER['REQUEST_METHOD']=='POST'){
$response = array();
$username = $_POST['username'];
// $password = md5("123456");//$_POST['password'];
$password = md5($_POST['password']);

// $sql = "SELECT * FROM anggota WHERE nim = '$username' AND password='$password' ";
// $sql2 = "SELECT * FROM petugas WHERE id_petugas = '$username' AND password='$password' ";

// $result = mysqli_query($con,$sql);
// $result2 = mysqli_query($con,$sql2);
$result = mysqli_query($con,"SELECT * FROM anggota WHERE nim = '$username' AND password='$password' ") or die(mysqli_error());
$result2 = mysqli_query($con,"SELECT * FROM petugas WHERE id_petugas = '$username' AND password='$password'  ") or die(mysqli_error());



    if (mysqli_num_rows($result) > 0){
     $response["Hasil"] = array();
     while($row = mysqli_fetch_array($result)){
     	 $hasil = array();
     	 $hasil ["username"]= $row["nim"];
         $hasil ["password"]= $row["password"];
     	 array_push($response["Hasil"],$hasil);
     	  $response["success"] = 1;
     	  $response["status"] = "anggota";
     }
	 
     echo json_encode($response);

    }
    else if (mysqli_num_rows($result2) > 0){

     $response["Hasil"] = array();
     while($row = mysqli_fetch_array($result2)){
     	 $hasil = array();
     	 $hasil ["username"]= $row["id_petugas"];
         $hasil ["password"] = $row["password"];
     	 array_push($response["Hasil"],$hasil);
     	  $response["success"] = 1;
     	  $response["status"] = "petugas";
          }
         echo json_encode($response);

  
    }else {
    $data["success"] = 0;
    $data["message"] = "User Belum Terdaftar";
    echo json_encode($data);
    }

   mysqli_close($con);
  } else {
    $response["success"] = 0;
    $response["message"] = "Error";
    echo json_encode($response);
  }



?>