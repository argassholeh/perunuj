<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
   $id_anggota =$_POST['id_anggota'];
   $tgl_dikembalikan= $_POST['tgl_dikembalikan'];
   // $status = "S";
   $denda =$_POST['denda'];

   
   // aksi untuk simpan pada database
    $sql = "UPDATE transaksi SET tgl_dikembalikan = '$tgl_dikembalikan', status='S', denda = '$denda' WHERE id_anggota = '$id_anggota'" ;
   
    // ini untuk menerima respon
    // bisa menggunakan jsonObject dan  StringRequest di androidnya
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