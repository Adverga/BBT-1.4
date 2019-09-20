package com.example.bbt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbt.R;

import java.util.ArrayList;

public class ViewLangkahAdapter extends RecyclerView.Adapter<ViewLangkahAdapter.aViewHolder> {
    private Context context;
    private ArrayList<String> mData1;
    private ArrayList<String> mData2;

    public ViewLangkahAdapter(Context context, ArrayList<String> mData1, ArrayList<String> mData2) {
        this.context = context;
        this.mData1 = mData1;
        this.mData2 = mData2;
    }

    @NonNull
    @Override
    public aViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_langkah, parent, false);
        aViewHolder viewHolder = new aViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull aViewHolder holder, final int position) {
        Glide.with(context).load(mData1.get(position)).into(holder.mImageView);
        holder.mEditText.setText(mData2.get(position));
    }
    @Override
    public int getItemCount() {
        return mData2.size();
    }
    public class aViewHolder extends RecyclerView.ViewHolder {
        private TextView  mEditText;
        private ImageView mImageView;
        public aViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgVLangkah);
            mEditText = itemView.findViewById(R.id.editVText2);
        }
    }
}
