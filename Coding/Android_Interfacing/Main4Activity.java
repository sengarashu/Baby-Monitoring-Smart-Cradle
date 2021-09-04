package com.example.riya.bmsc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Main4Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }
    public void signout(View view){
        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(Main4Activity.this,Main3Activity.class));

    }
}
