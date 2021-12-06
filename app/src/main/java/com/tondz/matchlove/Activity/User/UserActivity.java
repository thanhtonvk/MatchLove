package com.tondz.matchlove.Activity.User;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tondz.matchlove.Activity.User.Adapter.ViewPagerAdapter;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

public class UserActivity extends AppCompatActivity {


    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    AccountDBContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_main).setChecked(true);

                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_search).setChecked(true);

                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_match).setChecked(true);

                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_message).setChecked(true);

                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menu_person).setChecked(true);

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_main:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_search:
                        viewPager.setCurrentItem(1);

                        break;
                    case R.id.menu_match:
                        viewPager.setCurrentItem(2);

                        break;
                    case R.id.menu_message:
                        viewPager.setCurrentItem(3);

                        break;
                    case R.id.menu_person:
                        viewPager.setCurrentItem(4);


                        break;
                }
                return true;
            }
        });
        viewPager.setOffscreenPageLimit(2);
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager_user);
        bottomNavigationView = findViewById(R.id.bottom_nav_user);
        dbContext = new AccountDBContext();
        Common.account.setLocation(Common.yourLatLng);
        dbContext.update(Common.account, UserActivity.this);

    }


}