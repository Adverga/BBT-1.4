package com.example.bbt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class userlist extends AppCompatActivity implements View.OnClickListener{

    private Button btnbudidaya, btnpenghas, btnproteksi, btnchat, btnout;
    private String name;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        firebaseAuth = FirebaseAuth.getInstance();

        name = getIntent().getExtras().get("user_name").toString();

        btnbudidaya = findViewById(R.id.btnbudidaya);
        btnpenghas = findViewById(R.id.btnpenghas);
        btnproteksi = findViewById(R.id.btnproteksi);
        btnchat = findViewById(R.id.btnchat);
        btnout = findViewById(R.id.btnout);

        btnbudidaya.setOnClickListener(this);
        btnpenghas.setOnClickListener(this);
        btnproteksi.setOnClickListener(this);
        btnchat.setOnClickListener(this);
        btnout.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnchat:
                login();
                break;
            case R.id.btnout:
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    private void login(){
        Intent intent = new Intent(userlist.this, Main2Activity.class);
        intent.putExtra("user_name", name);
        startActivity(intent);
    }

}
