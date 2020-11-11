<?php
   require_once('koneksi.php');
if($_SERVER['REQUEST_METHOD']=='POST') {
   $jenis_lama = $_POST['jenis_lama'];
   $jenis_baru = $_POST['jenis_baru'];
   
     $sql = "UPDATE jenis SET jenis_buku = '$jenis_baru' WHERE jenis_buku = '$jenis_lama'";
     if(mysqli_query($con,$sql)) {
       $response["success"] = 1;
       $response["message"] = "Berhasil";
       echo json_encode($response);
     } else {
       $response["success"] = 0;
       $response["message"] = "Gagal";
       echo json_encode($response);
     }
   mysqli_close($con);
  } else {
    $response["success"] = 0;
    $response["message"] = "Error";
    echo json_encode($response);
  }
?>