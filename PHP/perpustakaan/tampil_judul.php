<?php
  require_once('koneksi.php');
 // if($_SERVER['REQUEST_METHOD']=='POST') {
  // SELECT * From transaksi JOIN buku on buku.id_buku = transaksi.id_buku
  $result = mysqli_query($con,"SELECT * From buku order by judul asc ") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
    $response["Hasil"] = array();
    while($row = mysqli_fetch_array($result)){
      $hasil = array();
      $hasil["id_buku"] = $row["id_buku"];
      $hasil["judul"] = $row["judul"];
  
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
