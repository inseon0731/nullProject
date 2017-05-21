package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListViewAdapterNotice extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Notice> noticelist = null;
    private ArrayList<Notice> arraylist;

    public ListViewAdapterNotice(Context context, List<Notice> buylist) {
        mContext = context;
        this.noticelist = buylist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Notice>();
        this.arraylist.addAll(noticelist);
    }

    public class ViewHolder {
        public TextView note_title, note_date;
    }

    @Override
    public int getCount() {
        return noticelist.size();
    }

    @Override
    public Notice getItem(int position) {
        return noticelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ListViewAdapterNotice.ViewHolder holder;
        if (view == null) {
            holder = new ListViewAdapterNotice.ViewHolder();
            view = inflater.inflate(R.layout.noticeviewholder, null);
            holder.note_title = (TextView) view.findViewById(R.id.noteTitle);
            //holder.note_content = (TextView) view.findViewById(R.id.textExhibitCart);
            holder.note_date = (TextView) view.findViewById(R.id.noteDate);

            view.setTag(holder);
        } else {
            holder = (ListViewAdapterNotice.ViewHolder) view.getTag();
        }

        holder.note_title.setText('[' + noticelist.get(position).getMs_name() + ']' + noticelist.get(position).getNote_title() + '\n' + noticelist.get(position).getNote_content());
        //holder.note_content.setText(noticelist.get(position).getNote_content());
        holder.note_date.setText(noticelist.get(position).getNote_date());

       /*view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, SingleItemView.class);
                intent.putExtra("nameM",(heartlist.get(position).getNameM()));
                intent.putExtra("nameW",(heartlist.get(position).getNameW()));
                intent.putExtra("nameP",(heartlist.get(position).getNameP()));
                intent.putExtra("Image",(heartlist.get(position).getImage()));
                mContext.startActivity(intent);
            }
        });*/

        return view;
    }
}
