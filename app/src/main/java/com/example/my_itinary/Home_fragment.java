package com.example.my_itinary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        // Inflate the layout for this fragment
        ArrayList<Item_template> exList = new ArrayList<>();
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));
        exList.add(new Item_template("Line 1"));

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new ExampleAdapter(exList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
}
