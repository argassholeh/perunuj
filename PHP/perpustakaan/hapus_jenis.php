<?php
   require_once('koneksi.php');
if($_SERVER['REQUEST_METHOD']=='POST') {
   $jenis_buku = $_POST['jenis_buku'];
  $result = mysqli_query($con,"SELECT * FROM jenis where jenis_buku='$jenis_buku'") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
      $sql = "DELETE FROM jenis WHERE jenis_buku = '$jenis_buku'";
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