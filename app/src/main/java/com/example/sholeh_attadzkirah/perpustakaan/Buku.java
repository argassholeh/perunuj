package com.example.sholeh_attadzkirah.perpustakaan;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sholeh_attadzkirah.perpustakaan.config.koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.CariBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.HapusBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.SimpanBuku;

public class Buku extends AppCompatActivity implements View.OnClickListener {

//    Button

    Spinner spinjenis, spintempat;
    ArrayList<String> arrayDatajenis = new ArrayList<>();
    ArrayList<String> listID_jenis = new ArrayList<>();
//
    private ArrayList<String> arrayListtempat = new ArrayList<>();
    ArrayList<String> listID_tempat = new ArrayList<>();

    private ProgressDialog progres;
    Toolbar toolBarisi;

    EditText edJudul, edPengarang, edPenerbit, edThnterbit, stock , edCariBuku;

//    ImageView iv;

    Button pilih, simpan, hapus, cari ;
    ImageView iv;
    Bitmap bitmap = null;
    Uri imageUri;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    Preferences preferences;

//    String c = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Buku");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinjenis = findViewById(R.id.spin_jenisbuku);
        spintempat= findViewById(R.id.spin_lokasi);
        edJudul = findViewById(R.id.edJudul);
        edPengarang = findViewById(R.id.edpengarang);
        edPenerbit = findViewById(R.id.edpenerbit);
        edThnterbit = findViewById(R.id.edthnterbit);
        stock = findViewById(R.id.edstoknya);
        iv = findViewById(R.id.img_ftobuku);
        edCariBuku = findViewById(R.id.ed_caribuku);
        simpan = findViewById(R.id.btnSimpan);
        simpan.setOnClickListener(this);
        pilih = findViewById(R.id.btnftoBuku);
        pilih.setOnClickListener(this);
        hapus = findViewById(R.id.btnHapusbuku);
        hapus.setOnClickListener(this);
        cari = findViewById(R.id.btncaribuku);
        cari.setOnClickListener(this);


            spinjenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    //                Toast.makeText(getBaseContext(), listID_jenis.get(position) + " selected", Toast.LENGTH_LONG).show();
    //                tampilJenisbuku(listID_jenis.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            tampilJenisbuku();

        spintempat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                tampilPoint(listID_jen.get(position));
                Toast.makeText(getApplicationContext(), listID_tempat.get(position) + "selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tampilDataTempat();
        perizinan();


    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSimpan:
//                Toast.makeText(Buku.this, "Simpan", Toast.LENGTH_SHORT).show();
                upload();
                break;
            case R.id.btncaribuku:
//                Toast.makeText(Buku.this, "cari", Toast.LENGTH_SHORT).show();
                caribuku();

                break;
            case R.id.btnftoBuku:
//                Toast.makeText(Buku.this, "pilih foto", Toast.LENGTH_SHORT).show();
                selectImage();

                break;
            case R.id.btnHapusbuku:
//                Toast.makeText(Buku.this, "Hapus buku", Toast.LENGTH_SHORT).show();
                hapus();

                break;
            default:
                break;
        }

    }
    private void perizinan() {
        ActivityCompat.requestPermissions(Buku.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                99);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    perizinan();
                }
                return;
            }
        }
    }

    public  void  tampilJenisbuku(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilSpinjenis, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int sukses;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sukses = jsonObject.getInt("success");
                    arrayDatajenis.clear();
                    listID_jenis.clear();
                    if (sukses == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Hasil");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayDatajenis.add(object.getString("jenis_buku"));
                            listID_jenis.add(object.getString("id_jenis"));
                            //Toast.makeText(IsiData.this, response, Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Hahaha", response);
                        ArrayAdapter<String> adap = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayDatajenis);
                        spinjenis.setAdapter(adap);
                    } else {
                        Toast.makeText(Buku.this, "Data Belum Ada", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("Data Belum Ada", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buku.this, "Eror Cari " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void tampilDataTempat() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilSpintempat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int sukses;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sukses = jsonObject.getInt("success");
                    arrayListtempat.clear();
                    listID_tempat.clear();
                    if (sukses == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Hasil");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayListtempat.add(object.getString("tempat"));
                            listID_tempat.add(object.getString("id_lokasi"));
                            //Toast.makeText(IsiData.this, response, Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Hahaha", response);
                        ArrayAdapter<String> adap = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayListtempat);
                        spintempat.setAdapter(adap);
                    } else {
                        Toast.makeText(Buku.this, "Data Belum Ada", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("Data Belum Ada", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buku.this, "Eror Cari " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void selectImage() {
        final CharSequence[] options = {"Ambil Foto", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(
                Buku.this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ambil Foto")) {
                    String fileName = "new-photo-name.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);

                } else if (options[item].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        });
        builder.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = imageUri;
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (selectedImageUri != null) {
            try {
                String filemanagerstring = selectedImageUri.getPath();
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(Buku.this, "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(Buku.this, "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        final int REQUIRED_SIZE = 1024;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);
        iv.setImageBitmap(bitmap);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void upload() {
        final String idjenis_ = listID_jenis.get(spinjenis.getSelectedItemPosition());
        final String idtempat_ = listID_tempat.get(spintempat.getSelectedItemPosition());
        final String judul_ = edJudul.getText().toString();
        final String pengarang_ = edPengarang.getText().toString();
        final String penerbit_ = edPenerbit.getText().toString();
        final String tahunterbit_ = edThnterbit.getText().toString();
        final String stock_ =stock.getText().toString();


        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = SimpanBuku;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              if (response.equals("0")){
                    Toast.makeText(Buku.this, "Data Berhasil Di Edit", Toast.LENGTH_SHORT).show();
                    kosong();
//                            akunsama();
                    progres.dismiss();

//                            Toast.makeText(Add.this, "Username sudah ada yang pakai. Ulangi lagi", Toast.LENGTH_SHORT).show();
                }else  {
                    Toast.makeText(Buku.this, response, Toast.LENGTH_SHORT).show();
                    kosong();
//                            Intent intent = new Intent(getApplicationContext(),Login.class);
//                            startActivity(intent);
//                            finish();
                    progres.dismiss();

                }


                progres.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(ImageProfile.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                Toast.makeText(Buku.this, "Pilih foto terlebih dahulu", Toast.LENGTH_LONG).show();
                progres.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

//                params.put( key_id_alumni, preferences.getUsername());
                params.put(koneksi.key_idjenis,idjenis_);
                params.put(koneksi.key_idtempat, idtempat_);
                params.put(koneksi.key_judulbuku,judul_);
                params.put(koneksi.key_pengarang,pengarang_);
                params.put(koneksi.key_penerbit,penerbit_);
                params.put(koneksi.key_thn_terbit,tahunterbit_);
                params.put(koneksi.key_stock,stock_);
                params.put(koneksi.key_fotobuku, getStringImage(bitmap));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void caribuku() {
        final String judulsearch_ = edCariBuku.getText().toString();
//        String a = edCariBuku.getText().toString();
//        String[] b = a.split(" ");
//
//        for (int i = 0; i < b.length; i++){
//            if(i == 0){
//                c = b[i]; //
//            }else{
//                c = c+ "%20"+b[i];
//            }

//        }

        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = CariBuku;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         if (response.contains("1")) {
//                            Toast.makeText(getApplicationContext(), "Data Ada", Toast.LENGTH_SHORT).show();
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
                                    edJudul.setText(c.getString(koneksi.key_judulbuku));
                                    edPengarang.setText(c.getString(koneksi.key_pengarang));
                                    edPenerbit.setText(c.getString(koneksi.key_penerbit));
                                    edThnterbit.setText(c.getString(koneksi.key_thn_terbit));
                                    stock.setText(c.getString(koneksi.key_stock));

                                }
                                progres.dismiss();
//                                ArrayAdapter<String> adap1 = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayDatajenis);
//                                spinjenis.setAdapter(adap1);
//                                ArrayAdapter<String> adap2 = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayListtempat);
//                                spintempat.setAdapter(adap2);
                            } catch (JSONException e) {
                                progres.dismiss();
                                e.printStackTrace();
                            }

                        } else {
                            progres.dismiss();
                            Toast.makeText(getApplicationContext(), "Tidak ada", Toast.LENGTH_SHORT).show();
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
                params.put(koneksi.key_judulbuku, judulsearch_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void hapus() {
        final String judul_ = edJudul.getText().toString();
        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = HapusBuku;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {
                            progres.dismiss();
                            Toast.makeText(Buku.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            kosong();
                        } else {
                            progres.dismiss();
                            Toast.makeText(Buku.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                        progres.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progres.dismiss();
                        Toast.makeText(Buku.this, "Tidak ada buku", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(koneksi.key_judulbuku, judul_);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void kosong(){

//        spinjenis.setEnabled(null);
        edJudul.setText("");
        edPengarang.setText("");
        edPenerbit.setText("");
        edThnterbit.setText("");
        stock.setText("");


    }

}
