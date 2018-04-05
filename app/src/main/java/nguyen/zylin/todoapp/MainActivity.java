package nguyen.zylin.todoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import nguyen.zylin.todoapp.Adapter.ViewPagerAdapter;
import nguyen.zylin.todoapp.Fragment.DoneFragment;
import nguyen.zylin.todoapp.Fragment.TaskFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.id_tab_layout);
        viewPager = findViewById(R.id.id_viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());



        /**Add fragment*/
        viewPagerAdapter.addFragmentList(new TaskFragment(), "Task");
        viewPagerAdapter.addFragmentList(new DoneFragment(), "Done");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
