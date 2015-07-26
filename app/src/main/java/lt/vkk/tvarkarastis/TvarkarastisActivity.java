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

import io.karim.MaterialTabs;
import lt.vkk.tvarkarastis.dienos.StudentasFragment;

public class TvarkarastisActivity extends AppCompatActivity {

    SharedPreferences settings;

    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     * android:noHistory="true"
     */
    int backButtonCount = 0;
    private MaterialTabs tabHost;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvarkarastis);

        settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tabHost = (MaterialTabs) this.findViewById(R.id.material_tabs);
        tabHost.setViewPager(viewPager);
        tabHost.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(4);
    }

    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Norėdami išeiti dar kartą paspauskit Išeiti mygtuką.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        int count = 5;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int which = settings.getInt("whoIam3", 0);
            switch (position) {
                case 0:
                    return StudentasFragment.newInstance(which, 1);
                case 1:
                    return StudentasFragment.newInstance(which, 2);
                case 2:
                    return StudentasFragment.newInstance(which, 3);
                case 3:
                    return StudentasFragment.newInstance(which, 4);
                case 4:
                    return StudentasFragment.newInstance(which, 5);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.dienos)[position];
        }
    }
}
