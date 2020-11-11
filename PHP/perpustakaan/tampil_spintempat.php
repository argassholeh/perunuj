<?php
  require_once('koneksi.php');
 // if($_SERVER['REQUEST_METHOD']=='POST') {
  $result = mysqli_query($con,"SELECT * From lokasi order by tempat asc ") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
    $response["Hasil"] = array();
    while($row = mysqli_fetch_array($result)){
      $hasil = array();
      $hasil["id_lokasi"] = $row["id_lokasi"];
      $hasil["tempat"] = $row["tempat"];
  
      array_push($response["Hasil"],$hasil);
      $response["success"] = 1;
    }
    echo json_encode($response);
  
  }else{
    $response["success"] = 0;
    $response["message"] = "Hasil Tidak Di Ditemukan";
    echo json_encode($response);
  }
 //   mysqli_close($con);
 // } else {
 //   $response["success"] = 0;
 //   $response["message"] = "Error";
 //   echo json_encode($response);
 // }
?>
