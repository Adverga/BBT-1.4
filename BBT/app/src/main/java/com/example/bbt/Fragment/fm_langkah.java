package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bbt.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fm_langkah extends Fragment {


    private ArrayList<String> listLangkah;

    public fm_langkah() {
        // Required empty public constructor
    }

    public static fm_langkah newInstance(ArrayList<String> param1) {
        fm_langkah fragment = new fm_langkah();
        Bundle args = new Bundle();
        args.putStringArrayList("listLangkah", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listLangkah = getArguments().getStringArrayList("listLangkah");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_langkah, container, false);
    }

}
