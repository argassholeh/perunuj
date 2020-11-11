package com.example.sholeh_attadzkirah.perpustakaan.mRecycler;

public class Model_Buku {
    private String idbuku;
    private String judul;
    private String stock;
    private String image;

    public Model_Buku(String idbuku, String judul, String stock, String image){
        super();
        this.idbuku = idbuku;
        this.judul = judul;
        this.stock = stock;
        this.image = image;

    }
//
    public String getIdBuku() {
        return idbuku;
    }

    public String getJudul() {
        return judul;
    }

    public String getStock() {
        return stock;
    }


    public String getImage() {
        return image;
    }


}
