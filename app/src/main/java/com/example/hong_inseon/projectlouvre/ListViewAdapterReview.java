package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hong_inseon.projectlouvre.dao.Review;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterReview extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Review> reviewlist = null;
    private ArrayList<Review> arraylist;

    public ListViewAdapterReview(Context context, List<Review> reviewlist) {
        mContext = context;
        this.reviewlist = reviewlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Review>();
        this.arraylist.addAll(reviewlist);
    }

    public class ViewHolder {
        public TextView user_name, review_content ,review_date;
        public RatingBar review_RatingBar;
    }

    @Override
    public int getCount() {
        return reviewlist.size();
    }

    @Override
    public Review getItem(int position) {
        return reviewlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ListViewAdapterReview.ViewHolder holder;
        if (view == null) {
            holder = new ListViewAdapterReview.ViewHolder();
            view = inflater.inflate(R.layout.noticeviewholder, null);
            holder.user_name = (TextView) view.findViewById(R.id.review_User);
            holder.review_content = (TextView) view.findViewById(R.id.review_Content);
            holder.review_date = (TextView) view.findViewById(R.id.review_Date);
            holder.review_RatingBar = (RatingBar)view.findViewById(R.id.review_RatingBar);

            view.setTag(holder);
        } else {
            holder = (ListViewAdapterReview.ViewHolder) view.getTag();
        }

        holder.user_name.setText(reviewlist.get(position).getUser_name());
        holder.review_RatingBar.setRating(reviewlist.get(position).getReview_rating());
        holder.review_content.setText(reviewlist.get(position).getReview_content());
        holder.review_date.setText(reviewlist.get(position).getReview_date());

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
