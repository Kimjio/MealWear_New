package com.kimjio.mealwear.meal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.MenuItem;

import com.kimjio.mealwear.BapTool;
import com.kimjio.mealwear.ProcessTask;
import com.kimjio.mealwear.R;
import com.kimjio.mealwear.Tools;
import com.kimjio.mealwear.school.SchoolSelectActivity;

import org.hyunjun.school.School;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class MealActivity extends Activity {

    Calendar calendar;
    int YEAR, MONTH, DAY;
    int DAY_OF_WEEK;

    //BapDownloadTask processTask;

    private ArrayList<MealListData> mealListDataList = new ArrayList<>();


    private RecyclerView recyclerView;
    private WearableActionDrawerView wearableActionDrawerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        recyclerView = findViewById(R.id.mealRecyclerView);
        wearableActionDrawerView = findViewById(R.id.bottom_action_drawer);
        //wearableActionDrawerView.setIsLocked(true);
        wearableActionDrawerView.getController().peekDrawer();

        getCalendarInstance(true);


        /*MealListData mealListData = new MealListData();
        mealListData.setImageTime(R.drawable.ic_breakfast);
        mealListData.setTime(getString(R.string.breakfast));
        mealListData.setMealText("급식\n급식\n급식");
        mealListDataList.add(mealListData);

        MealListData mealListData2 = new MealListData();
        mealListData2.setImageTime(R.drawable.ic_launch);
        mealListData2.setTime(getString(R.string.lunch));
        mealListData2.setMealText("급식\n급식\n급식");
        mealListDataList.add(mealListData2);

        MealListData mealListData3 = new MealListData();
        mealListData3.setImageTime(R.drawable.ic_dinner);
        mealListData3.setTime(getString(R.string.dinner));
        mealListData3.setMealText("급식\n급식\n급식");
        mealListDataList.add(mealListData3);*/

        getBapList(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_set_day) {
            //startActivity(new Intent(this, class));
            return true;
        } else if (id == R.id.action_clear_data) {
            clearApplicationData();
            recreate();
            return true;
        } else if (id == R.id.action_school_reset) {
            startActivity(new Intent(this, SchoolSelectActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            //startActivity(new Intent(this, class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 데이터 삭제
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib") && !(s.equals("bap_datas"))) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty or this is a file so delete it
        assert dir != null;
        return dir.delete();
    }

    private void getCalendarInstance(boolean getInstance) {
        if (getInstance || (calendar == null))
            calendar = Calendar.getInstance();
        YEAR = calendar.get(Calendar.YEAR);
        MONTH = calendar.get(Calendar.MONTH);
        DAY = calendar.get(Calendar.DAY_OF_MONTH);
        DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
    }

    private void getBapList(boolean isUpdate) {
        boolean isNetwork = Tools.isOnline(this);
            if (!isNetwork) {
                startActivity(new Intent(this, ConfirmationActivity.class).putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION).putExtra(ConfirmationActivity.EXTRA_MESSAGE, "네트워크 없음"));
                return;
            }

        MealListData mealListData = new MealListData();
        mealListDataList.add(mealListData);


        //mCalendar.set(YEAR, MONTH, DAY);
        mealRecyclerViewInit();
    }

    public void mealRecyclerViewInit() {
        recyclerView.setAdapter(new MealRecyclerAdapter(mealListDataList, this));
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    /*public void setCalenderBap() {
        getCalendarInstance(false);

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                mCalendar.set(year, month, day);
                getCalendarInstance(false);

                getBapList(true);
            }
        }, year, month, day);
        datePickerDialog.setAccentColor(ThemeStore.accentColor(this));
        datePickerDialog.setYearRange(2006, 2030);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.show(getFragmentManager(), "Tag");
    }*/

    /*public class BapDownloadTask extends ProcessTask {
        public BapDownloadTask(Context context) {
            super(context);
        }

        @Override
        public void onPreDownload() {

        }

        @Override
        public void onUpdate(int progress) {
            /*mDialog.setProgress(progress);
        }

        @Override
        public void onFinish(long result) {
            /*if (mDialog != null)
                mDialog.dismiss();

            if (result == -1) {
                /*new MaterialDialog.Builder(BapActivity.this)
                        .title(R.string.I_do_not_know_the_error_title)
                        .content(R.string.I_do_not_know_the_error_message)
                        .positiveText(android.R.string.ok)
                        .show();

                return;
            }

            getBapList(false);

            /*if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);
            //mPullToRefreshView.setRefreshing(false);
        }
    }*/
}
