package com.example.sholeh_attadzkirah.perpustakaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class LupaPassword extends AppCompatActivity {
    Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Akun");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }
}
