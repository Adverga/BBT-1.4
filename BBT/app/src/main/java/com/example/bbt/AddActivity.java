package com.example.bbt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bbt.Adapter.abAdapter;
import com.example.bbt.Fragment.AddFragment1;
import com.example.bbt.Fragment.AddFragment2;
import com.example.bbt.Fragment.Produk;

import java.util.ArrayList;

import io.isfaaghyth.rak.Rak;

public class AddActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQ = 5;
    private TextView idTipe;
    private ProgressBar progressBar;
    private String tipe;
    private Produk data;
    private FrameLayout fmContainerAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Rak.initialize(this);
        //init
        idTipe = findViewById(R.id.idTipe);
        fmContainerAdd = findViewById(R.id.fmContainerAdd);
        progressBar = findViewById(R.id.progressBarA);

        //setTipe
        tipe = getIntent().getExtras().getString("tipe");
        idTipe.setText(idTipe.getText()+tipe);

        if (getIntent().getExtras().getSerializable("Produk")!=null){
            data = (Produk) getIntent().getExtras().getSerializable("Produk");
        }else {
            data = null;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fmContainerAdd, AddFragment1.newInstance(tipe, data))
                .commit();
    }

}
