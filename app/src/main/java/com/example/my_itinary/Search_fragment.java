package com.example.my_itinary;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.my_itinary.schema.Circuit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search_fragment extends Fragment{

    Spinner spinnerCountry, spinnerCity;
    ArrayList<String> countries = new ArrayList();
    ArrayList<String> cities = new ArrayList();
    Button searchButton;

    public Search_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        init(v);



        return v;
    }

    public void init(View v)
    {
        spinnerCountry = v.findViewById(R.id.spinner);
        spinnerCity = v.findViewById(R.id._dynamic);
        searchButton = v.findViewById(R.id.searchButton);

        Database.getInstance().getCountries(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(!countries.contains(document.toObject(Circuit.class).getCountry()))
                        {
                            countries.add(document.toObject(Circuit.class).getCountry());
                        }
                        Log.v("TAG", document.getId() + " => " + document.getData());
                    }
                    //Creating the ArrayAdapter instance having the country list
                    ArrayAdapter aaCountries = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, countries);
                    aaCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    spinnerCountry.setAdapter(aaCountries);
                    spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                        {
                            Database.getInstance().getCities(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(!cities.contains(document.toObject(Circuit.class).getCity()))
                                            {
                                                cities.add(document.toObject(Circuit.class).getCity());
                                            }
                                            Log.v("TAG", document.getId() + " => " + document.getData());
                                        }
                                        ArrayAdapter aaCities = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, cities);
                                        aaCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        //Setting the ArrayAdapter data on the Spinner
                                        spinnerCity.setAdapter(aaCities);
                                        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l)
                                            {
                                                searchButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent intent = new Intent(getContext(), Home_activity.class);
                                                        intent.putExtra("country",countries.get(i));
                                                        intent.putExtra("city", cities.get(j));
                                                        Log.v("country selected ",countries.get(i));
                                                        Log.v("city selected ",cities.get(j));
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });

                                    }
                                }
                            }, countries.get(i));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else
                {
                    Log.v("Error","Couldn't query countries");
                }
            }
        });
    }




}
