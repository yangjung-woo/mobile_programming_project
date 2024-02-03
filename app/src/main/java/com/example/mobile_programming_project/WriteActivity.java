package com.example.mobile_programming_project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.example.mobile_programming_project.BitmapConverter;

public class WriteActivity extends AppCompatActivity
{
    private final String TAG = this.getClass().getSimpleName();
    String json;
    static RequestQueue requestQueue;


    // 파싱할 정보들 모음
    String parsing_context,parsing_category,U_ID,parsing_title,parsing_bitmap;
    Button btn_upload_article,btn_cancel_article;
    ImageView upload_image;
    EditText edit_title, edit_context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        btn_upload_article = findViewById(R.id.Wbtn_upload);
        btn_cancel_article = findViewById(R.id.Wbtn_cancel);
        //getImage = findViewById(R.id.imageView);
        //게시글에 넣을 제목과 내용
        edit_title = findViewById(R.id.edit_title);
        edit_context = findViewById(R.id.edit_content);
        upload_image= findViewById(R.id.imageView);

        // home page 에서 전달한 U_ID 값을 사용할게
        U_ID = getIntent().getStringExtra("U_ID");

        // 서버에 요청
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        upload_image.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            launcher.launch(intent);
        });


        btn_upload_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //전달할 정보  1. 제목 , 내용 , 이미지 , 라디오버튼 정보
                RadioGroup category = findViewById(R.id.Wbtn_radioGroup);
                RadioButton select_category = findViewById(category.getCheckedRadioButtonId());
                BitmapDrawable bitmap_img = (BitmapDrawable) upload_image.getDrawable();
                Bitmap bitmap = bitmap_img.getBitmap(); //bitmap 에 이미지 비트맵 저장
                parsing_bitmap = BitmapConverter.bitmapToByteArray(bitmap);

                // 입력문 전부다 입력해주세요
                // 파싱 객체
                parsing_title = edit_title.getText().toString();
                if (parsing_title == ""){
                    Toast.makeText(getApplicationContext(), "제목을 입력 해주세요!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    parsing_context = edit_context.getText().toString();
                    if (parsing_context =="")
                    {
                        Toast.makeText(getApplicationContext(), "내용을 입력 해주세요!!", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        if (select_category != null) //라디오 버튼 선택시!
                        {
                            parsing_category = select_category.getText().toString();

                            writepost();
                            Toast.makeText(getApplicationContext(), "업로드 되었습니다", Toast.LENGTH_SHORT).show();


                            //finish():
                        } else {
                            Toast.makeText(getApplicationContext(), "카테고리를 선택 해주세요", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),homepage.class);
                            intent.putExtra("U_ID", U_ID);
                            startActivity(intent);
                        }
                    }
                }

            }
        });
        btn_cancel_article.setOnClickListener(new View.OnClickListener() {
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
                        Glide.with(WriteActivity.this)
                                .load(uri)
                                .into(upload_image);
                    }
                }
            }
    ); //Activity Result end

    public void writepost()  // php 서버로 데이터 전송
    {
        String URL = "http://192.168.176.130:443/mobilewritepost.php"; //기숙사 yonsei  IP

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

                param.put("title",  parsing_title);
                param.put("content",  parsing_context);
                param.put("category",parsing_category);
                param.put("Image",parsing_bitmap);
                param.put("U_ID",U_ID);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }

}
