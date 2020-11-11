-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 30 Apr 2019 pada 07.43
-- Versi Server: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_perpus`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `anggota`
--

CREATE TABLE IF NOT EXISTS `anggota` (
  `nim` char(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `id_fakultas` int(11) NOT NULL,
  `id_prodi` int(11) NOT NULL,
  `nohp` varchar(15) NOT NULL,
  `foto` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `anggota`
--

INSERT INTO `anggota` (`nim`, `nama`, `alamat`, `id_fakultas`, `id_prodi`, `nohp`, `foto`, `password`) VALUES
('16010210', 'Muhammad Sholeh', 'Tongas Wetan, Tongas, Probolinggo', 1, 1, '082336181538', 'foto.JPG', 'e10adc3949ba59abbe56e057f20f883e'),
('16010213', 'Alfian', 'Tongas', 2, 2, '082929929', 'YAuViGU4mkS.jpg', '827ccb0eea8a706c4c34a16891f84e7b');

-- --------------------------------------------------------

--
-- Struktur dari tabel `buku`
--

CREATE TABLE IF NOT EXISTS `buku` (
  `id_buku` int(11) NOT NULL,
  `id_jenis` int(11) NOT NULL,
  `id_lokasi` int(11) NOT NULL,
  `judul` varchar(50) NOT NULL,
  `pengarang` varchar(50) NOT NULL,
  `penerbit` varchar(50) NOT NULL,
  `thn_terbit` int(4) NOT NULL,
  `stock` int(3) NOT NULL,
  `jml_terpinjam` int(11) NOT NULL,
  `foto_buku` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `buku`
--

INSERT INTO `buku` (`id_buku`, `id_jenis`, `id_lokasi`, `judul`, `pengarang`, `penerbit`, `thn_terbit`, `stock`, `jml_terpinjam`, `foto_buku`) VALUES
(1, 1, 1, 'Jaringan Komputer', 'Ifnu Bima', 'Programer Indonesia', 2016, 5, 0, 'cover1.JPG'),
(2, 2, 2, 'Sistem Operasi', 'Alby', 'Sholeh', 2010, 3, 0, 'Cover2.JPG'),
(12, 11, 1, 'Android Expert', 'Abd', 'Loko', 2019, 2, 0, 'U7vEX1jxJRc.jpg'),
(13, 8, 1, 'Android Lengkap', 'Xn', '2019', 646, 664, 0, 'cMPfOdpe7Fl.jpg'),
(14, 8, 1, 'Android Developer', 'wahyu', 'cv', 2019, 1, 0, 'WmjnEfrDuGG.jpg'),
(15, 8, 1, 'Cara Belajar Cepat Android', 'sholeh', 'cv', 2019, 2, 0, 'FKGZT2FZBTi.jpg'),
(16, 8, 1, 'Android', 'hsns', 'bsbs', 4959, 9494, 0, 'ejY4PLG6ExN.jpg'),
(17, 8, 1, 'Islam', 'jsnsb', 'bdjd', 6469, 1, 0, '5mIY5Vo2wOC.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `fakultas`
--

CREATE TABLE IF NOT EXISTS `fakultas` (
  `id` int(11) NOT NULL,
  `jns_fakultas` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `fakultas`
--

INSERT INTO `fakultas` (`id`, `jns_fakultas`) VALUES
(1, 'Teknik'),
(2, 'Tarbiyah');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis`
--

CREATE TABLE IF NOT EXISTS `jenis` (
  `id_jenis` int(11) NOT NULL,
  `jenis_buku` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `jenis`
--

INSERT INTO `jenis` (`id_jenis`, `jenis_buku`) VALUES
(1, 'Buku'),
(2, 'Majalah'),
(3, 'Religi'),
(4, 'Hukum Islam'),
(5, 'Olahraga'),
(6, 'Politik'),
(7, 'Jurnal'),
(8, 'Budaya'),
(11, 'Pemrograman');

-- --------------------------------------------------------

--
-- Struktur dari tabel `lokasi`
--

CREATE TABLE IF NOT EXISTS `lokasi` (
  `id_lokasi` int(11) NOT NULL,
  `tempat` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `lokasi`
--

INSERT INTO `lokasi` (`id_lokasi`, `tempat`) VALUES
(1, '1A'),
(2, '1B'),
(3, '1C'),
(4, '1H'),
(5, '1D'),
(6, '1E'),
(7, '1F'),
(8, '1G');

-- --------------------------------------------------------

--
-- Struktur dari tabel `petugas`
--

CREATE TABLE IF NOT EXISTS `petugas` (
  `id_petugas` char(5) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `no_hp` char(15) NOT NULL,
  `foto` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `petugas`
--

INSERT INTO `petugas` (`id_petugas`, `nama`, `alamat`, `no_hp`, `foto`, `password`) VALUES
('P01', 'Alfin', 'Paiton', '082336181538', 'foto1.JPG', 'e10adc3949ba59abbe56e057f20f883e'),
('P02', 'Agus', 'Paiton', '09393973', 'hzY0nybAx9L.jpg', '827ccb0eea8a706c4c34a16891f84e7b');

-- --------------------------------------------------------

--
-- Struktur dari tabel `prodi`
--

CREATE TABLE IF NOT EXISTS `prodi` (
  `id` int(11) NOT NULL,
  `id_fakultas` int(11) NOT NULL,
  `nm_prodi` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `prodi`
--

INSERT INTO `prodi` (`id`, `id_fakultas`, `nm_prodi`) VALUES
(1, 1, 'Informatika'),
(2, 2, 'Hukum Islam');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE IF NOT EXISTS `transaksi` (
  `id` int(11) NOT NULL,
  `id_anggota` int(11) NOT NULL,
  `id_buku` int(11) NOT NULL,
  `tgl_pinjam` varchar(50) NOT NULL,
  `tgl_kembali` varchar(50) NOT NULL,
  `tgl_dikembalikan` varchar(50) NOT NULL,
  `status` enum('S','B') NOT NULL,
  `denda` char(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`id`, `id_anggota`, `id_buku`, `tgl_pinjam`, `tgl_kembali`, `tgl_dikembalikan`, `status`, `denda`) VALUES
(3, 16010210, 14, '  23-4-2019', '24-4-2019', '23-4-2020', 'S', '90'),
(4, 16010210, 16, '  23-4-2019', '25-4-2019', '23-4-2020', 'S', '90');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`nim`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id_buku`);

--
-- Indexes for table `fakultas`
--
ALTER TABLE `fakultas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jenis`
--
ALTER TABLE `jenis`
  ADD PRIMARY KEY (`id_jenis`);

--
-- Indexes for table `lokasi`
--
ALTER TABLE `lokasi`
  ADD PRIMARY KEY (`id_lokasi`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id_petugas`);

--
-- Indexes for table `prodi`
--
ALTER TABLE `prodi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `buku`
--
ALTER TABLE `buku`
  MODIFY `id_buku` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `fakultas`
--
ALTER TABLE `fakultas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `jenis`
--
ALTER TABLE `jenis`
  MODIFY `id_jenis` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `lokasi`
--
ALTER TABLE `lokasi`
  MODIFY `id_lokasi` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `prodi`
--
ALTER TABLE `prodi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
