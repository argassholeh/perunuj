package com.example.sholeh_attadzkirah.perpustakaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Tentang extends AppCompatActivity {

    Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Tetang");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }
}
