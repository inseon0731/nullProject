package com.example.hong_inseon.projectlouvre;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class popup1 extends AppCompatActivity {
    ImageView button;
    SeekBar seekbar;
    public static MediaPlayer mp;
    //private int getnum;
    private TextView playstart;
    private TextView playlast;

    int m, time, timel;
    String str;
    //int a;

    /*public class BleList {//리스트뷰 어뎁터 선언
        private ArrayList<BluetoothDevice> devices;
        private ArrayList<Integer> RSSIs;

        public BleList() {
            super();
            devices = new ArrayList<BluetoothDevice>();
            RSSIs = new ArrayList<Integer>();
        }

        public void addDevice(BluetoothDevice device, int rssi) {
            if (!devices.contains(device)) {
                devices.add(device);
                RSSIs.add(rssi);
            } else {
                RSSIs.set(devices.indexOf(device), rssi);
            }
        }

        public void clear() {
            devices.clear();
            RSSIs.clear();
        }
    }

    private BleList bleList;

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d("scan",device.getName() + " RSSI :" + rssi + " Record " + scanRecord);
            if(device.getAddress().equals("F0:9D:50:D7:49:6C")) {
                bleList.addDevice(device, rssi);
            }
            try {
                if (bleList.RSSIs.get(0) < -55) {
                    mp.stop();
                    onBackPressed();
                }
            } catch(Exception E) {

            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup1);

        //Intent i = getIntent();
        //getnum = i.getIntExtra("value", -1);



        seekbar = (SeekBar) findViewById(R.id.seekBar1);
        playstart = (TextView)findViewById(R.id.textplaystart1);
        playlast = (TextView)findViewById(R.id.textplaylast1);

        mp = MediaPlayer.create(popup1.this, R.raw.music4);

        mp.start();
        Thread();

        button = (ImageView) findViewById(R.id.playpause1);

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
                time= mp.getCurrentPosition()/1000;
                m = time/60;
                time = time%60;
                if(time >= 10)
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
            //button.setText("start");
            seekbar.setProgress(0);
        } else {
            mp.start();
            //button.setText("stop");
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
        mp.stop();
        super.onPause();
    }

    @Override
    public void onStop() {
        mp.stop();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        this.finish();
    }


}