package com.example.mobile_programming_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class detailpost extends homepage {
    private final String TAG = this.getClass().getSimpleName();
    String json;
    static RequestQueue requestQueue;


    // 파싱할 정보들 모음
    String NUMBER,U_ID,parsing_title,parsing_bitmap;
    String Bitmap_string;
    Button btn_return;
    ImageView upload_image;
    TextView Title, Content;
    Bitmap return_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpost);
        //getImage = findViewById(R.id.imageView);
        //게시글에 넣을 제목과 내용
        Title = findViewById(R.id.title);
        Content = findViewById(R.id.content);
        upload_image =findViewById(R.id.show_post_ImageView);
        btn_return =findViewById(R.id.dBtn_cancel);
        // home page 에서 전달한 U_ID 값을 사용할게
        NUMBER = getIntent().getStringExtra("postnumber");



        // 서버에 요청
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        callpost();

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    } // onCreate end

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Log.e(TAG, "result : " + result);
                        Intent intent = result.getData();
                        Log.e(TAG, "intent : " + intent);
                        Uri uri = intent.getData();
                        Log.e(TAG, "uri : " + uri);
                        //upload_image.setImageURI(uri);
                        Glide.with(detailpost.this)
                                .load(uri)
                                .into(upload_image);
                    }
                }
            }
    ); //Activity Result end

    
    public void callpost()  // php 서버로 데이터 전송
    {
        String URL = "http://192.168.176.130:443/callpost.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            JSONObject jsonObject;
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                try {
                    jsonObject = new JSONObject(response);
                    json=response;
                    detailpostParsing pp = new detailpostParsing();
                    try {
                            pp.jsonParsing(response);
                            Title.setText(pp.calltitle);
                            Content.setText(pp.callcontent);
                            Bitmap_string = pp.callimagestring;
                            if (Bitmap_string!="")
                            {
                                return_bitmap = BitmapConverter.binaryStringToBitmap(Bitmap_string);
                                upload_image.setImageBitmap(return_bitmap);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "이미지가 없는 게시글입니다", Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }}) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음

                param.put("post_no",  NUMBER);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }

}
