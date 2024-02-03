package com.example.mobile_programming_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    String getID,getPW;
    String U_ID;
    EditText loginID, loginPW;
    Button Button_add, Button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button_add = findViewById(R.id.goregister);
        Button_login = findViewById(R.id.btnlogin);
        loginID = findViewById(R.id.editID);
        loginPW = findViewById(R.id.editPW);

        if (requestQueue==null)
        {
            requestQueue=Volley.newRequestQueue(getApplicationContext());
        }

        Button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getID=loginID.getText().toString();
                getPW=loginPW.getText().toString();
                login();
            }
        });
        Button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),register.class);
                startActivity(intent);
            }
        });
    }
    public void login() {
        //php url 입력
        String URL = "http://192.168.176.130:443/mobileLogin.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    json = response;
                    loginParsing lo = new loginParsing();
                    try {
                        lo.jsonParsing(json);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                    U_ID=lo.idx;
                    if (U_ID!=""){
                        Intent intent = new Intent(getApplicationContext(), homepage.class);
                        intent.putExtra("U_ID", U_ID);
                        startActivity(intent);
                        //finish();   //로그인 성공 시 뒤로가기 버튼 눌렀을 때 로그인 화면으로 돌아가지 않게 하기 위함
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "ID/PW를 다시 확인하세요", Toast.LENGTH_SHORT).show();
                    }
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
                param.put("getID", getID);
                param.put("getPW", getPW);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

}