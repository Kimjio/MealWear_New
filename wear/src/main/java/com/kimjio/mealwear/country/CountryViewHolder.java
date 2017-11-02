package com.kimjio.mealwear.country;

import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableRecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimjio.mealwear.R;

/**
 * Created by kimji on 2017-10-16.
 */

public class CountryViewHolder extends WearableRecyclerView.ViewHolder {
    ImageView img;
    TextView countryText;

    public CountryViewHolder(View itemView){
        super(itemView);

        img = itemView.findViewById(R.id.imageView);
        countryText = itemView.findViewById(R.id.countryText);
    }
}
