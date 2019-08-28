package com.example.bbt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbt.Fragment.AddFragment;
import com.example.bbt.Fragment.ItemClickListener;
import com.example.bbt.Fragment.Produk;
import com.example.bbt.R;

import java.util.List;

import io.isfaaghyth.rak.Rak;

public class produkAdapter extends RecyclerView.Adapter<produkAdapter.ViewHolder> {
    private Context mContext;
    private List<Produk> produkList;
    private FragmentActivity activity;
    private String tipe;

    public produkAdapter(FragmentActivity activity, Context mContext, List<Produk> produkList, String tipe) {
        this.mContext = mContext;
        this.produkList = produkList;
        this.activity = activity;
        this.tipe = tipe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.konten_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Produk produk = produkList.get(position);
        holder.textView.setText(produk.getJudul());

        Glide.with(mContext).load(produk.getImage()).into(holder.imageView);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openEdit(produkList.get(pos));
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rak.entry("listAlat",produk.getListAlat());
                Rak.entry("listBahan", produk.getListBahan());
                Rak.entry("listinfo", produk.getListInfo());
                Rak.entry("listLangkah", produk.getListLangkah());


            }
        });
    }

    private void openEdit(Produk produk) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fmContainer, new AddFragment(tipe,produk));
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageView imageView;
        public CardView cardView;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.kText);
            imageView = itemView.findViewById(R.id.kImage);

            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }
}
