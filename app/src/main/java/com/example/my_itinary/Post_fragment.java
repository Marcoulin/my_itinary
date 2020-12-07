package com.example.my_itinary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class Post_fragment extends Fragment {
    EditText city, country;
    Button adresse1, adresse2, adresse3;
    public static FragmentManager fragmentManager;
    AutocompleteSupportFragment autocompleteSupportFragment;


    public Post_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);
        init(v);

        autocompleteSupportFragment = (AutocompleteSupportFragment) getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("Place", "Place: " + place.getName() + ", " + place.getId());
            }   
            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                Log.i("Error", "An error occurred: " + status);
            }
        });

        adresse1.setOnClickListener(view -> {
            fragmentManager = getFragmentManager();
            fragment_maps_add fma = new fragment_maps_add(city.getText().toString(), country.getText().toString());
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fma).commit();
        });
        // Inflate the layout for this fragment

        return v;
    }

    private void init(View v)
    {
        city = v.findViewById(R.id.cityTxt);
        country = v.findViewById(R.id.countryTxt);
        adresse1 = v.findViewById(R.id.adress1);
        adresse2 = v.findViewById(R.id.adress2);
        adresse3 = v.findViewById(R.id.adress3);
    }
}
