package com.example.simpletodo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// responsible for displaying data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    // defining member variable to be used in all methods
    List<String> items;
    OnLongClickListener longClickListener;

    // make a constructor (function): right-click -> generate -> constructor
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items; // set member variable to variable passed into constructor
        this.longClickListener = longClickListener;
    }

    // responsible for creating each view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // use layout inflator to inflate a view
        View todoView = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);

        // wrap inside a vie holder and return
        return new ViewHolder(todoView);
    }

    // responsible for taking data at a position and putting it into a view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        // gram the item at the position
        String item = items.get(i);

        // bind the item into the specified view holder
        viewHolder.bind(item);
    }

    // number of items available in data
    @Override
    public int getItemCount() {
        return items.size();
    }
    // Container to provide easy access to views that represent each row of the list

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem; // define text view

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // notify listener which item was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
