package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class popup1 extends AppCompatActivity {
    Button button;
    SeekBar seekbar;
    public static MediaPlayer mp;
    public static int rssi1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup1);

        seekbar = (SeekBar) findViewById(R.id.seekBar1);

        mp = MediaPlayer.create(popup1.this, R.raw.music1);
        mp.start();
        Thread();

        button = (Button) findViewById(R.id.popupB1);

        seekbar.setMax(mp.getDuration());

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                if (fromUser)
                    mp.seekTo(progress);
            }
        });
    }

    public void onClickPopupB1(View v) {
        if (mp.isPlaying()) {
            mp.stop();
            try {
                mp.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.seekTo(0);
            button.setText("start");
            seekbar.setProgress(0);
        } else {
            mp.start();
            button.setText("stop");
            Thread();
        }
    }

    public void Thread(){
        Runnable task = new Runnable(){
            public void run(){
                while(mp.isPlaying()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    seekbar.setProgress(mp.getCurrentPosition());
                    if(rssi1 < -55)
                    {
                        onBackPressed();
                    }
                    Log.d("@@@@@@", rssi1 + "");
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void g1c(View v) {
        Intent data = new Intent();
        data.putExtra("value1", 13);
        data.putExtra("value2", 0);
        setResult(1,data);
        this.finish();
    }

    @Override
    public void onPause() {
        //mp.stop();
        super.onPause();
    }

    @Override
    public void onStop() {
        //mp.stop();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        this.finish();
    }


}