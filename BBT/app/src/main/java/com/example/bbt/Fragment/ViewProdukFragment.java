package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.bbt.Adapter.ViewProdukAdapter;
import com.example.bbt.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.isfaaghyth.rak.Rak;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProdukFragment extends Fragment {
    private static final String ARGS_ALAT = "listAlat";
    private static final String ARGS_BAHAN = "listBahan";
    private static final String ARGS_LANGKAH = "listLangkah";
    private static final String ARGS_INFO = "listInfo";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String keyAlat, keyBahan, keyLangkah, keyInfo;
    private ArrayList<String> listAlat, listBahan, listLangkah, listInfo;

    public ViewProdukFragment() {
        // Required empty public constructor
    }
    public static ProdukFragment newInstance(String listAlat, String listBahan, String listLangkah, String listInfo) {
        ProdukFragment fragment = new ProdukFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_ALAT,listAlat);
        args.putString(ARGS_BAHAN,listBahan);
        args.putString(ARGS_LANGKAH,listLangkah);
        args.putString(ARGS_INFO,listInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.keyAlat = getArguments().getString(ARGS_ALAT);
            this.keyBahan = getArguments().getString(ARGS_BAHAN);
            this.keyLangkah = getArguments().getString(ARGS_LANGKAH);
            this.keyInfo = getArguments().getString(ARGS_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_produk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAlat = (ArrayList<String>) dataSnapshot.child("listAlat").child(keyAlat).getValue();
                listBahan = (ArrayList<String>) dataSnapshot.child("listBahan").child(keyBahan).getValue();
                listLangkah = (ArrayList<String>) dataSnapshot.child("listLangkah").child(keyLangkah).getValue();
                listInfo = (ArrayList<String>) dataSnapshot.child("listInfo").child(keyInfo).getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tabLayout = view.findViewById(R.id.tabViewProduk);
        viewPager = view.findViewById(R.id.pagerViewProduk);

        setupAdapter(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupAdapter(ViewPager vp) {
        ViewProdukAdapter adapter = new ViewProdukAdapter(getChildFragmentManager());
        adapter.addFragment(fm_alatbahan.newInstance(listAlat,listBahan), "Alat & Bahan");
        adapter.addFragment(fm_langkah.newInstance(listLangkah), "Langkah");
        adapter.addFragment(fm_info.newInstance(listInfo),"Informasi Tambahan");
        vp.setAdapter(adapter);
    }
}
