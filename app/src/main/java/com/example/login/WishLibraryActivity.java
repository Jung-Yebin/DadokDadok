package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WishLibraryActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;
    private ListView list;
    Button home, register_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_library);

        home = findViewById(R.id.btn_wishtohome);
        register_book = findViewById(R.id.btn_wishbookregister);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        ArrayList<String> booknamelist = new ArrayList<>();
        //ArrayList<String> bookreviewlist = new ArrayList<>();
        ArrayList<String> Authorlist = new ArrayList<>();
        ArrayList<String> punisherlist = new ArrayList<>();
        ArrayList<String> punishedatelist = new ArrayList<>();
        ArrayList<String> genrelist = new ArrayList<>();


        list = (ListView)findViewById(R.id.wishlist);

        ArrayAdapter<String> bookname_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, booknamelist);
        ArrayAdapter<String> Author_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Authorlist);
        ArrayAdapter<String> punisher_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, punisherlist);
        ArrayAdapter<String> punishedate_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, punishedatelist);
        ArrayAdapter<String> genre_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genrelist);
        //ArrayAdapter<String> bookreview_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookreviewlist);

        DatabaseRef.child("WishBook").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    UserBook up = ds.getValue(UserBook.class);
                    //Userpost up = ds.getValue(Userpost.class);

                    booknamelist.add(up.getBookname());
                    Authorlist.add(up.getAuthor());
                    punisherlist.add(up.getPunisher());
                    punishedatelist.add(up.getPunishdate());
                    genrelist.add(up.getGenre());
                    //bookreviewlist.add(up.getBookreview());
                }

                list.setAdapter(bookname_adapter);
                bookname_adapter.notifyDataSetChanged();
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(WishLibraryActivity.this, WishDialog.class);
                        String input_bookname = bookname_adapter.getItem(i).toString();
                        String input_author = Author_adapter.getItem(i).toString();
                        String input_punisher = punisher_adapter.getItem(i).toString();
                        String input_punishdate = punishedate_adapter.getItem(i).toString();
                        String input_genre = genre_adapter.getItem(i).toString();

                        //String input_bookreview = bookreview_adapter.getItem(i).toString();
                        intent.putExtra("bookname", input_bookname);
                        intent.putExtra("author",input_author);
                        intent.putExtra("punisher", input_punisher);
                        intent.putExtra("punishdate",input_punishdate);
                        intent.putExtra("genre", input_genre);

                        startActivity(intent);
                        //Toast.makeText(LibraryActivity.this,  adapter.getItem(i) + "클릭" + bookreview_adapter.getItem(i), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        register_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishLibraryActivity.this, WishBookRegisterActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishLibraryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}