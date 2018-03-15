package com.kimjio.mealwear;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolMenu;
import org.hyunjun.school.SchoolSchedule;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public abstract class ProcessTask extends AsyncTask<Integer, Integer, Boolean> {

    private Preference preference;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public abstract void onPreDownload();

    public abstract void onUpdate(int progress);

    public abstract void onFinish(boolean result);

    public ProcessTask(Context context) {
        this.preference = new Preference(context);
        this.context = context;
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
        updateUpProgress(0, 24, 25);
        final String CountryCode = preference.getString("CountryCode", "");
        final String schulCode = preference.getString("schulCode", "");
        publishProgress(25);
        final String schulCrseScCode = preference.getString("schulCrseScCode", "");
        final String schulKndScCode = preference.getString("schulKndScCode", "");

        int year = params[0];
        int month = params[1];
        int day = params[2];

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        updateUpProgress(25, 34, 45);

        publishProgress(35);
        updateUpProgress(35, 39, 75);

        try {
            School api = new School(SchoolConverter.IntToType(Integer.parseInt(preference.getString("schulCrseScCode", ""))),
                    SchoolConverter.IntToRegion(preference.getInt("schoolCountryID", -1)),
                    preference.getString("schulCode", ""));
            publishProgress(40);

            updateUpProgress(40, 45, 50);
            List<SchoolMenu> menu = api.getMonthlyMenu(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
            publishProgress(46);
            List<SchoolSchedule> schedule = api.getMonthlySchedule(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
            publishProgress(47);
            for (int index = 47; index <= 55; index++) {
                publishProgress(index);
                SystemClock.sleep(60);
            }
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            String[] Breakfast = new String[maxDay];
            publishProgress(56);
            String[] Lunch = new String[maxDay];
            publishProgress(57);
            String[] Dinner = new String[maxDay];
            publishProgress(58);
            String[] Schedule = new String[maxDay];
            publishProgress(59);

            for (int i = 0; i < menu.size(); i++) {
                Breakfast[i] = menu.get(i).breakfast;
                Lunch[i] = menu.get(i).lunch;
                Dinner[i] = menu.get(i).dinner;
                Schedule[i] = schedule.get(i).schedule;
            }
            updateUpProgress(60, 75, 25);
            String[] Calender = getCalenderList(year, month, day);
            updateUpProgress(75, 100, 25);

            BapTool.saveBapData(context, Calender, Breakfast, Lunch, Dinner);
            ScheduleTool.saveScheduleData(context, Calender, Schedule);
            publishProgress(100);

        } catch (SchoolException e) {
            Log.w("ProcessTask Error", e.getMessage());

            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String[] getCalenderList(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

        String[] Calender = new String[maxDay];

        for (int i = minDay - 1; i <= maxDay - 1; i++) {
            String DayOfWeek = "";
            calendar.set(year, month, i + 1);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    DayOfWeek = "일";
                    break;
                case 2:
                    DayOfWeek = "월";
                    break;
                case 3:
                    DayOfWeek = "화";
                    break;
                case 4:
                    DayOfWeek = "수";
                    break;
                case 5:
                    DayOfWeek = "목";
                    break;
                case 6:
                    DayOfWeek = "금";
                    break;
                case 7:
                    DayOfWeek = "토";
                    break;
            }
            Calender[i] = Integer.toString(year) + String.format(Locale.KOREA, ".%02d", month + 1) + String.format(Locale.KOREA, ".%02d", i + 1) + "(" + DayOfWeek + ")";
        }
        return Calender;
    }

    private void updateUpProgress(int before, int afther, int speed) {
        for (int index = before; index <= afther; index++) {
            publishProgress(index);
            SystemClock.sleep(speed);
        }
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
