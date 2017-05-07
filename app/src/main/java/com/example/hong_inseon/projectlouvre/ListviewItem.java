package com.example.hong_inseon.projectlouvre;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import java.text.Collator;
import java.util.Comparator;

public class ListviewItem extends AppCompatActivity {

    // 리스트 정보를 담을 객체 생성
    // 사진
    public Bitmap mPhoto;
    // 진행상황
    public String mProcess;
    // 주제
    public String mSubject;

    //알파벳 순으로 정렬
    public static final Comparator<ListviewItem> ALPHA_COMPARATOR = new Comparator<ListviewItem>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(ListviewItem mListDate_1, ListviewItem mListDate_2) {
            return sCollator.compare(mListDate_1.mSubject, mListDate_2.mSubject);
        }
    };
/*
    public void setIcon(Drawable icon){
        iconDrawable = icon;
    }

    public void setTitlename(String title){
        titleStr = title;
    }

    public void setSubjectStr(String subject){
        titleStr = subject;
    }

    public Drawable getIcon(){
        return this.iconDrawable;
    }

    public String getTitlename(){
        return this.titleStr;
    }

    public String getSubjectStr(){
        return this.subjectStr;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_item);

        listView = (ListView)findViewById(R.id.ListView);
    }
*/



}