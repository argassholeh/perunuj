package com.example.sholeh_attadzkirah.perpustakaan;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

import com.example.sholeh_attadzkirah.perpustakaan.config.koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.akanLogin;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.key_password;
import static com.example.sholeh_attadzkirah.perpustakaan.config.koneksi.key_username;



public class SignIn extends AppCompatActivity {

    private EditText ed_Username;
    private EditText ed_Password;
    private Button btn_Login;

    private String username;
    private String password;
    private String idnya;
    private ProgressDialog progres;


    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ed_Username = findViewById(R.id.edUser);
        ed_Password = findViewById(R.id.edPassword);
        btn_Login = findViewById(R.id.btnLogin);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        preferences = new Preferences(this);

        if (preferences.getSPSudahLogin() && (preferences.getSPStatus().equalsIgnoreCase("anggota"))){
            this.finish();
            startActivity(new Intent(SignIn.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

        }else if (preferences.getSPSudahLogin() && (preferences.getSPStatus().equalsIgnoreCase("petugas"))){
            this.finish();
            startActivity(new Intent(SignIn.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

        }
      }
    public void login(){
        userLogin();
    }
    private void userLogin() {
        username = ed_Username.getText().toString().trim();
        password = ed_Password.getText().toString().trim();

        if (!validasi()) return;

        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = koneksi.akanLogin;


        StringRequest stringRequest = new StringRequest(Request.Method.POST,koneksi.akanLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//response.trim().equalsIgnoreCase("korcam")
                        try {
                            String aktif = "";
                            String username = "";
                            JSONObject jsonObject;
                            jsonObject = new JSONObject(response);
//                            Toast.makeText(SignIn.this,jsonObject.toString(),Toast.LENGTH_LONG).show();

                            String status = jsonObject.getString("status");
                            JSONArray result = jsonObject.getJSONArray("Hasil");
                            for(int i =0; i < result.length(); i++){
                                JSONObject c = result.getJSONObject(i);
                               // aktif = c.getString("status");
                                username = c.getString("username"); // tampilkan id alumni

                            }

                            if(status.equalsIgnoreCase("anggota")){
//                                Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                                openUtama(username, aktif);
                                preferences.saveSPBoolean(Preferences.SP_SUDAH_LOGIN, true);
                                preferences.saveSPString(preferences.SP_STATUS, "anggota");
                                preferences.saveSPString(preferences.SP_Aktif,aktif);
                                preferences.saveSPString(preferences.SP_UserName,username);
                                finish();
                                Toast.makeText(SignIn.this,"Sukses Pengguna",Toast.LENGTH_LONG).show();
                                progres.dismiss();
                            }else if (status.equalsIgnoreCase("petugas")){
                                openUtama(username, aktif);

                                preferences.saveSPBoolean(Preferences.SP_SUDAH_LOGIN, true);
                                preferences.saveSPString(preferences.SP_STATUS, "petugas");
                                preferences.saveSPString(preferences.SP_Aktif,aktif);
                                preferences.saveSPString(preferences.SP_UserName,username);
                                finish();
                                Toast.makeText(SignIn.this,"Sukses Petugas",Toast.LENGTH_LONG).show();
                                progres.dismiss();

                            } else {
//                                Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                                Toast.makeText(SignIn.this,"User Name dan Password Salah",Toast.LENGTH_LONG).show();

                                progres.dismiss();
//
                            }
                        } catch (JSONException e) {
//                            progres.dismiss();
                            Toast.makeText(SignIn.this,"User Name dan Password Salah",Toast.LENGTH_LONG).show();
//                            Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        progres.dismiss();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignIn.this,error.toString(),Toast.LENGTH_LONG ).show();
//                        Toast.makeText(SignIn.this,"Koneksi Internet Anda Kurang Stabil",Toast.LENGTH_LONG).show();
                        progres.dismiss();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(key_username,username);
                map.put(key_password,password);
                //   map.put(key_id_akun,idnya);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openUtama(String username, String status){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("status", status);
//        Toast.makeText(this, "u "+username+"s"+status, Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();

    }


    private boolean validasi() {


        boolean valid = true;

        String susername = ed_Username.getText().toString().trim();
        String spassword = ed_Password.getText().toString().trim();

        if (susername.isEmpty() || ed_Username.length() < 3 || ed_Username.length() > 15) {
            ed_Username.setError("masukkan username 3 - 15 karakter");
            valid = false;
        } else {
            ed_Username.setError(null);
        }

        if (spassword.isEmpty() || ed_Password.length() < 5 || ed_Password.length() > 10) {
            ed_Password.setError("masukkan password 5 - 10 karakter");
            valid = false;
        } else {
            ed_Password.setError(null);
        }

        return valid;
    }
}
