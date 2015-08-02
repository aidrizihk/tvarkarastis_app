package lt.vkk.tvarkarastis;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

    SharedPreferences settings;
    int backButtonCount = 0;
    int who;
    int which;
    Destytojas destytojas;
    Grupe grupe;

    @Bind(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvarkarastis);
        ButterKnife.bind(this);

        settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        who = settings.getInt("whoIam2", 0); // 1 - lecturer, 2 - student, 0 - null
        which = settings.getInt("whoIam3", 0);
        /*if (who == 1) {
            destytojas = Destytojas.getSelected(which);
        } else {
            grupe = Grupe.getSelected(which);
        }*/


        // Scan which group selected.
        //int which = settings.getInt("whoIam3", 0);
        // Scan group name.
        //String grupeName = Grupe.getSelected(which).getPavadinimas();
        // For Some reason crashes and setTitle returns null.
        // Set custom ActionBar.
        //ActionBar ab = getActionBar();
        //ab.setTitle(grupeName);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        mViewPager.setAdapter(adapter);


        mMaterialTabs.setViewPager(mViewPager);

        // Set cached side Tabs limit to 0.
        //mViewPager.setOffscreenPageLimit(0);

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
                System.out.println("destytojo paskaitos:: " + PaskaitosIrasas.getAllbyDestytojas(which, 1));
                switch (position) {
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
                }
            } else {
                // student
                System.out.println("studento paskaitos:: " + PaskaitosIrasas.getAllbyGrupe(which, 1));
                switch (position) {
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
                }
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // refresh
                new Delete().from(PaskaitosIrasas.class).execute();
                new Delete().from(Destytojas.class).execute();
                new Delete().from(Grupe.class).execute();
                mainActivity.checkInternet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
