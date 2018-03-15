package com.kimjio.mealwear.meal;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.AcceptDenyDialog;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.kimjio.mealwear.BapTool;
import com.kimjio.mealwear.LoadingDialog;
import com.kimjio.mealwear.ProcessTask;
import com.kimjio.mealwear.R;
import com.kimjio.mealwear.Tools;
import com.kimjio.mealwear.school.SchoolSelectActivity;

import java.util.ArrayList;
import java.util.Calendar;

import static com.kimjio.mealwear.BapTool.BAP_PREFERENCE_NAME;
import static com.kimjio.mealwear.ScheduleTool.SCHEDULE_PREFERENCE_NAME;

public class MealActivity extends WearableActivity {

    Calendar calendar;
    int YEAR, MONTH, DAY;
    int DAY_OF_WEEK;

    LoadingDialog loadingDialog;

    BapDownloadTask processTask;

    private ArrayList<MealListData> mealListDataList = new ArrayList<>();

    private MealRecyclerAdapter mealRecyclerAdapter = new MealRecyclerAdapter(mealListDataList, this);

    private WearableRecyclerView wearableRecyclerView;
    private WearableActionDrawerView wearableActionDrawerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        wearableRecyclerView = findViewById(R.id.mealRecyclerView);
        wearableActionDrawerView = findViewById(R.id.bottom_action_drawer);
        wearableActionDrawerView.setPeekOnScrollDownEnabled(true);
        wearableActionDrawerView.getController().peekDrawer();
        wearableActionDrawerView.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //TODO Menu
                int id = item.getItemId();
                if (id == R.id.action_set_day) {
                    setCalenderBap();
                    return true;
                } else if (id == R.id.action_clear_data) {
                    AcceptDenyDialog acceptDenyDialog = new AcceptDenyDialog(MealActivity.this);
                    /*
                     * com.kimjio.mealwear_preferences.xml
                     * bap_datas.xml
                     * schedule.xml
                     */
                    acceptDenyDialog.setTitle("급식 데이터 초기화");
                    acceptDenyDialog.setMessage("계속하시겠습니까?");
                    acceptDenyDialog.setNegativeButton(new AcceptDenyDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    acceptDenyDialog.setPositiveButton(new AcceptDenyDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getApplicationContext().getSharedPreferences(BAP_PREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().apply();
                            recreate();
                        }
                    });
                    acceptDenyDialog.show();
                    return true;
                } else if (id == R.id.action_school_reset) {
                    AcceptDenyDialog acceptDenyDialog = new AcceptDenyDialog(MealActivity.this);
                    acceptDenyDialog.setTitle("학교 변경");
                    acceptDenyDialog.setMessage("계속하시겠습니까?");
                    acceptDenyDialog.setNegativeButton(new AcceptDenyDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    acceptDenyDialog.setPositiveButton(new AcceptDenyDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getApplicationContext().getSharedPreferences(BAP_PREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().apply();
                            getApplicationContext().getSharedPreferences(SCHEDULE_PREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().apply();

                            startActivity(new Intent(getApplicationContext(), SchoolSelectActivity.class));
                            finish();
                        }
                    });
                    acceptDenyDialog.show();
                    return true;
                } else return id == R.id.action_settings;
            }
        });
        wearableRecyclerView.setAdapter(mealRecyclerAdapter);
        wearableRecyclerView.setLayoutManager(new WearableLinearLayoutManager(getApplicationContext()));
        wearableRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(wearableRecyclerView);

        getCalendarInstance(true);

        getBapList(true);
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

        mealListDataList.clear();
        mealRecyclerAdapter.notifyDataSetChanged();

        getCalendarInstance(false);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        BapTool.restoreBapDateClass mData = BapTool.restoreBapData(this, year, month, day);

        if (mData.isBlankDay) {
            if (isUpdate && isNetwork) {
                loadingDialog = new LoadingDialog(this);
                loadingDialog.show();

                processTask = new BapDownloadTask(this);
                processTask.execute(year, month, day);
            } else {
                startActivity(new Intent(this, ConfirmationActivity.class).putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION).putExtra(ConfirmationActivity.EXTRA_MESSAGE, "네트워크 없음"));
            }
            return;
        }

        MealListData mealListBreakfastData = new MealListData(0);
        mealListBreakfastData.setMealBreakfastText(mData.Breakfast);
        mealListDataList.add(mealListBreakfastData);

        MealListData mealListLunchData = new MealListData(1);
        mealListLunchData.setMealLunchText(mData.Lunch);
        mealListDataList.add(mealListLunchData);

        MealListData mealListDinnerData = new MealListData(2);
        mealListDinnerData.setMealDinnerText(mData.Dinner);
        mealListDataList.add(mealListDinnerData);

        mealRecyclerAdapter.notifyDataSetChanged();
    }

    public void setCalenderBap() {
        getCalendarInstance(false);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(MealActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                wearableActionDrawerView.getController().closeDrawer();
                calendar.set(year, month, dayOfMonth);
                getCalendarInstance(false);

                getBapList(true);
                wearableRecyclerView.getAdapter().notifyDataSetChanged();
                wearableActionDrawerView.getController().peekDrawer();
            }
        }, year, month, day).show();
    }

    @SuppressLint("StaticFieldLeak")
    public class BapDownloadTask extends ProcessTask {
        public BapDownloadTask(Context context) {
            super(context);
        }

        @Override
        public void onPreDownload() {

        }

        @Override
        public void onUpdate(int progress) {
            loadingDialog.setProgress(progress);
        }

        @Override
        public void onFinish(boolean result) {
            if (loadingDialog != null)
                loadingDialog.dismiss();

            if (!result) {
                AcceptDenyDialog acceptDenyDialog = new AcceptDenyDialog(getApplicationContext());
                acceptDenyDialog.setTitle("오류!");
                acceptDenyDialog.setMessage("알 수 없는 오류가 발생함");
                acceptDenyDialog.setNegativeButton(new AcceptDenyDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                acceptDenyDialog.setPositiveButton(new AcceptDenyDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                acceptDenyDialog.show();
                return;
            }

            getBapList(false);

            /*if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);
            //mPullToRefreshView.setRefreshing(false);*/
        }
    }
}
