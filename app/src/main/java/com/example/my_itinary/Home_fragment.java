package com.example.my_itinary;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my_itinary.schema.Circuit;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference cirRef = db.collection("Circuit");

    private CircuitAdapter adapter;

    public static final String EXTRA_CITY = "com.example.my_itinary.EXTRA_CITY";
    public static final String EXTRA_COUNTRY = "com.example.my_itinary.EXTRA_COUNTRY";
    public static final String EXTRA_ID = "com.example.my_itinary.EXTRA_ID";
    public static final String EXTRA_IMG = "com.example.my_itinary.IMG";
    public static final String EXTRA_ADRESS_1 = "com.example.my_itinary.ADRESS_1";
    public static final String EXTRA_ADRESS_2 = "com.example.my_itinary.ADRESS_2";
    public static final String EXTRA_ADRESS_3 = "com.example.my_itinary.ADRESS_3";

    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        setupRecyclerView(v, cirRef);
        if (getArguments() != null) {
            filter(getArguments(), v);
        }
        adapter.setOnItemClickListener(new CircuitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Circuit circuit = documentSnapshot.toObject(Circuit.class);
                toDescPage(circuit);
            }
        });
        return v;
    }


    private void toDescPage(Circuit circuit) {

        Intent intent = new Intent(getActivity(), Description_activity.class);
        intent.putExtra(EXTRA_CITY, circuit.getCity());
        intent.putExtra(EXTRA_COUNTRY, circuit.getCountry());
        intent.putExtra(EXTRA_ID, mAuth.getUid());
        intent.putExtra(EXTRA_IMG, circuit.getPicture());
        intent.putExtra(EXTRA_ADRESS_1, circuit.getAdresse1());
        intent.putExtra(EXTRA_ADRESS_2, circuit.getAdresse2());
        intent.putExtra(EXTRA_ADRESS_3, circuit.getAdresse3());

        Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
        startActivity(intent, bundle);


    }

    private void filter(Bundle arguments, View v) {
        FirestoreRecyclerOptions<Circuit> options;
        Query query;
        ArrayList<String> list = arguments.getStringArrayList("Id");
        query = cirRef.whereIn(FieldPath.documentId(), list);
        setupRecyclerView(v, query);
    }

    private void setupRecyclerView(View v, Query q) {
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
