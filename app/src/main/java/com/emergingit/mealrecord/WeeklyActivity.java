package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class WeeklyActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterClass adapter;
    LinearLayoutManager layoutManager;
    List<TestClass> testCases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        init();
    }

    private void init() {
        makeDemoList();
        putDatasOnRecycleView();
    }

    private void makeDemoList() {
        testCases.add(new TestClass("Moin", "Nikunja-2", "Dreamer"));
        testCases.add(new TestClass("Nihal", "Nikunja-2", "Boss"));
        testCases.add(new TestClass("Messi", "Paris", "Dream Chaser"));
        testCases.add(new TestClass("C.Ronaldo", "Rome", "Farmer"));
        testCases.add(new TestClass("Taylor Swift", "California", "Beign a Crush"));
    }

    private void putDatasOnRecycleView() {
        recyclerView = findViewById(R.id.reclmealViewes);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterClass(testCases);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}