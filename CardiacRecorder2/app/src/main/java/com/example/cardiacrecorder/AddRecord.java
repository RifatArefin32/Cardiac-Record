package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

public class AddRecord extends AppCompatActivity {
    Button addButton;
    EditText dateEd,sysEd,diaEd,hrEd,timeEd,comEd;
    ArrayList<DataModel> dataArray;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DataModel modelclass;
    Gson gson;
    String dateStr,timeStr;
    boolean isAllFieldsChecked = false;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        addButton=findViewById(R.id.addRecord);
        dateEd=findViewById(R.id.date);
        sysEd=findViewById(R.id.addSystolic);
        diaEd= findViewById(R.id.addDiastolic);
        hrEd =findViewById(R.id.addHeartRate);
        timeEd=findViewById(R.id.time);
        comEd=findViewById(R.id.addComment);

        retrieveData();
        datePicker();
        timePicker();

        addButton.setOnClickListener(view -> {
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {

                int sysInt = Integer.parseInt(sysEd.getText().toString());
                int diasInt = Integer.parseInt(diaEd.getText().toString());
                int heartInt = Integer.parseInt(hrEd.getText().toString());
                String commentStr = comEd.getText().toString();

                modelclass = new DataModel(dateStr,timeStr,sysInt,diasInt,heartInt,commentStr);
                dataArray.add(modelclass);
                Home.dataModelArrayList.add(modelclass);
                Home.cardiacAdapter.notifyDataSetChanged();
                Toast.makeText(AddRecord.this, "Data Insertion Successful", Toast.LENGTH_SHORT).show();
               // PreferenceManager.getDefaultSharedPreferences(AddRecord.this).edit().clear().commit();
                saveData();
                finish();

            }

        });
    }


    private boolean CheckAllFields() {
        if (dateEd.length() == 0) {
            dateEd.setError("This field is required");
            return false;
        }

        if (timeEd.length() == 0) {
            timeEd.setError("This field is required");
            return false;
        }

        if (sysEd.length() == 0) {
            sysEd.setError("This field is required");
            return false;
        }

        String s1 = sysEd.getText().toString();
        int n1 = Integer.parseInt(s1);
        if(n1<0 && n1>200)
        {
            sysEd.setError("Invalid data input");
            return false;
        }

        if (diaEd.length() == 0) {
            diaEd.setError("This field is required");
            return false;
        }

        String s2 = diaEd.getText().toString();
        int n2 = Integer.parseInt(s2);
        if(n2<0 && n2>120)
        {
            diaEd.setError("Invalid data input");
            return false;
        }

        if (hrEd.length() == 0) {
            hrEd.setError("This field is required");
            return false;
        }

        String s3 = hrEd.getText().toString();
        int n3 = Integer.parseInt(s3);

        if(n3<0)
        {
            hrEd.setError("Invalid data input");
            return false;
        }

        // after all validation return true if all required fields are inserted.
        return true;
    }

    private void datePicker()
    {
        dateEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddRecord.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateStr = dayOfMonth + "-" + (month + 1) + "-" + year;
                dateEd.setText(dateStr);
            }
        };
    }

    private void timePicker() {
        timeEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour =calendar.get(Calendar.HOUR_OF_DAY);
                int minute =calendar.get(Calendar.MINUTE);
                //calendar.clear();
                TimePickerDialog dialog =  new TimePickerDialog(AddRecord.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeStr = hourOfDay + ":" +minute;
                        timeEd.setText(timeStr);

                    }
                }, hour , minute,true);
                //  dialog.setTitle("Select Time");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }


    private void retrieveData()
    {
        sharedPreferences = getSharedPreferences("mishu",MODE_PRIVATE);
        gson = new Gson();
        String jsonString = sharedPreferences.getString("mishu",null);
        Type type = new TypeToken<ArrayList<DataModel>>(){}.getType();
        dataArray = gson.fromJson(jsonString,type);
        if(dataArray ==null)
        {
            dataArray = new ArrayList<>();
        }
    }
    private void saveData()
    {
        sharedPreferences = getSharedPreferences("mishu",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        String jsonString = gson.toJson(dataArray);
        Log.d("data inserted",jsonString);
        editor.putString("mishu",jsonString);
        editor.apply();
    }


}
