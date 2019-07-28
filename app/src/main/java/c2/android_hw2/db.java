package c2.android_hw2;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class db {
    public static String executeQuery(String php_url, Map<String, Object> item) {
        String result = "";
        HttpURLConnection conn=null;
        InputStream is =null;
        OutputStream os=null;
        try {
            URL url=new URL("http://192.168.1.2/android_hw2_php/"+php_url);//php的位置
            conn=(HttpURLConnection) url.openConnection();//對資料庫打開連結
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.connect();//接通資料庫
            if(item == null){
                is=conn.getInputStream();//從database 開啟 stream

                BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while((line = bufReader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                is.close();
                result = builder.toString();
            }
            else{
                String name="", gender="", birth="", height="", weight="", id="";
                if(item.get("name")!=null)
                    name = item.get("name").toString();
                if(item.get("gender")!=null)
                    gender = item.get("gender").toString();
                if(item.get("birth")!=null)
                    birth = item.get("birth").toString();
                if(item.get("height")!=null)
                    height = item.get("height").toString();
                if(item.get("weight")!=null)
                    weight = item.get("weight").toString();
                if(item.get("id")!=null)
                    id = item.get("id").toString();

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id", id)
                        .appendQueryParameter("name", name)
                        .appendQueryParameter("gender", gender)
                        .appendQueryParameter("birth", birth)
                        .appendQueryParameter("height", height)
                        .appendQueryParameter("weight", weight);
                String query = builder.build().getEncodedQuery();

                os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    result = br.readLine();
                }
                else {
                    result="Error";
                }
//                Log.e("log_tag", );
            }

        } catch(Exception e) {
            Log.e("log_tag", e.toString());
        }

        return result;
    }
}