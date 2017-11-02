package com.kimjio.mealwear.meal;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimjio.mealwear.R;

/**
 * Created by kimji on 2017-10-14.
 */

public class MealViewHolder extends RecyclerView.ViewHolder{

    ImageView img;
    TextView time, mealText;

    public MealViewHolder(View itemView){
        super(itemView);

        img = itemView.findViewById(R.id.imageView);
        time = itemView.findViewById(R.id.time);
        mealText = itemView.findViewById(R.id.mealText);
    }
}