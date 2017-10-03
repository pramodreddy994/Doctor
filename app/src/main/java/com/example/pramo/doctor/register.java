package com.example.pramo.doctor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {


    private EditText nametxt,emailtxt,passtxt,mobiletxt,hospitaltxt,speculationtxt;
    private Button submitbtn;
    public DatabaseReference databaseRef;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    String email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        databaseRef= FirebaseDatabase.getInstance().getReference("Doctor");
        nametxt= (EditText) findViewById(R.id.nametxt);
        emailtxt= (EditText) findViewById(R.id.emailtxt);
        passtxt=(EditText) findViewById(R.id.passtxt);
        mobiletxt=(EditText) findViewById(R.id.mobiletxt);
        hospitaltxt=(EditText) findViewById(R.id.hospitaltxt);
        speculationtxt=(EditText) findViewById(R.id.speculationtxt);
        submitbtn=(Button) findViewById(R.id.submitbtn);

        //setSubmitbtn();



        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nametxt.getText().toString().trim();
                email=emailtxt.getText().toString().trim();
                pass=passtxt.getText().toString().trim();
                String mobile=mobiletxt.getText().toString().trim();
                String hospital=hospitaltxt.getText().toString().trim();
                String speculation=speculationtxt.getText().toString().trim();

                HashMap<String,String> datamap=new  HashMap<String,String>();
                datamap.put("name",name);
                datamap.put("email",email);
                datamap.put("password",pass);
                datamap.put("mobile",mobile);
                datamap.put("hospital",hospital);
                datamap.put("speculation",speculation);
                registeruser();
                databaseRef.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Stored..",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(register.this,"unable to store...",Toast.LENGTH_SHORT).show();
                        }
                    }

                });


            }
        });}

    private void  registeruser(){

        if(TextUtils.isEmpty( email)){
            Toast.makeText(this,"please enter your email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty( pass)){
            Toast.makeText(this,"please enter your password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("you are regestering please wait");
        progressDialog.show();
        //firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(this, n)
        firebaseAuth.createUserWithEmailAndPassword(email ,pass )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Registered Successfully ",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(register.this,"Registered failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }
}
