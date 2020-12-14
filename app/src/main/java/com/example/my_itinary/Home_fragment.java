package com.example.my_itinary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my_itinary.schema.Circuit;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference cirRef = db.collection("Circuit");

    private CircuitAdapter adapter; 

    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        setupRecyclerView(v);
        return v;
    }

    private void setupRecyclerView(View v) {
        Query q = cirRef.orderBy("username");
        FirestoreRecyclerOptions<Circuit> options = new FirestoreRecyclerOptions.Builder<Circuit>()
                .setQuery(q, Circuit.class)
                .build();

        adapter = new CircuitAdapter(options, getContext());

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
