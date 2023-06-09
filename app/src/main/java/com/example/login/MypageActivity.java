package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MypageActivity extends AppCompatActivity implements ChangeDialog.ChageDialogInterface {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;
    private LinearLayoutCompat baseLayout;
    private Button btnBackgroundChange, btnChangeFont;
    private TextView NickName, uid, uname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        init();

        Button home = findViewById(R.id.btn_home_mypage);
        Button change = findViewById(R.id.btn_changeid);

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );

        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        DatabaseRef.child("User").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);

                String id = user.getEmailId();
                String name = user.getUsername();

                //TextView uid = (TextView) findViewById(R.id.tv_uid);
                //TextView uname = (TextView) findViewById(R.id.tv_uname);
                uid.setText(id);
                uname.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDialog f = new ChangeDialog();
                f.show(getSupportFragmentManager(), "개인정보변경");
            }
        });
    }

    public void init(){
        baseLayout = (LinearLayoutCompat)findViewById(R.id.baseLayout2);
        btnBackgroundChange = findViewById(R.id.btnBackgroundChange2);
        registerForContextMenu(btnBackgroundChange);

        btnChangeFont = findViewById(R.id.btnChangeFont2);
        registerForContextMenu(btnChangeFont);

        //NickName = findViewById(R.id.NickName);
        uid = findViewById(R.id.tv_uid);
        uname = findViewById(R.id.tv_uname);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater mInflater = getMenuInflater();
        if(v == btnBackgroundChange){
            menu.setHeaderTitle("배경색 변경");
            mInflater.inflate(R.menu.chang_color, menu);
        }
        if(v == btnChangeFont){
            menu.setHeaderTitle("폰트 변경");
            mInflater.inflate(R.menu.change_font, menu);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemWhite:
                baseLayout.setBackgroundColor(Color.WHITE);
                return true;
            case R.id.itemGreen:
                baseLayout.setBackgroundColor(Color.GREEN);
                return true;
            case R.id.itemBlue:
                baseLayout.setBackgroundColor(Color.BLUE);
                return true;

            case R.id.Gangwongyoyungmodubold:
                Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "강원교육모두 bold.ttf");
                uid.setTypeface(typeFace1);
                uname.setTypeface(typeFace1);
                return true;

            case R.id.Gangwongyoyungmodulight:
                Typeface typeFace2 = Typeface.createFromAsset(getAssets(),"강원교육모두 light.ttf");
                uid.setTypeface(typeFace2);
                uname.setTypeface(typeFace2);
                return true;
            case R.id.Gangwongyoyuksaeom:
                Typeface typeFace3 = Typeface.createFromAsset(getAssets(),"강원교육새음.ttf");
                uid.setTypeface(typeFace3);
                uname.setTypeface(typeFace3);
                return true;
            case R.id.Gangwongyoyukteunteun:
                Typeface typeFace4 = Typeface.createFromAsset(getAssets(),"강원교육튼튼.ttf");
                uid.setTypeface(typeFace4);
                uname.setTypeface(typeFace4);
                return true;
            case R.id.Gangwongyoyukyeonoksaem:
                Typeface typeFace5 = Typeface.createFromAsset(getAssets(),"강원교육현옥샘.ttf");
                uid.setTypeface(typeFace5);
                uname.setTypeface(typeFace5);
                return true;
        }
        return false;
    }


    @Override
    public void applyTexts(String stringone) {
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        if (stringone.toString() == "" ){
            Toast.makeText(MypageActivity.this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MypageActivity.this, "회원정보가 변경되었습니다.", Toast.LENGTH_SHORT).show();
            DatabaseRef.child("User").child(firebaseUser.getUid()).child("username").setValue(stringone);
        }

    }
}