package com.example.bbt.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bbt.R;
import com.example.bbt.Adapter.homeAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Produk> listBudidaya, listPengolahan, listProteksi, listPenyuluhan, listSosial;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = view.findViewById(R.id.tabHome);
        viewPager = view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        homeAdapter adapter = new homeAdapter(getChildFragmentManager());
        adapter.addFragment(new ProdukFragment("Budidaya"),"Budidaya Pertanian");
        adapter.addFragment(new ProdukFragment("Pengolahan"), "Pengolahan Hasil");
        adapter.addFragment(new ProdukFragment("Proteksi"),"Proteksi Tanaman");
        adapter.addFragment(new ProdukFragment("Penyuluhan"), "Penyuluhan Pertanian");
        adapter.addFragment(new ProdukFragment("Sosial"), "Sosial Ekonomi");
        viewPager.setAdapter(adapter);
    }
}
