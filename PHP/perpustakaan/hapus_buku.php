<?php
   require_once('koneksi.php');
if($_SERVER['REQUEST_METHOD']=='POST') {
   $judul = $_POST['judul'];
  $result = mysqli_query($con,"SELECT * FROM buku where judul='$judul'") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
      $sql = "DELETE FROM buku WHERE judul = '$judul'";
     if(mysqli_query($con,$sql)) {
       $response["success"] = 1;
       $response["message"] = "Berhasil";
       echo json_encode($response);
     } else {
       $response["success"] = 0;
       $response["message"] = "Gagal";
       echo json_encode($response);
     }
  }else{
       $response["success"] = 0;
       $response["message"] = "Data tidak ada";
       echo json_encode($response);
  }
   mysqli_close($con);
  } else {
    $response["success"] = 0;
    $response["message"] = "Error";
    echo json_encode($response);
  }
?>