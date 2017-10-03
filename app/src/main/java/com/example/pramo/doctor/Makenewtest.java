package com.example.pramo.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class Makenewtest extends AppCompatActivity {

    private Button mrecordbtn,submitbtn,next;
    private TextView mrecordlabel,number;
    private MediaRecorder mRecorder;
    private String mFileName=null;
    private static final String LOG_TAG="Record_log";
    private StorageReference mstorage;
    private ProgressDialog mprogress;
    private EditText choice1txt,choice2txt,choice3txt,choice4txt,answertext;
    private DatabaseReference databaseRef;
    Uri  link;
    String date_upload;
    HashMap<String,String> datamap;
    SharedPreferences sp;
   // private static String audio_url;
    int index;
    SharedPreferences.Editor e;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makenewtest);
        mstorage=FirebaseStorage.getInstance().getReference();
        databaseRef= FirebaseDatabase.getInstance().getReference().child("users");
        mprogress=new ProgressDialog(this);
        submitbtn=(Button) findViewById(R.id.submitbtn);
        number= (TextView) findViewById(R.id.number);
        mrecordbtn=(Button) findViewById(R.id.recordbtn);
        mrecordlabel=(TextView) findViewById(R.id.recordlabel);
        choice1txt=(EditText) findViewById(R.id.choice1txt);
        choice2txt=(EditText) findViewById(R.id.choice2txt);
        choice3txt=(EditText) findViewById(R.id.choice3txt);
        choice4txt=(EditText) findViewById(R.id.choice4txt);
        answertext=(EditText) findViewById(R.id.answertext);
        sp=getSharedPreferences("index",MODE_PRIVATE);
        e=sp.edit();
        index=sp.getInt("index",1);
        number.setText(""+sp.getInt("index",1));

        // Environment.
       //Environment.getExternalStorageDirectory().getAbsolutePath();
       // mstorage= FirebaseStorage.getInstance().getReference();
      // mFileName="/recorded_audio.3gp";
      //  submitbtn();
        mrecordbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){

                    startRecording();
                    Date d=new Date();
                    date_upload=d.toString();
                    mrecordlabel.setText("Recording started");
                    Toast.makeText(Makenewtest.this,"Recording started",Toast.LENGTH_LONG);

                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    //try {
                      //  mRecorder.prepare();
                    Date d=new Date();
                    date=d.toString();
                       stopRecording();
                        MediaPlayer mp=new MediaPlayer();
                    try {
                        mp.setDataSource(mFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mrecordlabel.setText("Recording stopped");
                }
                return false;
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // uploadAudio();

                String choice1=choice1txt.getText().toString().trim();
                String choice2=choice2txt.getText().toString().trim();
                String choice3=choice3txt.getText().toString().trim();
                String choice4=choice4txt.getText().toString().trim();
                String answer=answertext.getText().toString().trim();

              // while (link.toString()==null){
                datamap=new HashMap<String,String>();
                datamap.put("choice1",choice1);
                datamap.put("choice2",choice2);
                datamap.put("choice3",choice3);
                datamap.put("choice4",choice4);
                datamap.put("date",date_upload);
                datamap.put("ans",answer);
                datamap.put("audio_url",link.toString());
               //}

                if (TextUtils.isEmpty(choice1)||TextUtils.isEmpty(choice2)||TextUtils.isEmpty(choice3)||TextUtils.isEmpty(choice4)){
                    Toast.makeText(Makenewtest.this,"please enter the choice",Toast.LENGTH_LONG).show();
                    return;
                }

                databaseRef.child(patients.u_email).child(patients.u_email+""+index).setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Makenewtest.this,"Stored..",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Makenewtest.this,"unable to store...",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });

    }
    private void startRecording() {
        mFileName=Environment.getExternalStorageDirectory().getAbsolutePath()+"/recorded_audio.3gpp";
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFileName);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");

        }
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        uploadAudio();
    }

    private void uploadAudio(){
       // final Uri link;
        mprogress.setMessage("Uploading Audio...");
        mprogress.show();

        final Uri uri=Uri.fromFile(new File(mFileName));
        final StorageReference filepath=mstorage.child("Audio").child(date);
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mprogress.dismiss();
                mrecordlabel.setText("Uploading the fineshed");
                link =taskSnapshot.getDownloadUrl();
            }
        });
    }
  //  public void submitbtn(){

   // }

    public void nextbtn(View v){
        if(index<=10){
            e.putInt("index",index+=1);
            e.commit();
        startActivity(new Intent(Makenewtest.this,Makenewtest.class));
        }
        if(index>10){
            e.putInt("index",1);
                    e.commit();
            startActivity(new Intent(Makenewtest.this,patients.class));
            finish();
            patients.u_email=null;
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        e.putInt("index",1);
        e.commit();
        patients.u_email=null;
        super.onBackPressed();
    }
}