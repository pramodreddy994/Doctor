package com.example.pramo.doctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.pramo.doctor.R.id.allscores;

public class patientsMarks extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView allscores;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_marks);

        allscores=(ListView) findViewById(R.id.allscores);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://doctor-69aec.firebaseio.com/score/"+patients.u_email);
        FirebaseListAdapter<Score> firebaseListAdapter=new FirebaseListAdapter<Score>(patientsMarks.this,Score.class,android.R.layout.simple_list_item_1,databaseReference) {
            @Override
            protected void populateView(View v, Score model, int position) {
                tv= (TextView) v.findViewById(android.R.id.text1);
                tv.setText(""+model.getScore());
            }


//
        };
        allscores.setAdapter(firebaseListAdapter);

    }
}
