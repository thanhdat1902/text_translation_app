package com.cs426.imageetranslation.activity.translation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.adapter.TranslationPagerAdapter;
import com.cs426.imageetranslation.helper.GlobalState;
import com.google.android.material.tabs.TabLayout;

public class TranslationTabsActivity extends AppCompatActivity {
    int type; //check if this activity start from ChangePwdActivity
    boolean firstChangeTab = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_tab_content);

        Intent intent = getIntent();
        type = intent.getIntExtra("profile",-1);

        prepareTabsLayout(tabLayout);
        GlobalState.tabScreens = 1;
        final TranslationPagerAdapter adapter = new TranslationPagerAdapter
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
                        if(type == 1 && firstChangeTab == true && tab.getPosition() != 1) {
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
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_world));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.profile_icon));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.WHITE);
        drawable.setSize(2, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);
    }

}