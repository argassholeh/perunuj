<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
    $jenis_buku = $_POST['jenis_buku'];
    $result = mysqli_query($con,"SELECT jenis_buku FROM jenis WHERE jenis_buku = '$jenis_buku' ") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
  echo "0";
  }else{
      $simpan = "insert into jenis (jenis_buku) values('$jenis_buku')";
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