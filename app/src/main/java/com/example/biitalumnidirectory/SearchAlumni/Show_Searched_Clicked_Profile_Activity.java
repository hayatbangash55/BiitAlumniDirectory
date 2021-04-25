package com.example.biitalumnidirectory.SearchAlumni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.biitalumnidirectory.MyProfile.EducationInfo_Fragment;
import com.example.biitalumnidirectory.MyProfile.FamilyInfo_Fragment;
import com.example.biitalumnidirectory.MyProfile.Gallery_Fragment;
import com.example.biitalumnidirectory.MyProfile.JobInfo_Fragment;
import com.example.biitalumnidirectory.MyProfile.PersonalInfo_Fragment;
import com.example.biitalumnidirectory.MyProfile.PublicationInfo_Fragment;
import com.example.biitalumnidirectory.MyProfile.ShowProfile_ViewPagerAdapter;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.tabs.TabLayout;

public class Show_Searched_Clicked_Profile_Activity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedReference SharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_searched_clicked_profile);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Searched Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.profile_tablayout);
        viewPager = (ViewPager) findViewById(R.id.profile_ViewPager);
        setPrivatePager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(6);
        SharedRef = new SharedReference(getApplicationContext());


    }


    //Add Fragment Here
    private void setPrivatePager(ViewPager privatePager) {

        Show_Searched_Clicked_Profile_ViewPagerAdapter viewPagerAdaptor = new Show_Searched_Clicked_Profile_ViewPagerAdapter(getSupportFragmentManager(), 6);
        viewPagerAdaptor.addfragment(new PersonalInfo_Fragment(), "Personal\nInfo");
        viewPagerAdaptor.addfragment(new EducationInfo_Fragment(), "Education\nInfo");
        viewPagerAdaptor.addfragment(new PublicationInfo_Fragment(), "Publication\nInfo");
        viewPagerAdaptor.addfragment(new JobInfo_Fragment(), "Job\nInfo");
        viewPagerAdaptor.addfragment(new FamilyInfo_Fragment(), "Family\nInfo");
        viewPagerAdaptor.addfragment(new Gallery_Fragment(), "Image\nGallery");
        viewPager.setAdapter(viewPagerAdaptor);

    }


    @Override
    public void onBackPressed() {
        SharedRef.save_LoadUserDataByCNIC(SharedRef.get_LoginCNIC());
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                SharedRef.save_LoadUserDataByCNIC(SharedRef.get_LoginCNIC());
                finish();
                break;
        }

        return true;
    }

}
