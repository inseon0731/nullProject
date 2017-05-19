package com.example.hong_inseon.projectlouvre;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class JoinActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etName;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private RadioGroup rg;
    private RadioButton rb;
    private Button btnDone;
    private Button btnCancel;
    private int rbi = -1;
    private User userData, getUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getUserData = new User();
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText)findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if (password.equals(confirm)) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이메일 입력 확인
                if (etEmail.getText().toString().length() == 0) {
                    Toast.makeText(JoinActivity.this, "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if (etPassword.getText().toString().length() == 0) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if (etPasswordConfirm.getText().toString().length() == 0) {
                    Toast.makeText(JoinActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }

                rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String radioStr = rb.getText().toString();
                if(radioStr.equals("남"))
                    rbi = 0;
                else if(radioStr.equals("여"))
                    rbi = 1;
                if(rbi == -1)
                {
                    Toast.makeText(JoinActivity.this, "성별을 선택해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*Intent result = new Intent();
                result.putExtra("email", etEmail.getText().toString());

                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                finish();*/
                String result = SendByHttp("/insertJsonUser.jsp"); // 메시지를 서버에 보냄

                Log.i("서버에서 받은 전체 내용 : ", result);
                //String[][] parsedData = jsonParserList(); // 받은 메시지를 json 파싱결과를 museum객체에 저장
                getUserData = jsonParser(result);

                if(getUserData.getUser_email().equals(etEmail.getText()))
                {
                    Toast.makeText(JoinActivity.this, "이미 있는 메일입니다. 메일을 바꿔주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(JoinActivity.this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                JoinActivity.this.finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE16/insertJsonUser.jsp?ue="+ etEmail.getText() + "&un=" + etName.getText() + "&pw=" + etPassword.getText() + "&gd=" + rbi;
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
}