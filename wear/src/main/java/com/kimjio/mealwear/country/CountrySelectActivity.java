package com.kimjio.mealwear.country;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;

import com.kimjio.mealwear.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CountrySelectActivity extends WearableActivity {
    WearableRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_select);
        recyclerView = findViewById(R.id.countryRecyclerView);
        ArrayList<CountryListData> countryListDataList = new ArrayList<>();

        CountryRecyclerAdapter countryRecyclerAdapter = new CountryRecyclerAdapter(getListItems(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.selContEducation)))), this);

        recyclerView.setAdapter(countryRecyclerAdapter);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    public ArrayList<CountryListData> getListItems(ArrayList<String> arrayList) {
        ArrayList<CountryListData> countryListDataArrayList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            CountryListData countryListData = new CountryListData();
            countryListData.setCountryName(arrayList.get(i));
            countryListDataArrayList.add(countryListData);
        }
        return countryListDataArrayList;
    }
}
