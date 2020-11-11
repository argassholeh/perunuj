<?php 
	/* ===== www.dedykuncoro.com ===== */
	include_once "koneksi.php";

	$judul = $_POST['judul'];

	$query = mysqli_query($con, "SELECT * FROM buku WHERE judul LIKE '%".$judul."%'");

	$num_rows = mysqli_num_rows($query);

	if ($num_rows > 0){
		$json = '{"value":1, "results": [';

		while ($row = mysqli_fetch_array($query)){
			$char ='"';

			$json .= '{
				"id_buku": "'.str_replace($char,'`',strip_tags($row['id_buku'])).'",
				"judul": "'.str_replace($char,'`',strip_tags($row['judul'])).'"
			},';
		}

		$json = substr($json,0,strlen($json)-1);

		$json .= ']}';

	} else {
		$json = '{"value":0, "message": "Data tidak ditemukan."}';
	}

	echo $json;

	mysqli_close($con);
?>