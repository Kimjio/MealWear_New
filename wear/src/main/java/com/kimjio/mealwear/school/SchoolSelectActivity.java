package com.kimjio.mealwear.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;

import com.kimjio.mealwear.R;

//TODO Spinner
public class SchoolSelectActivity extends WearableActivity {

    private WearableRecyclerView recyclerView;
    private SchoolRecyclerAdapter schoolRecyclerAdapter;
    public static final int REQUEST_COUNTRY = 0;
    public static final int REQUEST_SCHOOL_TYPE = 1;
    public static final int REQUEST_SCHOOL_SELECT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_select);
        schoolRecyclerAdapter = new SchoolRecyclerAdapter(this);
        recyclerView = findViewById(R.id.schoolRecyclerView);
        recyclerView.setAdapter(schoolRecyclerAdapter);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        schoolRecyclerAdapter.onActivityResult(requestCode, resultCode, intent);
    }
}
