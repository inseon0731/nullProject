package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by 박명준 on 2017-03-10.
 */

public class MyTabFragment4 extends Fragment {
    private ListView mListView = null;
    private ListViewAdapterNotice mAdapter = null;
    private ArrayList<Notice> noticelist = new ArrayList<Notice>();

    Notice noteData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String result = SendByHttp("/getJsonNoticeList.jsp"); // 메시지를 서버에 보냄

        Log.i("서버에서 받은 전체 내용 : ", result);
        //String[][] parsedData = jsonParserList(); // 받은 메시지를 json 파싱결과를 museum객체에 저장
        noticelist = jsonParserList(result);

        final View view = inflater.inflate(R.layout.activity_list, container, false);

        mListView = (ListView) view.findViewById(R.id.mList);


        mAdapter = new ListViewAdapterNotice(view.getContext(), noticelist);
        mListView.setAdapter(mAdapter);

        return view;
    }

    private String SendByHttp(String msg) {

        if (msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE" + MainActivity.version + "/getJsonNoticeList.jsp";
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
            client.getConnectionManager().shutdown();    // 연결 지연 종료
            return "";
        }
    }

    /**
     * 받은 JSON 객체를 파싱하는 메소드
     *
     * @param pRecvServerPage
     * @return
     */
    public ArrayList jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            JSONObject jsonObject = new JSONObject(pRecvServerPage);
            JSONArray jarray = jsonObject.getJSONArray("notices");

            // 받아온 pRecvServerPage를 분석하는 부분
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                if (jObject != null) { //museum 데이터 객체에 파싱한 값 저장.
                    noteData = new Notice();
                    noteData.setNote_title(jObject.getString("note_tile"));
                    noteData.setMs_name(jObject.getString("ms_name"));
                    noteData.setNote_content(jObject.getString("note_content"));
                    noteData.setNote_date(jObject.getString("note_date"));
                    noticelist.add(noteData);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for (int i = 0; i < jarray.length(); i++) {
                Log.i("JSON을 파싱한 데이터 출력해보기" + i + " : ", noteData.toString());
            }
            return noticelist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
