package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bbt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class fm_alatbahan extends Fragment {


    private ArrayList<String> listAlat = new ArrayList<>();
    private ArrayList<String> listBahan = new ArrayList<>();
    private ListView listviewAlat;
    private ListView listviewBahan;
    private ArrayAdapter<String> alatAdapter, bahanAdapter;
    private String keyAlat, keyBahan;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");

    public fm_alatbahan() {
        // Required empty public constructor
    }

    public static fm_alatbahan newInstance(String keyAlat, String keyBahan) {
        fm_alatbahan fragment = new fm_alatbahan();
        Bundle args = new Bundle();
        args.putString("listAlat", keyAlat);
        args.putString("listBahan", keyBahan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyAlat = getArguments().getString("listAlat");
            keyBahan = getArguments().getString("listBahan");
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

        alatAdapter = new ArrayAdapter<>(getActivity(), R.layout.text_card, listAlat);
        bahanAdapter = new ArrayAdapter<>(getActivity(), R.layout.text_card, listBahan);

        listviewAlat.setAdapter(alatAdapter);
        listviewBahan.setAdapter(bahanAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> setAlat ,setBahan;
                setAlat = (ArrayList<String>) dataSnapshot.child("listAlat").child(keyAlat).getValue();
                setBahan = (ArrayList<String>) dataSnapshot.child("listBahan").child(keyBahan).getValue();

                listAlat.clear();
                listBahan.clear();

                listAlat.addAll(setAlat);
                listBahan.addAll(setBahan);

                alatAdapter.notifyDataSetChanged();
                bahanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
