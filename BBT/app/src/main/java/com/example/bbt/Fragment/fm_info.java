package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
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
public class fm_info extends Fragment {


    private ArrayList<String> listInfo;

    public fm_info() {
        // Required empty public constructor
    }
    public static fm_info newInstance(ArrayList<String> param1) {
        fm_info fragment = new fm_info();
        Bundle args = new Bundle();
        args.putStringArrayList("listinfo", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listInfo = getArguments().getStringArrayList("listInfo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
