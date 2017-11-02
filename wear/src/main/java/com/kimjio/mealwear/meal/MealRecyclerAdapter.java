package com.kimjio.mealwear.meal;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimjio.mealwear.Preference;
import com.kimjio.mealwear.R;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by kimji on 2017-10-14.
 */

public class MealRecyclerAdapter extends RecyclerView.Adapter<MealViewHolder> {

    private ArrayList<MealListData> mealListDataArrayList;

    private Activity activity;

    private School.Region schoolRegion;
    private School.Type schoolType;

    private String[] timeStrings;
    private int[] images = {R.drawable.ic_breakfast, R.drawable.ic_launch, R.drawable.ic_dinner};
    private School school;

    private List<SchoolMenu> schoolMenuArrayList;

    private Preference preference;

    public MealRecyclerAdapter(ArrayList<MealListData> items, Activity activity) {
        this.mealListDataArrayList = items;
        this.activity = activity;
        this.preference = new Preference(activity);

        //initSchoolMenu();
    }

    private void initSchoolMenu() {
        switch (preference.getInt("schoolCountryID", -1)) {
            case 0:
                schoolRegion = School.Region.SEOUL;
                break;
            case 1:
                schoolRegion = School.Region.BUSAN;
                break;
            case 2:
                schoolRegion = School.Region.DAEGU;
                break;
            case 3:
                schoolRegion = School.Region.INCHEON;
                break;
            case 4:
                schoolRegion = School.Region.GWANGJU;
                break;
            case 5:
                schoolRegion = School.Region.DAEJEON;
                break;
            case 6:
                schoolRegion = School.Region.ULSAN;
                break;
            case 7:
                schoolRegion = School.Region.SEJONG;
                break;
            case 8:
                schoolRegion = School.Region.GYEONGGI;
                break;
            case 9:
                schoolRegion = School.Region.KANGWON;
                break;
            case 10:
                schoolRegion = School.Region.CHUNGBUK;
                break;
            case 11:
                schoolRegion = School.Region.CHUNGNAM;
                break;
            case 12:
                schoolRegion = School.Region.JEONBUK;
                break;
            case 13:
                schoolRegion = School.Region.JEONNAM;
                break;
            case 14:
                schoolRegion = School.Region.GYEONGBUK;
                break;
            case 15:
                schoolRegion = School.Region.GYEONGNAM;
                break;
            case 16:
                schoolRegion = School.Region.JEJU;
        }
        switch (Integer.parseInt(preference.getString("schulCrseScCode", ""))) {
            case 1:
                schoolType = School.Type.KINDERGARTEN;
                break;
            case 2:
                schoolType = School.Type.ELEMENTARY;
                break;
            case 3:
                schoolType = School.Type.MIDDLE;
                break;
            case 4:
                schoolType = School.Type.HIGH;
                break;
        }
        //this.school = new School(schoolType, schoolRegion, preference.getString("schulCode", ""));
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    schoolMenuArrayList = new School(schoolType, schoolRegion, preference.getString("schulCode", "")).getMonthlyMenu(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
                    Log.e("TAG", schoolMenuArrayList.size() + "");
                } catch (SchoolException e) {
                    e.printStackTrace();
                    Log.e("TAG", "SchoolException!");
                }
            }
        }.start();
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_meal_info, viewGroup, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MealViewHolder viewHolder, int position) {
        //MealListData item = mealListDataArrayList.get(position);
        timeStrings = new String[]{activity.getString(R.string.breakfast), activity.getString(R.string.lunch), activity.getString(R.string.dinner)};
        initSchoolMenu();
        viewHolder.img.setImageResource(images[0]);
        viewHolder.time.setText(timeStrings[0]);
        viewHolder.mealText.setText(schoolMenuArrayList.get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1).lunch);
        viewHolder.img.setImageResource(images[1]);
        viewHolder.time.setText(timeStrings[1]);
        viewHolder.mealText.setText(schoolMenuArrayList.get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1).breakfast);
        viewHolder.img.setImageResource(images[2]);
        viewHolder.time.setText(timeStrings[2]);
        viewHolder.mealText.setText(schoolMenuArrayList.get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1).dinner);

    }

    @Override
    public int getItemCount() {
        return mealListDataArrayList.size();
    }
}
