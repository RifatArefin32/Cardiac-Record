package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import android.app.DatePickerDialog;

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

        addButton.setOnClickListener(view -> {
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {

                int sysInt = Integer.parseInt(sysEd.getText().toString());
                int diasInt = Integer.parseInt(diaEd.getText().toString());
                int heartInt = Integer.parseInt(hrEd.getText().toString());
                String commentStr = comEd.getText().toString();
                dateStr = dateEd.getText().toString();
                timeStr = timeEd.getText().toString();

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

    /**
     * This CheckAllFields() function checks if the fields are valid or not.
     * @return
     *  Return boolean flag
     */

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
        if(n1<0)
        {
            sysEd.setError("Invalid data input");
            return false;
        }
        else if(n1>200)
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
        if(n2<0)
        {

                diaEd.setError("Invalid data input");
                return false;

        }
        else if(n2>120)
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
        if(n3>190)
        {
            hrEd.setError("Invalid data input");
            return false;
        }

        // after all validation return true if all required fields are inserted.
        return true;
    }


    /**
     * This retrieveData() function retrieves data from sharedpreference to our array list.
      */
    private void retrieveData()
    {
        sharedPreferences = getSharedPreferences("record",MODE_PRIVATE);
        gson = new Gson();
        String jsonString = sharedPreferences.getString("record",null);
        Type type = new TypeToken<ArrayList<DataModel>>(){}.getType();
        dataArray = gson.fromJson(jsonString,type);
        if(dataArray ==null)
        {
            dataArray = new ArrayList<>();
        }
    }
    /**
     * This saveData() function saves the data in sharedpreference.
     */
    private void saveData()
    {
        sharedPreferences = getSharedPreferences("record",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        String jsonString = gson.toJson(dataArray);
        Log.d("data inserted",jsonString);
        editor.putString("record",jsonString);
        editor.apply();
    }


}