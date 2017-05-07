package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapterMuseumS extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<MuseumS> museumData = null;
    private ArrayList<MuseumS> museumArrayList;

    /**
     * @param context
     * @param museumArrayList : arraylist getMuseumList()
     */
    public ListViewAdapterMuseumS(Context context, List<MuseumS> museumArrayList) {
        mContext = context;
        if(museumArrayList==null) {
            return;
        }
        this.museumData = museumArrayList;
        inflater = LayoutInflater.from(mContext);
        this.museumArrayList = new ArrayList<MuseumS>();
        this.museumArrayList.addAll(museumArrayList);
    }

    public class ViewHolder {
        public TextView nameM, nameA;
        public RatingBar rating;
        public ImageView Image;
    }

    @Override
    public int getCount() {
        return museumData.size();
    }

    @Override
    public MuseumS getItem(int position) {
        return museumData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ListViewAdapterMuseumS.ViewHolder holder;
        if (view == null) {
            holder = new ListViewAdapterMuseumS.ViewHolder();
            view = inflater.inflate(R.layout.museumviewholder, null);
            holder.nameM = (TextView) view.findViewById(R.id.nameMuseum);
            holder.nameA = (TextView) view.findViewById(R.id.addrMuseum);
            holder.rating = (RatingBar)view.findViewById(R.id.ratingMuseum);
            holder.Image = (ImageView)view.findViewById(R.id.imageMuseum);
            view.setTag(holder);
        } else {
            holder = (ListViewAdapterMuseumS.ViewHolder) view.getTag();
        }

        holder.nameM.setText(museumData.get(position).getName());
        holder.nameA.setText(museumData.get(position).getAddr());
        //ratingbar : int <- parseInt.getMs_rating() : string
        holder.rating.setRating(museumData.get(position).getRate());
        // 이미지 주소값으로 이미지 불러와서 holder에 추가하기
        // holder.Image.setImageResource(museumData.get(position).getMs_img());

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        museumData.clear();
        if (charText.length() == 0) {
            museumData.addAll(museumArrayList);
        }
        else
        {
            for (MuseumS ms : museumArrayList)
            {
                if (ms.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    museumData.add(ms);
                }
            }
        }
        notifyDataSetChanged();
    }
    //버튼을 눌렀을때 포함한 문자가 있을때 이를 리스트에 띄워 주는 메소드
}
