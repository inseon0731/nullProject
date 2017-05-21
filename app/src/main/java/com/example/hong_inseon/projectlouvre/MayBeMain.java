package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MayBeMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int[] tabIcons = {R.drawable.heart_fill, R.drawable.clock, R.drawable.calender, R.drawable.loudspeaker};
    public static Intent aa;
    public static Intent bb;
    public static Intent cc;
    public static Intent dd;
    public static int men = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_may_be_main);

        aa = new Intent(this, Cart.class);
        bb = new Intent(this, Profile.class);
        cc = new Intent(this, LikeExhibition.class);
        dd = new Intent(this, LikeMuseum.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarM);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutM);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewM);
        navigationView.setNavigationItemSelectedListener(this);

        if (MainActivity.un != -1)
        {
            View v = navigationView.getHeaderView(0);
            TextView lt = (TextView) v.findViewById(R.id.loginText);
            TextView lb1 = (TextView) v.findViewById(R.id.loginButton);
            TextView lb2 = (TextView) v.findViewById(R.id.loginButton2);

            lt.setText(MainActivity.uname + "님 환영합니다!");
            lb1.setText("로그아웃");
            lb2.setVisibility(View.INVISIBLE);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.rcvr_tl_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("인기전시"));
        tabLayout.addTab(tabLayout.newTab().setText("진행중전시"));
        tabLayout.addTab(tabLayout.newTab().setText("예정전시"));
        tabLayout.addTab(tabLayout.newTab().setText("공지사항"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

        tabLayout.setTabTextColors(getResources().getColor(R.color.colorBlack), getResources().getColor(R.color.colorBlack));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.rcvr_vp_pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        int _numOfTabs;

        public PagerAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this._numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    MyTabFragment1 tab1 = new MyTabFragment1(); // Fragment 는 알아서 만들자
                    return tab1;
                case 1:
                    MyTabFragment2 tab2 = new MyTabFragment2();
                    return tab2;
                case 2:
                    MyTabFragment3 tab3 = new MyTabFragment3();
                    return tab3;
                case 3:
                    MyTabFragment4 tab4 = new MyTabFragment4();
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return _numOfTabs;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutM);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            setResult(0);
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(men != -1) {
            Intent data = new Intent();
            data.putExtra("value1", men);
            setResult(1,data);
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.some, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, OptionP.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }//클릭했을시


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cartid) {
            startActivityForResult(aa,0);
        } else if (id == R.id.profileid) {
            startActivityForResult(bb,1);
        } else if (id == R.id.heartid) {
            startActivityForResult(cc,2);
        } else if (id == R.id.templeid) {
            startActivityForResult(dd,3);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutM);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0){

        } else if(resultCode == 1) {
            men = data.getIntExtra("value1", -1);
            if (men == 0) {
                startActivityForResult(aa, 0);
            } else if (men == 1) {
                startActivityForResult(bb, 1);
            } else if (men == 2) {
                startActivityForResult(cc, 2);
            } else if (men == 3) {
                startActivityForResult(dd, 3);
            }
        }
    }

    public void log(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void navJoin(View v) {  startActivity(new Intent(this, JoinActivity.class));}
}
