package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bbt.Adapter.ViewLangkahAdapter;
import com.example.bbt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fm_langkah extends Fragment {


    private ArrayList<String> listLangkah = new ArrayList<>();
    private ListView listviewLangkah;
    private ArrayAdapter langkahAdapter;
    private RecyclerView rvItemLangkah;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");
    private String keyLangkah;
    private String keyLangkahImg;
    private ArrayList<String> listLangkahImg = new ArrayList<>();
    private ViewLangkahAdapter viewLangkahAdapter;

    public fm_langkah() {
        // Required empty public constructor
    }

    public static fm_langkah newInstance(String keyLangkah, String keyLangkahImg) {
        fm_langkah fragment = new fm_langkah();
        Bundle args = new Bundle();
        args.putString("listLangkah", keyLangkah);
        args.putString("listLangkahImg", keyLangkahImg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyLangkah = getArguments().getString("listLangkah");
            keyLangkahImg = getArguments().getString("listLangkahImg");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_langkah, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listviewLangkah = view.findViewById(R.id.listviewLangkah);
//        rvItemLangkah = view.findViewById(R.id.rvlistviewLangkah);

        langkahAdapter = new ArrayAdapter<String>(getActivity(), R.layout.text_card, listLangkah);
        listviewLangkah.setAdapter(langkahAdapter);

//        rvItemLangkah.setLayoutManager(new LinearLayoutManager(getContext()));
//        viewLangkahAdapter = new ViewLangkahAdapter(getContext(), listLangkahImg, listLangkah);
//        rvItemLangkah.setAdapter(viewLangkahAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> setLangkah;
                setLangkah = (ArrayList<String>) dataSnapshot.child("listLangkah").child(keyLangkah).getValue();
//                ArrayList<String> setLangkahImg;
//                setLangkahImg = (ArrayList<String>) dataSnapshot.child("listLangkahImg").child(keyLangkahImg).getValue();

                listLangkah.clear();
//                listLangkahImg.clear();

                listLangkah.addAll(setLangkah);
//                listLangkahImg.addAll(setLangkahImg);

                langkahAdapter.notifyDataSetChanged();
//                viewLangkahAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
