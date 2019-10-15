package com.example.bbt.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bbt.Fragment.AddFragment1;
import com.example.bbt.Fragment.EditFragment1;
import com.example.bbt.Fragment.Produk;
import com.example.bbt.R;

import io.isfaaghyth.rak.Rak;

public class ViewEdit extends AppCompatActivity {

    private TextView idTipe;
    private String tipe;
    private Produk data;
    private FrameLayout fmContainerAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);
        Rak.initialize(this);
        //init
        idTipe = findViewById(R.id.idTipee);
        fmContainerAdd = findViewById(R.id.fmContainerAdde);

        //setTipe
        tipe = getIntent().getExtras().getString("tipe");
        idTipe.setText(idTipe.getText()+tipe);
        data = (Produk) getIntent().getExtras().getSerializable("produk");
        Log.d("cek data", data.getJudul());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fmContainerAdde, EditFragment1.newInstance(tipe, data))
                .commit();
    }
}
