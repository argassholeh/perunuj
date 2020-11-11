<?php
require_once('koneksi.php');
if($_SERVER['REQUEST_METHOD']=='POST') {
   $id_jenis =  $_POST['id_jenis'];
   $id_lokasi = $_POST['id_lokasi'];
   $judul = $_POST['judul'];
   $pengarang= $_POST['pengarang'];
   $penerbit= $_POST['penerbit'];
   $thn_terbit = $_POST['thn_terbit'];
   $stock = $_POST['stock'];
   // $jml_terpinjam =$_POST['jml_terpinjam'];
   $foto =$_POST['foto_buku'];
    function acakangkahuruf($panjang){
        $karakter="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        $string="";
        for($i=0;$i<=$panjang;$i++){
        $pos=rand(0,strlen($karakter)-1);
        $string.= $karakter{$pos}; 
        }
        return $string;
    }
       $namaimage = acakangkahuruf(10); 
       $path = "foto/buku/".$namaimage.".jpg";
       $imagename =$namaimage.".jpg";  
       $result = mysqli_query($con,"SELECT * FROM buku where judul='$judul'") or die(mysqli_error());
    if (mysqli_num_rows($result) > 0){
       $edit = "UPDATE buku SET id_jenis = '$id_jenis', id_lokasi = '$id_lokasi',  pengarang = '$pengarang', penerbit = '$penerbit', thn_terbit = '$thn_terbit', stock = '$stock',  foto_buku = '$imagename' WHERE judul = '$judul' ";
    file_put_contents($path,base64_decode($foto));
    if(mysqli_query($con,$edit)) {
       echo "0";
     } 

    }else{
    $simpan = "INSERT INTO buku (id_jenis, id_lokasi, judul, pengarang, penerbit, thn_terbit, stock, foto_buku ) VALUES('$id_jenis', '$id_lokasi','$judul','$pengarang', '$penerbit', '$thn_terbit', '$stock', '$imagename')";
    file_put_contents($path,base64_decode($foto));

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
   mysqli_close($con);
  } else {
    $response["success"] = 0;
    $response["message"] = "Error";
    echo json_encode($response);
  }
?>