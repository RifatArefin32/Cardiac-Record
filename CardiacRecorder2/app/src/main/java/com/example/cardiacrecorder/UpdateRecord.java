package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TimePicker;
import android.widget.DatePicker;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateRecord extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;
    ArrayList<DataModel> dataModelArrayList;
    DataModel dataModel;
    EditText dateET,timeET,systolicET,diastolicET,heartRateET,commentET;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    String dateStr,timeStr;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",-1);
        dateET= findViewById(R.id.updateDate);
        timeET = findViewById(R.id.updateTime);
        systolicET = findViewById(R.id.updateSystolic);
        diastolicET = findViewById(R.id.updateDiastolic);
        heartRateET = findViewById(R.id.updateHeartRate);
        commentET = findViewById(R.id.updateComment);
        Button updateButton = findViewById( R.id.updateRecord);
        retrieveData();


        dataModel = dataModelArrayList.get(index);

        dateET.setText(dataModel.getDate());
        timeET.setText(dataModel.getTime());
        systolicET.setText(String.valueOf(dataModel.getSystolic()));
        diastolicET.setText(String.valueOf(dataModel.getDiastolic()));
        heartRateET.setText(String.valueOf(dataModel.getHeartRate()));
        commentET.setText(dataModel.getComment());
        
        
        updateButton.setOnClickListener(view -> {

            isAllFieldsChecked = CheckAllFields();
            if(isAllFieldsChecked)
            {
                int sysInt = Integer.parseInt(systolicET.getText().toString());
                int diasInt = Integer.parseInt(diastolicET.getText().toString());
                int heartInt = Integer.parseInt(heartRateET.getText().toString());
                String commentStr = commentET.getText().toString();
                dateStr = dateET.getText().toString();
                timeStr = timeET.getText().toString();
                dataModel = new DataModel(dateStr,timeStr,sysInt,diasInt,heartInt,commentStr);

                dataModelArrayList.set(index,dataModel);
                Home.dataModelArrayList.set(index,dataModel);
                Home.cardiacAdapter.notifyDataSetChanged();
                PreferenceManager.getDefaultSharedPreferences(UpdateRecord.this).edit().clear().commit();
                saveData();
                Toast.makeText(UpdateRecord.this,"Update successful",Toast.LENGTH_SHORT).show();

                finish();
            }


        });

    }

    private boolean CheckAllFields() {
        if (dateET.length() == 0) {
            dateET.setError("This field is required");
            return false;
        }

        if (timeET.length() == 0) {
            timeET.setError("This field is required");
            return false;
        }

        if (systolicET.length() == 0) {
            systolicET.setError("This field is required");
            return false;
        }

        String s1 = systolicET.getText().toString();
        int n1 = Integer.parseInt(s1);
        if(n1<0 && n1>200)
        {
            systolicET.setError("Invalid data input");
            return false;
        }

        if (diastolicET.length() == 0) {
            diastolicET.setError("This field is required");
            return false;
        }

        String s2 = diastolicET.getText().toString();
        int n2 = Integer.parseInt(s2);
        if(n2<0 && n2>120)
        {
            diastolicET.setError("Invalid data input");
            return false;
        }

        if (heartRateET.length() == 0) {
            heartRateET.setError("This field is required");
            return false;
        }

        String s3 = heartRateET.getText().toString();
        int n3 = Integer.parseInt(s3);

        if(n3<0)
        {
            heartRateET.setError("Invalid data input");
            return false;
        }

        // after all validation return true if all required fields are inserted.
        return true;
    }




    private void retrieveData()
    {
        sharedPreferences = getSharedPreferences("mishu",MODE_PRIVATE);
        gson = new Gson();
        String jsonString = sharedPreferences.getString("mishu",null);
        Type type = new TypeToken<ArrayList<DataModel>>(){}.getType();
        dataModelArrayList = gson.fromJson(jsonString,type);
        if(dataModelArrayList ==null)
        {
            dataModelArrayList = new ArrayList<>();
        }
    }

    private void saveData()
    {
        sharedPreferences = getSharedPreferences("mishu",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        String jsonString = gson.toJson(dataModelArrayList);
        editor.putString("mishu",jsonString);
        editor.apply();
    }



}
