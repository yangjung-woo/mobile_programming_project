package com.example.mobile_programming_project;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class homepage extends AppCompatActivity {

    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    String getID,getPW;
    String user_index;
    String sel_title;
    EditText loginID, loginPW;
    Button Button_add, Button_login;
    ListView lvlist;
    ArrayList<String> plist;
    String PostNumber;
    String U_ID;

    @Override
    protected void onResume() {
        super.onResume();
        postlist();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        // xml 에서 객체 가져온거 모음
        ImageButton btn_home,btn_chat,btn_reservation,btn_myprofile;
        btn_chat =findViewById(R.id.btn_chat);
        btn_home =findViewById(R.id.btn_home);
        btn_reservation =findViewById(R.id.btn_reservation);
        btn_myprofile =findViewById(R.id.btn_myprofile);
        ImageButton btn_write =findViewById(R.id.btn_write);
        lvlist=findViewById(R.id.postlist);

        // MainActivity 로그인 에서 U_ID 값을 가져와서 U_ID 에 저장
        U_ID = getIntent().getStringExtra("U_ID");


        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        postlist();
        lvlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sel_title=plist.get(i).toString();
                PostNumber="";
                int index = 0;
                while (sel_title.charAt(index)!='.')
                {
                    PostNumber+=sel_title.charAt(index);
                    index+=1;
                }
                Intent intent=new Intent(homepage.this,detailpost.class);
                intent.putExtra("postnumber",PostNumber); // U_ID를 게시글 작성시에 사용하도록 전달
                startActivity(intent);
            }
        });

        // 버튼 클릭시 동작 여러개
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent write_activity = new Intent(homepage.this,WriteActivity.class);
                write_activity.putExtra("U_ID",U_ID); // U_ID를 게시글 작성시에 사용하도록 전달
                startActivity(write_activity);
            }
        });
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "아직 미구현입니다", Toast.LENGTH_SHORT).show();
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "아직 미구현입니다", Toast.LENGTH_SHORT).show();
            }
        });
        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "아직 미구현입니다", Toast.LENGTH_SHORT).show();
            }
        });
        btn_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this, userprofileActivity.class);
                intent.putExtra("U_ID",U_ID);
                startActivity(intent);
            }
        });



    }

    public void postlist() {
        //php url 입력
        String URL = "http://192.168.176.130:443/calltitle.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    int index=0;
                    plist = new ArrayList<String>();
                    jsonObject = new JSONObject(response);
                    json=response;
                    postlistParsing listParsing = new postlistParsing();
                    try {
                        while (true) {
                            if (listParsing.title == null) {

                                break;
                            }

                            listParsing.list(response,index);
                            plist.add(listParsing.title);
                            index++;
                        } }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter MyAdapter = new ArrayAdapter(homepage.this, android.R.layout.simple_dropdown_item_1line,plist);
                    lvlist.setAdapter(MyAdapter);
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
                param.put("U_ID",  U_ID);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}