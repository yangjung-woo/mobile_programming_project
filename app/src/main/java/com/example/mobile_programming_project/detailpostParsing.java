package com.example.mobile_programming_project;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class detailpostParsing {
    String calltitle,callcontent,callimagestring="";
    public void jsonParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray detailObject = jsonObject.getJSONArray("webnautes");
        JSONObject detail = detailObject.getJSONObject(0);
        callcontent = detail.getString("contents");
        calltitle = detail.getString("title");

        callimagestring = detail.getString("picture");
        int k =0;


        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }
}