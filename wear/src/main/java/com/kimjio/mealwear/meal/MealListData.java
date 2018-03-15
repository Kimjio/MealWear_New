package com.kimjio.mealwear.meal;

/**
 * Created by kimji on 2017-10-14.
 */

public class MealListData {
    private int type;

    private String mealBreakfastText;

    private String mealLunchText;

    private String mealDinnerText;

    public MealListData(int type) {
        if (!(type >= 0 && type <= 2)) throw new IllegalArgumentException("Type is not 0 ~ 2");
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getMealBreakfastText() {
        return mealBreakfastText;
    }

    public String getMealDinnerText() {
        return mealDinnerText;
    }

    public String getMealLunchText() {
        return mealLunchText;
    }

    public void setMealBreakfastText(String mealBreakfastText) {
        this.mealBreakfastText = mealBreakfastText;
    }

    public void setMealLunchText(String mealLunchText) {
        this.mealLunchText = mealLunchText;
    }

    public void setMealDinnerText(String mealDinnerText) {
        this.mealDinnerText = mealDinnerText;
    }
}
