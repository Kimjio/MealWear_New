package com.kimjio.mealwear.list;

import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableRecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimjio.mealwear.R;

/**
 * Created by kimji on 2017-10-15.
 */

public class SchoolSelectListViewHolder extends WearableRecyclerView.ViewHolder{
    ImageView imageView;
    TextView schoolName;
    TextView zipAddress;
    String orgCode;
    String schulKndScCode;
    String schulCrseScCode;

    public SchoolSelectListViewHolder(View view){
        super(view);
        imageView = view.findViewById(R.id.schoolListImageView);
        schoolName = view.findViewById(R.id.schoolName);
        zipAddress = view.findViewById(R.id.zipAddress);
    }
}
