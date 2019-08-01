package com.example.bbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class adminlist extends AppCompatActivity implements View.OnClickListener{
    private Button btnbudidaya, btnpenghas, btnproteksi;//, btnchat, btnout;
    private String name;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlist);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        name = getIntent().getExtras().get("user_name").toString();

        btnbudidaya = findViewById(R.id.btnbudidaya);
        btnpenghas = findViewById(R.id.btnpenghas);
        btnproteksi = findViewById(R.id.btnproteksi);
        //btnchat = findViewById(R.id.btnchat);
        //btnout = findViewById(R.id.btnout);
        FloatingActionButton fbtnchat = findViewById(R.id.fbtnchat1);

        btnbudidaya.setOnClickListener(this);
        btnpenghas.setOnClickListener(this);
        btnproteksi.setOnClickListener(this);
        //btnchat.setOnClickListener(this);
        //btnout.setOnClickListener(this);
        fbtnchat.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.btnchat:
                login();
                break;
            case R.id.btnout:
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;*/
            case R.id.fbtnchat1:
                login();
                break;
        }
    }

    private void login(){
        Intent intent = new Intent(adminlist.this, Main3Activity.class);
        intent.putExtra("user_name", name);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.signout:
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
