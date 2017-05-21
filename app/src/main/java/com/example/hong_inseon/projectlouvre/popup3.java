package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hong_inseon.projectlouvre.dao.Museum;
import com.example.hong_inseon.projectlouvre.dao.Piece;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class popup3 extends AppCompatActivity {
    ImageView button;
    SeekBar seekbar;
    public static MediaPlayer mp;

    private TextView playstart, playlast, cont;
    int m, time, timel;
    String str;

    Piece pcData, getPcData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup3);

        seekbar = (SeekBar) findViewById(R.id.seekBar3);
        playstart = (TextView)findViewById(R.id.textplaystart3);
        playlast = (TextView)findViewById(R.id.textplaylast3);

        mp = MediaPlayer.create(this, R.raw.music6);
        mp.start();
        Thread();
        mp.setLooping(true);

        button = (ImageView) findViewById(R.id.playpause3);

        seekbar.setMax(mp.getDuration());

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                if (fromUser)
                    mp.seekTo(progress);
                time= mp.getCurrentPosition()/1000;
                m = time/60;
                time = time%60;
                if(time>= 10)
                    str = String.format("%d : %d", m, time);
                else
                    str = m + " : 0" + time;
                playstart.setText(str);
                timel= mp.getDuration()/1000 - mp.getCurrentPosition()/1000;
                m = timel/60;
                timel = timel%60;
                if(timel >= 10)
                    str = String.format("%d : %d", m, timel);
                else
                    str = m + " : 0" + timel;
                playlast.setText(str);
            }
        });

        String result = SendByHttp("/getJsonPiece.jsp"); // 메시지를 서버에 보냄

        Log.i("서버에서 받은 전체 내용 : ", result);
        //String[][] parsedData = jsonParserList(); // 받은 메시지를 json 파싱결과를 museum객체에 저장
        getPcData = jsonParser(result);

        cont = (TextView)findViewById(R.id.doroktext3);
        cont.setText(getPcData.getPc_name() + "\n" + getPcData.getPc_author() + "\n" + getPcData.getPc_make() + "\n" + getPcData.getPc_size() + "\n" + getPcData.getPc_exp());
    }

    /*public void onClickClosePopup3(View v) {
        switch (v.getId()) {
            case R.id.b://
                mp.stop();
                this.finish();
                break;
        }
    }*/

    public void onClickPopupB3(View v) {
        if (mp.isPlaying()) {
            // 음악을 정지합니다
            mp.stop();
            try {
                // 음악을 재생할경우를 대비해 준비합니다
                // prepare()은 예외가 2가지나 필요합니다
                mp.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 음악 진행 정도를 0, 즉 처음으로 되돌립니다
            mp.seekTo(0);

            // 버튼의 글자를 시작으로, 시크바를 처음으로 되돌립니다
            //button.setText("start");
            seekbar.setProgress(0);
        } else {
            // 음악을 실행합니다
            mp.start();
            //button.setText("stop");

            /**
             * 쓰래드를 돌려 1초마다 SeekBar를 움직이게 합니다
             */
            Thread();
        }
    }

    public void Thread(){
        Runnable task = new Runnable(){
            public void run(){
                /**
                 * while문을 돌려서 음악이 실행중일때 게속 돌아가게 합니다
                 */
                while(mp.isPlaying()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    /**
                     * music.getCurrentPosition()은 현재 음악 재생 위치를 가져오는 구문 입니다
                     */
                    seekbar.setProgress(mp.getCurrentPosition());
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void g3c(View v) {
        Intent data = new Intent();
        data.putExtra("value1", 12);
        data.putExtra("value2", 2);
        //mp.stop();
        setResult(3,data);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        this.finish();
    }

    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        //String URL = ServerUtil.SERVER_URL;
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE" + MainActivity.version + "/getJsonPiece.jsp?mn=1&en=1&pn=3";
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
    public Piece jsonParser(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            JSONObject jObject = new JSONObject(pRecvServerPage);

            pcData = new Piece();
            pcData.setMs_no(jObject.getString("ms_no"));
            pcData.setEx_no(jObject.getString("ex_no"));
            pcData.setPc_no(jObject.getString("pc_no"));
            pcData.setPc_name(jObject.getString("pc_name"));
            pcData.setPc_author(jObject.getString("pc_author"));
            pcData.setPc_make(jObject.getString("pc_make"));
            pcData.setPc_exp(jObject.getString("pc_exp"));
            pcData.setPc_img(jObject.getString("pc_img"));
            pcData.setPc_audio(jObject.getString("pc_audio"));
            pcData.setPc_size(jObject.getString("pc_size"));

            Log.i("JSON을 파싱한 데이터 출력해보기"+" : ", pcData.toString());

            return pcData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
