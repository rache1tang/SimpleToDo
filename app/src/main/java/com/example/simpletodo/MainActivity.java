package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items; // the to-do list is stored as a list of strings

    // add each item as a member variable so we can add appropriate logic later
    Button addButton;
    EditText etItem;
    RecyclerView rvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define member variables with the id they are associated with
        addButton = findViewById(R.id.addButton);
        etItem = findViewById(R.id.etItem);
        rvItem = findViewById(R.id.rvItems);

        items = new ArrayList<>();
        items.add("Buy Milk");
        items.add("Take out Trash");
        items.add("Hello");

        ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));
    }
}