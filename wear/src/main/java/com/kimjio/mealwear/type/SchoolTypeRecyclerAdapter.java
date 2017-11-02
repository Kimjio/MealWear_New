package com.kimjio.mealwear.type;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableRecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kimjio.mealwear.R;

import java.util.ArrayList;

/**
 * Created by kimji on 2017-10-16.
 */

public class SchoolTypeRecyclerAdapter extends WearableRecyclerView.Adapter<WearableRecyclerView.ViewHolder> {

    private ArrayList<SchoolTypeListData> schoolTypeListDataArrayList;
    private Activity activity;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public SchoolTypeRecyclerAdapter(ArrayList<SchoolTypeListData> items, Activity activity) {
        this.schoolTypeListDataArrayList = items;
        this.activity = activity;
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionHeader (position)) {
            return TYPE_HEADER;
        } else if(isPositionFooter (position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader (int position) {
        return position == 0;
    }
    private boolean isPositionFooter (int position) {
        return position == schoolTypeListDataArrayList.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from (viewGroup.getContext()).inflate (R.layout.header_item, viewGroup, false);
            return new HeaderViewHolder(v);
        } else if(viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from (viewGroup.getContext()).inflate (R.layout.footer_item, viewGroup, false);
            return new FooterViewHolder (v);
        } else if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_school_type, viewGroup, false);
            return new SchoolTypeViewHolder(view);
        }
        return null;
    }

    private SchoolTypeListData getItem (int position) {
        return schoolTypeListDataArrayList.get (position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof SchoolTypeViewHolder) {
            View.OnClickListener view = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setResult(Activity.RESULT_OK, new Intent().putExtra("schoolTypeID", position));
                    activity.finish();
                }
            };
            SchoolTypeListData item = getItem(position-1);
            SchoolTypeViewHolder schoolTypeViewHolder = (SchoolTypeViewHolder) viewHolder;
            schoolTypeViewHolder.schoolTypeText.setText(item.getSchoolTypeText());
            schoolTypeViewHolder.img.setOnClickListener(view);
            schoolTypeViewHolder.schoolTypeText.setOnClickListener(view);
            schoolTypeViewHolder.itemView.setOnClickListener(view);
        }

    }

    @Override
    public int getItemCount() {
        return schoolTypeListDataArrayList.size()+2;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder (View itemView) {
            super (itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder (View itemView) {
            super (itemView);
        }
    }
}