package mobapps.avbgiet.com.collegecompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import mobapps.avbgiet.com.collegecompanion.events.eventsFragment;
import mobapps.avbgiet.com.collegecompanion.notifications.NotificationService;
import mobapps.avbgiet.com.collegecompanion.others.LoginActivity;
import mobapps.avbgiet.com.collegecompanion.syllabus.syllabusFragment;
import mobapps.avbgiet.com.collegecompanion.notifications.notificationFragment;
import mobapps.avbgiet.com.collegecompanion.others.aboutAppFragment;
import mobapps.avbgiet.com.collegecompanion.others.profileFragment;
import mobapps.avbgiet.com.collegecompanion.Faculty.searchFacultyFragment;
import mobapps.avbgiet.com.collegecompanion.timeTable.timeTableFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  //variables
  Fragment fragment = null;
  private FirebaseAuth firebaseAuth;
  private FirebaseUser currentUser;
  private DatabaseReference databaseReference;

  private TextView headerUserName, headerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //fireBase initialization
       firebaseAuth = FirebaseAuth.getInstance();
       currentUser = FirebaseAuth.getInstance().getCurrentUser();
       databaseReference = FirebaseDatabase.getInstance().getReference("Database").child("AppUsers").child(currentUser.getUid());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);   // to make the icons colored
        navigationView.setNavigationItemSelectedListener(this);

      View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
      headerUserName = (TextView) navHeaderView.findViewById(R.id.textViewNavUserName);

      headerEmail = (TextView) navHeaderView.findViewById(R.id.textViewNavEmailAddress);
      headerEmail.setText(currentUser.getEmail());



        if(savedInstanceState == null)
        {
          //loading Notifications fragment as default for first time
          getSupportActionBar().setTitle("Notifications");
          fragment = new notificationFragment();
          FragmentManager fragmentManager = getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.replace(R.id.frameLayout, fragment);
          fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int id = item.getItemId();

        if (id == R.id.nav_notifications)
        {
            getSupportActionBar().setTitle("Notifications");
            fragment = new notificationFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++)
            {
              getSupportFragmentManager().popBackStackImmediate();
            }
          //fragmentTransaction.addToBackStack("fragment");
          fragmentTransaction.commit();
        }

        else if (id == R.id.nav_timetable)
        {
            getSupportActionBar().setTitle("Time Table");
            fragment = new timeTableFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack("timetableFragment");
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_faculty)
        {
            getSupportActionBar().setTitle("Faculty");
            fragment = new searchFacultyFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack("facultyFragment");
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_events)
        {
            getSupportActionBar().setTitle("Events");
            fragment = new eventsFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack("eventsFragment");
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_syllabus)
        {
            getSupportActionBar().setTitle("Syllabus");
            fragment = new syllabusFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack("syllabusFragment");
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_profile)
        {
            getSupportActionBar().setTitle("Profile");
            fragment = new profileFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack("settingsFragment");
            fragmentTransaction.commit();
        }

        /*else if (id == R.id.nav_about)
        {
            getSupportActionBar().setTitle("About App");
            fragment = new aboutAppFragment();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack("aboutFragment");
            fragmentTransaction.commit();
        }*/

        else if (id == R.id.nav_logout)
        {
          //stop notification service
          stopService(new Intent(this, NotificationService.class));

          //log out user
          firebaseAuth.signOut();
          Toast.makeText(MainActivity.this, "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
          startActivity(new Intent(MainActivity.this, LoginActivity.class));
          finish();
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //for loading different fragments
    private void loadFragment(Fragment fragment)
    {
      if(fragment != null)
      {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.commit();
      }
    }

  public void setActionBarTitle(String title){
    getSupportActionBar().setTitle(title);
  }


}
