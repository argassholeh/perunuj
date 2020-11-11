package com.example.sholeh_attadzkirah.perpustakaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AkunPass extends AppCompatActivity implements View.OnClickListener {

    Button masukpass;

    Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_pass);

        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Akun");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        masukpass = findViewById(R.id.btn_masukpass);

        masukpass.setOnClickListener(this);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplication(),LupaPassword.class);
        startActivity(intent);
        finish();

    }
}
