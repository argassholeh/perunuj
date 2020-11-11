<?php
  // ini untuk manggil koneksi
  require_once('koneksi.php');
  // ini untuk menerima Request pada android
if($_SERVER['REQUEST_METHOD']=='POST') {
  // ini variabel 
   $id_petugas =$_POST['id_petugas'];
   $nama = $_POST['nama'];
   $alamat = $_POST['alamat'];
   $no_hp = $_POST['nohp'];
   $foto= $_POST['foto'];
   $password =  md5($_POST['password']);

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
       $path = "foto/petugas/".$namaimage.".jpg";
       $imagename =$namaimage.".jpg";  

   // aksi untuk simpan pada database
    $sql = "INSERT INTO petugas (id_petugas, nama,alamat, no_hp, foto, password ) VALUES('$id_petugas', '$nama','$alamat','$no_hp', '$imagename', '$password')";
    file_put_contents($path,base64_decode($foto));
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