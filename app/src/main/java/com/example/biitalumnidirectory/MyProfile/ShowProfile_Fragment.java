package com.example.biitalumnidirectory.MyProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.R;
import com.google.android.material.tabs.TabLayout;

public class ShowProfile_Fragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ShowProfile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_show_profile, container, false);

        tabLayout = (TabLayout) v.findViewById(R.id.profile_tablayout);
        viewPager = (ViewPager) v.findViewById(R.id.profile_ViewPager);
        setPrivatePager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(6);

        return v;
    }

    //Add Fragment Here
    private void setPrivatePager(ViewPager privatePager) {

        ShowProfile_ViewPagerAdapter viewPagerAdaptor = new ShowProfile_ViewPagerAdapter(getChildFragmentManager(), 6);
        viewPagerAdaptor.addfragment(new PersonalInfo_Fragment(), "Personal\nInfo");
        viewPagerAdaptor.addfragment(new EducationInfo_Fragment(), "Education\nInfo");
        viewPagerAdaptor.addfragment(new PublicationInfo_Fragment(), "Publication\nInfo");
        viewPagerAdaptor.addfragment(new JobInfo_Fragment(), "Job\nInfo");
        viewPagerAdaptor.addfragment(new Attributes_Fragment(), "Attributes\nInfo");
        viewPagerAdaptor.addfragment(new FamilyInfo_Fragment(), "Family\nInfo");
        viewPagerAdaptor.addfragment(new Gallery_Fragment(), "Image\nGallery");
        viewPager.setAdapter(viewPagerAdaptor);

    }


}
