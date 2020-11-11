<?php
  require_once('koneksi.php');
 // if($_SERVER['REQUEST_METHOD']=='POST') {
  $jenis_buku= $_POST['jenis_buku'];
  $result = mysqli_query($con,"SELECT id_jenis FROM jenis where jenis_buku ='$jenis_buku' ") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
    $response["Hasil"] = array();
    while($row = mysqli_fetch_array($result)){
      $hasil = array();
      $hasil["id_jenis"] = $row["id_jenis"];
      // $hasil["jenis_buku"] = $row["jenis_buku"];
      array_push($response["Hasil"],$hasil);
      $response["success"] = 1;
    }
    echo json_encode($response);
  
  }else{
    $response["success"] = 0;
    $response["message"] = "Hasil Tidak Di Ditemukan";
    echo json_encode($response);
  }
   mysqli_close($con);
 // } else {
   // $response["success"] = 0;
   // $response["message"] = "Error";
   // echo json_encode($response);
 // }
?>
