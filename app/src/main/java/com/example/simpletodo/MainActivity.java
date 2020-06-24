package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items; // the to-do list is stored as a list of strings

    // add each item as a member variable so we can add appropriate logic later
    Button addButton;
    EditText etItem;
    RecyclerView rvItem;
    ItemsAdapter itemsAdapter;

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

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {

            @Override
            public void onItemLongClicked(int position) {
                // delete item from the model
                String todoItem = items.remove(position);

                // notify adapter
                itemsAdapter.notifyItemRemoved(position);

                // show confirmation message that text was removed
                Toast.makeText(getApplicationContext(), "'" + todoItem + "'" + " was removed :)", Toast.LENGTH_SHORT).show();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = etItem.getText().toString();
                // add item to model
                items.add(todoItem);

                // notify that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);

                // show confirmation message that text was added
                Toast.makeText(getApplicationContext(), "'" + todoItem + "'" + " was added :)", Toast.LENGTH_SHORT).show();

                etItem.setText(""); // clear text once submitted
            }
        });
    }
}