package com.example.mobile_programming_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class userprofileActivity extends AppCompatActivity {
    String U_ID,U_ID2;
    static RequestQueue requestQueue;
    String json;
    String TEL;
    String EMAIL;
    String BIRTHDAY;
    String user;
    TextView ID_text, tel_text, Email_text, birth_text;
    TextView gender_Text;
    String sel_title;
    String PostNumber;
    ListView mypost;
    ArrayList<String> mylist;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        mypost=findViewById(R.id.my_list);
        U_ID = getIntent().getStringExtra("U_ID");

        if (requestQueue== null)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        mypostlist();
        if (requestQueue== null)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        profile_update(); // DB 에서 UID를 KEY로 사용하여 개인정보 표시
        Button authorize_doctor = findViewById(R.id.btn_authorize);



        authorize_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "계획상 미구현 입니다!", Toast.LENGTH_SHORT).show();
            }
        });
        Button cancel = findViewById(R.id.btn_profile_return);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homepage.class);
                intent.putExtra("U_ID", U_ID);
                startActivity(intent);
            }
        });
        mypost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sel_title=mylist.get(i).toString();
                PostNumber="";
                int index = 0;
                while (sel_title.charAt(index)!='.')
                {
                    PostNumber+=sel_title.charAt(index);
                    index+=1;
                }
                Intent intent=new Intent(userprofileActivity.this,detailpost.class);
                intent.putExtra("postnumber",PostNumber); // U_ID를 게시글 작성시에 사용하도록 전달
                startActivity(intent);
            }
        });

    }
    public void profile_update() {

        //php url 입력
        //String URL = "http://192.168.253.10:443/mobileLogin.php";// 핫스팟 IP
        String URL = "http://192.168.176.130:443/mobileprofile.php";  //기숙사 IP
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            // RequestQueue  클라이언트에서 서버로 전송시 추가적인 데이터를 body 에 추가 POST
            JSONObject jsonObject;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {

                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    json = response;
                    profileParsing lo = new profileParsing();
                    try {
                        lo.jsonParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    U_ID = lo.idx;
                    EMAIL = lo.email;
                    TEL =lo.tel;
                    BIRTHDAY =lo.birth;
                    i =lo.gender;

                    ID_text = findViewById(R.id.H_tVID);
                    ID_text.setText("ID: "+ U_ID);

                    Email_text= findViewById(R.id.H_tVemail);
                    Email_text.setText("이메일: "+EMAIL);

                    tel_text= findViewById(R.id.H_tVTel);
                    tel_text.setText("전화 번호: "+TEL);

                    birth_text= findViewById(R.id.H_tVBirth);
                    birth_text.setText("생년월일: "+BIRTHDAY);

                    gender_Text = findViewById(R.id.H_gender);
                    if (i ==1)
                    {
                        gender_Text.setText("성별: 남성" );
                    }
                    else{
                        gender_Text.setText("성별: 여성" );
                    }
                    mypostlist();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "-1", Toast.LENGTH_SHORT).show();
                //에러나면 error로 나옴
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                param.put("uid", U_ID);  //  getID 값을 key 로 서버에 전송?
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }


    public void mypostlist() {
        //php url 입력
        String URL = "http://192.168.176.130:443/mypost.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    int index=0;
                    mylist = new ArrayList<String>();
                    jsonObject = new JSONObject(response);
                    json=response;
                    profileParsing list = new profileParsing();
                    list.jsonParsing(response);
                    try {
                        while (true) {
                            if (list.title == null) {

                                break;
                            }
                            list.mypostlist(response,index);
                            mylist.add(list.title);
                            index++;
                        } }catch (JSONException e) {

                        e.printStackTrace();
                    }

                    ArrayAdapter MyAdapter = new ArrayAdapter(userprofileActivity.this, android.R.layout.simple_dropdown_item_1line,mylist);
                    mypost.setAdapter(MyAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                param.put("uid",  U_ID);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
