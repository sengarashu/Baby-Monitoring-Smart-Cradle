package com.example.riya.bmsc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main3Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth  mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText emailText;
    private EditText passText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        emailText=(EditText)findViewById(R.id.email);
        passText=(EditText)findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent i=new Intent(Main3Activity.this,Main4Activity.class);
                    startActivity(i);
                }
            }
        };
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    public void loginClicked(View view){
        String email=emailText.getText().toString();
        String pass=passText.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Enter both values",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(Main3Activity.this,"Incorrect",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
