package com.example.bbt.Fragment;


import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bbt.R;
import com.example.bbt.Adapter.homeAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Map<String, ArrayList<Produk>> map = new HashMap<>();
    private String[] tipe = {"Budidaya","Pengolahan","Proteksi","Penyuluhan","Sosial"};
    private String[] title = {"Budidaya Pertanian","Pengolahan Hasil","Proteksi Tanaman","Penyuluhan Pertanian","Sosial Ekonomi"};
    private boolean mod;
    private ProgressDialog progressDialog;

    public HomeFragment() {
        for (String a : tipe){
            map.put(a, new ArrayList<Produk>());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Produk");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tabLayout = view.findViewById(R.id.tabHome);
        viewPager = view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        for (int i = 0; i < tipe.length; i++){
            map.get(tipe[i]).clear();
            for (DataSnapshot ds : dataSnapshot.child(tipe[i]).getChildren()) {
                Produk p = ds.getValue(Produk.class);
                Log.d("cek lagi", String.valueOf(ds.getKey()));
                if (p != null) {
                    map.get(tipe[i]).add(p);
                    Log.d("cek p listalat "+tipe[i], p.getListAlat());
                }
            }
            Log.d("cek list size", String.valueOf(map.get(tipe[i]).size()));
        }
        progressDialog.dismiss();
    }

    private void setupViewPager(ViewPager viewPager) {
        homeAdapter adapter = new homeAdapter(getChildFragmentManager());
        for (int i = 0; i < tipe.length; i++){
            adapter.addFragment(ProdukFragment.newInstance(tipe[i],map.get(tipe[i])),title[i]);
        }
        viewPager.setAdapter(adapter);
    }
}
