package c2.android_hw2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2019/7/28.
 */

public class AddUserDataActivity extends Activity {

    private EditText birthday, eHeight, eWeight, eName;
    private RadioButton rMale, rFemale;
    private String gender;
    private TextView tError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
        getViews();
    }

    private void getViews(){
        birthday = findViewById(R.id.birthday);
        eName = findViewById(R.id.name);
        eWeight = findViewById(R.id.weight);
        eHeight = findViewById(R.id.height);
        rMale = findViewById(R.id.male);
        rFemale = findViewById(R.id.female);
        tError = findViewById(R.id.error);
    }

    public void datePicker(View v){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String dateTime = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
                birthday.setText(dateTime);
            }
        }, year, month, day).show();
    }

    public void Submit(View v){
//        ArrayList<Map<String, Object>> arr_list = new ArrayList<Map<String, Object>>();

        String str_height = eHeight.getText().toString();
        String str_weight = eWeight.getText().toString();

        String name = eName.getText().toString();
        String birth = birthday.getText().toString();

        if(rMale.isChecked())
            gender = "Male";
        else if(rFemale.isChecked())
            gender = "Female";

        if((!TextUtils.isEmpty(str_height)) & (!TextUtils.isEmpty(str_weight)) & (!TextUtils.isEmpty(name)) & (!TextUtils.isEmpty(gender)) & (!TextUtils.isEmpty(birth))){
            try {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("name", name);
                item.put("gender", gender);
                item.put("birth", birth);
                item.put("height", str_height);
                item.put("weight", str_weight);

                String result = db.executeQuery("insertUserData.php", item);
                Log.e("log_tag", result);

            } catch(Exception e) {
                Log.e("log_tag", e.toString());
            }
            tError.setText("");
            this.finish();
        }
        else
            tError.setText("有欄位輸入錯誤或未填寫");

    }

    public void onBackClick(View view) {
        this.finish();
    }
}
