package com.radiofarda.istgah.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.radiofarda.istgah.fragments.PlaceholderFragment;
import com.radiofarda.istgah.fragments.PodcastsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PodcastsFragment.newInstance();
            case 1:
                return PlaceholderFragment.newInstance(position + 1);
            case 2:
                return PlaceholderFragment.newInstance(position + 1);
        }
        return null;
     }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Podcast";
            case 1:
                return "Live Radio";
            case 2:
                return "Contact Us";
        }
        return null;
    }
}