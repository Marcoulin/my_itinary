package com.example.my_itinary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class Description_activity extends AppCompatActivity {
    private TextView country, city, adress1, adress2, adress3;
    private ImageView picImage;
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
        postBtn = findViewById(R.id.postBtn);

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