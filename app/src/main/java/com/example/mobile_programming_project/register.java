package com.example.mobile_programming_project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class register extends MainActivity{
    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    Button createaccount;
    String nid,npw,pwasn,newbir,email,number,newaccount,newgender;
    EditText newID, newPW,pwASGN,Email,Number;
    TextView newbirth;
    RadioGroup Radiogender;
    RadioButton select_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button create_account = findViewById(R.id.complete);
        // Weight=findViewById(R.id.weight);
        // Height=findViewById(R.id.height);
        newID=findViewById(R.id.id);
        newPW=findViewById(R.id.password);
        pwASGN = findViewById(R.id.passsign);
        newbirth=findViewById(R.id.birth);
        Email=findViewById(R.id.email);
        Number=findViewById(R.id.number);
        Radiogender=findViewById(R.id.group);


        Calendar calendar1=Calendar.getInstance();

        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }


        Calendar c = Calendar.getInstance();


        newbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popdialog(c);
            }
        });

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nid = newID.getText().toString();
                npw = newPW.getText().toString();
                newbir = newbirth.getText().toString();
                email = Email.getText().toString();
                number = Number.getText().toString();
                pwasn = pwASGN.getText().toString();
                select_gender = findViewById(Radiogender.getCheckedRadioButtonId());
                if (select_gender!=null) {
                    newgender = select_gender.getText().toString();
                    if (nid.equals("") || npw.equals("") || newbir.equals("") || email.equals("") || number.equals("") || newgender.equals("")) {
                        Toast.makeText(getApplicationContext(), "입력사항을 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    } else {
                        if (npw.equals(pwasn)) {
                        createaccount();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "입력사항을 모두 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
            });
        };


    public void popdialog(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        InputMethodManager manage = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manage.hideSoftInputFromWindow(newbirth.getWindowToken(),0);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                register.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        newbirth.setText(year+"/"+(monthOfYear + 1)+"/"+dayOfMonth);

                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
    public void createaccount() {

        //php url 입력
        String URL = "http://192.168.176.130:443/mobileregister.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            JSONObject jsonObject;
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴

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
                param.put("nid", nid);
                param.put("npw", npw);
                param.put("newbir", newbir);
                param.put("email", email);
                param.put("number", number);
                param.put("newgender", newgender);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }


}


