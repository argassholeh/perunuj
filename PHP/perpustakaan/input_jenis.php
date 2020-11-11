<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
// if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
    $id_jenis= $_POST['id_jenis'];
    $jenis_buku = $_POST['jenis_buku'];
    $result = mysqli_query($con,"SELECT  jenis_buku From jenis WHERE jenis_buku= '$jenis_buku' ") or die(mysqli_error());
    if (mysqli_num_rows($result) > 0){
       $edit = "UPDATE jenis SET jenis_buku= '$jenis_buku' WHERE id_jenis= '$id_jenis' ";
    if(mysqli_query($con,$edit)) {
       echo "0";
     } 

    }else{
    $simpan = "insert into jenis (jenis_buku) values('$jenis_buku')";

    if(mysqli_query($con,$simpan)) {
       $response["success"] = 1;
       $response["message"] = "Berhasil Di Simpan";
       echo json_encode($response);
     } else {
       $response["success"] = 0;
       $response["message"] = "Gagal Di Simpan";
       echo json_encode($response);
     }

    }
   // aksi untuk simpan pada database    
  //  mysqli_close($con);
  // } else {
  //   $response["success"] = 0;
  //   $response["message"] = "Error";
  //   echo json_encode($response);
  // }
?>