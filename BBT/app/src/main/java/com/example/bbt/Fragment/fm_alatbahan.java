package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bbt.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fm_alatbahan extends Fragment {


    private ArrayList<String> listAlat;
    private ArrayList<String> listBahan;
    private ListView listviewAlat;
    private ListView listviewBahan;

    public fm_alatbahan() {
        // Required empty public constructor
    }

    public static fm_alatbahan newInstance(ArrayList<String> param1, ArrayList<String> param2) {
        fm_alatbahan fragment = new fm_alatbahan();
        Bundle args = new Bundle();
        args.putStringArrayList("listAlat", param1);
        args.putStringArrayList("listBahan", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listAlat = getArguments().getStringArrayList("listAlat");
            listBahan = getArguments().getStringArrayList("listBahan");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alatbahan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listviewAlat = view.findViewById(R.id.listviewAlat);
        listviewBahan = view.findViewById(R.id.listviewBahan);

        listviewAlat.setAdapter(new AlatAdap(listAlat));
        listviewBahan.setAdapter(new AlatAdap(listBahan));
    }

    class AlatAdap extends BaseAdapter{
        ArrayList<String> arrayList;
        public AlatAdap(ArrayList<String> arrayList){
            this.arrayList = arrayList;
        }
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.text_card,null);
            TextView textView = view.findViewById(R.id.textAll);
            textView.setText(arrayList.get(i));
            return view;
        }
    }
}
