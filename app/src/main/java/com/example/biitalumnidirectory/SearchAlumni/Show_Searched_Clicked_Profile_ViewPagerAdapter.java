package com.example.biitalumnidirectory.SearchAlumni;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Show_Searched_Clicked_Profile_ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> fragmentTitle = new ArrayList<>();

    public Show_Searched_Clicked_Profile_ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }// getItem is called to instantiate the fragment for the given page.

    @Override
    public int getCount() {
        return fragmentList.size();
    } // Show pages equal to.. .fragmentlistszie() pages.

    public void addfragment(Fragment fragment, String title) {

        fragmentList.add(fragment);
        fragmentTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }


}
