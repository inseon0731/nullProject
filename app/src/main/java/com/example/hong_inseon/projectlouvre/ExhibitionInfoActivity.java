package com.example.hong_inseon.projectlouvre;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ExhibitionInfoActivity extends AppCompatActivity implements OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    final Context context = this;
    public Button btnOptAlert;

    public static Intent aa;
    public static Intent bb;
    public static Intent cc;
    public static Intent dd;
    public static int men = -1;
    private int un = 1, mn = 1, en = 1, buy = 0;

    private int[] tabIcons = {R.drawable.heart, R.drawable.clock, R.drawable.calender, R.drawable.loudspeaker};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //?? 에러있는 듯?
        setContentView(R.layout.activity_exhibition_info_drawer);

        //un = LoginActivity.un;

        aa = new Intent(this, Cart.class);
        bb = new Intent(this, Profile.class);
        cc = new Intent(this, LikeExhibition.class);
        dd = new Intent(this, LikeMuseum.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.menuTabLayout);

        /*WebView webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE/getExhiTab.jsp");*/

        //if(VERSION.SDK_INT >= 19) {
        //    webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //}

        final ViewPager viewPager = (ViewPager) findViewById(R.id.menupager);
        final ExhibitionInfoActivity.PagerAdapter adapter = new ExhibitionInfoActivity.PagerAdapter
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

        //옵션 상자
        btnOptAlert = (Button) findViewById(R.id.buttonBuyOpt);
        //클릭이벤트
        btnOptAlert.setOnClickListener(this);
    }

    public void onClick(View v) {

        final CharSequence [] items = {"가이드", "도록", "가이드+도록"};
        final int [] selectedIndex = {0};

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setPositiveButton("구매", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //프로그램을 종료한다
                which++;
                buy = which;
                String result = SendByHttp("/insertJsonBuy.jsp");

                Toast.makeText(getApplicationContext(), items[selectedIndex[0]] + " 구매했습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.setSingleChoiceItems(items,
                0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        selectedIndex[0] = which;

                    }
                });

        dialog.show();

        /*
        dialog.setItems(items, new DialogInterface.OnClickListener(){
            //리스트 선택시 이벤트

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), items[which]+" 선택했습니다", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();



        final CharSequence[] items = {"가이드", "도록", "가이드+도록"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);


        //alertDialogBuilder.setTitle("옵션 선택 목록 대화상자"); //제목 셋팅



        //다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        //alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        LayoutParams params = alertDialogBuilder.getContext().
                //getWindow().getAttributes();
        params.x=100;
        params.y=200;
        alertDialog.getWindow().setAttributes(params);


        //다이얼로그 보여주기
        alertDialog.show();

        */
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE14/insertJsonBuy.jsp?un="+ un + "&mn=" + mn + "&en="+ en + "&bt=" + buy;
        DefaultHttpClient client = new DefaultHttpClient();

        try {
			/* 체크할 값 서버로 전송 : 쿼리문이 아니라 넘어갈 uri주소 */
            HttpPost post = new HttpPost(URL);
			/* 지연시간 최대 3초 */
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

			/* 데이터 보낸 뒤 서버에서 데이터를 받아오는 과정 */
            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "euc-kr"));
            String line = null;
            String result = "";
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();	// 연결 지연 종료
            return "";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.some, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent i = new Intent(this, OptionP.class);
        startActivity(i);

        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    public void map(View v) {
        startActivity(new Intent(this, MapActivity.class));
    }

    public void navJoin(View v) {  startActivity(new Intent(this, JoinActivity.class));}

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
                    MenuTabFrag1 mtab1 = new MenuTabFrag1(); // Fragment 는 알아서 만들자
                    return mtab1;
                case 1:
                    MenuTabFrag2 tab2 = new MenuTabFrag2();
                    return tab2;
                case 2:
                    MenuTabFrag3 tab3 = new MenuTabFrag3();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return _numOfTabs;
        }
    }
}

