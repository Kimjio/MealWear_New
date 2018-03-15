package com.kimjio.mealwear.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import com.kimjio.mealwear.R;
import com.kimjio.mealwear.Tools;

import net.htmlparser.jericho.Source;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URL;
import java.util.ArrayList;

public class SchoolSelectListActivity extends WearableActivity {
    private RecyclerView recyclerView;
    ArrayList<SchoolSelectListData> schoolListDataArraySelectList = new ArrayList<>();
    int schoolType;
    String schoolCountry, schoolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_select_list);
        recyclerView = findViewById(R.id.schoolSelectRecyclerView);
        Intent intent = getIntent();
        schoolType = intent.getIntExtra("schoolType", -1);
        schoolCountry = intent.getStringExtra("schoolCountry");
        schoolName = intent.getStringExtra("schoolName");

        if (Tools.isOnline(this)) {
            new Thread() {
                public void run() {
                    schoolListSearch();
                }
            }.start();
        } else {
            startActivity(new Intent(this, ConfirmationActivity.class).putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION).putExtra(ConfirmationActivity.EXTRA_MESSAGE, "네트워크 없음"));
            finish();
        }
    }

    public String getHtmlToText(String sourceUrlString) {
        Source source;
        String content = null;
        try {
            source = new Source(new URL(sourceUrlString));
            source.fullSequentialParse();
            content = source.getSource().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


    public void schoolListSearch() {
        //목록 초기화
        schoolListDataArraySelectList.clear();
        //TODO
        String searchUrl = "https://par." + schoolCountry + "/spr_ccm_cm01_100.do?kraOrgNm=" + schoolName;

        try {

            Log.e("TAGE", getHtmlToText(searchUrl));

            JSONParser jsonParser = new JSONParser();

            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(getHtmlToText(searchUrl));

            JSONObject resultObject = (JSONObject) jsonObject.get("resultSVO");
            Log.d("School", "List : " + jsonObject.get("resultSVO"));

            JSONObject dataObject = (JSONObject) resultObject.get("data");

            JSONArray schoolInfoArray = (JSONArray) dataObject.get("orgDVOList");

            //목록 끝까지 반복
            for (int i = 0; i < schoolInfoArray.size(); i++) {

                //배열 안에 있는것도 JSON 형식이기 때문에 JSON Object 로 추출
                final JSONObject schoolObject = (JSONObject) schoolInfoArray.get(i);

                //JSON name으로 추출
                Log.d("School", "orgCode : " + schoolObject.get("orgCode"));
                Log.d("School", "kraOrgNm :" + schoolObject.get("kraOrgNm"));
                Log.d("School", "zipAdres :" + schoolObject.get("zipAdres"));
                Log.d("School", "schulKndScCode :" + schoolObject.get("schulKndScCode"));
                Log.d("School", "schulCrseScCode :" + schoolObject.get("schulCrseScCode"));
                Log.d("School", "schulCrseScCodeNm :" + schoolObject.get("schulCrseScCodeNm"));
                if (Integer.parseInt(schoolObject.get("schulCrseScCode").toString()) != schoolType) {
                    continue;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //목록 추가
                        SchoolSelectListData schoolSelectListData = new SchoolSelectListData();
                        schoolSelectListData.setCountryUrl(schoolCountry);
                        schoolSelectListData.setSchoolName(schoolObject.get("kraOrgNm").toString());
                        schoolSelectListData.setOrgCode(schoolObject.get("orgCode").toString());
                        schoolSelectListData.setZipAddress(schoolObject.get("zipAdres").toString());
                        schoolSelectListData.setSchulKndScCode(schoolObject.get("schulKndScCode").toString());
                        schoolSelectListData.setSchulCrseScCode(schoolObject.get("schulCrseScCode").toString());
                        schoolListDataArraySelectList.add(schoolSelectListData);
                    }
                });
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(new SchoolSelectRecyclerAdapter(schoolListDataArraySelectList, SchoolSelectListActivity.this));
                    recyclerView.setLayoutManager(new WearableLinearLayoutManager(getApplicationContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    SnapHelper snapHelper = new LinearSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerView);
                }
            });

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
