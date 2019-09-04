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
