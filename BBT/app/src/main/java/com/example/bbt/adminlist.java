package com.example.bbt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class adminlist extends AppCompatActivity implements View.OnClickListener{
    private Button btnbudidaya, btnpenghas, btnproteksi, btnchat, btnlogout;
    private String name;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlist);

        firebaseAuth = FirebaseAuth.getInstance();

        name = getIntent().getExtras().get("user_name").toString();

        btnbudidaya = findViewById(R.id.btnbudidaya);
        btnpenghas = findViewById(R.id.btnpenghas);
        btnproteksi = findViewById(R.id.btnproteksi);
        btnchat = findViewById(R.id.btnchat);
        btnlogout = findViewById(R.id.btnlogout);

        btnbudidaya.setOnClickListener(this);
        btnpenghas.setOnClickListener(this);
        btnproteksi.setOnClickListener(this);
        btnchat.setOnClickListener(this);
        btnlogout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnchat:
                login();
                break;
            case R.id.btnlogout:
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    private void login(){
        Intent intent = new Intent(adminlist.this, Main3Activity.class);
        intent.putExtra("user_name", name);
        startActivity(intent);
    }

}
