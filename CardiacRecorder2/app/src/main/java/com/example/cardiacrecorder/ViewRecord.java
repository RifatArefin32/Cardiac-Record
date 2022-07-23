package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewRecord extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;
    ArrayList<DataModel> recordsArrayList;
    DataModel dataModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        Intent intent = getIntent();
        int extras = intent.getIntExtra("position",0);

        TextView sys = findViewById(R.id.systolicData);
        TextView dias = findViewById(R.id.diastolicData);
        TextView heart = findViewById(R.id.heartRateData);
        TextView time = findViewById(R.id.measuredTime);
        TextView date = findViewById(R.id.measuredDate);
        TextView comment = findViewById(R.id.commentData);

        retrieveData();
/*      String Mdate = "";
        String Mtime = "";
        String systolic = "";
        String diastolic = "";
        String heartRate = "";
        String commentD = "";*/
        dataModel = recordsArrayList.get(extras);
        if(extras>=0)
        {
            sys.setText(""+dataModel.getSystolic());
            dias.setText(""+dataModel.getDiastolic());
            heart.setText(""+dataModel.getHeartRate());
            date.setText(""+dataModel.getDate());
            time.setText(""+dataModel.getTime());
            comment.setText(""+dataModel.getComment());
        }
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
        recordsArrayList = gson.fromJson(jsonString,type);
        if(recordsArrayList ==null)
        {
            recordsArrayList = new ArrayList<>();
        }
    }
}
