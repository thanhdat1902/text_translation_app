package com.cs426.imageetranslation;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cs426.imageetranslation.tabfragment.CameraFragment;
import com.cs426.imageetranslation.tabfragment.ProfileFragment;
import com.cs426.imageetranslation.tabfragment.TranslationFragment;

/**
 * Fragment to return the clicked tab.
 */
public class TranslationPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TranslationPagerAdapter (FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TranslationFragment();
            case 1: return new ProfileFragment();

            default: return null;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
