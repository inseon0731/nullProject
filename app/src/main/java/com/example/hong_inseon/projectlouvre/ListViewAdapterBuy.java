package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 박명준 on 2017-03-06.
 */

public class ListViewAdapterBuy extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Buy> buylist = null;
    private ArrayList<Buy> arraylist2;

    public ListViewAdapterBuy(Context context, List<Buy> buylist) {
        mContext = context;
        this.buylist = buylist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist2 = new ArrayList<Buy>();
        this.arraylist2.addAll(buylist);
    }

    public class ViewHolder {
        public TextView name1, name2;
        public ImageView image;
        public Button guide, dorok;
    }

    @Override
    public int getCount() {
        return buylist.size();
    }

    @Override
    public Buy getItem(int position) {
        return buylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ListViewAdapterBuy.ViewHolder holder;
        if (view == null) {
            holder = new ListViewAdapterBuy.ViewHolder();
            view = inflater.inflate(R.layout.cartviewholder, null);
            holder.name1 = (TextView) view.findViewById(R.id.textMuseumCart);
            holder.name2 = (TextView) view.findViewById(R.id.textExhibitCart);
            holder.image = (ImageView) view.findViewById(R.id.imageCart);
            holder.dorok = (Button)view.findViewById(R.id.button4);
            holder.guide = (Button)view.findViewById(R.id.button5);
            view.setTag(holder);
        } else {
            holder = (ListViewAdapterBuy.ViewHolder) view.getTag();
        }

        holder.name1.setText(buylist.get(position).getNameExhibit());
        holder.name2.setText(buylist.get(position).getNameMuseum());
        holder.image.setImageResource(buylist.get(position).getImage());
        if(buylist.get(position).Dorok())
            holder.dorok.setBackgroundResource(R.drawable.blue);
        if(buylist.get(position).Guide())
            holder.guide.setBackgroundResource(R.drawable.purple);

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

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        buylist.clear();
        if (charText.length() == 0) {
            buylist.addAll(arraylist2);
        }
        else
        {
            for (Buy wp : arraylist2)
            {
                if (wp.getNameExhibit().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    buylist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
