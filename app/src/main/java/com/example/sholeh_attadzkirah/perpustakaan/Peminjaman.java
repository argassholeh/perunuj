package com.example.sholeh_attadzkirah.perpustakaan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sholeh_attadzkirah.perpustakaan.R;
import com.example.sholeh_attadzkirah.perpustakaan.config.koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;

import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.SimpanJenisBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.SimpantrPinjam;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.key_idBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.key_tglKembali;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.key_tglPinjam;


public class Peminjaman extends AppCompatActivity {

    private static final String TAG = "Peminjaman";

    Spinner spin_Judul;
        EditText ednim, tglPinjam, tglKembali;

    int tgl, bulan, tahun;
    private ProgressDialog progres;

//    TextView tglKembali;


    private TextView dateTimeDisplay;
    private SimpleDateFormat dateFormat;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Spinner spinjudul;
    ArrayList<String> arrayDatajudul = new ArrayList<>();
    ArrayList<String> listID_judul = new ArrayList<>();

    Button btnPinjam;


    Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);
        toolBarisi = findViewById(R.id.toolbar);
        toolBarisi.setTitle("Detail Peminjaman");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ednim = findViewById(R.id.edNim);
        spin_Judul = findViewById(R.id.spin_judul_buku);
        spin_Judul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), listID_judul.get(position) + " selected", Toast.LENGTH_LONG).show();
//                tampilJenisbuku(listID_jenis.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tampilJudulBuku();


        tglPinjam = findViewById(R.id.edTglPinjam);
        tglKembali = findViewById(R.id.ed_tglkembali);
        btnPinjam = findViewById(R.id.btnSimpanJ);
        btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });


        tglKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Peminjaman.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd-mm-yyy: " + day + "-" + month + "-" + year);

                String date = day + "-" + month + "-" + year;
                tglKembali.setText(date);
            }
        };


        Calendar calendar = Calendar.getInstance();
        tgl = calendar.get(Calendar.DATE);
        bulan = calendar.get(Calendar.MONTH) + 1;
        tahun = calendar.get(Calendar.YEAR);
//        String finaltanggal =tahun + "-"+ bulan+ "-"+ hari;
        String finaltanggal = tgl + "-" + bulan + "-" + tahun;
        tglPinjam.setText("  " + finaltanggal);
        bataskembali();


//        tglKembali.setText("  " + finaltanggal);
//

        // Parse the input date
//        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");
//        Date inputDate = fmt.parse("10-22-2011 01:00");
//
//// Create the MySQL datetime string
//        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = fmt.format(inputDate);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    public void tampilJudulBuku() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilSpinjudul, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int sukses;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sukses = jsonObject.getInt("success");
                    arrayDatajudul.clear();
                    listID_judul.clear();
                    if (sukses == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Hasil");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayDatajudul.add(object.getString("judul"));
                            listID_judul.add(object.getString("id_buku"));
                            //Toast.makeText(IsiData.this, response, Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Hahaha", response);
                        ArrayAdapter<String> adap = new ArrayAdapter<String>(Peminjaman.this, R.layout.support_simple_spinner_dropdown_item, arrayDatajudul);
                        spin_Judul.setAdapter(adap);
                    } else {
                        Toast.makeText(Peminjaman.this, "Data Belum Ada", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("Data Belum Ada", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Peminjaman.this, "Eror Cari " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mapp = new HashMap<>();
//                mapp.put("id_kecamatan", idkat);
                return mapp;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void simpan() {
        final String idAnggota_ = ednim.getText().toString();
        final String idJudul_ = listID_judul.get(spin_Judul.getSelectedItemPosition());
        final String tglPinjam_ = tglPinjam.getText().toString();
        final String tglKembali_ = tglKembali.getText().toString();
        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = SimpantrPinjam;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //untuk menerima respon ketika berhasil / tidak berhasil pada php.
//                        Log.d("ini error", response);
                        if (response.equalsIgnoreCase("0")) {
                            Toast.makeText(Peminjaman.this, "Anda Belum Daftar Anggota", Toast.LENGTH_SHORT).show();
//                            akunsama();
                            progres.dismiss();

//                            Toast.makeText(Add.this, "Username sudah ada yang pakai. Ulangi lagi", Toast.LENGTH_SHORT).show();
                        } else {
                            kosongkan();
                            Toast.makeText(Peminjaman.this, "Berhasil", Toast.LENGTH_SHORT).show();
//                            kosongkan();
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
                        Toast.makeText(Peminjaman.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                        progres.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //untuk mengirim request pada php
                params.put(koneksi.key_idanggota, idAnggota_);
                params.put(koneksi.key_idBuku, idJudul_);
                params.put(koneksi.key_tglPinjam, tglPinjam_);
                params.put(koneksi.key_tglKembali, tglKembali_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void kosongkan() {
        ednim.setText("");
        tglKembali.setText("");
    }

    public void bataskembali() {
        DateFormat formatt = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
       // Tanggal 7 Hari kedepan
        cal.add(Calendar.DAY_OF_MONTH, 7);
       // Toast.makeText(Peminjaman.this, "7 Hari Lagi Tanggal : "+formatt.format(cal.getTime()), Toast.LENGTH_SHORT).show();
        tglKembali.setText(formatt.format(cal.getTime()));
//        Date tgl;
//        try {
//            tgl = formatt.parse(tglPinjam.getText().toString());
//            cal3.setTime(tgl);
//            cal3.add(Calendar.DAY_OF_MONTH, 3);
//            System.out.println("3 Hari Setelah 5 Maret 2011 adalah Tanggal : "+sdf.format(cal3.getTime()));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
