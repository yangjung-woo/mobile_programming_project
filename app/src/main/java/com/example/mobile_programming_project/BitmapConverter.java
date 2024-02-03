package com.example.mobile_programming_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapConverter {
    // php 전송시   bitmap -> String 으로 변환해주는 메소드 이하 3개
    public static String bitmapToByteArray(Bitmap bitmap){
        String image ="";
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        image = ""+ byteArrayToBinaryString(byteArray);
        return image;
    }
    public static String byteArrayToBinaryString(byte[] b){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< b.length; i++){
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }
    public static String byteToBinaryString(byte n){
        StringBuilder sb =new StringBuilder("00000000");
        for (int bit= 0; bit<8;bit++){
            if(((n>>bit)&1)>0){
                sb.setCharAt(7-bit,'1');
            }
        }
        return sb.toString();
    }
    // DB 에서 꺼내올시    String -> Bitmap 으로 변환해주는 메소드 이하 3개
    /*
    public static Bitmap ByteArrayToBitmap (byte [] $byteArray){


        Bitmap bitmap = BitmapFactory.decodeByteArray($byteArray , 0, $byteArray.length);
        return bitmap;
    }

     */

    public static  Bitmap binaryStringToBitmap(String s)
    {
        byte[] $byteArray =binaryStringToByteArray(s);

        Bitmap bitmap = BitmapFactory.decodeByteArray($byteArray,0,$byteArray.length);
        return bitmap;
    }
    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }


}
