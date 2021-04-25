package com.example.biitalumnidirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.biitalumnidirectory.Admin_Notification.Admin_Notification_Activity;
import com.example.biitalumnidirectory.Alumni_Notification.Alumni_Notification_Activity;
import com.example.biitalumnidirectory.ChangeStudentStatus.AddAlumni_Fragment;
import com.example.biitalumnidirectory.ChangeStudentStatus.RemoveAlumni_Fragment;
import com.example.biitalumnidirectory.Events.EventMenu_Fragment;
import com.example.biitalumnidirectory.Friends.FriendMenu_Fragment;
import com.example.biitalumnidirectory.Groups.Groups_Activity;
import com.example.biitalumnidirectory.Jobs.JobMenu_Fragment;
import com.example.biitalumnidirectory.Login_Classes.Login;
import com.example.biitalumnidirectory.MyProfile.ShowProfile_Fragment;
import com.example.biitalumnidirectory.SearchAlumni.SearchAlumni_Fragment;
import com.example.biitalumnidirectory.SearchEventsAndJobs.SearchEventsAndJobs_Fragment;
import com.example.biitalumnidirectory.Survey.SurveyMenu_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    SharedReference SharedRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedRef = new SharedReference(this);
        drawerLayout = findViewById(R.id.drawer);
        drawerLayout.addDrawerListener(toggle);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        toggle.syncState();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");

        //remove this when project finalized becuase it will be assigned from login page
        SharedRef.save_UserLoginCNIC("1410103808805");
        SharedRef.save_UserType("alumni");


        SharedRef.save_LoadUserDataByCNIC(SharedRef.get_LoginCNIC());

        if (SharedRef.get_UserType().equalsIgnoreCase("admin")) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Admin_DashBoard_Fragment()).commit();
            toolbar.setTitle("Event Menu");
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.admin_drawer_items);

        } else {

            SharedRef.save_EditProfile(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ShowProfile_Fragment()).commit();
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.alumni_drawer_items);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.my_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShowProfile_Fragment()).commit();
                SharedRef.save_EditProfile(false);
                getSupportActionBar().setTitle("My Profile");
                break;

            case R.id.edit_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShowProfile_Fragment()).commit();
                SharedRef.save_EditProfile(true);
                getSupportActionBar().setTitle("Edit Profile");
                break;

            case R.id.Search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchAlumni_Fragment()).commit();
                SharedRef.save_EditProfile(false);
                break;

            case R.id.friend_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FriendMenu_Fragment()).commit();
                SharedRef.save_EditProfile(false);
                break;

            case R.id.SearchEventsAndJobs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchEventsAndJobs_Fragment()).commit();
                break;

            case R.id.groups:
                Intent intent = new Intent(MainActivity.this, Groups_Activity.class);
                startActivity(intent);
                SharedRef.save_EditProfile(false);
                break;

            case R.id.Alumni_Events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventMenu_Fragment()).commit();
                break;

            case R.id.Alumni_Job:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new JobMenu_Fragment()).commit();
                break;

            case R.id.Alumni_Survey:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SurveyMenu_Fragment()).commit();
                break;


            case R.id.alumni_logout:
                intent = new Intent(MainActivity.this, Login.class);

                SharedRef.save_RememberMe(false);
                SharedRef.save_registrationStatus(false);
                startActivity(intent);
                break;

            //Admin
            case R.id.admin_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Admin_DashBoard_Fragment()).commit();
                break;

            case R.id.event:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventMenu_Fragment()).commit();
                break;

            case R.id.job:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new JobMenu_Fragment()).commit();
                break;

            case R.id.survey:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SurveyMenu_Fragment()).commit();
                break;

            case R.id.addAlumni:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddAlumni_Fragment()).commit();
                break;
//
//            case R.id.removeAlumni:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new RemoveAlumni_Fragment()).commit();
//                break;


            case R.id.admin_logout:
                intent = new Intent(MainActivity.this, Login.class);
                SharedRef.save_RememberMe(false);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.bell_icon:
                if (SharedRef.get_UserType().equalsIgnoreCase("admin")) {
                    Intent intent = new Intent(MainActivity.this, Admin_Notification_Activity.class);
                    startActivity(intent);
                    break;
                }

                if (SharedRef.get_UserType().equalsIgnoreCase("alumni")) {
                    Intent intent = new Intent(MainActivity.this, Alumni_Notification_Activity.class);
                    startActivity(intent);
                    break;
                }
        }

        return super.onOptionsItemSelected(item);
    }


    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}