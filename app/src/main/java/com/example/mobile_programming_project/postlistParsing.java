package com.example.mobile_programming_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class postlistParsing {
    String title="";
    String p_no="";
    public void list(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");
        JSONObject detailObject = recoArray.getJSONObject(index);
        //zero = detailObject.getString("0");
        title = detailObject.getString("title");
        p_no = detailObject.getString("post_no");
        title=p_no+".    "+title;

//        trainer_idx=recoObject.getString("trainer_idx");
    }
}

