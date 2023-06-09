package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TimerActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    private Chronometer chronometer;
    private boolean running;
    private long pauserOffsef;

    Button start, stop, reset, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("시간 : %s");

        start = findViewById(R.id.btn_timerstart);
        stop = findViewById(R.id.btn_timerstop);
        reset = findViewById(R.id.btn_timerreset);
        home = findViewById(R.id.btn_timertohome);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        DatabaseRef.child("BookTimer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    pauserOffsef = 0;
                }
                else{
                    pauserOffsef = Long.valueOf(String.valueOf(snapshot.getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! running){
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauserOffsef);
                    chronometer.start();
                    running = true;
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running){

                    chronometer.stop();
                    pauserOffsef = SystemClock.elapsedRealtime()-chronometer.getBase();
                    running = false;
                    DatabaseRef.child("BookTimer").child(firebaseUser.getUid()).setValue(pauserOffsef);
                    System.out.println(pauserOffsef + "시간");
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauserOffsef = 0;
                chronometer.stop();
                running = false;
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}