package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity implements GoalDialog.GoalDialogInterface{
    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    Button goal, home, detail, usersrank;
    private ProgressBar progressBar;
    private double bookcount;
    private String temp_value;
    private double value;
    ArrayList<String> booknamelist = new ArrayList<>();
    TextView tvreadbook, tvbookgoal, tvper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );

        home = findViewById(R.id.btn_home4);
        goal = findViewById(R.id.btn_bookcount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvreadbook = findViewById(R.id.tv_readbook);
        tvbookgoal = findViewById(R.id.tv_bookgoal);
        tvper = findViewById(R.id.tv_per);
        detail = findViewById(R.id.btn_detail);
        usersrank = findViewById(R.id.btn_usersrank);

        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        //현재 읽은 책
        DatabaseRef.child("ReadBook").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    UserBook up = ds.getValue(UserBook.class);
                    //Userpost up = ds.getValue(Userpost.class);
                    booknamelist.add(up.getBookname());

                    bookcount = booknamelist.size();

                    DatabaseRef.child("Goal").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            System.out.println(snapshot.getValue() + "스냅샷");

                            if (snapshot.getValue() == null){
                                //
                            }
                            else{
                                temp_value = snapshot.getValue().toString();
                                value = Integer.parseInt(temp_value);

                                //bookount = 실제 읽은 책수
                                //value = 목표

                                double temp_per = (bookcount / value) * 100;
                                int per = (int)(Math.round(temp_per));


                                if (per >= 100){
                                    per = 100;
                                }

                                progressBar.setProgress(per);
                                tvreadbook.setText( String.valueOf(Math.round(bookcount)));
                                tvbookgoal.setText( String.valueOf(Math.round(value)));
                                tvper.setText( String.valueOf(Math.round(per)));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalDialog g = new GoalDialog();
                g.show(getSupportFragmentManager(), "목표설정");
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, GenreChartActivity.class);
                startActivity(intent);
            }
        });
        usersrank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, UsersRankActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void applyInteger(String stringone) {
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();
        Integer a = Integer.parseInt(stringone);
        if (stringone.toString() == "" ){
            Toast.makeText(StatisticsActivity.this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(StatisticsActivity.this, "목표가 설정되었습니다.", Toast.LENGTH_SHORT).show();
            DatabaseRef.child("Goal").child(firebaseUser.getUid()).setValue(Integer.parseInt(stringone));
        }
    }
}