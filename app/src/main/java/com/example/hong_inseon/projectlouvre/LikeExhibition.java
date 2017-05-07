package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.hong_inseon.projectlouvre.dao.Exhibition;
import java.util.ArrayList;

public class LikeExhibition extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    ListViewAdapterExhibition listh;
    String[] name1, name2, name3;
    int[] Image;
    ArrayList<Exhibition> arraylist = new ArrayList<Exhibition>();
    private int men;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        men = -1;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        name1 = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};

        name2 = new String[] { "Beijing", "New Delhi", "Washington D.C.",
                "Jakarta", "Brazilia", "Islamabad", "Abuja", "Dacca",
                "Moskva", "Tokyo"};
        name3 = new String[] { "2017.02.28~2017.07.21", "2017.02.27~2017.07.21", "2017.02.28~2017.07.21",
                "2017.02.26~2017.07.21", "2017.02.25~2017.07.21", "2017.02.24~2017.07.21", "2017.02.23~2017.07.21"
                , "2017.02.22~2017.07.21","2017.02.21~2017.07.21", "2017.02.20~2017.07.21"};
        Image = new int[] {R.drawable.no,R.drawable.cart,R.drawable.heart,R.drawable.louvre,R.drawable.profile,
                            R.drawable.mypage,R.drawable.temple,R.drawable.search,R.drawable.cart,R.drawable.profile};
        //리스트에 들어갈 자료들 정리

        list = (ListView)findViewById(R.id.listViewHeart);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(LikeExhibition.this, ExhibitionInfoActivity.class);
                startActivity(intent);
            }
        });

        for (int i = 0; i < name1.length; i++)
        {
            Exhibition hs = new Exhibition(name1[i],name2[i],name3[i], Image[i]);
            arraylist.add(hs);
        }
        //리스트배열을 정리

        listh = new ListViewAdapterExhibition(this, arraylist);
        list.setAdapter(listh);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
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
            men = 0;
        } else if (id == R.id.profileid) {
            men = 1;
        } else if (id == R.id.heartid) {
            men = 2;
        } else if (id == R.id.templeid) {
            men = 3;
        }
        Intent data = new Intent();
        data.putExtra("value1", men);
        setResult(1,data);
        this.finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void log(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
