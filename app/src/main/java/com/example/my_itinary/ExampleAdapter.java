package com.example.my_itinary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<Item_template> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
        }
    }

    public ExampleAdapter(ArrayList<Item_template> exList)
    {
        mExampleList = exList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Item_template currentItem = mExampleList.get(position);

        holder.mTextView1.setText(currentItem.getmText1());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
