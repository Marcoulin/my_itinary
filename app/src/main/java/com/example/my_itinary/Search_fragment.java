package com.example.my_itinary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.my_itinary.schema.Circuit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search_fragment extends Fragment {

    Spinner spinnerCountry, spinnerCity;
    ArrayList<String> countries;
    ArrayList<String> cities;
    Button searchButton;
    ArrayList<String> circuitId;
    String country ="";
    String city="";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference circuitRef = db.collection("Circuit");

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

    public void init(View v) {
        spinnerCountry = v.findViewById(R.id.spinner);
        spinnerCity = v.findViewById(R.id._dynamic);
        searchButton = v.findViewById(R.id.searchButton);

        countries = new ArrayList<>();
        cities = new ArrayList<>();

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(spinnerCity);
        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(spinnerCountry);



        Database.getInstance().getCountries(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!countries.contains(document.toObject(Circuit.class).getCountry())) {
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
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            country = adapterView.getSelectedItem().toString();
                            Database.getInstance().getCities(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        cities.clear();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (!cities.contains(document.toObject(Circuit.class).getCity())) {
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
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                                                city = adapterView.getSelectedItem().toString();
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
                } else {
                    Log.v("Error", "Couldn't query countries");
                }
            }
        });
        circuitId = new ArrayList<>();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                circuitRef.whereEqualTo("country", country)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Log.v("SUCCES", "Entering to queries;" + country);
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                                    Circuit circuit = document.toObject(Circuit.class);
                                    circuitId.add(document.getId());
                                    //Log.v("SUCCES", circuitId.size() + ";");
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("City", city);
                                bundle.putString("Country", country);
                                bundle.putStringArrayList("Id", circuitId);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Home_fragment home_fragment = new Home_fragment();
                                home_fragment.setArguments(bundle);

                                fragmentTransaction.replace(R.id.fragment_container, home_fragment)
                                        .setCustomAnimations(
                                                R.anim.slide_in_left,
                                                R.anim.slide_out_right,
                                                R.anim.slide_in_right,
                                                R.anim.slide_out_left
                                        ).commit();


                            }

                        });
            }
        });


    }


}
