package com.kimjio.mealwear.type;

import android.support.wear.widget.WearableRecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimjio.mealwear.R;

/**
 * Created by kimji on 2017-10-16.
 */

public class SchoolTypeViewHolder extends WearableRecyclerView.ViewHolder {
    ImageView img;
    TextView schoolTypeText;

    public SchoolTypeViewHolder(View itemView){
        super(itemView);

        img = itemView.findViewById(R.id.imageView);
        schoolTypeText = itemView.findViewById(R.id.countryText);
    }
}
