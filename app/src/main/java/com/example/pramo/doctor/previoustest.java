package com.example.pramo.doctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class previoustest extends AppCompatActivity {

    private TextView privoustxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previoustest);
        privoustxt=(TextView) findViewById(R.id.previoustxt);

        privoustxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(previoustest.this,patients.class);
                startActivity(i);
            }
        });
    }
}
