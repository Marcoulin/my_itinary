package com.example.my_itinary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Description_activity extends AppCompatActivity {
    private TextView country; 
    private TextView city; 
    private TextView user; 
    private TextView adress1, adress2, adress3;
    private ImageView picImage;
    private String MAPBOX = "pk.eyJ1IjoiZmFobGV1bmciLCJhIjoiY2tpZjVwMzV0MTVrejJzcnNleGcwZzd1byJ9.9iL1X5kkiKOqLInFZF51zA";
    private Point point1, point2, point3;
    private FloatingActionButton f1, f2, f3;
    private ImageView i1, i2, i3;
    private Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_description);
        init();

        final Intent intent = getIntent();

        setDescription(intent);
        super.onCreate(savedInstanceState);
        f1.setOnClickListener(view -> {
            showLocation(adress1.getText().toString());

        });
        f2.setOnClickListener(view -> {
            showLocation(adress2.getText().toString());
           /* Fragment gmap = new fragment_maps_add(adress2.getText().toString());
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, gmap).commit();*/
        });
        f3.setOnClickListener(view -> {
            showLocation(adress3.getText().toString());
           /* Fragment gmap = new fragment_maps_add(adress3.getText().toString());
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, gmap).commit();*/
        });
        postBtn.setOnClickListener(view -> {
            drawCircuit();
        });
    }

    private void showLocation(String address)
    {
        MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken(MAPBOX)
                .query(address)
                .build();

        mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                List<CarmenFeature> results = response.body().features();

                if (results.size() > 0) {

                    // Log the first results Point.
                    Point firstResultPoint = results.get(0).center();
                    Log.v("TAG", "onResponse: " + firstResultPoint.toString());
                    Fragment gmap = new fragment_maps_add(firstResultPoint);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, gmap).commit();

                } else {

                    // No result for your request were found.
                    Log.d("TAG", "onResponse: No result found");
                    Toast.makeText(getApplicationContext(), "Location error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private void drawCircuit()
    {
        MapboxGeocoding client = MapboxGeocoding.builder()
                .accessToken(MAPBOX)
                .query(adress1.getText().toString())
                .build();

        client.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                List<CarmenFeature> results1 = response.body().features();

                if (results1.size() > 0) {
                    point1 = results1.get(0).center();
                    MapboxGeocoding client2 = MapboxGeocoding.builder().accessToken(MAPBOX).query(adress2.getText().toString()).build();
                    client2.enqueueCall(new Callback<GeocodingResponse>() {
                        @Override
                        public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                            List<CarmenFeature> results2 = response.body().features();
                            if(results2.size() > 0)
                            {
                                point2 = results2.get(0).center();
                                MapboxGeocoding client3 = MapboxGeocoding.builder().accessToken(MAPBOX).query(adress3.getText().toString()).build();
                                client3.enqueueCall(new Callback<GeocodingResponse>() {
                                    @Override
                                    public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                                        List<CarmenFeature> results3 = response.body().features();
                                        if(results3.size() > 0)
                                        {
                                            point3 = results3.get(0).center();
                                            Fragment gmap = new fragment_maps_add(point1,point2,point3);
                                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, gmap).commit();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Location error", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GeocodingResponse> call, Throwable t) {

                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Location error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GeocodingResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Location error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    private void init() {

        country = findViewById(R.id.desc_country);
        city = findViewById(R.id.desc_city);
        adress1 = findViewById(R.id.desc_adress1);
        adress2 = findViewById(R.id.desc_adress2);
        adress3 = findViewById(R.id.desc_adress3);
        picImage = findViewById(R.id.des_image);
        f1 = findViewById(R.id.floatingActionButton1);
        f2 = findViewById(R.id.floatingActionButton2);
        f3 = findViewById(R.id.floatingActionButton3);
        i1 = findViewById(R.id.locIcon1);
        i2 = findViewById(R.id.locIcon2);
        i3 = findViewById(R.id.locIcon3);
        postBtn = findViewById(R.id.circuitBtn);

        makeAnimations();

    }

    private void makeAnimations() {
        animate_(adress1, Techniques.FadeInUp);
        animate_(adress2, Techniques.FadeInUp);
        animate_(adress3, Techniques.FadeInUp);
        animate_(f1, Techniques.FadeInLeft);
        animate_(f2, Techniques.FadeInLeft);
        animate_(f3, Techniques.FadeInLeft);
        animate_(picImage, Techniques.FadeInDown);
        animate_(i1, Techniques.FadeInRight);
        animate_(i2, Techniques.FadeInRight);
        animate_(i3, Techniques.FadeInRight);
        animate_(postBtn, Techniques.FadeInUp);
    }

    private void animate_(View v, Techniques fadeInUp) {
        YoYo.with(Techniques.FadeInUp)
                .duration(3000)
                .playOn(v);
    }

    private void setDescription(Intent intent) {
        String get_city = intent.getStringExtra(Home_fragment.EXTRA_CITY);
        String get_country = intent.getStringExtra(Home_fragment.EXTRA_COUNTRY);
        String get_ad1 = intent.getStringExtra(Home_fragment.EXTRA_ADRESS_1);
        String get_ad2 = intent.getStringExtra(Home_fragment.EXTRA_ADRESS_2);
        String get_ad3 = intent.getStringExtra(Home_fragment.EXTRA_ADRESS_3);
        String get_img = intent.getStringExtra(Home_fragment.EXTRA_IMG);

        String[] splitad1 = get_ad1.split(",");
        String[] splitad2 = get_ad2.split(",");
        String[] splitad3 = get_ad3.split(",");

        Picasso.get().load(get_img).into(picImage);
        country.setText(get_country);
        city.setText(get_city);
        adress1.setText(splitad1[0]);
        adress2.setText(splitad2[0]);
        adress3.setText(splitad3[0]);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}