package com.example.bbt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private EditText inuser, inpass;
    private Button btnlgn, btnregister;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener AuthListener;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){
            Intent intent = new Intent(this, Decide.class);
            startActivity(intent);
            finish();
        }
        /*AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };*/

        inuser = findViewById(R.id.inuser);
        inpass = findViewById(R.id.inpass);
        btnlgn = findViewById(R.id.btnlgn);
        btnregister = findViewById(R.id.btnregister);
        progress = new ProgressDialog(this);
        progress.setMessage("Logging in, please wait");

        btnlgn.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnlgn:
                login();
                break;
            case R.id.btnregister:
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private boolean inputValidated(){
        boolean res = true;
        if (inuser.getText().toString().isEmpty()){
            res = false;
            inuser.setError("This is required");
        }if (inpass.getText().toString().isEmpty()){
            res = false;
            inpass.setError("This is required");
        }
        return res;
    }

    private void login(){
        if (inputValidated()){
            String username = this.inuser.getText().toString();
            String password = this.inpass.getText().toString();
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Decide.class);
                        startActivity(intent);
                        finish();
                    } else{
                        String err = task.getException().getMessage();
                        if (err.contains("password")){
                            inpass.setError(err);
                        } else {
                            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    /*@Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(AuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthListener != null) {
            firebaseAuth.removeAuthStateListener(AuthListener);
        }
    }*/


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
