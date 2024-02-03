package com.example.mobile_programming_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class loginParsing {
    String idx="";
    String zero="";
    public void jsonParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray detailArray = jsonObject.getJSONArray("webnautes");

        JSONObject detailObject = detailArray.getJSONObject(0);

        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        // JSONObject detailObject = jsonObject.getJSONObject("webnautes");

        idx = detailObject.getString("U_ID");
        //zero = detailObject.getString("0");
    }
}