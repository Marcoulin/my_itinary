package com.example.my_itinary;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.example.my_itinary.schema.Circuit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
    String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiZmFobGV1bmciLCJhIjoiY2tpZjVwMzV0MTVrejJzcnNleGcwZzd1byJ9.9iL1X5kkiKOqLInFZF51zA";
    private static final int GALLERY_REQUEST_CODE = 123;
    TextView txt1, txt2, txt3;
    EditText city, country;
    Button adresse1, adresse2, adresse3;

    private Uri imageData;
    private StorageReference storageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference fileReference;
    private CollectionReference circuitRef = db.collection("Circuit");



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
                addCircuit(v);
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

            Toast.makeText(getActivity(),
                    imageData.toString(), Toast.LENGTH_LONG).show();

        }


    }

    public void addCircuit(final View v)
    {

        String postAdress = txt1.getText().toString() + ":" + txt2.getText().toString() + ":"+ txt3.getText().toString();
        String postCity = city.getText().toString();
        String postCountry = country.getText().toString();
        String [] adressSplit = postAdress.split(":");

        if(!imageData.toString().isEmpty())
        {
            fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageData));
            fileReference.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Circuit circuit = new Circuit(adressSplit[0], adressSplit[1], adressSplit[2], postCity, postCountry, uri.toString(), "Fab" );
                            circuitRef.add(circuit);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
