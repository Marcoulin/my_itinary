package com.example.my_itinary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
/**
 * A simple {@link Fragment} subclass.
 */
public class Post_fragment extends Fragment {
    Database database = Database.getInstance();
    Button postBtn;
    ImageView picBtn;
    Uri imageData;
    String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiZmFobGV1bmciLCJhIjoiY2tpZjVwMzV0MTVrejJzcnNleGcwZzd1byJ9.9iL1X5kkiKOqLInFZF51zA";
    private static final int GALLERY_REQUEST_CODE = 123;
    TextView txt;
    EditText city, country;
    Button adresse1, adresse2, adresse3;


    public Post_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);

        init(v);

        adresse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt = v.findViewById(R.id.adress1Txt);
                setAdress(txt, savedInstanceState);
                adresse1.setVisibility(View.INVISIBLE);
            }
        });

        adresse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt = v.findViewById(R.id.adress2Txt);
                setAdress(txt, savedInstanceState);
                adresse2.setVisibility(View.INVISIBLE);
            }
        });

        adresse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt = v.findViewById(R.id.adress3Txt);
                setAdress(txt, savedInstanceState);
                adresse3.setVisibility(View.INVISIBLE);
            }
        });

        picBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromAlbum();
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("id", ""+Preferences.read("ID", null));
                database.insertCircuit(country.getText().toString(),city.getText().toString(),adresse1.getText().toString(),adresse2.getText().toString(),adresse3.getText().toString(),null,Preferences.read("ID", null));
            }
        });

        return v;
    }



    private void setAdress(TextView txt, Bundle savedInstanceState) {

        PlaceAutocompleteFragment autocompleteFragment;
        if (savedInstanceState == null) {
            autocompleteFragment = PlaceAutocompleteFragment.newInstance(MAPBOX_ACCESS_TOKEN);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, autocompleteFragment, TAG);
            transaction.addToBackStack(null).commit();

        } else {
            autocompleteFragment = (PlaceAutocompleteFragment)
                    getActivity().getSupportFragmentManager().findFragmentByTag(TAG);
        }


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(CarmenFeature carmenFeature) {
                Log.v("placename",""+carmenFeature.placeName());
                txt.setText(carmenFeature.placeName());
                txt.setVisibility(View.VISIBLE);
                getActivity().onBackPressed();
            }

            @Override
            public void onCancel() {
                getActivity().onBackPressed();

            }
        });
    }

    private void getImageFromAlbum() {
        try{
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(i.ACTION_GET_CONTENT);
            startActivityForResult(i.createChooser(i, "Pick an image"), GALLERY_REQUEST_CODE);

        }catch(Exception exp){
            Log.i("Error",exp.toString());
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            imageData = data.getData();

            Toast.makeText(getActivity(),
                    imageData.toString(), Toast.LENGTH_LONG).show();

        }


    }

    private void init(View v)
    {
        city = v.findViewById(R.id.cityTxt);
        country = v.findViewById(R.id.countryTxt);
        adresse1 = v.findViewById(R.id.adress1);
        adresse2 = v.findViewById(R.id.adress2);
        adresse3 = v.findViewById(R.id.adress3);
        picBtn = v.findViewById(R.id.picBtn);
        postBtn = v.findViewById(R.id.postBtn);

    }
}
