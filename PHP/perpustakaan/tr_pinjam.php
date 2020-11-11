<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
// if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
    $id_anggota = $_POST['id_anggota'];
    $id_buku=  $_POST['id_buku'];
    $tgl_pinjam = $_POST['tgl_pinjam'];
    $tgl_kembali = $_POST['tgl_kembali'];
    $status = "B";

// jika buku yg pinjam belum di kembalikan status 'B' maka tidak boleh pinjam lagi
    // $result = mysqli_query($con,"SELECT anggota.nim, transaksi.status FROM anggota INNER JOIN transaksi on transaksi.id= anggota.id WHERE nim = '$id_anggota' AND transaksi.status ='S'  ") or die(mysqli_error());
    // $result = mysqli_query($con,"SELECT anggota.nim, transaksi.id, transaksi.status FROM anggota, transaksi  WHERE anggota.nim = '$id_anggota'  And transaksi.status='S' ") or die(mysqli_error());


     $result = mysqli_query($con,"SELECT nim FROM anggota WHERE nim= '$id_anggota' ") or die(mysqli_error());
   
  if (mysqli_num_rows($result) > 0 ){
      
      $simpan = "insert into transaksi (id_anggota, id_buku, tgl_pinjam, tgl_kembali, status ) values('$id_anggota', '$id_buku', '$tgl_pinjam', '$tgl_kembali', '$status')";
      if  (mysqli_query($con,$simpan)){
      echo "Berhasil";
      }else{
      echo "Gagal";
      }
  }else{
      echo "0";
  }
   // aksi untuk simpan pada database    
  //  mysqli_close($con);
  // } else {
  //   $response["success"] = 0;
  //   $response["message"] = "Error";
  //   echo json_encode($response);
  // }
?>