package com.example.hong_inseon.projectlouvre;
import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;

public class MapActivity extends AppCompatActivity {

    static int inf = 999999, position = 0, make = -1, dest= -1; // inf는 무한대 값
    static int start = -1, end = -1;
    static boolean seSet = false, isOpened = false, nn = false, se = false, mapSet = false;
    int g;
    private LayoutInflater inflater;
    private RelativeLayout LL;
    private boolean LayOpen =  false;
    public static int opennum = 0;

    static int[][] weightMatrix = {
            //  0    1    2    3    4    5    6    7    8    9    10   11   12   13   14
        /* 0 */ {   0, 260, inf, 230, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf},
        /* 1 */ { 260,   0, 220, inf, inf, 570, inf, inf, inf, inf, inf, inf, inf, inf, inf},
        /* 2 */ { inf, 220,   0, inf, inf, inf, 570, inf, inf, inf, inf, inf, inf, inf, inf},
        /* 3 */ { 230, inf, inf,   0, 340, inf, inf, inf, inf, inf, inf, inf, inf,  80, inf},
        /* 4 */ { inf, inf, inf, 340,   0, 260, inf, 300, inf, inf, inf, inf, inf, inf, inf},
        /* 5 */ { inf, 570, inf, inf, 260,   0, 220, inf, inf, inf, inf, inf, inf, inf, 106},
        /* 6 */ { inf, inf, 570, inf, inf, 220,   0, inf, inf, 300, inf, inf, inf, inf, inf},
        /* 7 */ { inf, inf, inf, inf, 300, inf, inf,   0, 260, inf, 280, inf, inf, inf, inf},
        /* 8 */ { inf, inf, inf, inf, inf, inf, inf, 260,   0, 220, inf, 280, inf, inf, inf},
        /* 9 */ { inf, inf, inf, inf, inf, inf, 300, inf, 220,   0, inf, inf, 280, inf, inf},
        /*10 */ { inf, inf, inf, inf, inf, inf, inf, 280, inf, inf,   0, 260, inf, inf, inf},
        /*11 */ { inf, inf, inf, inf, inf, inf, inf, inf, 280, inf, 260,   0, 220, inf, inf},
        /*12 */ { inf, inf, inf, inf, inf, inf, inf, inf, inf, 280, inf, 220,   0, inf, inf},
        /*13 */ { inf, inf, inf,  80, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf},
        /*14 */ { inf, inf, inf, inf, inf, 106, inf, inf, inf, inf, inf, inf, inf, inf, inf},
    };

    float xf, yf, r;

    private Point now;
    private Point[] pa;
    int[] path;
    private Paint mPaint;
    private FrameLayout ll;

    private final static float diagonal = 1721.56818f;
    private final static int WM = 726, HM = 1561;
    private final static float WMd = 726.0f, HMd = 1561.0f;

    private View vv;
    private View alongLocate;

    private Intent intent3, intent2, intent1;

    //private static int count = 0;

    public static double dis[] = {0,0,0,0}, dis2[] = {0,0};
    private double [][]dis22 = new double[3][15];
    private int i=0, vw;
    private int Xc=0, Yc=0,Xp=0,Yp=0;
    private float z[] = {1.0f,0.75f,150f};//3번째는 높이
    private float  XX=0, YY=0;
    private ImageView view;
    private int rwidth, rheight;

    private double place[][] = {{300.0d,450.0d},{550.0d,880.0d},{700.0d,1380.0d}};//작품들의 위치를 표시 가로 50에 세로 100을 빼야 한다
    private double placeR[][] = {{place[0][0]/WMd, place[0][1]/HMd},{place[1][0]/WMd, place[1][1]/HMd},{place[2][0]/WMd, place[2][1]/HMd}};

    private BluetoothManager bm;    //기본적으로 존재
    private BluetoothAdapter mba;   //블루스트 탐색과 연결을 담당

    private FrameLayout.LayoutParams kkk;       //이미지 뷰를 받음

    public class BleList extends BaseAdapter{//리스트뷰 어뎁터 선언
        private ArrayList<BluetoothDevice> devices;
        private ArrayList<Integer> RSSIs;
        private LayoutInflater inflater;

        public BleList(){
            super();
            devices = new ArrayList<BluetoothDevice>();
            RSSIs = new ArrayList<Integer>();
            inflater = ((Activity) MapActivity.this).getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device,int rssi){
            if(!devices.contains(device)){
                devices.add(device);
                RSSIs.add(rssi);
            }
            else{
                RSSIs.set(devices.indexOf(device),rssi);
            }
        }

        public void clear(){
            devices.clear();
            RSSIs.clear();
        }

        @Override
        public int getCount() {
            return devices.size();
        }

        @Override
        public Object getItem(int position) {
            return devices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(android.R.layout.two_line_list_item,null);
                viewHolder.deviceName = (TextView) convertView.findViewById(android.R.id.text1);
                viewHolder.deviceRssi = (TextView) convertView.findViewById(android.R.id.text2);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String deviceName = devices.get(position).getName();
            int rssi = RSSIs.get(position);

            viewHolder.deviceName.setText(deviceName != null && deviceName.length() > 0 ?deviceName:"알 수 없는 장치");
            viewHolder.deviceRssi.setText(String.valueOf(rssi));
            Log.d("scan","rssi : "+rssi);
            try {
                if (position < 3)
                    dis[position] = rtd(rssi);
            }
            catch(Exception e) { }

            return convertView;
        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceRssi;
    }

    private ListView lv;
    private Button b;
    private BleList bleList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //inflater설정하는 부분,그리고 위치 설정부분
        g=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 93, MapActivity.this.getResources().getDisplayMetrics());
        LL = (RelativeLayout)findViewById(R.id.bloonCh);
        inflater = (LayoutInflater)getSystemService(this.LAYOUT_INFLATER_SERVICE);

        position = 0;
        make = -1;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(7);

        ll = (FrameLayout)findViewById(R.id.main);

        vv = new MyView(this);
        alongLocate = new ViewAlongLoacte(this);

        r=getStatusBarHeight()/2;

        intent1 = new Intent(this, popup1.class);
        intent2 = new Intent(this, popup2.class);
        intent3 = new Intent(this, popup3.class);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(MapActivity.this, "접근이 허가되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //Toast.makeText(MapActivity.this, "접근이 거절되었습니다.\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("접근을 거절하면 서비스 이용이 불가능합니다.\n\n접근을 [Setting] > [Permission]에서 허가해주세요")
                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        //블루투스 초기화 부분
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BLUETOOTH},1);
        bm = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mba = bm.getAdapter();
        if(mba == null || !mba.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent); //REQUEST_ENABLE_BT
            Toast.makeText(this, "블루투스를 켜주세요", Toast.LENGTH_SHORT).show();
            finish();
        }//블루투스가 켜져 있지 않을시 키도록 함
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "No LE Support.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }//BLE를 지원 않는 경우 종료


        bleList = new BleList();
        lv = (ListView) findViewById(R.id.locationE);
        lv.setAdapter(bleList);
        mba.startDiscovery();
        mba.startLeScan(leScanCallback);

        //지도 설정을 위한 부분
        DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();        //각 기기의 가로, 세로 비율을 알기 위한 매개변수

        rwidth = disp.widthPixels;                   //가로의의 좌우 여백을 조금 없앤다
        rheight = disp.heightPixels;                 //세로의 상하 여백을 조금 없앤다.
        XX = WMd/HMd*(float)rheight;                  //가로세로비율을 가로에 적용
        YY = HMd/WMd*(float)rwidth;                   //가로세로비율을 세로에 적용
        view = (ImageView) findViewById(R.id.location1);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        vw = view.getMeasuredWidth();
        if(WMd/HMd < ((float)rwidth/rheight)) {      //만약 가로에 여백이 남으면 - 총 화면 크기 비교
            Xc = (int) ((rwidth - XX) / 2);                   //Xc를 정함
            xf = XX-vw;
            yf = (float)rheight-(float)getStatusBarHeight()-vw;
        } else if(WMd/HMd > ((float)rwidth/rheight)) {  //만약 세로에 여백이 남으면
            Yc = (int) (((float)rheight - YY - (float)getStatusBarHeight()) / 2);                //Yc를 정함
            xf = (float)rwidth-vw;
            yf = YY-vw;
        }
        //같은 비율일시에는 그대로 진행해도 된다. 여기까지가 핸드폰 크기에따른 설정

        //위치 설정을 위한 부분
        now = new Point((float)Xc + xf*z[0]+vw/2, (float)Yc + yf*z[1]+vw/2);

        pa = new Point[15];
        float [] pxd = {220.0f, 480.0f, 700.0f, 220.0f, 220.0f, 480.0f, 700.0f, 220.0f, 480.0f, 700.0f, 220.0f, 480.0f, 700.0f,300.0f, 550.0f};
        float [] pyd = {230.0f, 230.0f, 230.0f, 460.0f, 800.0f, 800.0f, 800.0f, 1100.0f, 1100.0f, 1100.0f, 1380.0f, 1380.0f, 1380.0f, 450.0f, 880.0f};
        float[] pxdc = new float[pxd.length];
        float[] pydc = new float[pyd.length];
        for(int i=0;i<pa.length;i++) {
            pxdc[i] = (float)Xc+pxd[i]/WMd*xf+vw/2;
            pydc[i] = (float)Yc+pyd[i]/HMd*yf+vw/2;
            pa[i] = new Point(pxdc[i],pydc[i]);
        }

        //경로를 레이아웃에 추가하는 부분
        ll.addView(vv);
        ll.addView(alongLocate);

        //작품들의 위치를 선정하는 부분
        kkk = (FrameLayout.LayoutParams) view.getLayoutParams();
        Xp = (int) (xf * placeR[0][0]);
        Yp = (int) (yf * placeR[0][1]);
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);

        view = (ImageView) findViewById(R.id.location2);
        kkk = (FrameLayout.LayoutParams) view.getLayoutParams();
        Xp = (int) (xf * placeR[1][0]);
        Yp = (int) (yf * placeR[1][1]);
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);

        view = (ImageView) findViewById(R.id.location3);
        kkk = (FrameLayout.LayoutParams) view.getLayoutParams();
        Xp = (int) (xf * placeR[2][0]);
        Yp = (int) (yf * placeR[2][1]);
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);

        view = (ImageView)findViewById(R.id.location);
        kkk = (FrameLayout.LayoutParams)view.getLayoutParams();
        Xp = (int) (xf * z[0]);
        Yp = (int) (yf * z[1]);                               //위의 조건문에서 바뀐 Xc나 Yc에 대해 Xp와 Yp를 정한다.
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);               //마진 설정을 한다. 즉 현재 자신의 위치 표시
    }

    class MyView extends View {
        public MyView(Context c, AttributeSet a) {
            super(c, a);
        }

        public MyView(Context c) {
            super(c);
        }

        @Override
        protected void onDraw(Canvas c) {
            try {
                c.drawCircle((float) pa[make].getPx(), (float) pa[make].getPy(), r, mPaint);
            }
            catch(Exception e)
            {

            }
            //화살표 그리기 (나중에)
        }
    }

    class ViewAlongLoacte extends View {
        public ViewAlongLoacte(Context c, AttributeSet a) {
            super(c, a);
        }

        public ViewAlongLoacte(Context c) {
            super(c);
        }

        @Override
        protected void onDraw(Canvas c) {
            try {
                for (int i = 0; i < path.length - 1; i++) {
                    c.drawLine((float) pa[path[i]].getPx(), (float) pa[path[i]].getPy(), (float) pa[path[i + 1]].getPx(), (float) pa[path[i + 1]].getPy(), mPaint);
                }
                if (nn) {
                    if (se)
                        c.drawLine((float) now.getPx(), (float) now.getPy(), (float) pa[path[0]].getPx(), (float) pa[path[0]].getPy(), mPaint);
                    else
                        c.drawLine((float) now.getPx(), (float) now.getPy(), (float) pa[path[path.length - 1]].getPx(), (float) pa[path[path.length - 1]].getPy(), mPaint);
                }
                nn = false;
            }
            catch(Exception e)
            {

            }
            //화살표 그리기 (나중에)
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d("scan",device.getName() + " RSSI :" + rssi + " Record " + scanRecord);
            if(device.getAddress().equals("F0:9D:50:D7:49:6C")) {
                bleList.addDevice(device, rssi);
                bleList.notifyDataSetChanged(); //beacon1
            }
            if(device.getAddress().equals("E5:F1:43:74:F5:76")) {
                bleList.addDevice(device, rssi);
                bleList.notifyDataSetChanged(); //beacon2
            }
            if(device.getAddress().equals("DA:DF:3A:F3:52:38")) {
                bleList.addDevice(device, rssi);
                bleList.notifyDataSetChanged(); //beacon3
            }
            /*if(device.getAddress().equals("C2:7D:CD:BE:0C:F4") && bleList.getCount() == 3) {
                bleList.addDevice(device, rssi);
                bleList.notifyDataSetChanged(); // beacon4
            }*/

            view = (ImageView)findViewById(R.id.location);
            kkk = (FrameLayout.LayoutParams)view.getLayoutParams();
            Log.v("@@@", bleList.getCount() + "");

            try {
                if(!LayOpen) {
                    for (int k = 0; k < bleList.getCount(); k++) {
                        if (bleList.RSSIs.get(k) >= -55) {
                            if (bleList.devices.get(k).getAddress().equals("F0:9D:50:D7:49:6C")) {
                                LayOpen = true;
                                opennum = 1;
                                z[0] = 250.0f / WMd;
                                z[1] = 450.0f / HMd;
                                now = new Point((float) Xc + xf * z[0] + vw / 2, (float) Yc + yf * z[1] + vw / 2);
                                Xp = (int) (xf * z[0]);
                                Yp = (int) (yf * z[1]);                               //위의 조건문에서 바뀐 Xc나 Yc에 대해 Xp와 Yp를 정한다.
                                kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);
                                intent1.putExtra("value", make);
                                startActivityForResult(intent1, 1);
                            }
                            if (bleList.devices.get(k).getAddress().equals("E5:F1:43:74:F5:76")) {
                                z[0] = 500.0f / WMd;
                                z[1] = 880.0f / HMd;
                                now = new Point((float) Xc + xf * z[0] + vw / 2, (float) Yc + yf * z[1] + vw / 2);
                                Xp = (int) (xf * z[0]);
                                Yp = (int) (yf * z[1]);                               //위의 조건문에서 바뀐 Xc나 Yc에 대해 Xp와 Yp를 정한다.
                                kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);
                                intent2.putExtra("value", make);
                                LayOpen = true;
                                opennum = 2;
                                startActivityForResult(intent2, 2);
                            }
                            if (bleList.devices.get(k).getAddress().equals("DA:DF:3A:F3:52:38")) {
                                z[0] = 650.0f / WMd;
                                z[1] = 1380.0f / HMd;
                                now = new Point((float) Xc + xf * z[0] + vw / 2, (float) Yc + yf * z[1] + vw / 2);
                                Xp = (int) (xf * z[0]);
                                Yp = (int) (yf * z[1]);                               //위의 조건문에서 바뀐 Xc나 Yc에 대해 Xp와 Yp를 정한다.
                                kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);
                                intent3.putExtra("value", make);
                                LayOpen = true;
                                opennum = 3;
                                startActivityForResult(intent3, 3);
                            } else {
                                Log.v("@@", "no beacon here\n");
                            }
                        }

                        /*if(bleList.RSSIs.get(k) < -55)
                        {
                            if (bleList.devices.get(k).getAddress().equals("F0:9D:50:D7:49:6C")) {
                                popup1.mp.stop();
                            }
                            if (bleList.devices.get(k).getAddress().equals("E5:F1:43:74:F5:76")) {
                                popup2.mp.stop();
                            }
                            if (bleList.devices.get(k).getAddress().equals("DA:DF:3A:F3:52:38")) {
                                popup3.mp.stop();
                            }
                        }*/
                    }
                }
            }
            catch (Exception e)
            {
                Log.v("@@@@","catchLE");
            }
        }
    };

    public double rtd(int a) {
        Log.d("scan",Math.pow(7.0,-a/25.0)+"cm");//원래 10의 -a/20.0 승
        return 3.0*10.0/(4.0*3.14*2.4)*Math.pow(9.0,-a/22.0); //
    } //rssiToDistance(cm단위) a가0이면 약 0.995

    public void map(View v) {

    }

    public void reset(View v) {
        start = -1;
        end = -1;
        seSet = false;
        vv.setVisibility(INVISIBLE);
        alongLocate.setVisibility(INVISIBLE);
        vv.invalidate();
        LayOpen = false;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void oneP(View v)
    {
        if(isOpened)
            return;


        inflater.inflate(R.layout.bloon, LL, true);
        isOpened = true;

        int px,py;
        px = (int)(xf * placeR[0][0]) + Xc +vw/2-g;
        py = (int)(yf * placeR[0][1]) + Yc-g*5/6;
        kkk = (FrameLayout.LayoutParams) LL.getLayoutParams();

        kkk.setMargins(px, py, 0, 0);

        TextView textMarker = (TextView)findViewById(R.id.textMarker);
        Button bs = (Button)findViewById(R.id.buttonSF);
        Button bd = (Button)findViewById(R.id.buttonDR);
        Button bc = (Button)findViewById(R.id.buttonClose);
        textMarker.setText("First");
        if(seSet) {
            bs.setText("도착");
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    end = 13;
                    path = Dikstra.dikstra(weightMatrix, start, end);
                    alongLocate.invalidate();
                    vv.setVisibility(View.INVISIBLE);
                    alongLocate.setVisibility(View.VISIBLE);
                    LL.removeAllViews();
                    seSet = false;
                    isOpened = false;
                }
            });
        }
        else {
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start = 13;
                    LL.removeAllViews();
                    seSet = true;
                    isOpened = false;
                    alongLocate.setVisibility(View.INVISIBLE);
                }
            });
        }

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL.removeAllViews();
                isOpened = false;
                alongLocate.setVisibility(INVISIBLE);
                intent1.putExtra("value",make);
                onStopMp(opennum);
                startActivityForResult(intent1,1);
            }
        });
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nowMP.setMP(false);
                try{
                    popup1.mp.stop();
                }catch(Exception e) {

                }
                LL.removeAllViews();
                isOpened = false;
            }
        });
    }

    public void twoP(View v)
    {
        if(isOpened)
            return;

        inflater.inflate(R.layout.bloon, LL, true);
        isOpened = true;
        kkk = (FrameLayout.LayoutParams) LL.getLayoutParams();
        kkk.setMargins((int)(xf * placeR[1][0]) + Xc +vw/2-g,(int)(yf * placeR[1][1]) + Yc-g*5/6,0,0);

        TextView textMarker = (TextView)findViewById(R.id.textMarker);
        Button bs = (Button)findViewById(R.id.buttonSF);
        Button bd = (Button)findViewById(R.id.buttonDR);
        Button bc = (Button)findViewById(R.id.buttonClose);
        textMarker.setText("Second");
        if(seSet) {
            bs.setText("도착");
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    end = 14;
                    path = Dikstra.dikstra(weightMatrix, start, end);
                    alongLocate.invalidate();
                    alongLocate.setVisibility(View.VISIBLE);
                    LL.removeAllViews();
                    seSet = false;
                    isOpened = false;
                }
            });
        }
        else {
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start = 14;
                    LL.removeAllViews();
                    seSet = true;
                    isOpened = false;
                    alongLocate.setVisibility(View.INVISIBLE);
                }
            });
        }

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL.removeAllViews();
                isOpened = false;
                vv.setVisibility(View.INVISIBLE);
                alongLocate.setVisibility(View.INVISIBLE);
                intent2.putExtra("value",make);
                onStopMp(opennum);
                startActivityForResult(intent2,2);
            }
        });
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL.removeAllViews();
                isOpened = false;
                try {
                    popup2.mp.stop();
                }catch(Exception e) {

                }
            }
        });
    }

    public void threeP(View v)
    {
        if(isOpened)
            return;

        int px,py;
        px = (int)(xf * placeR[2][0]) + Xc +vw/2-g;
        py = (int)(yf * placeR[2][1]) + Yc-g*5/6;
        kkk = (FrameLayout.LayoutParams) LL.getLayoutParams();
        if(px < 0)
            px = 0;
        else if( px > rwidth - 2*g)
            px = rwidth - 2*g;
        if(py < 0)
            py = 0;
        kkk.setMargins(px, py, 0, 0);
        if(px != (int)(xf * placeR[2][0]) + Xc +vw/2-g)
            inflater.inflate(R.layout.bloone, LL, true);
        else
            inflater.inflate(R.layout.bloon, LL, true);
        isOpened = true;

        TextView textMarker = (TextView)findViewById(R.id.textMarker);
        Button bs = (Button)findViewById(R.id.buttonSF);
        Button bd = (Button)findViewById(R.id.buttonDR);
        Button bc = (Button)findViewById(R.id.buttonClose);
        textMarker.setText("Third");
        if(seSet) {
            bs.setText("도착");
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    end = 12;
                    path = Dikstra.dikstra(weightMatrix, start, end);
                    alongLocate.invalidate();
                    vv.setVisibility(View.INVISIBLE);
                    alongLocate.setVisibility(View.VISIBLE);
                    LL.removeAllViews();
                    seSet = false;
                    isOpened = false;
                }
            });
        }
        else {
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start = 12;
                    LL.removeAllViews();
                    seSet = true;
                    isOpened = false;
                    alongLocate.setVisibility(View.INVISIBLE);
                }
            });
        }

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL.removeAllViews();
                isOpened = false;
                alongLocate.setVisibility(View.INVISIBLE);
                intent3.putExtra("value",make);
                onStopMp(opennum);
                startActivityForResult(intent3,3);
            }
        });
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL.removeAllViews();
                isOpened = false;
                try{
                    popup3.mp.stop();
                }catch(Exception e) {

                }
            }
        });
    }

    public void onClick(View v)
    {
        if(isOpened)
            return;
        switch(v.getId())
        {
            case R.id.location:
                int px=0,py=0;
                px = Xc + (int)(xf*z[0]) + vw/2 - g;
                py = Yc + (int)(yf*z[1]) -g*5/6;
                kkk = (FrameLayout.LayoutParams) LL.getLayoutParams();
                if(px < 0)
                    px = 0;
                else if( px > rwidth - 2*g)
                    px = rwidth - 2*g;
                if(py < 0)
                    py = 0;
                kkk.setMargins(px, py, 0, 0);
                inflater.inflate(R.layout.bloonme, LL, true);
                nn = true;
                isOpened = true;

                TextView textMarker = (TextView)findViewById(R.id.textMarker);
                Button bs = (Button)findViewById(R.id.buttonSF);
                Button bc = (Button)findViewById(R.id.buttonClose);
                if(seSet) {
                    bs.setText("도착");
                    bs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LL.removeAllViews();
                            seSet = false;
                            isOpened = false;
                            end = 0;
                            double min = Point.getD(now, pa[0]);
                            for (int i = 1; i < 15; i++) {
                                if (min > Point.getD(now, pa[i])) {
                                    min = Point.getD(now, pa[i]);
                                    end = i;
                                }
                            }
                            path = Dikstra.dikstra(weightMatrix, start, end);
                            if(path != null && path.length != 1) {
                                if (Point.getD(now, pa[path[1]]) < Point.getD(pa[path[0]], pa[path[1]])) {
                                    for (int i = 0; i < path.length - 1; i++)
                                        path[i] = path[i + 1];
                                }
                            }
                            se = false;
                            if(start == end)
                                nn = false;
                            else
                                nn = true;
                            alongLocate.invalidate();
                            alongLocate.setVisibility(View.VISIBLE);
                        }
                    });
                }
                else {
                    bs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            double min = Point.getD(now, pa[0]);
                            start = 0;
                            for (int i = 1; i < 15; i++) {
                                if (min > Point.getD(now, pa[i])) {
                                    min = Point.getD(now, pa[i]);
                                    start = i;
                                }
                            }
                            se = true;
                            nn = true;
                            LL.removeAllViews();
                            seSet = true;
                            isOpened = false;
                            alongLocate.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                bc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LL.removeAllViews();
                        isOpened = false;
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch(requestCode)
            {
                case 1:
                    make = data.getIntExtra("value1", -1);
                    opennum = 1;
                    break;
                case 2:
                    make = data.getIntExtra("value1", -1);
                    opennum = 2;
                    break;
                case 3:
                    make = data.getIntExtra("value1", -1);
                    opennum = 3;
                    break;
            }
            LayOpen = false;
            vv.invalidate();
            vv.setVisibility(View.VISIBLE);
        }
        catch(Exception e)
        {
            make = -1;
        }
    }

    public void onBackPressed() {
        mba.stopLeScan(leScanCallback);
        bleList.clear();
        bleList.notifyDataSetChanged();
        this.finish();
    }

    @Override
    public void onStart() {
        LayOpen = false;
        super.onStart();
    }

    @Override
    public void onResume() {
        LayOpen = false;
        super.onResume();
    }

    public void onStopMp(int pos) {
        if(pos == 1)
            popup1.mp.stop();
        else if(pos == 2)
            popup2.mp.stop();
        else if(pos == 3)
            popup3.mp.stop();
        opennum = 0;
    }
}