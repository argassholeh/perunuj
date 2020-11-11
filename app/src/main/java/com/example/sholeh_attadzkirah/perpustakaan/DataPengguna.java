package com.example.sholeh_attadzkirah.perpustakaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class DataPengguna extends AppCompatActivity implements View.OnClickListener {

    private CardView cvAnggota, cvViewAnggota, cvPetugas, cvViewPetugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengguna);

        cvAnggota = findViewById(R.id.cv_anggota);
        cvViewAnggota = findViewById(R.id.cv_viewanggota);
        cvPetugas = findViewById(R.id.cv_petugas);
        cvViewPetugas = findViewById(R.id.cv_viewpetugas);

        cvAnggota.setOnClickListener(this);
        cvViewAnggota.setOnClickListener(this);
        cvPetugas.setOnClickListener(this);
        cvViewPetugas .setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cv_anggota : i = new Intent(this,Anggota.class);startActivity(i);break;
            case R.id.cv_viewanggota : i = new Intent(this,ViewAnggota.class);startActivity(i);break;
            case R.id.cv_petugas : i = new Intent(this,Petugas.class);startActivity(i);break;
            case R.id.cv_viewpetugas: i = new Intent(this,ViewPetugas.class);startActivity(i);break;
        }
    }
}
