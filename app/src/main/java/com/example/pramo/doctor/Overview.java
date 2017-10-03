package com.example.pramo.doctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Overview extends AppCompatActivity {
   public Button scorebtn,addtestbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        scorebtn=(Button) findViewById(R.id.scorebtn);
        addtestbtn=(Button) findViewById(R.id.addtestbtn);
        scorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Overview.this,patientsMarks.class);
                startActivity(i);
            }
        });
        addtestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("error","executed");
                Intent i=new Intent(Overview.this,newtest.class);
                startActivity(i);
            }
        });
    }
}
