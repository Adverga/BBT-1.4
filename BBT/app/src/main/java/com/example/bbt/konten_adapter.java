package com.example.bbt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class konten_adapter extends RecyclerView.Adapter<konten_adapter.ViewHolder> {

    private Context mContext;
    private List<konten> kontenList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
    public konten_adapter(Context mContext, List<konten> kontenList){
        this.mContext = mContext;
        this.kontenList = kontenList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.konten_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        konten Konten = kontenList.get(position);
        holder.title.setText(Konten.getTitle());
        // loading image using Glide Library
        Glide.with(mContext).load(Konten.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return kontenList.size();
    }

}
