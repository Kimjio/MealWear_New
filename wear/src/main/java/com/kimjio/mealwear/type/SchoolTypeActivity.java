package com.kimjio.mealwear.type;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;

import com.kimjio.mealwear.R;
import com.kimjio.mealwear.country.CountryListData;
import com.kimjio.mealwear.country.CountryRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class SchoolTypeActivity extends Activity {
    WearableRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_type);
        recyclerView = findViewById(R.id.schoolTypeRecyclerView);
        ArrayList<SchoolTypeListData> schoolTypeListDataArrayList = new ArrayList<>();

        SchoolTypeRecyclerAdapter schoolTypeRecyclerAdapter = new SchoolTypeRecyclerAdapter(getListItems(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.selSchoolNo)))), this);

        recyclerView.setAdapter(schoolTypeRecyclerAdapter);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    public ArrayList<SchoolTypeListData> getListItems (ArrayList<String> arrayList) {
        ArrayList<SchoolTypeListData> schoolTypeListDataArrayList = new ArrayList<> ();
        for (int i = 0; i < arrayList.size(); i++) {
            SchoolTypeListData schoolTypeListData = new SchoolTypeListData();
            schoolTypeListData.setSchoolTypeText(arrayList.get(i));
            schoolTypeListDataArrayList.add (schoolTypeListData);
        }
        return schoolTypeListDataArrayList;
    }
}
