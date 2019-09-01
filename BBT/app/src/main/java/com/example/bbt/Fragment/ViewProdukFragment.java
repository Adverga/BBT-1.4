package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bbt.Adapter.ViewProdukAdapter;
import com.example.bbt.R;
import com.google.android.material.tabs.TabLayout;

import io.isfaaghyth.rak.Rak;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProdukFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ViewProdukFragment() {
        // Required empty public constructor
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

        tabLayout = view.findViewById(R.id.tabViewProduk);
        viewPager = view.findViewById(R.id.pagerViewProduk);

        setupAdapter(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupAdapter(ViewPager vp) {
        ViewProdukAdapter adapter = new ViewProdukAdapter(getChildFragmentManager());
        adapter.addFragment(new fm_alatbahan(), "Alat & Bahan");
        adapter.addFragment(new fm_langkah(), "Langkah");
        adapter.addFragment(new fm_langkah(),"Informasi Tambahan");
        vp.setAdapter(adapter);
    }
}
