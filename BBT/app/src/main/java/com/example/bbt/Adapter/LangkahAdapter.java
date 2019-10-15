package com.example.bbt.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbt.Fragment.AddFragment2;
import com.example.bbt.R;

import java.util.ArrayList;
import java.util.List;

public class LangkahAdapter extends RecyclerView.Adapter<LangkahAdapter.aViewHolder> {
    private Context context;
    private ArrayList<String> mData1;
    private ArrayList<Uri> mData11;
    private ArrayList<String> mData2;
    public LangkahAdapter(Context context, ArrayList<String> mData1, ArrayList<String> mData2, String n) {
        this.context = context;
        this.mData1 = mData1;
        this.mData2 = mData2;
    }
    public LangkahAdapter(Context context, ArrayList<Uri> mData1, ArrayList<String> mData2) {
        this.context = context;
        this.mData11 = mData1;
        this.mData2 = mData2;
    }
    @NonNull
    @Override
    public aViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kontenedit2, parent, false);
        aViewHolder viewHolder = new aViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull aViewHolder holder, final int position) {
        if (mData1!=null)
            Glide.with(context).load(mData1.get(position)).into(holder.mImageView);
        else
        Glide.with(context).load(mData11.get(position)).into(holder.mImageView);
        holder.mEditText.setText(mData2.get(position));
        holder.mBtnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mData1!=null) mData1.remove(position);
//                else
                    mData11.remove(position);
                mData2.remove(position);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData2.size();
    }
    public class aViewHolder extends RecyclerView.ViewHolder {
        private TextView  mEditText, mBtnMin;
        private ImageView mImageView;
        public aViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgLangkah);
            mEditText = itemView.findViewById(R.id.editText2);
            mBtnMin = itemView.findViewById(R.id.btnMin);
        }
    }
}
