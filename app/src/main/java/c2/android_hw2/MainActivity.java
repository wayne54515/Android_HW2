package c2.android_hw2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private ListView mListView;
    private ArrayList<Map<String, Object>> arr_list = new ArrayList<Map<String, Object>>();
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();

    }

    public void getData(){
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new ViewAdapter(MainActivity.this, arr_list));

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        try {
            String result = db.executeQuery("getUserData.php", null);

            jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                String name = jsonData.getString("name");
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("object", name);
                item.put("id", jsonData.getString("id"));

                arr_list.add(item);

            }
        } catch(Exception e) {
            Log.e("log_tag", e.toString());
        }

        mListView.setAdapter(new ViewAdapter(MainActivity.this, arr_list));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(MainActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();

                // 存物件必須實作序列化
                bundle.putString("id", parent.getItemAtPosition(position).toString());

                Intent intent = new Intent(MainActivity.this, UserDataActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void addUser(View v){
        Intent intent = new Intent(this, AddUserDataActivity.class);
//        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void refresh(View v){
        finish();
        startActivity(getIntent());
    }
}
