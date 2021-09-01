package com.cs426.imageetranslation.activity.slider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.R;

public class SliderActivity extends AppCompatActivity implements View.OnClickListener{
    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.logo, R.drawable.logoo, R.drawable.logo, R.drawable.logoo};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitity_slider_intro);

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(SliderActivity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        com.google.android.material.floatingactionbutton.FloatingActionButton btnNext = findViewById(R.id.btnNextScreen);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNextScreen: {
                startActivity(new Intent(this, GetImageTabsActivity.class));
                break;
            }
        }
    }
}