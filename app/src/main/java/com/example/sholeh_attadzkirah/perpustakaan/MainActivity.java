package com.example.sholeh_attadzkirah.perpustakaan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.sholeh_attadzkirah.perpustakaan.mRecycler.Model_Buku;
import com.example.sholeh_attadzkirah.perpustakaan.mRecycler.adapter_buku;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.CariBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.PencarianBuku;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.tampilItemBuku;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1pengguna, floatingActionButton2buku ;

    Toolbar toolBarisi;

    ArrayList<Model_Buku> daftar_item = new ArrayList<Model_Buku>();
    private ProgressDialog progres;
    adapter_buku adapter_buku;
    RecyclerView recycler;
    View v;

    Preferences preferences;


    private ProgressDialog pDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Button btnsearch;
    EditText ed_pencarian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnsearch = findViewById(R.id.bt_caribuku);
        ed_pencarian = findViewById(R.id.ed_caribku);

//        tampilbuku();

        preferences = new Preferences(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        recycler= findViewById(R.id.rvViewBuku);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        // get_id_lembaga_alumni, get Jenis Kegiatan nya
//        tampil_item(getIntent().getStringExtra("id_buku"),getIntent().getStringExtra("jenis_kegiatan"));
//        Toast.makeText(getApplication(),getIntent().getStringExtra("id_lembaga_alumni")+"  "+getIntent().getStringExtra("jenis_kegiatan"),Toast.LENGTH_SHORT).show();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);

        if(!preferences.getSPStatus().equalsIgnoreCase("petugas")){
            materialDesignFAM.setVisibility(View.GONE);
        }

      //  Toast.makeText(this, "s"+!preferences.getSPStatus().equalsIgnoreCase("petugas"), Toast.LENGTH_SHORT).show();
//

        floatingActionButton1pengguna = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_datapengguna);
        floatingActionButton2buku = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_databuku);

        floatingActionButton1pengguna.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                //untuk aksi ketika di klik
                Intent intent = new Intent(getApplication(), DataPengguna.class);
                startActivity(intent);
            }
        });
        floatingActionButton2buku.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
//                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplication(), DataBuku.class);
                startActivity(intent);


            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pencarian_buku();
            }
        });



    }
    public void keluar(){
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
        build.setMessage("Anda yakin ingin keluar aplikasi ?");
        build.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        build.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        build.create().show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            keluar();
        }
    }

    @Override
    public void onResume()
    {
        tampilbuku(); // biar reload data atau refresh ketika sudah ganti foto profile
        super.onResume();
        // Load data and do stuff
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_akun) {
            Intent intent = new Intent(getApplication(),AkunPass.class);
            startActivity(intent);



        } else if (id == R.id.nav_tentang) {

            Intent intent = new Intent(getApplication(),Tentang.class);
            startActivity(intent);



        } else if (id == R.id.nav_exit) {
            exit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void exit (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin, ingin keluar akun?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferences.saveSPBoolean(preferences.SP_SUDAH_LOGIN, false);
//                startActivity(new Intent(getApplication(), SignIn.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                getApplication().fini;
                finish();

//
                Toast.makeText(getApplication(), "Berhasil Keluar", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(), SignIn.class));
//                getActivity().finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alert.show();


//        default:
//        return super.onOptionsItemSelected(item);

    }

    private void tampilbuku() {

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        String url = tampilItemBuku ;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplication(), "RESPON"+response, Toast.LENGTH_SHORT).show();
                        if(response.contains("1")) {
                            try {
                                pDialog.dismiss();
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
//                                Toast.makeText(getApplication(),""+result, Toast.LENGTH_SHORT).show();
                                daftar_item.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
//                                    Toast.makeText(getApplication(), "RESPON "+c, Toast.LENGTH_SHORT).show();
                                    String id = c.getString("id_buku");
                                    String judul = c.getString("judul");
                                    String stock = c.getString("stock");
                                    String image = c.getString("foto_buku");

//
//


                                    daftar_item.add(new Model_Buku(id, judul,stock, image));

                                }
                                pDialog.dismiss();
                                adapter_buku = new adapter_buku(daftar_item);
                                recycler.setAdapter(adapter_buku);
                            } catch (JSONException e) {
                                pDialog.dismiss();
                                Toast.makeText(getApplication(), "Belum Ada Buku", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplication(), "cek "+e, Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }else{
                            pDialog.dismiss();
                            Toast.makeText(getApplication(), "Tidak ada", Toast.LENGTH_SHORT).show();
                        }
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplication(), "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplication(),error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put(key_id_lembaga_alumni,idL);
//                params.put(key_jenis_kegiatan,jk);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void pencarian_buku() {
        final String judulsearch_ = ed_pencarian.getText().toString();
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
        String url = PencarianBuku;
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
//                                arrayDatajenis.clear();
//                                listID_jenis.clear();
//                                arrayListtempat.clear();
//                                listID_tempat.clear();
                                daftar_item.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
//                                    arrayDatajenis.add(c.getString("jenis_buku"));
//                                    listID_jenis.add(c.getString("id_jenis"));
//                                    arrayListtempat.add(c.getString("tempat"));
//                                    listID_tempat.add(c.getString("id_lokasi"));

//                                    spinjenis.setT
//                                    edJudul.setText(c.getString(koneksi.key_judulbuku));
//                                    edPengarang.setText(c.getString(koneksi.key_pengarang));
//                                    edPenerbit.setText(c.getString(koneksi.key_penerbit));
//                                    edThnterbit.setText(c.getString(koneksi.key_thn_terbit));
//                                    stock.setText(c.getString(koneksi.key_stock));
                                    String id = c.getString("id_buku");
                                    String judul = c.getString("judul");
                                    String stock = c.getString("stock");
                                    String image = c.getString("foto_buku");
                                    daftar_item.add(new Model_Buku(id, judul,stock, image));

                                }
                                progres.dismiss();
                                adapter_buku = new adapter_buku(daftar_item);
                                recycler.setAdapter(adapter_buku);
//                                ArrayAdapter<String> adap1 = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayDatajenis);
//                                spinjenis.setAdapter(adap1);
//                                ArrayAdapter<String> adap2 = new ArrayAdapter<String>(Buku.this, R.layout.support_simple_spinner_dropdown_item, arrayListtempat);
//                                spintempat.setAdapter(adap2);
                            } catch (JSONException e) {
                                Toast.makeText(getApplication(), "Belum Ada Buku "+e, Toast.LENGTH_SHORT).show();
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
}
