//http://cocomo.tistory.com/387
//http://swalloow.tistory.com/60
//jsp서버연동 : http://blog.naver.com/PostView.nhn?blogId=abcdtyy456&logNo=220597953881
//
package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnJoin; //sign up button
    private Button btnLogin; //login button

    private CheckBox autoLogin;

    //StringBuffer sb = new StringBuffer();
    //ArrayList<String> emailList = new ArrayList<>();
    //ArrayList<String> pwList = new ArrayList<>();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    //Boolean loginChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);

        pref = getSharedPreferences("pref", 0);
        editor = pref.edit();

        //if autoLogin checked, get input
        if (pref.getBoolean("autoLogin", false)) {
            etEmail.setText(pref.getString("id", ""));
            etPassword.setText(pref.getString("pw", ""));
            autoLogin.setChecked(true);
            // goto LoginActivity
        }

        //autoLogin.setOnCheckedChangeListener(onCheckedChangeListener);
        btnLogin.setOnClickListener(onClickListener);
        btnJoin.setOnClickListener(onClickListener);
    }

    private String SendByHttp(String msg) {
        if (msg == null) {
            msg = "";
        }

        // 웹서버 주소 설정
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE3/WebContent/test.jsp";
        DefaultHttpClient client = new DefaultHttpClient();

        try {
            /* 체크할 id와 pw값 서버로 전송 */
            HttpPost post = new HttpPost(URL + "?etEmail=" + etEmail.getText().toString() + "&etPassword=" + etPassword.getText().toString());

            /* 데이터 보낸 뒤 서버에서 데이터를 받아오는 과정 */
            HttpResponse response = client.execute(post);

            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line = null;
            String result = "";

            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) { // 예외처리
            e.printStackTrace();
            client.getConnectionManager().shutdown(); // 연결 지연 종료
            return "";
        }
    }

    // 받아온 데이터 파싱하는 함수
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage); // 받아온 데이터 확인

        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("LoginResult");

            // 받아온 pRecvServerPage를 분석하는 부분
            String[] jsonName = {"Result"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][0]);
            }
            return parseredData; // 파싱한 데이터 넘김
        } catch (JSONException e) { // 예외처리
            e.printStackTrace();
            return null;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override

        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btnLogin :

                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }

                    String loginid = etEmail.getText().toString();

                    //String loginpw = etPassword.getText().toString();

                    try {
                        String result = SendByHttp(loginid);
                        String[][] parsedData = jsonParserList(result);

                        //String result = new UserTask().execute(loginid, loginpw, "login").get();

                        Log.v("ab", "onclick11111111");



                        if (parsedData[0][0].equals("true")) {

                            Toast.makeText(LoginActivity.this, "로그인", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);

                            startActivity(intent);

                            finish();

                        } else if (parsedData[0][0].equals("false")) {

                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();

                            etEmail.setText("");

                            etPassword.setText("");

                        } else if (parsedData[0][0].equals("noId")) {

                            Toast.makeText(LoginActivity.this, "존재하지 않는 아이디", Toast.LENGTH_SHORT).show();

                            etEmail.setText("");

                            etPassword.setText("");

                        }


                    } catch (Exception e) {

                    }

                    break;


                case R.id.btnJoin:

                    String joinid = etEmail.getText().toString();

                    String joinpwd = etPassword.getText().toString();

                    try {

                        String result = SendByHttp(joinid);
                        // String result  = new UserTask().execute(joinid,joinpwd,"join").get();

                        if (result.equals("id")) {

                            Toast.makeText(LoginActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();

                            etEmail.setText("");

                            etPassword.setText("");

                        } else if (result.equals("ok")) {

                            etEmail.setText("");

                            etPassword.setText("");

                            Toast.makeText(LoginActivity.this, "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                    }

                    break;

            } // switch 끝
        } //onclick 끝
    };




}