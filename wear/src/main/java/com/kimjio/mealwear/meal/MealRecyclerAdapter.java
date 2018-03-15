package com.kimjio.mealwear.meal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimjio.mealwear.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by kimji on 2017-10-14.
 */

public class MealRecyclerAdapter extends RecyclerView.Adapter<MealViewHolder> {

    private ArrayList<MealListData> mealListDataArrayList;

    private WearableActivity wearableActivity;

    private String[] timeStrings;
    private int[] images = {R.drawable.ic_breakfast, R.drawable.ic_launch, R.drawable.ic_dinner};

    //private Preference preference;

    public MealRecyclerAdapter(ArrayList<MealListData> items, WearableActivity wearableActivity) {
        this.mealListDataArrayList = items;
        this.wearableActivity = wearableActivity;
        //this.preference = new Preference(activity);

        //initSchoolMenu();
    }

    /*private void initSchoolMenu() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    schoolRegion = new SchoolConverter().IntToRegion(preference.getInt("schoolCountryID", -1));
                    schoolType = new SchoolConverter().IntToType(Integer.parseInt(preference.getString("schulCrseScCode", "")));
                    //this.school = new School(schoolType, schoolRegion, preference.getString("schulCode", ""));

                    schoolMenuArrayList = new School(schoolType, schoolRegion, preference.getString("schulCode", "")).getMonthlyMenu(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
                    Log.e("TAG", schoolMenuArrayList.size() + "");
                } catch (SchoolException e) {
                    e.printStackTrace();
                    Log.e("TAG", "SchoolException!");
                }
            }
        }.start();
    }*/

    @Override
    @NotNull
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_meal_info, viewGroup, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull MealViewHolder viewHolder, int position) {
        MealListData item = mealListDataArrayList.get(position);
        timeStrings = new String[]{wearableActivity.getString(R.string.breakfast), wearableActivity.getString(R.string.lunch), wearableActivity.getString(R.string.dinner)};
        //initSchoolMenu();
        switch (item.getType()) {
            case 0:
                viewHolder.img.setImageResource(images[0]);
                viewHolder.time.setText(timeStrings[0]);
                viewHolder.mealText.setText(item.getMealBreakfastText());
                break;
            case 1:
                viewHolder.img.setImageResource(images[1]);
                viewHolder.time.setText(timeStrings[1]);
                viewHolder.mealText.setText(item.getMealLunchText());
                break;
            case 2:
                viewHolder.img.setImageResource(images[2]);
                viewHolder.time.setText(timeStrings[2]);
                viewHolder.mealText.setText(item.getMealDinnerText());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mealListDataArrayList.size();
    }
}
