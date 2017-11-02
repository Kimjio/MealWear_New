package com.kimjio.mealwear;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import toast.library.meal.MealLibrary;

/**
 * Created by whdghks913 on 2015-02-17.
 */
public abstract class ProcessTask extends AsyncTask<Integer, Integer, Boolean> {

    private Preference mPref;

    public abstract void onPreDownload();

    public abstract void onUpdate(int progress);

    public abstract void onFinish(boolean result);

    public ProcessTask(Context mContext) {
        this.mPref = new Preference(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPreDownload();
    }

    /**
     * params[0] : year
     * params[1] : month
     * params[2] : day
     */
    @Override
    protected Boolean doInBackground(Integer... params) {
        publishProgress(5);

        final String CountryCode = mPref.getString("CountryCode", "");
        final String schulCode = mPref.getString("schulCode", "");
        final String schulCrseScCode= mPref.getString("schulCrseScCode", "");
        final String schulKndScCode = mPref.getString("schulKndScCode", "");

        /*final String CountryCode = "dge.go.kr"; // 접속 할 교육청 도메인
        final String schulCode = "D100000282"; // 학교 고유 코드
        final String schulCrseScCode = "4"; // 학교 종류 코드 1
        final String schulKndScCode = "04"; // 학교 종류 코드 2*/

        final String year = Integer.toString(params[0]);
        String month = Integer.toString(params[1] + 1);
        String day = Integer.toString(params[2]);

        if (month.length() <= 1)
            month = "0" + month;
        if (day.length() <= 1)
            day = "0" + day;

        publishProgress(35);

        try {
            String[] Calender = MealLibrary.getDateNew(CountryCode, schulCode,
                    schulCrseScCode, schulKndScCode, "1", year, month, day);

            publishProgress(50);

            String[] Breakfast = MealLibrary.getMealNew(CountryCode, schulCode,
                    schulCrseScCode, schulKndScCode, "1", year, month, day);

            publishProgress(60);

            String[] Lunch = MealLibrary.getMealNew(CountryCode, schulCode,
                    schulCrseScCode, schulKndScCode, "2", year, month, day);

            publishProgress(75);

            String[] Dinner = MealLibrary.getMealNew(CountryCode, schulCode,
                    schulCrseScCode, schulKndScCode, "3", year, month, day);

            //BapTool.saveBapData(mContext, Calender, Breakfast, Lunch, Dinner);

            publishProgress(100);


        } catch (Exception e) {
            Log.e("ProcessTask Error", "Message : " + e.getMessage());
            Log.e("ProcessTask Error", "LocalizedMessage : " + e.getLocalizedMessage());

            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {
        onUpdate(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        onFinish(result);
    }
}
