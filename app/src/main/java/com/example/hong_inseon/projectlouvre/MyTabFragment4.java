package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 박명준 on 2017-03-10.
 */

public class MyTabFragment4 extends Fragment {
    private ListView mListView = null;
    private MyTabFragment4.ListViewAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_list, container, false);

        mListView = (ListView)view.findViewById(R.id.mList);

        mAdapter = new MyTabFragment4.ListViewAdapter(view.getContext());
        mListView.setAdapter(mAdapter);

        mAdapter.addItem("15 ","열다섯번째 공지사항","04/10");
        mAdapter.addItem("14 ","열네번째 공지사항","04/10");
        mAdapter.addItem("13 ","열세번째 공지사항","04/09");
        mAdapter.addItem("12 ","열두번째 공지사항","04/09");
        mAdapter.addItem("11 ","열한번째 공지사항","04/08");
        mAdapter.addItem("10 ","열번째 공지사항","04/07");
        mAdapter.addItem(" 9 ","아홉번째 공지사항","04/06");
        mAdapter.addItem(" 8 ","여덟번째 공지사항","04/06");
        mAdapter.addItem(" 7 ","일곱번째 공지사항","04/05");
        mAdapter.addItem(" 6 ","여섯번째 공지사항","04/05");
        mAdapter.addItem(" 5 ","다섯번째 공지사항","04/03");
        mAdapter.addItem(" 4 ","네번째 공지사항","04/01");
        mAdapter.addItem(" 3 ","세번째 공지사항","03/10");
        mAdapter.addItem(" 2 ","두번째 공지사항","03/05");
        mAdapter.addItem(" 1 ","첫번째 공지사항","02/10");

        return view;
    }

    private class ViewHolder {
        public TextView mNo;
        public TextView mTitle;
        public TextView mDate;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;

        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<Notice> mListviewItem = new ArrayList<Notice>();

        // ListViewAdapter의 생성자
        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return mListviewItem.size();
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return mListviewItem.get(position);
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addItem(String N, String T, String D) {
            Notice addInfo = null;
            addInfo = new Notice();
            addInfo.mNo = N;
            addInfo.mTitle = T;
            addInfo.mDate = D;

            mListviewItem.add(addInfo);
        }

        public void remove(int position) {
            mListviewItem.remove(position);
            dataChange();
        }

        /*public void sort() {
            Collections.sort(mListviewItem, ListviewItem.ALPHA_COMPARATOR);
            dataChange();
        }*/

        public void dataChange() {
            mAdapter.notifyDataSetChanged();
        }


        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyTabFragment4.ViewHolder holder;

            if (convertView == null) {
                holder = new MyTabFragment4.ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.noticeviewholder, null);

                holder.mNo = (TextView) convertView.findViewById(R.id.No);
                holder.mTitle = (TextView) convertView.findViewById(R.id.Title);
                holder.mDate = (TextView) convertView.findViewById(R.id.Date);

                convertView.setTag(holder);
            } else {
                holder = (MyTabFragment4.ViewHolder) convertView.getTag();
            }

            Notice mItem = mListviewItem.get(position);

            holder.mNo.setText(mItem.mNo);
            holder.mTitle.setText(mItem.mTitle);
            holder.mDate.setText(mItem.mDate);

            return convertView;
        }
    }
}
