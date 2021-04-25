package com.example.biitalumnidirectory.Friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.R;
import com.google.android.material.tabs.TabLayout;


public class FriendMenu_Fragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public FriendMenu_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_friend_menu, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Friends");

        tabLayout = v.findViewById(R.id.friend_tablayout);
        viewPager = v.findViewById(R.id.friend_ViewPager);
        setPrivatePager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        return v;
    }


    //Add Fragment Here
    private void setPrivatePager(ViewPager privatePager) {

        FriendMenu_ViewPagerAdapter viewPagerAdaptor = new FriendMenu_ViewPagerAdapter(getChildFragmentManager(), 4);
        viewPagerAdaptor.addfragment(new Friends_Fragment(), "Friends");
        viewPagerAdaptor.addfragment(new FriendRequests_Fragment(), "Friend\nRequests");
        viewPagerAdaptor.addfragment(new RequestSent_Fragment(), "Request Sent");
        viewPager.setAdapter(viewPagerAdaptor);
    }
}
