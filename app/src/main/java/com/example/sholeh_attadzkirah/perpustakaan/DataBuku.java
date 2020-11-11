package com.example.sholeh_attadzkirah.perpustakaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class DataBuku extends AppCompatActivity implements View.OnClickListener {

    private CardView cvAddBuku, cvDetailPinjam, cvJenisBook, cvTempatBook, cvDetailPengembalian, cvViewPeminjaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_buku);

        cvAddBuku= findViewById(R.id.cv_addbuku);
        cvDetailPinjam= findViewById(R.id.cv_detailpinjam);
        cvDetailPengembalian = findViewById(R.id.cv_detailpengembalian);
        cvViewPeminjaman= findViewById(R.id.cv_viewPeminjaman);
        cvJenisBook = findViewById(R.id.cv_jenisbuku);
        cvTempatBook = findViewById(R.id.cv_tempatbuku);

        cvAddBuku.setOnClickListener(this);
        cvDetailPinjam.setOnClickListener(this);
        cvDetailPengembalian.setOnClickListener(this);
        cvViewPeminjaman.setOnClickListener(this);
        cvJenisBook.setOnClickListener(this);
        cvTempatBook.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cv_addbuku:
                i = new Intent(this, Buku.class);
                startActivity(i);
                break;
            case R.id.cv_detailpinjam:
                i = new Intent(this, Peminjaman.class);
                startActivity(i);
                break;
            case R.id.cv_detailpengembalian:
                i = new Intent(this, Pengembalian.class);
                startActivity(i);
                break;
            case R.id.cv_viewPeminjaman:
                i = new Intent(this, ViewPeminjaman.class);
                startActivity(i);
                break;
            case R.id.cv_jenisbuku:
                i = new Intent(this, JenisBook.class);
                startActivity(i);
                break;
            case R.id.cv_tempatbuku:
                i = new Intent(this, TempatBook.class);
                startActivity(i);
                break;

            default:
                    break;

        }
    }
}
