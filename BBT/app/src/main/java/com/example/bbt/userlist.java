package com.example.bbt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class userlist extends AppCompatActivity implements View.OnClickListener{

    private Button btnbudidaya, btnpenghas, btnproteksi, btnchat;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        name = getIntent().getExtras().get("user_name").toString();

        btnbudidaya = findViewById(R.id.btnbudidaya);
        btnpenghas = findViewById(R.id.btnpenghas);
        btnproteksi = findViewById(R.id.btnproteksi);
        btnchat = findViewById(R.id.btnchat);

        btnbudidaya.setOnClickListener(this);
        btnpenghas.setOnClickListener(this);
        btnproteksi.setOnClickListener(this);
        btnchat.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnchat:
                login();
                break;
        }
    }

    private void login(){
        Intent intent = new Intent(userlist.this, Main2Activity.class);
        intent.putExtra("user_name", name);
        startActivity(intent);
    }

}
