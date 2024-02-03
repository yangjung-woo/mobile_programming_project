package com.example.mobile_programming_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class profileParsing {
    String idx="";
    String email="";
    String birth ="";
    int gender;
    String tel ="";
    String title="";
    String p_no="";
    public void jsonParsing(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray detailArray = jsonObject.getJSONArray("webnautes");
        JSONObject detailObject = detailArray.getJSONObject(0);
        //일단 예제에서는 하나만 가져와서 json에 json 쌓여있는 형태임
        // JSONObject detailObject = jsonObject.getJSONObject("webnautes");
        idx = detailObject.getString("U_ID");
        email = detailObject.getString("EMAIL");

        gender =detailObject.getInt("GENDER");

        tel = detailObject.getString("NUM");
        birth =detailObject.getString("BIR");
        //zero = detailObject.getString("0");
    }
    public void mypostlist(String json, int index) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray recoArray = jsonObject.getJSONArray("webnautes");
        JSONObject detailObject = recoArray.getJSONObject(index);
        idx = detailObject.getString("U_ID");
        email = detailObject.getString("EMAIL");

        gender =detailObject.getInt("GENDER");

        tel = detailObject.getString("NUM");
        birth =detailObject.getString("BIR");
        title = detailObject.getString("title");
        p_no = detailObject.getString("post_no");
        title=p_no+".    "+title;

//        trainer_idx=recoObject.getString("trainer_idx");
    }
}



