package com.kimjio.mealwear.list;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.view.AcceptDenyDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimjio.mealwear.Preference;
import com.kimjio.mealwear.R;

import java.util.ArrayList;

/**
 * Created by kimji on 2017-10-15.
 */

public class SchoolSelectRecyclerAdapter extends WearableRecyclerView.Adapter<WearableRecyclerView.ViewHolder> {
    private ArrayList<SchoolSelectListData> schoolListDataArraySelectList;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Activity activity;

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == schoolListDataArraySelectList.size() + 1;
    }

    public SchoolSelectRecyclerAdapter(ArrayList<SchoolSelectListData> items, Activity activity) {
        this.schoolListDataArraySelectList = items;
        this.activity = activity;
    }

    @Override
    public WearableRecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_item, viewGroup, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_item, viewGroup, false);
            return new FooterViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_school_select_list, viewGroup, false);
            return new SchoolSelectListViewHolder(view);
        }

        return null;
    }

    private SchoolSelectListData getItem(int position) {
        return schoolListDataArraySelectList.get(position);
    }

    @Override
    public void onBindViewHolder(WearableRecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof SchoolSelectListViewHolder) {
            SchoolSelectListViewHolder schoolSelectListViewHolder = (SchoolSelectListViewHolder) viewHolder;
            final SchoolSelectListData item = getItem(position - 1);
            View.OnClickListener view = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AcceptDenyDialog acceptDenyDialog = new AcceptDenyDialog(activity);
                    acceptDenyDialog.setNegativeButton(new AcceptDenyDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    acceptDenyDialog.setPositiveButton(new AcceptDenyDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Preference preference = new Preference(activity);
                            preference.putString("CountryCode", item.getCountryUrl());
                            preference.putString("schulCode", item.getOrgCode());
                            preference.putString("schulKndScCode", item.getSchulKndScCode());
                            preference.putString("schulCrseScCode", item.getSchulCrseScCode());
                            activity.setResult(Activity.RESULT_OK, new Intent());
                            activity.finish();
                        }
                    });
                    acceptDenyDialog.setTitle(item.getSchoolName());
                    acceptDenyDialog.setMessage("계속하시겠습니까?");
                    acceptDenyDialog.show();
                }
            };

            schoolSelectListViewHolder.imageView.setOnClickListener(view);
            schoolSelectListViewHolder.schoolName.setOnClickListener(view);
            schoolSelectListViewHolder.zipAddress.setOnClickListener(view);
            schoolSelectListViewHolder.schoolName.setSelected(true);
            schoolSelectListViewHolder.zipAddress.setSelected(true);
            schoolSelectListViewHolder.schoolName.setText(item.getSchoolName());
            schoolSelectListViewHolder.zipAddress.setText(item.getZipAddress());
            schoolSelectListViewHolder.orgCode = item.getOrgCode();
            schoolSelectListViewHolder.schulKndScCode = item.getSchulKndScCode();
            schoolSelectListViewHolder.schulCrseScCode = item.getSchulCrseScCode();
        }
    }

    @Override
    public int getItemCount() {
        return schoolListDataArraySelectList.size() + 2;
    }

    class FooterViewHolder extends WearableRecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends WearableRecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}