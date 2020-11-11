<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
   $id_anggota =$_POST['id_anggota'];
   $id_buku =$_POST['id_buku'];
   $tgl_pinjam = $_POST['tgl_pinjam'];
   $tgl_kembali= $_POST['tgl_kembali'];
   $status = "B";

   
   // aksi untuk simpan pada database
    $sql = "INSERT INTO transaksi (id_anggota, id_buku,tgl_pinjam, tgl_kembali, status) VALUES('$id_anggota', '$id_buku','$tgl_pinjam','$tgl_kembali', '$status')";
   
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