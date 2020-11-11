package com.example.sholeh_attadzkirah.perpustakaan.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sholeh_attadzkirah.perpustakaan.DetailBuku;
import com.example.sholeh_attadzkirah.perpustakaan.MainActivity;
import com.example.sholeh_attadzkirah.perpustakaan.R;
import com.example.sholeh_attadzkirah.perpustakaan.config.koneksi;

import java.util.ArrayList;

public class adapter_buku extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Model_Buku> dataList;
    public adapter_buku(ArrayList<Model_Buku> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflat = LayoutInflater.from(parent.getContext());
        v = inflat.inflate(R.layout.custom_buku,parent,false);
        return new MyHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;

        Glide.with(myHolder.context).load(koneksi.tampilFotoItemBuku+dataList.get(position).getImage()).apply(new RequestOptions().placeholder(R.drawable.no_image).centerCrop()).into(myHolder.img);
        myHolder.judul.setText(dataList.get(position).getJudul());
        myHolder.stock.setText(dataList.get(position).getStock());
//        myHolder.deskrip.setText(dataList.get(position).getDescription());
        myHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(myHolder.context, DetailBuku.class);
////                i.putExtra("id buku", dataList.get(position).getIdBuku());
//                i.putExtra("judul buku", dataList.get(position).getJudul());
//                i.putExtra("stock", dataList.get(position).getStock());
//                i.putExtra("foto buku", koneksi.tampilFotoItemBuku+dataList.get(position).getImage());
//
//                myHolder.context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    class MyHolder extends RecyclerView.ViewHolder {

        TextView judul, stock;
        ImageView img;
        CardView card;
        Context context;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            img = itemView.findViewById(R.id.imgbuku);
            judul = itemView.findViewById(R.id.tvjudul_buku);
            stock = itemView.findViewById(R.id.tvtersedia);
            card = itemView.findViewById(R.id.cardbuku);
//            deskrip = itemView.findViewById(R.id.deskrip);

        }

    }
}
