package com.example.bbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.bbt.Fragment.ChatFragment;
import com.example.bbt.Fragment.ContactFragment;
import com.example.bbt.Fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import io.isfaaghyth.rak.Rak;

public class Main4Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private String name;
    private boolean mod;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        name = getIntent().getExtras().get("user_name").toString();
        mod = getIntent().getExtras().getBoolean("mod");

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
                    fm = HomeFragment.newInstance(mod);
                    break;
                case R.id.menuContact :
                    fm = new ContactFragment();
                    break;
                case R.id.menuChat :
                    fm = new ChatFragment(name, String.valueOf(mod));
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fmContainer, fm)
                    .commit();
            return true;
        }
    };

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
            case R.id.bantuan:
                Intent intent = new Intent(Main4Activity.this, bantuan.class);
                startActivity(intent);
                return true;
            case R.id.tentangKami:
                Intent intent1 = new Intent(Main4Activity.this, about_us.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
