package com.example.sholeh_attadzkirah.perpustakaan;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sholeh_attadzkirah.perpustakaan.config.koneksi;

import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.SimpanJenisBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.SimpanTempatBuku;

public class TempatBook extends AppCompatActivity {

    Toolbar toolBarisi;

    EditText edTempat;
    Button btnSimpanTempat;

    private ProgressDialog progres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_book);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Tempat Buku");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edTempat = findViewById(R.id.et_tempatbuku);
        btnSimpanTempat = findViewById(R.id.btn_Simpantempatbuku);
        btnSimpanTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    private void simpan() {
        final String tempat_ = edTempat.getText().toString();
        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = SimpanTempatBuku;


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //untuk menerima respon ketika berhasil / tidak berhasil pada php.
//                        Log.d("ini error", response);
                        if (response.equalsIgnoreCase("0")){
                            Toast.makeText(TempatBook.this, "Tempat Sudah Tersedia", Toast.LENGTH_SHORT).show();
//                            akunsama();
                            progres.dismiss();

//                            Toast.makeText(Add.this, "Username sudah ada yang pakai. Ulangi lagi", Toast.LENGTH_SHORT).show();
                        }else  {
                            Toast.makeText(TempatBook.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            kosongkan();
//                            Intent intent = new Intent(getApplicationContext(),Login.class);
//                            startActivity(intent);
//                            finish();
                            progres.dismiss();

                        }
                        progres.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //berfungsi memberi tahu jika ada kesalahan pada server atau koneksi aplikasi.
                        Toast.makeText(TempatBook.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                        progres.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //untuk mengirim request pada php
                params.put(koneksi.key_tempat,tempat_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void kosongkan(){
        edTempat.setText("");
    }
}
