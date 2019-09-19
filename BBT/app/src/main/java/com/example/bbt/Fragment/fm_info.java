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
import android.widget.ListView;

import com.example.bbt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fm_info extends Fragment {


    private ArrayList<String> listInfo = new ArrayList<>();
    private ListView listviewInfo;
    private ArrayAdapter<String> infoAdapter;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");
    private String keyInfo;

    public fm_info() {
        // Required empty public constructor
    }
    public static fm_info newInstance(String keyInfo) {
        fm_info fragment = new fm_info();
        Bundle args = new Bundle();
        args.putString("listInfo", keyInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyInfo = getArguments().getString("listInfo");
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
        listviewInfo = view.findViewById(R.id.listviewInfo);

        infoAdapter = new ArrayAdapter<String>(getActivity(), R.layout.text_card, listInfo);
        listviewInfo.setAdapter(infoAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> setInfo;
                setInfo = (ArrayList<String>) dataSnapshot.child("listInfo").child(keyInfo).getValue();
                Log.d("cek key", dataSnapshot.child("listInfo").child(keyInfo).getKey());
                Log.d("cek key", String.valueOf(dataSnapshot.child("listInfo").child(keyInfo).getValue()));
                Log.d("cek key", String.valueOf(dataSnapshot.child("listInfo").child(keyInfo).getRef()));
                Log.d("cek list", String.valueOf(setInfo));


                listInfo.clear();
                listInfo.addAll(setInfo);

                infoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
