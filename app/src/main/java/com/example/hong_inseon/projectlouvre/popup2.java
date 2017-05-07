package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class popup2 extends AppCompatActivity {
    Button button;
    SeekBar seekbar;
    public static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup2);

        seekbar = (SeekBar) findViewById(R.id.seekBar2);

        mp = MediaPlayer.create(this, R.raw.music2);
        mp.start();
        Thread();
        mp.setLooping(true);

        button = (Button) findViewById(R.id.popupB2);

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
            }
        });
    }

    /*public void onClickClosePopup3(View v) {
        switch (v.getId()) {
            case R.id.b://
                mp.stop();
                this.finish();
                break;
        }
    }*/

    public void onClickPopupB2(View v) {
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
            button.setText("start");
            seekbar.setProgress(0);
        } else {
            // 음악을 실행합니다
            mp.start();
            button.setText("stop");

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

    public void g2c(View v) {
        Intent data = new Intent();
        data.putExtra("value1", 14);
        data.putExtra("value2", 1);
        //mp.stop();
        setResult(2,data);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        this.finish();
    }
}
