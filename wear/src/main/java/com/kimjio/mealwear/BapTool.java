package com.kimjio.mealwear;

import android.content.Context;

import org.hyunjun.school.School;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import toast.library.meal.MealLibrary;

/**
 * Created by whdghks913 on 2015-02-17.
 */
@Deprecated
public class BapTool {
    public static final String BAP_PREFERENCE_NAME = "bap_datas";
    public static final int TYPE_BREAKFAST = 0;
    public static final int TYPE_LUNCH = 1;
    public static final int TYPE_DINNER = 2;

    public static String getBapStringFormat(int year, int month, int day, int type) {
        /**
         * Format : year-month-day-TYPE
         */
        // Calendar의 month는 1이 부족하므로 1을 더해줌
        month += 1;
        return year + "-" + month + "-" + day + "-" + type;
    }

    /**
     * Pref Name Format : 2015-02-17-TYPE_index
     * ex) 2015-02-17-1_3
     */
    public static void saveBapData(Context mContext, String[] Calender, String[] Breakfast, String[] Lunch, String[] Dinner) {
        Preference mPref = new Preference(mContext, BAP_PREFERENCE_NAME);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd(E)",
                Locale.KOREA);

        for (int index = 0; index < Calender.length; index++) {
            try {
                Calendar mDate = Calendar.getInstance();
                mDate.setTime(mFormat.parse(Calender[index]));

                int year = mDate.get(Calendar.YEAR);
                int month = mDate.get(Calendar.MONTH);
                int day = mDate.get(Calendar.DAY_OF_MONTH);

                String mPrefBreakfastName = getBapStringFormat(year, month, day, TYPE_BREAKFAST);
                String mPrefLunchName = getBapStringFormat(year, month, day, TYPE_LUNCH);
                String mPrefDinnerName = getBapStringFormat(year, month, day, TYPE_DINNER);

                String mBreakfast = Breakfast[index];
                String mLunch = Lunch[index];
                String mDinner = Dinner[index];

                if (!MealLibrary.isMealCheck(mBreakfast)) mBreakfast = "";
                if (!MealLibrary.isMealCheck(mLunch)) mLunch = "";
                if (!MealLibrary.isMealCheck(mDinner)) mDinner = "";

                mPref.putString(mPrefBreakfastName, mBreakfast);
                mPref.putString(mPrefLunchName, mLunch);
                mPref.putString(mPrefDinnerName, mDinner);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Format : 2015-2-11-2
     */
    public static restoreBapDateClass restoreBapData(Context mContext, int year, int month, int day) {
        Preference mPref = new Preference(mContext, BAP_PREFERENCE_NAME);
        Calendar mDate = Calendar.getInstance();
        mDate.set(year, month, day);

        /*Log.e("YEAR", "" + mDate.get(Calendar.YEAR));
        Log.e("MONTH", "" + mDate.get(Calendar.MONTH));
        Log.e("DAY_OF_MONTH", "" + mDate.get(Calendar.DAY_OF_MONTH));*/

        restoreBapDateClass mData = new restoreBapDateClass();

        String mPrefBreakfastName = getBapStringFormat(year, month, day, TYPE_BREAKFAST);
        String mPrefLunchName = getBapStringFormat(year, month, day, TYPE_LUNCH);
        String mPrefDinnerName = getBapStringFormat(year, month, day, TYPE_DINNER);

        /*Log.e("mPrefLunchName", "" + mPrefLunchName);
        Log.e("mPrefDinnerName", "" + mPrefDinnerName);*/

        mData.Breakfast = mPref.getString(mPrefBreakfastName, null);
        mData.Lunch = mPref.getString(mPrefLunchName, null);
        mData.Dinner = mPref.getString(mPrefDinnerName, null);

        /*Log.e("mData.Calender", "" + mData.Calender);
        Log.e("mData.DayOfTheWeek", "" + mData.DayOfTheWeek);
        Log.e("mData.Lunch", "" + mData.Lunch);
        Log.e("mData.Dinner", "" + mData.Dinner);*/

        if (mData.Breakfast == null && mData.Lunch == null && mData.Dinner == null) {
            mData.isBlankDay = true;
        }

        return mData;
    }

    public static class restoreBapDateClass {
        public String Breakfast;
        public String Lunch;
        public String Dinner;
        public boolean isBlankDay = false;
    }

    public static String replaceAndCheckString(String mString) {
        String[] mTrash = {"0", "1", "2" , "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "."};
        for (String e : mTrash) {
            mString = mString.replace(e, "");
        }
        if (! mString.equals("")) {
            return mString;
        } else {
            return  "급식이 없습니다";
        }
    }
}
