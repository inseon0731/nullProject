package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.hong_inseon.projectlouvre.dao.Museum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapterMuseum extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Museum> museumData = null;
    private ArrayList<Museum> museumArrayList;

    int[] a = {R.drawable.louvre, R.drawable.ms3,R.drawable.ms4, R.drawable.ms5,R.drawable.ms6, R.drawable.ms7, R.drawable.ms8,R.drawable.ms9, R.drawable.ms10,R.drawable.ms11,
            R.drawable.ms12, R.drawable.alfons,R.drawable.munch, R.drawable.louvre,R.drawable.orsay, R.drawable.bazille, R.drawable.alfons,R.drawable.munch, R.drawable.louvre,R.drawable.orsay,};

    /**
     * @param context
     * @param museumArrayList : arraylist getMuseumList()
     */
    public ListViewAdapterMuseum(Context context, List<Museum> museumArrayList) {
        mContext = context;
        if(museumArrayList==null) {
            return;
        }
        this.museumData = museumArrayList;
        inflater = LayoutInflater.from(mContext);
        this.museumArrayList = new ArrayList<Museum>();
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
    public Museum getItem(int position) {
        return museumData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.museumviewholder, null);
            holder.nameM = (TextView) view.findViewById(R.id.nameMuseum);
            holder.nameA = (TextView) view.findViewById(R.id.addrMuseum);
            holder.rating = (RatingBar)view.findViewById(R.id.ratingMuseum);
            holder.Image = (ImageView)view.findViewById(R.id.imageMuseum);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.nameM.setText(museumData.get(position).getMs_name());
        holder.nameA.setText(museumData.get(position).getMs_address());
        //ratingbar : int <- parseInt.getMs_rating() : string
        holder.rating.setRating(Float.parseFloat(museumData.get(position).getMs_rating()));
        // 이미지 주소값으로 이미지 불러와서 holder에 추가하기
        holder.Image.setImageResource(a[position]/*museumData.get(position).getMs_Img()*/);

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
            for (Museum ms : museumArrayList)
            {
                if (ms.getMs_name().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    museumData.add(ms);
                }
            }
        }
        notifyDataSetChanged();
    }
    //버튼을 눌렀을때 포함한 문자가 있을때 이를 리스트에 띄워 주는 메소드
}
