package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );

        FirebaseAuth = FirebaseAuth.getInstance();
        Button logout = findViewById(R.id.btn_logout);
        //Button write = findViewById(R.id.btn_write);
        Button library = findViewById(R.id.btn_library);
        Button mypage = findViewById(R.id.btn_mypage);
        Button statistics = findViewById(R.id.btn_statistics);
        //Button bookregister = findViewById(R.id.btn_bookregi);
        Button timer = findViewById(R.id.btn_timer);
        Button web = findViewById(R.id.btn_web);

        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃
                FirebaseAuth.signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        write.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //도서 작성
//                Intent intent = new Intent(MainActivity.this, Write_Activity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //서재로 이동
                Intent intent = new Intent(MainActivity.this, LibraryDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //마이페이지로 이동
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //통계페이지로 이동
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        bookregister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //책등록페이지로 이동
//                Intent intent = new Intent(MainActivity.this, BookRegisterActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.200.151:5000"));
                startActivity(intent);
                finish();
            }
        });

    }
}