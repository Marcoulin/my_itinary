package com.example.my_itinary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Description_activity extends AppCompatActivity {
    private TextView country; 
    private TextView city; 
    private TextView user; 
    private TextView adress1; 
    private TextView adress2; 
    private TextView adress3; 
    private ImageView picImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_description);
        
        final Intent intent = getIntent(); 
        
        setDescription(intent); 
        super.onCreate(savedInstanceState);
    }

    private void setDescription(Intent intent) {
        String get_city = intent.getStringExtra(Home_fragment.EXTRA_CITY);
        String get_country = intent.getStringExtra(Home_fragment.EXTRA_COUNTRY);
        String get_ad1 = intent.getStringExtra(Home_fragment.EXTRA_ADRESS_1);
        String get_ad2 = intent.getStringExtra(Home_fragment.EXTRA_ADRESS_2);
        String get_ad3 = intent.getStringExtra(Home_fragment.EXTRA_ADRESS_3);
        String get_img = intent.getStringExtra(Home_fragment.EXTRA_IMG);

        String [] splitad1 = get_ad1.split(",");
        String [] splitad2 = get_ad2.split(",");
        String [] splitad3 = get_ad3.split(",");



        country = findViewById(R.id.desc_country);
        city = findViewById(R.id.desc_city);
        adress1 = findViewById(R.id.desc_adress1);
        adress2 = findViewById(R.id.desc_adress2);
        adress3 = findViewById(R.id.desc_adress3);
        picImage = findViewById(R.id.des_image);

        Picasso.get().load(get_img).into(picImage);

        country.setText(get_country);
        city.setText(get_city);
        adress1.setText(splitad1[0]);
        adress2.setText(splitad2[0]);
        adress3.setText(splitad3[0]);

    }
}