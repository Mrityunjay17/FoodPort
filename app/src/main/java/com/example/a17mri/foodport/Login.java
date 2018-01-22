package com.example.a17mri.foodport;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  {
    private AppCompatEditText emilAddress,password;
    private FirebaseAuth mAuth;
    boolean success=false;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        emilAddress=findViewById(R.id.emailAddress);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(password.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }

                if(login(emilAddress.getText().toString(),password.getText().toString())){
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            back();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        back();
        super.onBackPressed();
    }

    private void back(){
        Intent intent=new Intent(getApplicationContext(),Main.class);
        startActivity(intent);
        finish();
    }


    private boolean login(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           success=true;
                        } else {
                            String message= task.getException().getMessage();
                            // If sign in fails, display a message to the user.
                            Snackbar bar=Snackbar.make(login.getRootView(),message,Snackbar.LENGTH_SHORT);
                            bar.show();
                        }

                        // ...
                    }
                });
        return success;
    }


}
