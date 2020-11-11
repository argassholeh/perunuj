<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
    $id_anggota = $_POST['id_anggota'];
    $tgl_dikembalikan = $_POST['tgl_dikembalikan'];
    $denda = $_POST['denda'];
   
// jika buku yg pinjam belum di kembalikan status 'B' maka tidak boleh pinjam lagi
    // $result = mysqli_query($con,"SELECT anggota.nim, transaksi.status FROM anggota INNER JOIN transaksi on transaksi.id= anggota.id WHERE nim = '$id_anggota' AND transaksi.status ='S'  ") or die(mysqli_error());
    // $result = mysqli_query($con,"SELECT anggota.nim, transaksi.id, transaksi.status FROM anggota, transaksi  WHERE anggota.nim = '$id_anggota'  And transaksi.status='S' ") or die(mysqli_error());


     $result = mysqli_query($con,"SELECT id_anggota FROM transaksi WHERE id_anggota= '$id_anggota' ") or die(mysqli_error());
   
  if (mysqli_num_rows($result) > 0 ){
      
      $simpan = "UPDATE transaksi SET tgl_dikembalikan = '$tgl_dikembalikan', denda = '$denda', status ='S' WHERE id_anggota = '$id_anggota'";
      if  (mysqli_query($con,$simpan)){
      echo "1";
      }else{
      echo "Gagal";
      }
  }else{
      echo "0";
  }
   // aksi untuk simpan pada database    
   mysqli_close($con);
  } else {
    $response["success"] = 0;
    $response["message"] = "Error";
    echo json_encode($response);
  }
?>