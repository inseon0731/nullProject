package com.example.hong_inseon.projectlouvre;



import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hong_inseon.projectlouvre.dao.Review;

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

public class MenuTabFrag3 extends Fragment {
    private ListView mListView = null;
    private ListViewAdapterReview mAdapter = null;
    private ArrayList<Review> reviewlist = new ArrayList<Review>();

    Review reviewData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String result = SendByHttp("/getJsonReview.jsp"); // 메시지를 서버에 보냄

        Log.i("서버에서 받은 전체 내용 : ", result);
        reviewlist = jsonParserList(result);

        final View view = inflater.inflate(R.layout.activity_list, container, false);

        mListView = (ListView) view.findViewById(R.id.mList);


        mAdapter = new ListViewAdapterReview(view.getContext(), reviewlist);
        mListView.setAdapter(mAdapter);

        return view;
    }

    private String SendByHttp(String msg) {

        if (msg == null)
            msg = "";

        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE" + MainActivity.version + msg + "?mn="+ MainActivity.mn + "&en=" + MainActivity.en;
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
            JSONArray jarray = jsonObject.getJSONArray("reviews");

            // 받아온 pRecvServerPage를 분석하는 부분
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                if (jObject != null) { //museum 데이터 객체에 파싱한 값 저장.
                    reviewData = new Review();
                    reviewData.setReview_no(jObject.getInt("review_no"));
                    reviewData.setMs_no(jObject.getInt("ms_no"));
                    reviewData.setEx_no(jObject.getInt("ex_no"));
                    reviewData.setReview_rating((float)jObject.getDouble("review_rating"));
                    reviewData.setReview_date(jObject.getString("review_date"));
                    reviewData.setReview_content(jObject.getString("review_content"));
                    reviewlist.add(reviewData);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for (int i = 0; i < jarray.length(); i++) {
                Log.i("JSON을 파싱한 데이터 출력해보기" + i + " : ", reviewData.toString());
            }
            return reviewlist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
