package lt.vkk.tvarkarastis;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.karim.MaterialTabs;
import lt.vkk.tvarkarastis.dienos.DestytojasFragment;
import lt.vkk.tvarkarastis.dienos.StudentasFragment;
import lt.vkk.tvarkarastis.models.Destytojas;
import lt.vkk.tvarkarastis.models.Grupe;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

public class TvarkarastisActivity extends AppCompatActivity {

    Context mContext;
    SharedPreferences settings;
    int backButtonCount = 0;
    int who;
    int mStudentLecturerID; // Student or Lecturer ID.

    @Bind(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.left_drawer)
    ListView mDrawerList;

    ActionBarDrawerToggle mDrawerToggle;

    private String[] mDrawerTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvarkarastis);
        ButterKnife.bind(this);


        settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        who = settings.getInt("whoIam2", 0); // 1 - lecturer, 2 - student, 0 - null
        mStudentLecturerID = settings.getInt("whoIam3", 0); // Scan which group selected.

        final String mTitle, mDrawerTitle;
        if (who == 1) {
            mDrawerTitle = mTitle = Destytojas.getSelected(mStudentLecturerID).getVardas() + " " + Destytojas.getSelected(mStudentLecturerID).getPavarde();
        } else {
            mDrawerTitle = mTitle = Grupe.getSelected(mStudentLecturerID).getPavadinimas();
        }


        ActionBar mActionBar = this.getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        // Set String array for drawer.
        mDrawerTexts = getResources().getStringArray(R.array.drawer_text);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.drawer_list_item,
                mDrawerTexts));

        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        /*mDrawerToggle = new ActionBarDrawerToggle(
                this,                  *//* host Activity *//*
                mDrawerLayout,         *//* DrawerLayout object *//*
                R.drawable.ic_drawer,  *//* nav drawer image to replace 'Up' caret *//*
                R.string.navigation_drawer_open,  *//* "open drawer" description for accessibility *//*
                R.string.navigation_drawer_close  *//* "close drawer" description for accessibility *//*
        ) {
            public void onDrawerClosed(View view) {
                // Sets ActionBar title to group or lecturer.
                //getSupportActionBar().setTitle(title);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }*/


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        mViewPager.setAdapter(adapter);

        mMaterialTabs.setViewPager(mViewPager);

        // Set cached side Tabs limit to 4.
        mViewPager.setOffscreenPageLimit(4);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        // Check if today is Sunday or Saturday
        if (day == 1 || day == 7) {
            // Set opened Tab to Monday
            mViewPager.setCurrentItem(0);
        } else {
            // Set opened Tab to current day of the week
            mViewPager.setCurrentItem(day - 2);
        }
    }

    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     * android:noHistory="true"
     */
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Paspauskite dar kartą norėdami išeiti.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tvarkarastis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // refresh
                new Delete().from(PaskaitosIrasas.class).execute();
                new Delete().from(Destytojas.class).execute();
                new Delete().from(Grupe.class).execute();
                //MainActivity.checkInternet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        //android.app.Fragment fragment = PlanetFragment.newInstance(position);

        //android.app.FragmentManager fragmentManager = getFragmentManager();
        //FragmentTransaction ft = fragmentManager.beginTransaction();
        //ft.replace(R.id.content_frame, fragment);
        //ft.commit();

        // update selected item title, then close the drawer
        //setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        // Number of tabs.
        int count = 5;

        // Manditory constructor.
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // lecturer
            if (who == 1) {
                return DestytojasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyDestytojas(mStudentLecturerID, ++position));
                /*switch (position) {
                    case 0:
                        return DestytojasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyDestytojas(which, 1));
                    case 1:
                        return DestytojasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyDestytojas(which, 2));
                    case 2:
                        return DestytojasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyDestytojas(which, 3));
                    case 3:
                        return DestytojasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyDestytojas(which, 4));
                    case 4:
                        return DestytojasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyDestytojas(which, 5));
                    default:
                        return null;
                }*/
            } else {
                // student
                return StudentasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyGrupe(mStudentLecturerID, ++position));
                /*switch (position) {
                    case 0:
                        return StudentasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyGrupe(which, 1));
                    case 1:
                        return StudentasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyGrupe(which, 2));
                    case 2:
                        return StudentasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyGrupe(which, 3));
                    case 3:
                        return StudentasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyGrupe(which, 4));
                    case 4:
                        return StudentasFragment.newInstance((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllbyGrupe(which, 5));
                    default:
                        return null;
                }*/
            }
        }

        @Override
        public int getCount() {
            return count;
        }

        // Returns String array of Tab titles.
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.dienos)[position];
        }
    }
}
