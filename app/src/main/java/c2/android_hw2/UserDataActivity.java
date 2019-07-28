package c2.android_hw2;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
//import java.util.function.DoubleConsumer;

/**
 * Created by user on 2019/7/28.
 */

public class UserDataActivity extends Activity{
    private TextView tBMI, tBMR;
    private EditText eHeight, eWeight, eGender, eName, eBirth;
    private int current_year,current_month, current_day;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data);
        getViews();
        current_date();
        getData();
    }

    public void getViews(){
        eHeight = findViewById(R.id.height);
        eWeight = findViewById(R.id.weight);
        eGender = findViewById(R.id.gender);
        eName = findViewById(R.id.name);
        eBirth = findViewById(R.id.birth);
        tBMI = findViewById(R.id.bmi);
        tBMR = findViewById(R.id.bmr);
    }

    public void current_date(){
        Calendar calendar = Calendar.getInstance();
        current_year = calendar.get(Calendar.YEAR);
        current_month = calendar.get(Calendar.MONTH) + 1;
        current_day = calendar.get(Calendar.DAY_OF_MONTH);

    }

    public void getData(){
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", id);

        try {
            String result = db.executeQuery("getUserDataById.php", item);

            JSONArray jsonArray = new JSONArray(result);

            JSONObject jsonData = jsonArray.getJSONObject(0);
            String name = jsonData.getString("name");
            String gender = jsonData.getString("gender");
            String birth = jsonData.getString("birth");
            String height = jsonData.getString("height");
            String weight = jsonData.getString("weight");

            double bmrValue;
            int age;
            String[] birth_arr = birth.split("-");
            int birth_year = Integer.parseInt(birth_arr[0]);
            int birth_month = Integer.parseInt(birth_arr[1]);
            int birth_day = Integer.parseInt(birth_arr[2]);
            if(current_month < birth_month)
                age = current_year - birth_year -1;
            else if(current_month > birth_day)
                age = current_year - birth_year;
            else
            if(current_day < birth_day)
                age = current_year - birth_year -1;
            else
                age = current_year - birth_year;

            double d_height = Double.parseDouble(height);
            double d_weight = Double.parseDouble(weight);

            double bmiValue = d_weight / ((d_height / 100) * (d_height / 100));
            if(gender.equals("Male"))
                bmrValue = 66 + (13.7 * d_weight) + (5.0 * d_height) - (6.8 * age);
            else
                bmrValue =  655 + (9.6 * d_weight) + (1.8 * d_height) - (4.7 * age);

            eName.setText(name);
            eGender.setText(gender);
            eBirth.setText(birth);
            eHeight.setText(height);
            eWeight.setText(weight);
            tBMI.setText(String.format("%.2f", bmiValue));
            tBMR.setText(String.format("%.2f", bmrValue));

        } catch(Exception e) {
            Log.e("log_tag", e.toString());
        }
    }

    public void onUpdateClick(View v){
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", id);

        String height = eHeight.getText().toString();
        String weight = eWeight.getText().toString();
        String name = eName.getText().toString();
        String birth = eBirth.getText().toString();
        String gender = eGender.getText().toString();

        if((!TextUtils.isEmpty(height)) & (!TextUtils.isEmpty(weight)) & (!TextUtils.isEmpty(name)) & (!TextUtils.isEmpty(gender)) & (!TextUtils.isEmpty(birth))) {
            try {
                item.put("name", name);
                item.put("gender", gender);
                item.put("birth", birth);
                item.put("height", height);
                item.put("weight", weight);

                String result = db.executeQuery("editUserData.php", item);
                Log.e("log_tag", result);

                double bmrValue;
                int age;
                String[] birth_arr = birth.split("-");
                int birth_year = Integer.parseInt(birth_arr[0]);
                int birth_month = Integer.parseInt(birth_arr[1]);
                int birth_day = Integer.parseInt(birth_arr[2]);
                if(current_month < birth_month)
                    age = current_year - birth_year -1;
                else if(current_month > birth_day)
                    age = current_year - birth_year;
                else
                if(current_day < birth_day)
                    age = current_year - birth_year -1;
                else
                    age = current_year - birth_year;

                double d_height = Double.parseDouble(height);
                double d_weight = Double.parseDouble(weight);

                double bmiValue = d_weight / ((d_height / 100) * (d_height / 100));
                if(gender.equals("Male"))
                    bmrValue = 66 + (13.7 * d_weight) + (5.0 * d_height) - (6.8 * age);
                else
                    bmrValue =  655 + (9.6 * d_weight) + (1.8 * d_height) - (4.7 * age);

                tBMI.setText(String.format("%.2f", bmiValue));
                tBMR.setText(String.format("%.2f", bmrValue));

            } catch (Exception e) {
                Log.e("log_tag", e.toString());
            }
        }
        else
            Toast.makeText(UserDataActivity.this, "Update Deny", Toast.LENGTH_SHORT).show();
    }

    public void onDelClick(View v){
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", id);

        try {
            String result = db.executeQuery("deleteUserData.php", item);
            Log.e("log_tag", result);
            this.finish();

        } catch (Exception e) {
            Log.e("log_tag", e.toString());
            Toast.makeText(UserDataActivity.this, "Delete Fail", Toast.LENGTH_SHORT).show();
        }

    }

    public void onBackClick(View view) {
        this.finish();
    }
}
