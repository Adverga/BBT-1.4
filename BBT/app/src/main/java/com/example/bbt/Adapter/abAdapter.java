package com.example.bbt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbt.R;

import java.util.List;

public class abAdapter extends RecyclerView.Adapter<abAdapter.aViewHolder> {
    private Context context;
    private List<String> mData;
    public abAdapter(Context context, List<String> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public aViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kontenedit, parent, false);
        aViewHolder viewHolder = new aViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull aViewHolder holder, final int position) {
        holder.mEditText.setText(mData.get(position));
        holder.mBtnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class aViewHolder extends RecyclerView.ViewHolder {
        private TextView mEditText,mBtnMin;
        public aViewHolder(@NonNull View itemView) {
            super(itemView);
            mEditText = itemView.findViewById(R.id.editText);
            mBtnMin = itemView.findViewById(R.id.btnMin);
        }
    }
}
