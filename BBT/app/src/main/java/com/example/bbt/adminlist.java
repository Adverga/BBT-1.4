package com.example.bbt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class adminlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlist);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnbudidaya:
                Intent intent = new Intent(adminlist.this, Main2Activity.class);
                startActivity(intent);
                break;
        }
    }
}
