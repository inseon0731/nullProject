package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hong_inseon.projectlouvre.dao.Exhibition;
import com.example.hong_inseon.projectlouvre.dao.Museum;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LikeExhibition extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    ListViewAdapterExhibition listh;
    String[] name1, name2, name3;
    int[] Image;
    ArrayList<Exhibition> arraylist = new ArrayList<Exhibition>();
    private int men;
    Exhibition exData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        if(MainActivity.un == -1) {
            Toast.makeText(this, "로그인 해주세요.", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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

        View v = navigationView.getHeaderView(0);
        TextView lt = (TextView ) v.findViewById(R.id.loginText);
        TextView lb1=(TextView)v.findViewById(R.id.loginButton);
        TextView lb2=(TextView)v.findViewById(R.id.loginButton2);

        lt.setText(MainActivity.uname+"님 환영합니다!");
        lb1.setText("로그아웃");
        lb2.setVisibility(View.INVISIBLE);

        /*name1 = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};

        name2 = new String[] { "Beijing", "New Delhi", "Washington D.C.",
                "Jakarta", "Brazilia", "Islamabad", "Abuja", "Dacca",
                "Moskva", "Tokyo"};
        name3 = new String[] { "2017.02.28~2017.07.21", "2017.02.27~2017.07.21", "2017.02.28~2017.07.21",
                "2017.02.26~2017.07.21", "2017.02.25~2017.07.21", "2017.02.24~2017.07.21", "2017.02.23~2017.07.21"
                , "2017.02.22~2017.07.21","2017.02.21~2017.07.21", "2017.02.20~2017.07.21"};
        Image = new int[] {R.drawable.no,R.drawable.cart,R.drawable.heart,R.drawable.louvre,R.drawable.profile,
                            R.drawable.mypage,R.drawable.temple,R.drawable.search,R.drawable.cart,R.drawable.profile};*/
        //리스트에 들어갈 자료들 정리

        list = (ListView)findViewById(R.id.listViewHeart);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(LikeExhibition.this, ExhibitionInfoActivity.class);
                startActivity(intent);
            }
        });

        /*for (int i = 0; i < name1.length; i++)
        {
            Exhibition hs = new Exhibition(name1[i],name2[i],name3[i], Image[i]);
            arraylist.add(hs);
        }*/
        //리스트배열을 정리

        String result = SendByHttp("/getJsonLikeExhibitionList.jsp");
        arraylist = jsonParserList(result);
        Log.i("@@@", ""+arraylist);
        listh = new ListViewAdapterExhibition(this, arraylist);
        list.setAdapter(listh);
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE" + MainActivity.version + msg + "?un=" + MainActivity.un;
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

    public ArrayList jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            JSONObject jsonObject = new JSONObject(pRecvServerPage);
            JSONArray jarray = jsonObject.getJSONArray("exhibitions");

            Log.i("@@@", ""+jarray);

            // 받아온 pRecvServerPage를 분석하는 부분
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                if(jObject != null) { //museum 데이터 객체에 파싱한 값 저장.
                    exData = new Exhibition();
                    exData.setEx_no(jObject.getString("ex_no"));
                    exData.setEx_name(jObject.getString("ex_name"));
                    exData.setEx_theme(jObject.getString("ex_theme"));
                    exData.setEx_like(jObject.getString("ex_like"));
                    exData.setEx_img(jObject.getString("ex_img"));
                    exData.setMs_no(jObject.getString("ms_no"));
                    exData.setEx_location(jObject.getString("ex_location"));
                    exData.setEx_rating(jObject.getString("ex_rating"));
                    exData.setEx_pay(jObject.getString("ex_pay"));
                    exData.setEx_exp(jObject.getString("ex_exp"));
                    exData.setEx_period(jObject.getString("ex_period"));
                    exData.setEx_ing(jObject.getString("ex_ing"));
                    arraylist.add(exData);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for(int i=0; i<jarray.length(); i++){
                Log.i("JSON을 파싱한 데이터 출력해보기"+i+" : ", exData.toString());
            }
            return arraylist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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

    public void navJoin(View v) {  startActivity(new Intent(this, JoinActivity.class));}
}
