<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
    $tempat = $_POST['tempat'];
    $result = mysqli_query($con,"SELECT tempat FROM lokasi WHERE  tempat = '$tempat' ") or die(mysqli_error());

  if (mysqli_num_rows($result) > 0){
      echo "0";
  }else{
      $simpan = "insert into lokasi (tempat) values('$tempat')";
      if  (mysqli_query($con,$simpan)){
      echo "Berhasil";
      }else{
      echo "Gagal";
      }
  }
   // aksi untuk simpan pada database
    
   mysqli_close($con);
  } else {
    $response["success"] = 0;
    $response["message"] = "Error";
    echo json_encode($response);
  }
?>