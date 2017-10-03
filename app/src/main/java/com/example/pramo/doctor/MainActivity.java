package com.example.pramo.doctor;

import android.app.ProgressDialog;
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

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button sineinbtn,sineupbtn;
    private EditText emailtxt,passtxt;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        sineinbtn=(Button) findViewById(R.id.sineinbtn);
        sineupbtn=(Button) findViewById(R.id.sineupbtn);
        emailtxt=(EditText) findViewById(R.id.emailtxt);
        passtxt=(EditText) findViewById(R.id.passtxt);


        sineupbtn.setOnClickListener(this);
        sineinbtn.setOnClickListener(this);

    }

    private void userlogin(){
        String email=emailtxt.getText().toString().trim();
        String password=passtxt.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("you are sinening please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Intent i=new Intent(MainActivity.this,patients.class);
                            startActivity(i);
                            progressDialog.dismiss();
                             }

                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v==sineupbtn){
            //registerbtn();
            Intent i=new Intent(this,register.class);
            startActivity(i);
        }
        if (v==sineinbtn){
             userlogin();
        }

    }
}
