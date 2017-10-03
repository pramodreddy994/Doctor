package com.example.pramo.doctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class newtest extends AppCompatActivity {
 private Button newtestbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtest);

        newtestbtn=(Button) findViewById(R.id.newtestbtn);


        newtestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(newtest.this,Makenewtest.class);
                startActivity(i);
            }
        });

    }
}
