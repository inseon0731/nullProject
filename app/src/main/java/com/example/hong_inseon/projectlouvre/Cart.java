package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Cart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    ListViewAdapterBuy listh;
    ArrayList<Buy> arraylist = new ArrayList<Buy>();
    Buy buyData;
    private int men;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout4);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view4);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        TextView lt = (TextView ) v.findViewById(R.id.loginText);
        TextView lb1=(TextView)v.findViewById(R.id.loginButton);
        TextView lb2=(TextView)v.findViewById(R.id.loginButton2);

        lt.setText(MainActivity.uname+"님 환영합니다!");
        lb1.setText("로그아웃");
        lb2.setVisibility(View.INVISIBLE);

        list = (ListView)findViewById(R.id.listViewCart);

        String result = SendByHttp("/getJsonBuy.jsp"); // 메시지를 서버에 보냄

        Log.i("서버에서 받은 전체 내용 : ", result);
        //String[][] parsedData = jsonParserList(); // 받은 메시지를 json 파싱결과를 museum객체에 저장
        arraylist = jsonParserList(result);
        listh = new ListViewAdapterBuy(this, arraylist);
        //tvRecvData.setText(result);	// 받은 메시지를 화면에 보여주r기

        Log.i("@@@", "" + arraylist);
        Log.i("###","" + listh);

        list.setAdapter(listh);
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE" + MainActivity.version + "/getJsonBuy.jsp?un="+ MainActivity.un;
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
            client.getConnectionManager().shutdown();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();	// 연결 지연 종료
            return "";
        }
    }

    /**
     * 받은 JSON 객체를 파싱하는 메소드
     * @param pRecvServerPage
     * @return
     */
    public ArrayList jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            JSONObject jsonObject = new JSONObject(pRecvServerPage);
            JSONArray jarray = jsonObject.getJSONArray("buys");
            int check=0;
            boolean check2 = false;

            // 받아온 pRecvServerPage를 분석하는 부분
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                if(jObject != null) { //museum 데이터 객체에 파싱한 값 저장.
                    buyData = new Buy();
                    buyData.setNameExhibit(jObject.getString("ms_name"));
                    buyData.setNameMuseum(jObject.getString("ex_name"));
                    buyData.setImage(jObject.getString("ex_img"));
                    check = Integer.parseInt(jObject.getString("buy_type"));
                    if(check%2 == 1)
                        check2 = true; // 도록 먼저 확인
                    buyData.setDorok(check2);
                    check2 = false;
                    if(check/2 == 1)
                        check2 = true;
                    buyData.setGuide(check2);
                    arraylist.add(buyData);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for(int i=0; i<jarray.length(); i++){
                Log.i("JSON을 파싱한 데이터 출력해보기"+i+" : ", buyData.toString());
            }
            return arraylist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout4);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            setResult(0);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout4);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void log(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void navJoin(View v) {  startActivity(new Intent(this, JoinActivity.class));}
}
