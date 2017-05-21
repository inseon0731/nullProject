package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;
import java.util.Locale;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class OptionP extends AppCompatActivity {
    EditText editsearch;
    TextView t,t1,t2;
    ListView list, list2;
    ListViewAdapterMuseum adapter;
    ListViewAdapterExhibition adapter2;
    ArrayList<Museum> arraylist = new ArrayList<Museum>();
    ArrayList<Exhibition> arraylist2 = new ArrayList<Exhibition>();

    private Museum msData;
    private Exhibition exData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_p);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);

        list = (ListView) findViewById(R.id.listview);
        list2 = (ListView)findViewById(R.id.listview2);

        String result = SendByHttp("/getJsonMuseumList.jsp");
        Log.i("###1",result);
        arraylist = jsonParserList(result);
        Log.i("@@@1", ""+arraylist);
        String result2 = SendByHttp2("/getJsonExhibitionList.jsp");
        Log.i("###2",result2);
        arraylist2 = jsonParserList2(result2);
        Log.i("@@@2", ""+arraylist2);

        adapter = new ListViewAdapterMuseum(this, arraylist);
        adapter2 = new ListViewAdapterExhibition(this, arraylist2);

        list.setAdapter(adapter);
        list2.setAdapter(adapter2);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(OptionP.this, ExhibitionInfoActivity.class);
                startActivity(intent);
            }
        });
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(OptionP.this, ExhibitionInfoActivity.class);
                startActivity(intent);
            }
        });


        t = (TextView)findViewById(R.id.Result);
        t1 = (TextView)findViewById(R.id.textMuseum);
        t2 = (TextView)findViewById(R.id.textExhibition);
        editsearch = (EditText) findViewById(R.id.search);
        editsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch(actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        t.setText("찾은 검색 : 박물관 " + adapter.getCount() + "건, 전시회 " + adapter2.getCount() + "건");
                        t1.setVisibility(VISIBLE);
                        t2.setVisibility(VISIBLE);
                        list.setVisibility(VISIBLE);
                        list2.setVisibility(VISIBLE);
                        Utility.setListViewHeightBasedOnChildren(list);
                        Utility.setListViewHeightBasedOnChildren(list2);
                        return true;
                    default:
                        return false;
                }
            }
        });

        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
// TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                t1.setVisibility(INVISIBLE);
                t2.setVisibility(INVISIBLE);
                list.setVisibility(INVISIBLE);
                list2.setVisibility(INVISIBLE);
                adapter.filter(text);
                adapter2.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
// TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
// TODO Auto-generated method stub
            }
        });
    }

    public void find(View v) {
        t.setText("찾은 검색 : 박물관 " + adapter.getCount() + "건, 전시회 " + adapter2.getCount() + "건");
        t1.setVisibility(VISIBLE);
        t2.setVisibility(VISIBLE);
        list.setVisibility(VISIBLE);
        list2.setVisibility(VISIBLE);
        Utility.setListViewHeightBasedOnChildren(list);
        Utility.setListViewHeightBasedOnChildren(list2);
    }

    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE"+MainActivity.version+"/getJsonMuseumList.jsp";
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
    public ArrayList jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            JSONObject jsonObject = new JSONObject(pRecvServerPage);
            JSONArray jarray = jsonObject.getJSONArray("museums");

            // 받아온 pRecvServerPage를 분석하는 부분
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                if(jObject != null) { //museum 데이터 객체에 파싱한 값 저장.
                    msData = new Museum();
                    msData.setMs_no(jObject.getString("ms_no"));
                    msData.setMs_name(jObject.getString("ms_name"));
                    msData.setMs_rating(jObject.getString("ms_rating"));
                    msData.setMs_exp(jObject.getString("ms_exp"));
                    msData.setMs_url(jObject.getString("ms_url"));
                    msData.setMs_like(jObject.getString("ms_like"));
                    msData.setMs_address(jObject.getString("ms_address"));
                    msData.setMs_holiday(jObject.getString("ms_holiday"));
                    msData.setMs_Img(jObject.getString("ms_img"));
                    msData.setMs_operating(jObject.getString("ms_operating"));
                    msData.setMs_phone(jObject.getString("ms_tel"));
                    arraylist.add(msData);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for(int i=0; i<jarray.length(); i++){
                Log.i("JSON을 파싱한 데이터 출력해보기"+i+" : ", msData.toString());
            }
            return arraylist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String SendByHttp2(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE" + MainActivity.version + msg;
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

    public ArrayList jsonParserList2(String pRecvServerPage) {
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
                    arraylist2.add(exData);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for(int i=0; i<jarray.length(); i++){
                Log.i("JSON을 파싱한 데이터 출력해보기"+i+" : ", exData.toString());
            }
            return arraylist2;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}