package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersRankActivity extends AppCompatActivity {

    TextView firstname, secondname, thirdname;
    TextView firstcnt, seconndcnt, thirdcnt;

    List<String> Userslist = new ArrayList<String>();
    List<String> Usersdate = new ArrayList<String>();
    List<String> Templist = new ArrayList<String>();
    static List<Integer> Temp2 = new ArrayList<Integer>();

    List<String> newname = new ArrayList<String>();
    List<Integer> newcount = new ArrayList<Integer>();

    Integer max;
    Integer first;
    Integer second;

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_rank);

        firstname = findViewById(R.id.tv_firstname);
        secondname = findViewById(R.id.tv_secondname);
        thirdname = findViewById(R.id.tv_thirdname);

        firstcnt = findViewById(R.id.tv_firstbookcnt);
        seconndcnt = findViewById(R.id.tv_secondbookcnt);
        thirdcnt = findViewById(R.id.tv_thirdbookcnt);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        DatabaseRef.child("ReadBook").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Userslist.add(ds.getKey());
                }
                for(int i =0; i < Userslist.size(); i ++){
                    DatabaseRef.child("ReadBook").child(Userslist.get(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                Usersdate.add(ds.getKey());
                                Templist.add(ds.getValue().toString());
                            }
                            int count = Templist.size();
                            Temp2.add(count);
                            Templist.clear();
                            max = Collections.max(Temp2);

                            first = Temp2.get(0);
                            second = Integer.MIN_VALUE;
                            for(int i=1; i<Temp2.size(); i++) {
                                if(first < Temp2.get(i)){
                                    second = first;
                                    first = Temp2.get(i);
                                }
                                else if(Temp2.get(i) != first && second <Temp2.get(i)) {
                                    second = Temp2.get(i);
                                }
                            }
//                            if(second == Integer.MIN_VALUE) {
//                                System.out.println("Does not exist");
//                            }

                            DatabaseRef.child("User").child(Userslist.get(Temp2.indexOf(first))).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    UserAccount user = snapshot.getValue(UserAccount.class);
                                    String name = user.getUsername();
                                    firstname.setText(name);
                                    firstcnt.setText(first.toString());

                                    DatabaseRef.child("User").child(Userslist.get(Temp2.indexOf(second))).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            UserAccount user = snapshot.getValue(UserAccount.class);
                                            String name = user.getUsername();
                                            secondname.setText(name);
                                            seconndcnt.setText(second.toString());

                                            for (int i = 0; i < Temp2.size(); i++) {
                                                newcount.clear();
                                                for (int j = 0; j < Temp2.size(); j++) {
                                                    newcount.add(Temp2.get(j));
                                                    Collections.sort(newcount, Collections.reverseOrder());
                                                }

                                                DatabaseRef.child("User").child(Userslist.get(Temp2.indexOf(newcount.get(2)))).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        UserAccount user = snapshot.getValue(UserAccount.class);
                                                        String name = user.getUsername();
                                                        thirdname.setText(name);
                                                        thirdcnt.setText(newcount.get(2).toString());
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


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
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
    }
}