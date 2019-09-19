package com.example.bbt.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbt.Fragment.ItemClickListener;
import com.example.bbt.Fragment.Produk;
import com.example.bbt.R;

import java.util.List;

public class produkAdapter extends RecyclerView.Adapter<produkAdapter.ViewHolder> {
    private Context mContext;
    private List<Produk> produkList;
    private String tipe;
    private boolean mod;
    private final ItemClickListener listener;

    public produkAdapter( Context mContext, List<Produk> produkList, String tipe, boolean mod, ItemClickListener listener) {
        this.mContext = mContext;
        this.produkList = produkList;
        this.tipe = tipe;
        this.mod = mod;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.konten_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Produk produk = produkList.get(position);
        holder.bind(produk, listener, position);
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.kText);
            imageView = itemView.findViewById(R.id.kImage);
            cardView = itemView.findViewById(R.id.kCardView);

        }
        public void bind(final Produk produk, final ItemClickListener listener, final int position){
            textView.setText(produk.getJudul());
            if (produk.getImage() == null){
                Glide.with(mContext).load(R.drawable.bbppok).into(imageView);
            }else Glide.with(mContext).load(produk.getImage()).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(produk, position);
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(produk, position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(mod){
                        final Dialog dialog = new Dialog(mContext);
                        dialog.setContentView(R.layout.dialog_view);
                        dialog.setTitle("Pilih Aksi");
                        dialog.show();

                        Button editButton = dialog.findViewById(R.id.bt_edit_data);
                        Button delButton = dialog.findViewById(R.id.bt_delete_data);

                        //apabila tombol edit diklik
                        editButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
//                                        Intent i = new Intent();
//                                        mContext.startActivity();
//                                        mContext.startActivity(FirebaseDBCreateActivity.getActIntent((Activity) context).putExtra("data", daftarBarang.get(position)));
                                    }
                                }
                        );

                        //apabila tombol delete diklik
                        delButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        listener.onDeleteData(produkList.get(position), position);
                                    }
                                }
                        );
                    }
                    return true;
                }
            });
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(mod){
                        final Dialog dialog = new Dialog(mContext);
                        dialog.setContentView(R.layout.dialog_view);
                        dialog.setTitle("Pilih Aksi");
                        dialog.show();

                        Button editButton = dialog.findViewById(R.id.bt_edit_data);
                        Button delButton = dialog.findViewById(R.id.bt_delete_data);

                        //apabila tombol edit diklik
                        editButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
//                                        Intent i = new Intent();
//                                        mContext.startActivity();
//                                        mContext.startActivity(FirebaseDBCreateActivity.getActIntent((Activity) context).putExtra("data", daftarBarang.get(position)));
                                    }
                                }
                        );

                        //apabila tombol delete diklik
                        delButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        listener.onDeleteData(produkList.get(position), position);
                                    }
                                }
                        );
                    }
                    return true;
                }
            });

        }
    }
}
