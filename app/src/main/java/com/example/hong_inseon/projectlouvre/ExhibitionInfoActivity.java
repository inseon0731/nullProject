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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hong_inseon.projectlouvre.dao.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ExhibitionInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    final Context context = this;
    //public Button btnOptAlert;

    public static Intent aa;
    public static Intent bb;
    public static Intent cc;
    public static Intent dd;
    public static int men = -1;
    private int mn = 1, en = 1, buy = 0;
    private boolean ch = false;
    private ImageView iv;
    //private int[] tabIcons = {R.drawable.heart, R.drawable.clock, R.drawable.calender, R.drawable.loudspeaker};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition_info_drawer);

        iv=(ImageView)findViewById(R.id.exHeart);

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.menuTabLayout);

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

        String result = SendByHttp2("/insertJsonBuy.jsp");
        ch = jsonParser(result);
        if(ch)
        {
            iv.setImageResource(R.drawable.heart_fill);
        }
    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonBuyOpt:
                final CharSequence[] items = {"가이드", "도록", "가이드+도록"};
                final int[] selectedIndex = {0};

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

                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.setSingleChoiceItems(items,
                        0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;

                            }
                        });
                dialog.show();


               /* dialog.setItems(items, new DialogInterface.OnClickListener() {
                    //리스트 선택시 이벤트

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), items[which] + " 선택했습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();*/
                break;
            case R.id.exHeart:
                String result2 = SendByHttp3("/getJsonLikeCHE.jsp"); // 메시지를 서버에 보냄
                ch = jsonParser2(result2);
                if(ch)
                {
                    iv.setImageResource(R.drawable.heart_fill);
                    Toast.makeText(ExhibitionInfoActivity.this, "좋아요 하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    iv.setImageResource(R.drawable.heart_ept);
                    Toast.makeText(ExhibitionInfoActivity.this, "좋아요를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exMap:
                startActivity(new Intent(this, MapActivity.class));
                break;
        }

    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE"+ MainActivity.version +"/insertJsonBuy.jsp?un="+ MainActivity.un + "&mn=" + mn + "&en="+ en + "&bt=" + buy;
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

    private String SendByHttp2(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE"+ MainActivity.version +"/getJsonLikeE.jsp?un="+ MainActivity.un + "&mn=" + mn + "&en="+ en;
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

    public boolean jsonParser(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        boolean check= false;
        try {
            JSONObject jObject = new JSONObject(pRecvServerPage);

            check = Boolean.parseBoolean(jObject.getString("like"));

            Log.i("JSON을 파싱한 데이터 출력해보기"+" : ", "" + check);
            //}
            return check;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String SendByHttp3(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE"+ MainActivity.version +"/getJsonLikeCHE.jsp?un="+ MainActivity.un + "&mn=" + mn + "&en="+ en;
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

    public boolean jsonParser2(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        boolean check= false;
        try {
            JSONObject jObject = new JSONObject(pRecvServerPage);

            check = Boolean.parseBoolean(jObject.getString("like"));

            Log.i("JSON을 파싱한 데이터 출력해보기"+" : ", "" + check);
            //}
            return check;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
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
        Intent i = new Intent(this, OptionP.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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

