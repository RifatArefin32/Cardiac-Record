package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.example.cardiacrecorder.adapter.RecordAdapter;
//import com.example.cardiacrecorder.model.RecordModel;
import com.example.cardiacrecorder.adapters.CardiacAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Home extends AppCompatActivity {
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CardiacAdapter.RecyclerViewClickListener listener;
    public static CardiacAdapter cardiacAdapter;
    public static ArrayList<DataModel> dataModelArrayList;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DataModel modelclass;
    Gson gson;

    FloatingActionButton button;
    Button deleteButton, updateButton;
    String s1,s2,s3,s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button=findViewById(R.id.fab);
        retrieveData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,AddRecord.class);
                startActivity(intent);
                //finish();

            }
        });

        recyclerView=findViewById(R.id.recordRecyclerView);
        cardiacAdapter =new CardiacAdapter(Home.this, dataModelArrayList,listener);
        linearLayoutManager = new LinearLayoutManager(Home.this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(cardiacAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        cardiacAdapter.setClickListener(new CardiacAdapter.ClickListener() {
            @Override
            public void customOnClick(int position, View v) {
                Intent intent = new Intent(Home.this,ViewRecord.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                dataModelArrayList.remove(position);
                cardiacAdapter.notifyItemRemoved(position);
                saveData();
                Toast.makeText(Home.this,"Delete Successful",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEditClick(int position) {
                Intent intent = new Intent(Home.this, UpdateRecord.class);
                intent.putExtra("index",position);
                startActivity(intent);
                //finish();
            }
        });


    }

    /**
     * This saveData() function saves the data in sharedpreference.
     */
    private void saveData()
    {
        sharedPreferences = getSharedPreferences("record",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        String jsonString = gson.toJson(dataModelArrayList);
        editor.putString("record",jsonString);
        editor.apply();
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
        dataModelArrayList = gson.fromJson(jsonString,type);
        if(dataModelArrayList ==null)
        {
            dataModelArrayList = new ArrayList<>();
        }
//        dataModelArrayList.clear();
//        saveData();
    }


}