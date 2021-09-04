package com.cs426.imageetranslation.activity.image;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cs426.imageetranslation.adapter.ImageTabsPagerAdapter;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.helper.GlobalState;
import com.google.android.material.tabs.TabLayout;

public class GetImageTabsActivity extends AppCompatActivity {
    int type;
    boolean firstChangeTab;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_tab_content);
        GlobalState.tabScreens = 0;
        prepareTabsLayout(tabLayout);

        Intent intent = getIntent();
        type = intent.getIntExtra("profile",-1);

        final ImageTabsPagerAdapter adapter = new ImageTabsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        if(type == 1) {
            TabLayout.Tab tab_1 = tabLayout.getTabAt(0);
            TabLayout.Tab tab_2 = tabLayout.getTabAt(1);
            tab_1.getIcon().setColorFilter(getResources().getColor(R.color.unselected_tab), PorterDuff.Mode.SRC_IN);
            tab_2.getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_IN);
            firstChangeTab = true;
            viewPager.setCurrentItem(1);
        }

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.unselected_tab), PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        if(type == 1 && firstChangeTab == true) {
                            viewPager.setCurrentItem(tab.getPosition());
                            tab.getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_IN);
                            TabLayout.Tab tab_2 = tabLayout.getTabAt(1);
                            tab_2.getIcon().setColorFilter(getResources().getColor(R.color.unselected_tab), PorterDuff.Mode.SRC_IN);
                            firstChangeTab = false;
                        }
                    }
                });


    }

    private void prepareTabsLayout(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.camera_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.profile_icon));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

}
