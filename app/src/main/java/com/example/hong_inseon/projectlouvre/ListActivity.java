package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hong-Inseon on 2017. 2. 1..
 */

public class ListActivity extends AppCompatActivity {

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mListView = (ListView) findViewById(R.id.mList);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        //첫번째 방법
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        mAdapter.addItem(BitmapFactory.decodeResource(getResources(), R.drawable.louvre, options),
                "Musée du Louvre",
                "진행중");
        mAdapter.addItem(BitmapFactory.decodeResource(getResources(), R.drawable.orsay, options),
                "Musée d'Orsay",
                "예정");
        mAdapter.addItem(BitmapFactory.decodeResource(getResources(), R.drawable.alfons, options),
                "알폰스 무하, 모던 그래픽 디자인의 선구자",
                "진행중");

        mAdapter.addItem(BitmapFactory.decodeResource(getResources(), R.drawable.bazille, options),
                "Frédéric Bazille",
                "진행중");
        /*
        mAdapter.addItem(getResources().getDrawable(R.drawable.louvre, null),
                "Lourve 모나리자",
                "진행중");

        mAdapter.addItem(getResources().getDrawable(R.drawable.orsay, null),
                "Orsay 박물관입니다",
                "예정");
        */
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //ListviewItem mItem = mAdapter.mListviewItem.get(position);
                //Toast.makeText(ListActivity.this, mItem.mSubject, Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(this, ExhibitionInfoActivity.class);
                //startActivity(intent);

                Intent intent = new Intent(ListActivity.this, ExhibitionInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ViewHolder {
        public ImageView mPhoto;
        public TextView mProcess;
        public TextView mSubject;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;

        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<ListviewItem> mListviewItem = new ArrayList<ListviewItem>();

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
        public void addItem(Bitmap photo, String mSubject, String mProcess) {
            ListviewItem addInfo = null;
            addInfo = new ListviewItem();
            addInfo.mPhoto = photo;
            addInfo.mSubject = mSubject;
            addInfo.mProcess = mProcess;

            mListviewItem.add(addInfo);
        }

        public void remove(int position){
            mListviewItem.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListviewItem, ListviewItem.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }


        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListActivity.ViewHolder holder;

            if(convertView == null) {
                holder = new ListActivity.ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_listview_item, null);

                holder.mPhoto = (ImageView) convertView.findViewById(R.id.mPhoto);
                holder.mSubject = (TextView) convertView.findViewById(R.id.mSubject);
                holder.mProcess = (TextView) convertView.findViewById(R.id.mProcess);

                convertView.setTag(holder);
            }
            else {
                holder = (ListActivity.ViewHolder) convertView.getTag();
            }

            ListviewItem mItem = mListviewItem.get(position);

            if(mItem.mPhoto != null){
                holder.mPhoto.setVisibility(View.VISIBLE);
                holder.mPhoto.setImageBitmap(mItem.mPhoto);
                //holder.mPhoto.setImageDrawable();
            } else {
                holder.mPhoto.setVisibility(View.GONE);
            }
            holder.mSubject.setText(mItem.mSubject);
            holder.mProcess.setText(mItem.mProcess);

            return convertView;
        }
        //커스텀리스트뷰 사용
/*
        listView = (ListView)findViewById(R.id.ListView);

        ArrayList<String> items = new ArrayList<>();
        items.add("Louvre");
        items.add("Orsay");
        items.add("ABCDE");
        items.add("BCDDD");
        items.add("DFAFSF");
        items.add("예술의 전당");

        CustomAdapter adapter = new CustomAdapter(this, 0, items);
        listView.setAdapter(adapter);
    }

    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourcedId, ArrayList<String> objects) {
            super(context, textViewResourcedId, objects);
            this.items = objects;
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 view를 리턴
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_listview_item, null);
            }


            // 화면에 표시될 view로부터 위젯에 대한 참조 획득
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView2);
            TextView textView = (TextView)v.findViewById(R.id.textView4);

            // 아이템 내 각 위젯에 데이터 반영
            textView.setText(items.get(position));

            return v;
        }*/

    }
}

