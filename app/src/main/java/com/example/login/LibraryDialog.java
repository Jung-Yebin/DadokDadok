package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LibraryDialog extends AppCompatActivity implements ChangeBookInfoDialog.ChangeBookInfoDialogInterface {
    TextView tv_bookname, tv_author, tv_punisher, tv_punishdate, tv_genre, tv_bookreview;
    Button goback, changeinfo, write_reiview, del;
    ArrayList<String> datelist = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    String temp_bookname;

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_dialog);

        tv_bookname = findViewById(R.id.tv_change_bname);
        tv_bookreview = findViewById(R.id.tv_change_breview);
        tv_author = findViewById(R.id.tv_change_bauthor);
        tv_punisher = findViewById(R.id.tv_change_bpunisher);
        tv_punishdate = findViewById(R.id.tv_change_bpunishdate);
        tv_genre = findViewById(R.id.tv_bgenre);
        goback = findViewById(R.id.btn_back);
        changeinfo = findViewById(R.id.btn_changeifo);
        write_reiview = findViewById(R.id.btn_writereview);
        del = findViewById(R.id.btn_readdel);

        Intent receive_intent = getIntent();

        temp_bookname = receive_intent.getStringExtra("bookname");
        String temp_author = receive_intent.getStringExtra("author");
        String temp_punisher= receive_intent.getStringExtra("punisher");
        String temp_punishdate = receive_intent.getStringExtra("punishdate");
        String temp_genre= receive_intent.getStringExtra("genre");

        tv_bookname.setText(temp_bookname);
        tv_author.setText(temp_author);
        tv_punisher.setText(temp_punisher);
        tv_punishdate.setText(temp_punishdate);
        tv_genre.setText(temp_genre);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();



        DatabaseRef.child("Review").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Userpost up = ds.getValue(Userpost.class);

                    if(up.getBookname().contains(temp_bookname)){
                        DatabaseRef.child("Review").child(firebaseUser.getUid()).child(temp_bookname).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Userpost user = snapshot.getValue(Userpost.class);

                                String review = user.getBookreview();
                                tv_bookreview.setText(review);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        tv_bookreview.setText("독후감을 작성해주세요");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryDialog.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeBookInfoDialog f = new ChangeBookInfoDialog();
                f.show(getSupportFragmentManager(), "책정보변경");
            }
        });

        write_reiview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryDialog.this, Write_Activity.class);
                startActivity(intent);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseRef.child("ReadBook").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            UserBook up = ds.getValue(UserBook.class);

                            date.add(ds.getKey());
                            datelist.add(up.getBookname());

                        }

                        DatabaseRef.child("ReadBook").child(firebaseUser.getUid()).child(date.get(datelist.indexOf(temp_bookname))).removeValue();
                        DatabaseRef.child("Review").child(firebaseUser.getUid()).child(temp_bookname).removeValue();
                        Toast.makeText(LibraryDialog.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LibraryDialog.this, LibraryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    @Override
    public void applyTexts(String str_bookname, String str_author, String str_punisher, String str_punishdate, String str_genre, String str_bookreview) {
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        if (str_bookname.toString()=="" || str_author.toString()=="" ||str_punisher.toString()==""||str_punishdate.toString()==""||str_bookreview.toString()==""){
            Toast.makeText(LibraryDialog.this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show();
        }

        else {
            Userpost post = new Userpost();
            post.setBookname(str_bookname);
            post.setBookreview(str_bookreview);

            UserBook bookinfo = new UserBook();
            bookinfo.setBookname(str_bookname);
            bookinfo.setAuthor(str_author);
            bookinfo.setPunisher(str_punisher);
            bookinfo.setPunishdate(str_punishdate);
            bookinfo.setGenre(str_genre);

            DatabaseRef.child("ReadBook").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        UserBook up = ds.getValue(UserBook.class);

                        date.add(ds.getKey());
                        datelist.add(up.getBookname());

                    }

                    if (datelist.contains(str_bookname)){
                        DatabaseRef.child("Review").child(firebaseUser.getUid()).child(temp_bookname).setValue(post);
                        DatabaseRef.child("ReadBook").child(firebaseUser.getUid()).child(date.get(datelist.indexOf(temp_bookname)).toString()).setValue(bookinfo);

                        Toast.makeText(LibraryDialog.this, "도서 정보가 변경되었습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LibraryDialog.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LibraryDialog.this, "도서명은 동일하여야 합니다.", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}