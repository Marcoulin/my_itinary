package com.example.my_itinary;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
/**
 * A simple {@link Fragment} subclass.
 */
public class Post_fragment extends Fragment {
    Database database = Database.getInstance();
    Button postBtn;
    ImageView postPic;
    FloatingActionButton picBtn;
    private static final int GALLERY_REQUEST_CODE = 123;
    TextView txt1, txt2, txt3;
    EditText city, country;
    Button adresse1, adresse2, adresse3;
    private final String MAPBOX = "pk.eyJ1IjoiZmFobGV1bmciLCJhIjoiY2tpZjVwMzV0MTVrejJzcnNleGcwZzd1byJ9.9iL1X5kkiKOqLInFZF51zA";

    private Uri imageData;
    private StorageReference storageRef;



    public Post_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);

        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        init(v);

        adresse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt1 = v.findViewById(R.id.adress1Txt);
                setAdress(txt1, savedInstanceState);
                adresse1.setVisibility(View.INVISIBLE);
            }
        });

        adresse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt2 = v.findViewById(R.id.adress2Txt);
                setAdress(txt2, savedInstanceState);
                adresse2.setVisibility(View.INVISIBLE);
            }
        });

        adresse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt3 = v.findViewById(R.id.adress3Txt);
                setAdress(txt3, savedInstanceState);
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
                addCircuit();
                postBtn.setVisibility(View.VISIBLE);
                v.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }
        });

        return v;
    }



    private void setAdress(TextView txt, Bundle savedInstanceState) {

        PlaceAutocompleteFragment autocompleteFragment;
        if (savedInstanceState == null) {
            autocompleteFragment = PlaceAutocompleteFragment.newInstance(MAPBOX);

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



    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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

            YoYo.with(Techniques.FadeIn)
                    .duration(2500)
                    .playOn(postPic);
            Picasso.get().load(imageData).into(postPic);
            postPic.setVisibility(View.VISIBLE);


        }


    }

    public void addCircuit()
    {

        String postAdress = txt1.getText().toString() + ":" + txt2.getText().toString() + ":"+ txt3.getText().toString();
        String postCity = city.getText().toString();
        String postCountry = country.getText().toString();
        String [] adressSplit = postAdress.split(":");

        if(!imageData.toString().isEmpty())
        {
            storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageData));
            database.insertCircuit(postCity, postCountry, adressSplit, storageRef, imageData);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Home_fragment home_fragment = new Home_fragment();

        fragmentTransaction.replace(R.id.fragment_container, home_fragment)
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                ).commit();
    }

    private void init(View v)
    {
        city = v.findViewById(R.id.cityTxt);
        country = v.findViewById(R.id.countryTxt);
        adresse1 = v.findViewById(R.id.adress1);
        adresse2 = v.findViewById(R.id.adress2);
        adresse3 = v.findViewById(R.id.adress3);
        postPic = v.findViewById(R.id.picPost);
        postBtn = v.findViewById(R.id.circuitBtn);
        picBtn = v.findViewById(R.id.picBtn);

    }
}
