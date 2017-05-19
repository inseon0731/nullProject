package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class OptionP extends AppCompatActivity {
    EditText editsearch;
    TextView t,t1,t2;
    int[] musImage, exImage;
    String[] musName, exName, musAddr, exContext, exDate, musRating;
    ListView list, list2;
    ListViewAdapterMuseumS adapter;
    ListViewAdapterExhibitionS adapter2;
    ArrayList<MuseumS> arraylist = new ArrayList<MuseumS>();
    ArrayList<ExhibitionS> arraylist2 = new ArrayList<ExhibitionS>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_p);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        //액션바에서 가운데 맞춤을 해주는 것
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
        //위에는 이미지를 넣고 싶을때 사용

        list = (ListView) findViewById(R.id.listview);
        list2 = (ListView)findViewById(R.id.listview2);

        musName = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};
        musRating = new String[] { "4", "3", "5", "2", "4", "3", "4", "5", "1", "5"};
        musAddr = new String[] { "경기도 부천시 원미구", "서울특별시 강남구", "인천광역시 남동구 백범로 124번길",
                "강원도 홍천시", "인천광역시 연수구 옥련동", "부산광역시 어딘가", "미국 Los Angelous 인지 어딘지 모름"
                , "우주 안드로메다","이세상 어딘가에 있을거라고 믿는곳", "도서관 4층 일반자료실 노트북코너"};
        musImage = new int[] {R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www,
                R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www};

        exName = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};
        exContext = new String[] { "Beijing", "New Delhi", "Washington D.C.",
                "Jakarta", "Brazilia", "Islamabad", "Abuja", "Dacca",
                "Moskva", "Tokyo"};
        exDate = new String[] { "2017.02.28~2017.07.21", "2017.02.27~2017.07.21", "2017.02.28~2017.07.21",
                "2017.02.26~2017.07.21", "2017.02.25~2017.07.21", "2017.02.24~2017.07.21", "2017.02.23~2017.07.21"
                , "2017.02.22~2017.07.21","2017.02.21~2017.07.21", "2017.02.20~2017.07.21"};
        exImage = new int[] {R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www,
                R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www,R.drawable.www};

        for (int i =0; i<10; i ++){
            MuseumS ms = new MuseumS();
            ms.setImage(musImage[i]);
            ms.setName(musName[i]);
            ms.setRate(Float.parseFloat(musRating[i]));
            ms.setAddr(musAddr[i]);
            arraylist.add(ms);

            ExhibitionS ex = new ExhibitionS();
            ex.setImage(exImage[i]);
            ex.setName(exName[i]);
            ex.setCont(exContext[i]);
            ex.setDate(exDate[i]);
            arraylist2.add(ex);
        }

        adapter = new ListViewAdapterMuseumS(this, arraylist);
        adapter2 = new ListViewAdapterExhibitionS(this, arraylist2);

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
}