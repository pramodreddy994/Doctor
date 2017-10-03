package com.example.pramo.doctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class patients extends AppCompatActivity {
    private ListView patientlst;
    private DatabaseReference databaseReference;
    ArrayList<HashMap> patientlist=new ArrayList<>();
    ArrayList<Dbase> adapter;
    static  String  u_email;

    TextView name_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        Intent intent = getIntent();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patients_list");
        adapter=new ArrayList<>();
        patientlst= (ListView) findViewById(R.id.patientlst);
        FirebaseListAdapter<Dbase> firebaseListAdapter=new FirebaseListAdapter<Dbase>(patients.this,Dbase.class,android.R.layout.simple_list_item_1,databaseReference) {
            @Override
            protected void populateView(View v, Dbase model, int position) {
                name_text= (TextView)v.findViewById(android.R.id.text1);
                adapter.add(model);

               name_text.setText("Name: "+model.getname());
            }
        };
        patientlst.setAdapter(firebaseListAdapter);

        patientlst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               u_email=adapter.get(i).getemail().replace(".","@");
                Toast.makeText(patients.this,""+u_email,Toast.LENGTH_LONG).show();
               Intent x=new Intent(patients.this,Overview.class);
                startActivity(x);
            }
        });

    }

}