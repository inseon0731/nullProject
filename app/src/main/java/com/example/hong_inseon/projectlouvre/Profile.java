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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hong_inseon.projectlouvre.dao.Piece;
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

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int men;
    private TextView tv;
    private String pw;
    User userData, getUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(MainActivity.un == -1) {
            Toast.makeText(this, "로그인 해주세요.", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        //un = LoginActivity.un;

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarP);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutP);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewP);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        TextView lt = (TextView ) v.findViewById(R.id.loginText);
        TextView lb1=(TextView)v.findViewById(R.id.loginButton);
        TextView lb2=(TextView)v.findViewById(R.id.loginButton2);

        lt.setText(MainActivity.uname+"님 환영합니다!");
        lb1.setText("로그아웃");
        lb2.setVisibility(View.INVISIBLE);

        String result = SendByHttp("/getJsonUser.jsp"); // 메시지를 서버에 보냄

        Log.i("서버에서 받은 전체 내용 : ", result);
        //String[][] parsedData = jsonParserList(); // 받은 메시지를 json 파싱결과를 museum객체에 저장
        getUserData = jsonParser(result);

        tv = (TextView)findViewById(R.id.textView12);
        tv.setText(getUserData.getUser_email());
        tv = (TextView)findViewById(R.id.textView14);
        tv.setText(getUserData.getUser_pw());
        tv = (TextView)findViewById(R.id.textView16);
        tv.setText(getUserData.getUser_name());
        tv = (TextView)findViewById(R.id.textView18);
        String str = getUserData.getUser_gender();
        if(str.equals("0"))
            tv.setText("남자");
        else if(str.equals("1"))
            tv.setText("여자");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutP);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutP);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonPReplace :
                tv = (TextView)findViewById(R.id.textView14);
                pw = tv.getText().toString();
                String result = SendByHttp2("/updateJsonUser.jsp"); // 메시지를 서버에 보냄
                getUserData = jsonParser(result);
                Toast.makeText(Profile.this, "수정 되었습니다.", Toast.LENGTH_SHORT).show();
                break ;
            case R.id.buttonPBack :
                onBackPressed();
                break ;
        }
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE"+ MainActivity.version +"/getJsonUser.jsp?un="+MainActivity.un;
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
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE"+ MainActivity.version +"/updateJsonUser.jsp?un="+ MainActivity.un + "&np=" + pw;
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

    /**
     * 받은 JSON 객체를 파싱하는 메소드
     * @param pRecvServerPage
     * @return
     */
    public User jsonParser(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            JSONObject jObject = new JSONObject(pRecvServerPage);

            userData = new User();
            userData.setUser_no(jObject.getString("user_no"));
            userData.setUser_name(jObject.getString("user_name"));
            userData.setUser_email(jObject.getString("user_email"));
            userData.setUser_pw(jObject.getString("user_pw"));
            userData.setUser_gender(jObject.getString("user_gender"));

            Log.i("JSON을 파싱한 데이터 출력해보기"+" : ", userData.toString());
            //}
            return userData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void log(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void navJoin(View v) {  startActivity(new Intent(this, JoinActivity.class));}
}
