package com.example.sholeh_attadzkirah.perpustakaan;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.CariBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.CariJenisBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.HapusBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.HapusJenisBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.SimpanJenisBuku;

public class JenisBook extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolBarisi;
    EditText edJenis, edjenisLama, edjenisBaru;
    Button btnSimpanJenis, btnHapusJenis, btnCariJenis, btnEditJenis;

    private ProgressDialog progres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis_book);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Jenis Buku");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edJenis = findViewById(R.id.et_jenisbuku);
        edjenisLama = findViewById(R.id.et_jenisbukulama);
        edjenisBaru = findViewById(R.id.et_idjenisbukubaru);
        btnSimpanJenis = findViewById(R.id.btnSimpanjenis);
        btnHapusJenis = findViewById(R.id.btnHapusJenis);
        btnCariJenis = findViewById(R.id.btnCariJenis);
        btnEditJenis = findViewById(R.id.btnUpdatejenis);
        btnSimpanJenis.setOnClickListener(this);
        btnHapusJenis.setOnClickListener(this);
        btnCariJenis.setOnClickListener(this);
        btnEditJenis.setOnClickListener(this);


    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSimpanjenis:
//                Toast.makeText(Buku.this, "Simpan", Toast.LENGTH_SHORT).show();
                simpan();

                break;
            case R.id.btnCariJenis:
//                Toast.makeText(Buku.this, "cari", Toast.LENGTH_SHORT).show();
                carijenis();


                break;
            case R.id.btnHapusJenis:
//                Toast.makeText(Buku.this, "pilih foto", Toast.LENGTH_SHORT).show();
                hapus();



                break;
            case R.id.btnUpdatejenis:
//                Toast.makeText(Buku.this, "Hapus buku", Toast.LENGTH_SHORT).show();
                edit();


                break;
            default:
                break;
        }
    }





    private void simpan() {
        final String jenis_ = edJenis.getText().toString();
        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = SimpanJenisBuku;


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //untuk menerima respon ketika berhasil / tidak berhasil pada php.
//                        Log.d("ini error", response);
                        if (response.equalsIgnoreCase("0")){
                            Toast.makeText(JenisBook.this, "Jenis Buku Sudah Tersedia ", Toast.LENGTH_SHORT).show();
//                            akunsama();
                            progres.dismiss();

//                            Toast.makeText(Add.this, "Username sudah ada yang pakai. Ulangi lagi", Toast.LENGTH_SHORT).show();
                        }else  {
                            Toast.makeText(JenisBook.this, "Berhasil Di Simpan", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(JenisBook.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                        progres.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //untuk mengirim request pada php
//                params.put(koneksi.key_idjenis,idjenis_);
                params.put(koneksi.key_jenis,jenis_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void kosongkan(){
        edJenis.setText("");
        edjenisBaru.setText("");
        edjenisLama.setText("");
    }


    private void carijenis() {
        final String jenis_ = edJenis.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CariJenisBuku,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {
                            Toast.makeText(getApplicationContext(), "Data Ada", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
//                                arrayDatajenis.clear();
//                                listID_jenis.clear();
//                                arrayListtempat.clear();
//                                listID_tempat.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
//                                    arrayDatajenis.add(c.getString("jenis_buku"));
//                                    listID_jenis.add(c.getString("id_jenis"));
//                                    arrayListtempat.add(c.getString("tempat"));
//                                    listID_tempat.add(c.getString("id_lokasi"));

//                                    spinjenis.setT
//                                    String jenis = c.getString("jenis_buku");
                                    edJenis.setText(c.getString(koneksi.key_jenis));
//                                    edPenerbit.setText(c.getString(koneksi.key_penerbit));
//                                    edThnterbit.setText(c.getString(koneksi.key_thn_terbit));
//                                    stock.setText(c.getString(koneksi.key_stock));

                                }

//                                ArrayAdapter<String> adap1 = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayDatajenis);
//                                spinjenis.setAdapter(adap1);
//                                ArrayAdapter<String> adap2 = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayListtempat);
//                                spintempat.setAdapter(adap2);
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(getApplicationContext(), "Data Tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progres.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(koneksi.key_jenis, jenis_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void hapus() {
        final String jenis_ = edJenis.getText().toString();
        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = HapusJenisBuku;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {
                            progres.dismiss();
                            Toast.makeText(JenisBook.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            kosongkan();
                        } else {
                            progres.dismiss();
                            Toast.makeText(JenisBook.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                        progres.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progres.dismiss();
                        Toast.makeText(JenisBook.this, "Tidak ada jenis buku", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(koneksi.key_jenis, jenis_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void edit() {
        final String jenislama_ = edjenisLama.getText().toString();
        final String jenisbaru_ = edjenisBaru.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.EditJenisBuku,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {
                            Toast.makeText(JenisBook.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            kosongkan();
                        } else {
                            Toast.makeText(JenisBook.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JenisBook.this, "Tidak ada jenis buku", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("jenis_lama",jenislama_);
                params.put("jenis_baru",jenisbaru_);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
