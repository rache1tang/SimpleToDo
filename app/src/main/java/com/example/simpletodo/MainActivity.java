package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

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

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {

            @Override
            public void onItemLongClicked(int position) {
                // delete item from the model
                String todoItem = items.remove(position).toString();

                // notify adapter
                itemsAdapter.notifyItemRemoved(position);

                // show confirmation message that text was removed
                Toast.makeText(getApplicationContext(), "'" + todoItem + "'" + " was removed :)", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                // create the new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);

                // pass in data being edited
                // pass data into intent (key[always a string] and value)
                i.putExtra(KEY_ITEM_TEXT, items.get(position)); // store text
                i.putExtra(KEY_ITEM_POSITION, position); // store position

                // display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
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
                saveItems();

            }
        });
    }

    // handle result of edit activity
    protected void onActivityResult(int RequestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && RequestCode == EDIT_TEXT_CODE) {
            // retrieve updated text value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);

            // extract the original position of the edited item
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            // update model
            items.set(position, itemText);

            // notify adapter
            itemsAdapter.notifyItemChanged(position);

            // persist changes
            saveItems();

            // show successful update
            Toast.makeText(getApplicationContext(), "Item changed to " + itemText, Toast.LENGTH_SHORT).show();
        } else {
            Log.w("Main Activity", "unkown call to onActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // read file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // write file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity", "Error writing items", e);
        }
    }
}