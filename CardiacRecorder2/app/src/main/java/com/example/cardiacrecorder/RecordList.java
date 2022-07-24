package com.example.cardiacrecorder;

import java.util.ArrayList;

public class RecordList {
    public static ArrayList<DataModel> cardiacRecordList =new ArrayList<>();


    public void addRecord(DataModel dataModel){
        if (cardiacRecordList.contains(dataModel)){
            throw new IllegalArgumentException();
        }
        cardiacRecordList.add(dataModel);
    }

    public void deleteRecord(int position){
        if(position>=0 && position< cardiacRecordList.size()){
            cardiacRecordList.remove(position);
        }
        else
        {
            throw new IllegalArgumentException();
        }

    }

    public int count() {
        return cardiacRecordList.size();
    }
}
