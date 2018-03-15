package com.kimjio.mealwear;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;

import com.kimjio.mealwear.meal.MealActivity;
import com.kimjio.mealwear.school.SchoolSelectActivity;

public class SplashActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Preference preference = new Preference(SplashActivity.this);
                if (preference.getString("CountryCode", "").isEmpty() ||
                        preference.getString("schulCode", "").isEmpty() ||
                        preference.getString("schulKndScCode", "").isEmpty() ||
                        preference.getString("schulCrseScCode", "").isEmpty()) {
                    startActivity(new Intent(SplashActivity.this, SchoolSelectActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MealActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
