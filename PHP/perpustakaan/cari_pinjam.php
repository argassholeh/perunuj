<?php
  require_once('koneksi.php');
  if($_SERVER['REQUEST_METHOD']=='POST') {
  $response = array();
  $id_anggota = $_POST['id_anggota'];
  $result = mysqli_query($con,"SELECT * FROM transaksi JOIN buku on buku.id_buku = transaksi.id_buku  WHERE id_anggota = '$id_anggota' ") or die(mysqli_error());
  if (mysqli_num_rows($result) > 0){
    $response["Hasil"] = array();
    while($row = mysqli_fetch_array($result)){
      $hasil = array();
      $hasil["id_anggota"] = $row["id_anggota"];
      $hasil["judul"] = $row["judul"];
      $hasil["tgl_pinjam"] = $row["tgl_pinjam"];
      $hasil["tgl_kembali"] = $row["tgl_kembali"];
      // $hasil["tgl_dikembalikan"] = $row["tgl_dikembalikan"];
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
  } else {
    $response["success"] = 0;
    $response["message"] = "Error";
    echo json_encode($response);
  }
?>
