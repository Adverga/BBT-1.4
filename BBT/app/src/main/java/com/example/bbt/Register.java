package com.example.bbt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText inuser, inemail, inpass, inconfirmpass;
    private Button btnregister;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inuser = findViewById(R.id.inuser);
        inemail = findViewById(R.id.inemail);
        inpass = findViewById(R.id.inpass);
        inconfirmpass = findViewById(R.id.inconfirmpass);
        btnregister = findViewById(R.id.btnregister);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnregister.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnregister:
                register();
                break;
        }
    }

    private void register(){
        if (inputValidated()){
            final String username = inuser.getText().toString(),
                    password = inpass.getText().toString(),
                    email = inemail.getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        User user = new User(firebaseAuth.getCurrentUser().getUid(),username,email);
                        databaseReference.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                        Toast.makeText(Register.this,"User Created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        //progressDialog.dismiss();
                    } else {
                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        //progressDialog.dismiss();
                    }
                }
            });
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
        }if (inemail.getText().toString().isEmpty()){
            res = false;
            inemail.setError("This is required");
        }if (inconfirmpass.getText().toString().isEmpty()){
            res = false;
            inconfirmpass.setError("This is required");
        }if (!inpass.getText().toString().equals(inconfirmpass.getText().toString())){
            res = false;
            inpass.setError("Different");
            inconfirmpass.setError("Different");
        }
        return res;
    }

}
