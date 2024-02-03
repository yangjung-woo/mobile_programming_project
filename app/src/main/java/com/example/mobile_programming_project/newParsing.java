package com.example.mobile_programming_project;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class newParsing {
    String idx="";
    public void jsonParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray detailObject = jsonObject.getJSONArray("webnautes");
        JSONObject detail = detailObject.getJSONObject(0);
        idx = detail.getString("user_idx");

        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        //JSONObject detailObject = jsonObject.getJSONObject("webnautes");


        //idx = detailObject.getString("user_idx");
        //zero = detailObject.getString("0");
    }
}