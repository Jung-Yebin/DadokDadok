package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LibraryDetailActivity extends AppCompatActivity {

    Button goread, goreading, gowish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        goread = findViewById(R.id.btn_goreadbook);
        goreading = findViewById(R.id.btn_goreadingbook);
        gowish = findViewById(R.id.btn_gowishbook);

        goread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryDetailActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        goreading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryDetailActivity.this, ReadingLibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gowish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryDetailActivity.this, WishLibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}