package com.kimjio.mealwear.country;

import android.app.Activity;
import android.content.Intent;
import android.support.wear.widget.WearableRecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimjio.mealwear.R;

import java.util.ArrayList;

/**
 * Created by kimji on 2017-10-16.
 */

public class CountryRecyclerAdapter extends WearableRecyclerView.Adapter<WearableRecyclerView.ViewHolder> {

    private ArrayList<CountryListData> countryListDataArrayList;
    private Activity activity;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public CountryRecyclerAdapter(ArrayList<CountryListData> items, Activity activity) {
        this.countryListDataArrayList = items;
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
        return position == countryListDataArrayList.size() + 1;
    }

    @Override
    public WearableRecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from (viewGroup.getContext()).inflate (R.layout.header_item, viewGroup, false);
            return new HeaderViewHolder(v);
        } else if(viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from (viewGroup.getContext()).inflate (R.layout.footer_item, viewGroup, false);
            return new FooterViewHolder (v);
        } else if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_country_list, viewGroup, false);
            return new CountryViewHolder(view);
        }
        return null;
    }

    private CountryListData getItem (int position) {
        return countryListDataArrayList.get (position);
    }

    @Override
    public void onBindViewHolder(WearableRecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof CountryViewHolder) {
            View.OnClickListener view = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setResult(Activity.RESULT_OK, new Intent().putExtra("schoolCountryID", position - 1));
                    activity.finish();
                }
            };
            CountryListData item = getItem(position-1);
            CountryViewHolder countryViewHolder = (CountryViewHolder) viewHolder;
            countryViewHolder.countryText.setText(item.getCountryName());
            countryViewHolder.img.setOnClickListener(view);
            countryViewHolder.countryText.setOnClickListener(view);
            countryViewHolder.itemView.setOnClickListener(view);
        }

    }

    @Override
    public int getItemCount() {
        return countryListDataArrayList.size()+2;
    }

    class FooterViewHolder extends WearableRecyclerView.ViewHolder {

        public FooterViewHolder (View itemView) {
            super (itemView);
        }
    }

    class HeaderViewHolder extends WearableRecyclerView.ViewHolder {

        public HeaderViewHolder (View itemView) {
            super (itemView);
        }
    }
}