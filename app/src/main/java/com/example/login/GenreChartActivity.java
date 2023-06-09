package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenreChartActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseRef;
    List<String> genre_list = new ArrayList<String>();
    List<Integer> genre_count = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_chart);

        PieChart pieChart = findViewById(R.id.Genre_PieChart);

        ArrayList<PieEntry> genre = new ArrayList<>();

        // 데이터베이스에서 값 받아오기

        FirebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference(
                "software"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getCurrentUser();

        DatabaseRef.child("ReadBook").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    UserBook up = ds.getValue(UserBook.class);
                    genre_list.add(up.getGenre());
                }

                for (int i = 0; i < genre_list.size(); i++){
                    System.out.println(genre_list.get(i));
                }

                for (int i = 0; i < genre_list.size(); i++){
                    genre_count.add(Collections.frequency(genre_list, genre_list.get(i)));
                }

                HashMap<String, Integer> genre_map = new HashMap<>();
                for(int i = 0; i < genre_list.size();i++){
                    genre_map.put(genre_list.get(i), genre_count.get(i));
                }

                for (Map.Entry<String, Integer> pair : genre_map.entrySet()) {
                    genre.add(new PieEntry(pair.getValue(), pair.getKey()));
                }

                PieDataSet pieDataSet = new PieDataSet(genre, "장르");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(16f);

                PieData pieDAta = new PieData(pieDataSet);

                pieChart.setData(pieDAta);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("읽은 책 분포");
                pieChart.animate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}