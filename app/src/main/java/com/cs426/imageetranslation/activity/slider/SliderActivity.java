package com.cs426.imageetranslation.activity.slider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.R;

public class SliderActivity extends AppCompatActivity implements View.OnClickListener{
    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitity_slider_intro);

        com.google.android.material.floatingactionbutton.FloatingActionButton btnNext = findViewById(R.id.btnNextScreen);
        btnNext.setOnClickListener(this);

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);

        sliderDotsPanel = (LinearLayout)findViewById(R.id.SliderDots);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(SliderActivity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        dotsCount = mViewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for(int i=0; i < dotsCount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);
            sliderDotsPanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i=0; i<dotsCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active));
                if (position == images.length-1){
                    btnNext.setVisibility(View.VISIBLE);
                }else{
                    btnNext.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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