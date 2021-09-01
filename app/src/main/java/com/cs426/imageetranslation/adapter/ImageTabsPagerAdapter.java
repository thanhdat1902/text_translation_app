package com.cs426.imageetranslation.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cs426.imageetranslation.tabfragment.CameraFragment;
import com.cs426.imageetranslation.tabfragment.ProfileFragment;

/**
 * Fragment to return the clicked tab.
 */
public class ImageTabsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ImageTabsPagerAdapter (FragmentManager fm, int NumOfTabs) {
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
            case 0: return new CameraFragment();
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

