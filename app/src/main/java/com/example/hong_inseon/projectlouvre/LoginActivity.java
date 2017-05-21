package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hong_inseon.projectlouvre.dao.User;

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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnJoin;
    private Button btnLogin;

    User userData, getUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(onClickListener);
        btnJoin.setOnClickListener(onClickListener);
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE"+ MainActivity.version+"/getJsonLogin.jsp?ue=" + etEmail.getText().toString() + "&up=" + etPassword.getText().toString();
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override

        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btnLogin:
                    if (etEmail.getText().toString().length() == 0 || etPassword.getText().toString().length() == 0)
                    {
                        Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 정확히 입력해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        String result = SendByHttp("/getJsonLogin.jsp"); // 메시지를 서버에 보냄
                        Log.i("서버에서 받은 전체 내용 : ", result);
                        getUserData = jsonParser(result);
                        if(getUserData.getUser_no().equals("-1"))
                        {
                            Toast.makeText(LoginActivity.this, "이메일과 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(getUserData.getUser_no().equals(""))
                        {
                            Toast.makeText(LoginActivity.this, "이메일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    catch(Exception e) {
                        Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "로그인하였습니다.", Toast.LENGTH_SHORT).show();
                    MainActivity.un = Integer.parseInt(getUserData.getUser_no());
                    MainActivity.uname = getUserData.getUser_name();
                    LoginActivity.this.finish();
                    break;

                case R.id.btnJoin:
                    Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                    startActivity(intent);
                    break;
            }
        } //onclick 끝
    };
}