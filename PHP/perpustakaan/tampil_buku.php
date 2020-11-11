<?php
  require_once('koneksi.php');
 // if($_SERVER['REQUEST_METHOD']=='POST') {
  // JOIN jenis on jenis.id_jenis = buku.id_jenis 
  $result = mysqli_query($con,"SELECT * FROM buku  ") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
    $response["Hasil"] = array();
    while($row = mysqli_fetch_array($result)){
      $hasil = array();
      $hasil["id_buku"] = $row["id_buku"];
      $hasil["judul"] = $row["judul"];
      $hasil["stock"] = $row["stock"];
      $hasil["foto_buku"] = $row["foto_buku"];
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
