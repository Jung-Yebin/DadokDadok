package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WishBookRegisterActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    List<UserBook> UserBook = new ArrayList<>();

    String[] items = {"철학", "종교", "사회과학", "자연과학", "기술과학", "예술", "언어", "문학", "역사", "자기계발", "여행", "기타"};

    List<String> datelist = new ArrayList<String>();
    TextView bookname, Author, punisher, punishdate;
    Button home, register;
    String bookgenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_register);

        Spinner genre = findViewById(R.id.genre_dropdown);
        bookname = findViewById(R.id.et_change_bookname);
        Author = findViewById(R.id.et_change_author);
        punisher = findViewById(R.id.et_publisher);
        punishdate = findViewById(R.id.et_publishyear);
        home = findViewById(R.id.btn_back);
        register = findViewById(R.id.btn_bookregister);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        String strtime = time.toString();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        genre.setAdapter(adapter);

        genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bookgenre = items[i].toString();
                //Toast.makeText(BookRegisterActivity.this, items[i]+"선택", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishBookRegisterActivity.this, WishLibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strbookname = bookname.getText().toString();
                String strauthor = Author.getText().toString();
                String strpunisher = punisher.getText().toString();
                String strpunishdate = punishdate.getText().toString();
                String strgenre = bookgenre;

                UserBook bookinfo = new UserBook();
                bookinfo.setBookname(strbookname);
                bookinfo.setAuthor(strauthor);
                bookinfo.setPunisher(strpunisher);
                bookinfo.setPunishdate(strpunishdate);
                bookinfo.setGenre(strgenre);

                if (strbookname.length() == 0 || strauthor.length() == 0 || strpunisher.length() == 0 || strpunishdate.length() == 0) {
                    Toast.makeText(WishBookRegisterActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseRef.child("WishBook").child(firebaseUser.getUid()).child(strtime).setValue(bookinfo);
                    datelist.add(strtime);
                    UserBook.add(bookinfo);
                    Toast.makeText(WishBookRegisterActivity.this, "입력완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WishBookRegisterActivity.this, WishLibraryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}