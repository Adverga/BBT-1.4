package com.example.bbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.bbt.Fragment.ChatFragment;
import com.example.bbt.Fragment.ContactFragment;
import com.example.bbt.Fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.isfaaghyth.rak.Rak;

public class Main4Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Rak.initialize(getApplicationContext());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(btmListener);
        bottomNavigationView.setSelectedItemId(R.id.menuHome);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fmContainer, new HomeFragment())
                .commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener btmListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fm = null;
            switch (menuItem.getItemId()){
                case  R.id.menuHome:
                    fm = new HomeFragment();
                    break;
                case R.id.menuContact :
                    fm = new ContactFragment();
                    break;
                case R.id.menuChat :
                    fm = new ChatFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fmContainer, fm)
                    .commit();
            return true;
        }
    };
}
