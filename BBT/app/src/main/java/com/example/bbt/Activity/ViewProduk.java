package com.example.bbt.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bbt.Adapter.ViewProdukAdapter;
import com.example.bbt.Fragment.Produk;
import com.example.bbt.Fragment.fm_alatbahan;
import com.example.bbt.Fragment.fm_info;
import com.example.bbt.Fragment.fm_langkah;
import com.example.bbt.R;
import com.google.android.material.tabs.TabLayout;

public class ViewProduk extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Produk produk;
    private boolean mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produk);

        tabLayout = findViewById(R.id.tabViewProduk);
        viewPager = findViewById(R.id.pagerViewProduk);

        if (getIntent().getExtras()==null){
            produk = new Produk();
        }else{
            produk = new Produk();
            produk.setJudul(getIntent().getStringExtra("judul"));
            produk.setImage(getIntent().getStringExtra("image"));
            produk.setListAlat(getIntent().getStringExtra("alat"));
            produk.setListBahan(getIntent().getStringExtra("bahan"));
            produk.setListLangkah(getIntent().getStringExtra("langkah"));
            produk.setListLangkahImg(getIntent().getStringExtra("langkahimg"));
            produk.setListInfo(getIntent().getStringExtra("info"));
            mod = getIntent().getExtras().getBoolean("mod");
        }

        setupAdapter();
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupAdapter() {
        ViewProdukAdapter adapter = new ViewProdukAdapter(getSupportFragmentManager());
        adapter.addFragment(fm_alatbahan.newInstance(produk.getListAlat(),produk.getListBahan()), "Alat & Bahan");
        adapter.addFragment(fm_langkah.newInstance(produk.getListLangkah(), produk.getListLangkahImg()), "Langkah");
        adapter.addFragment(fm_info.newInstance(produk.getListInfo()),"Informasi Tambahan");
        viewPager.setAdapter(adapter);
    }
}
