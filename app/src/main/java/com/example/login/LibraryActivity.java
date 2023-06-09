package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class LibraryActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;
    private ListView list;

    ArrayList<Userpost> userposts;
    ArrayList<UserBook> userBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Button home = findViewById(R.id.btn_wishtohome);
        Button readbookregister = findViewById(R.id.btn_readbookregister);
        ArrayList<String> booknamelist = new ArrayList<>();
        //ArrayList<String> bookreviewlist = new ArrayList<>();
        ArrayList<String> Authorlist = new ArrayList<>();
        ArrayList<String> punisherlist = new ArrayList<>();
        ArrayList<String> punishedatelist = new ArrayList<>();
        ArrayList<String> genrelist = new ArrayList<>();
        //TextView result = (TextView)findViewById(R.id.re);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();


        list = (ListView)findViewById(R.id.list);

        //List<String> data = new ArrayList<>();

        ArrayAdapter<String> bookname_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, booknamelist);
        ArrayAdapter<String> Author_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Authorlist);
        ArrayAdapter<String> punisher_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, punisherlist);
        ArrayAdapter<String> punishedate_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, punishedatelist);
        ArrayAdapter<String> genre_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genrelist);
        //ArrayAdapter<String> bookreview_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookreviewlist);

        DatabaseRef.child("ReadBook").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
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
                //result.setText(booknamelist.toString());
                int bookcount = 0;
                bookcount = booknamelist.size();

                list.setAdapter(bookname_adapter);
                bookname_adapter.notifyDataSetChanged();
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(LibraryActivity.this, LibraryDialog.class);
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        readbookregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, BookRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        return super.onOptionsItemSelected(item);
    }

}