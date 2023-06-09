package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    private LinearLayoutCompat baseLayout;
    private Button btnBackgroundChange, btnChangeFont;
    private TextView NickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    public void init(){
        baseLayout = (LinearLayoutCompat)findViewById(R.id.baseLayout);
        btnBackgroundChange = findViewById(R.id.btnBackgroundChange2);
        registerForContextMenu(btnBackgroundChange);

        btnChangeFont = findViewById(R.id.btnChangeFont2);
        registerForContextMenu(btnChangeFont);

        NickName = findViewById(R.id.NickName);
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
                NickName.setTypeface(typeFace1);
                return true;

            case R.id.Gangwongyoyungmodulight:
                Typeface typeFace2 = Typeface.createFromAsset(getAssets(),"강원교육모두 light.ttf");
                NickName.setTypeface(typeFace2);
                return true;
            case R.id.Gangwongyoyuksaeom:
                Typeface typeFace3 = Typeface.createFromAsset(getAssets(),"강원교육새음.ttf");
                NickName.setTypeface(typeFace3);
                return true;
            case R.id.Gangwongyoyukteunteun:
                Typeface typeFace4 = Typeface.createFromAsset(getAssets(),"강원교육튼튼.ttf");
                NickName.setTypeface(typeFace4);
                return true;
            case R.id.Gangwongyoyukyeonoksaem:
                Typeface typeFace5 = Typeface.createFromAsset(getAssets(),"강원교육현옥샘.ttf");
                NickName.setTypeface(typeFace5);
                return true;
        }
        return false;
    }
}