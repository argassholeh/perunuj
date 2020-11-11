package com.example.sholeh_attadzkirah.perpustakaan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.CariBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.CariPeminjam;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.key_idanggota;

public class Pengembalian extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Pengembalian";
    int tgl, bulan, tahun;

    Toolbar toolBarisi;
    String finaltanggal;

    EditText et_caripinjam,etnim, etjudul, ettglpinjam, ettglbatas,ettgldikembalikan, etdenda, etlama;
    Button btncari, btnsimpan;
    private ProgressDialog progres;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembalian);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Detail Pengembalian");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_caripinjam= findViewById(R.id.ed_caripeminjam);
        etnim =findViewById(R.id.edNim);
        etjudul = findViewById(R.id.edJudulBuku);
        ettglpinjam =findViewById(R.id.edTglPinjam);
        ettglbatas =findViewById(R.id.edTglKembali);
        ettgldikembalikan =findViewById(R.id.edtgldiKembalikan);
        etlama = findViewById(R.id.edLama);
        etdenda =findViewById(R.id.edDenda);
        btncari =findViewById(R.id.bt_caripinjam);
        btnsimpan =findViewById(R.id.btnSimpanPg);
        btncari.setOnClickListener(this);
        btnsimpan.setOnClickListener(this);

        ettgldikembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Pengembalian.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd-mm-yyy: " + day + "-"+month + "-"  + year);

                String date =day + "-" +month + "-"  + year;
                ettgldikembalikan.setText(date);
            }
        };

//
        Calendar calendar = Calendar.getInstance();
        tgl = calendar.get(Calendar.DATE);
        bulan = calendar.get(Calendar.MONTH) + 1;
        tahun = calendar.get(Calendar.YEAR);
//        String finaltanggal =tahun + "-"+ bulan+ "-"+ hari;
        finaltanggal = tgl + "-" + bulan + "-" + tahun;
        ettgldikembalikan.setText("  " + finaltanggal);

      //  hitungDenda();


    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_caripinjam:
//                Toast.makeText(Buku.this, "Simpan", Toast.LENGTH_SHORT).show();
                caripinjam();

                break;
            case R.id.btnSimpanPg:
//                Toast.makeText(Buku.this, "cari", Toast.LENGTH_SHORT).show();
                Pengembalian();
                break;

            default:
                break;
        }
    }
    private void caripinjam() {
        final String peminjam_ = et_caripinjam.getText().toString();
        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = CariPeminjam;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {
                            Toast.makeText(getApplicationContext(), "Data Ada", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    etnim.setText(c.getString("id_anggota"));
                                    etjudul.setText(c.getString("judul"));
                                    ettglpinjam.setText(c.getString("tgl_pinjam"));
                                    ettglbatas.setText(c.getString("tgl_kembali"));
//                                    ettgldikembalikan.setText(c.getString("tgl_dikembalikan"));

                                }

                                hitungDenda();
                                progres.dismiss();

                            } catch (JSONException e) {
                                progres.dismiss();
                                e.printStackTrace();
                            }

                        } else {
                            progres.dismiss();
                            Toast.makeText(getApplicationContext(), "Tidak ada ", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(), "Tidak ada", Toast.LENGTH_SHORT).show();
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
                params.put(key_idanggota, peminjam_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void Pengembalian() {
        final String idanggota_ = etnim.getText().toString();
        final String tgldikembalikan_ = ettgldikembalikan.getText().toString();
        final String denda_ = etdenda.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.SimpanPengembalian,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {
                            Toast.makeText(Pengembalian.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            kosongkan();
                        } else {
                            Toast.makeText(Pengembalian.this, "Gagal ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Pengembalian.this, "Tidak ada jenis buku", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_anggota",idanggota_);
                params.put("tgl_dikembalikan",tgldikembalikan_);
                params.put("denda",denda_);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void hitungDenda(){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        final String tglpinjam = ettglpinjam.getText().toString();
        final String tglkembali = ettglbatas.getText().toString();
        final String tgldikembalikan = ettgldikembalikan.getText().toString();
        try {
            Date tgl_kembali = format.parse(ettglbatas.getText().toString());
            Date tgl_dikembalikan = format.parse(ettgldikembalikan.getText().toString());
            long tgl_kembalinya = tgl_kembali.getTime();
            long tgl_dikembalikannya = tgl_dikembalikan.getTime();
            long perbedaan = tgl_dikembalikannya - tgl_kembalinya;
            long hari = perbedaan / (24*60*60*1000); // 24 jam, 60 detik, 60 menit, 1000 menandalan waktu karena waktu di mulai daro 1000 mili
            long denda = 500;
            long total =  denda * hari;

            if (perbedaan >= 1) {
                etlama.setText(String.valueOf(hari+ " Hari"));
                etdenda.setText(String.valueOf(total));
            } else {
                etlama.setText(String.valueOf("Belum terlambat"));
                etdenda.setText(String.valueOf(0));
            }




//            Toast.makeText(this, Long.toString(tgl_dikembalikannya) + " hari "+ Long.toString(tgl_kembalinya), Toast.LENGTH_LONG).show();
//            Toast.makeText(this, Long.toString(hari), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

//        String hasil;
//
//        Calendar newDate = Calendar.getInstance();
//
//        Calendar tanggal = Calendar.getInstance();
//
//        int hari = newDate.get(Calendar.DAY_OF_MONTH);
//
//        int bulan = newDate.get(Calendar.MONTH);
//
//        int tahun = newDate.get(Calendar.YEAR);
//
////        String input = String.valueOf(tvDateResult.getText().toString());
//        String tanggals[] = tglpinjam.split("-");
//        int tglP = Integer.parseInt(tanggals[0]);
//        int blnP = Integer.parseInt(tanggals[1]);
//        int thnP = Integer.parseInt(tanggals[2]);
//
//
//        Toast.makeText(Pengembalian.this, "Tanggal P"+tglP, Toast.LENGTH_SHORT).show();
//
//
//
//
//
//
//
//
//        int hasilnya = 0;
//
//        etdenda.setText(String.valueOf(hasilnya));

    }
    public void kosongkan(){
        etnim.setText("");
        ettgldikembalikan.setText("");
        etjudul.setText("");
        et_caripinjam.setText("");
        ettglbatas.setText("");
        ettgldikembalikan.setText("");
        ettglpinjam.setText("");
        etdenda.setText("");
        etlama.setText("");
    }

}
