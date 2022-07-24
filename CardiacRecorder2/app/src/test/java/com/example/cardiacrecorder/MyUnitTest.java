package com.example.cardiacrecorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MyUnitTest {

    @Test
    public void addRecordTest(){
        RecordList recordList= new RecordList();
        DataModel dataModel= new DataModel("12-01-2022","8:20",80, 120, 64, "Normal");
        recordList.addRecord(dataModel);
        assertEquals(1,recordList.cardiacRecordList.size());

        DataModel dataModel2 = new DataModel("11-02-2022","8:30",70, 160, 77, "Not Normal");
        recordList.addRecord(dataModel2);
        assertEquals(2,recordList.cardiacRecordList.size());
        assertTrue(recordList.cardiacRecordList.contains(dataModel));
        assertTrue(recordList.cardiacRecordList.contains(dataModel2));


    }

    @Test
    public void  addRecordExceptionTest(){
        RecordList recordList= new RecordList();
        DataModel dataModel= new DataModel("12-01-2022","8:20",80, 120, 64, "Normal");
        recordList.addRecord(dataModel);
        assertThrows(IllegalArgumentException.class, () -> recordList.addRecord(dataModel));

    }

    @Test
    public void deleteRecordTest(){
        RecordList recordList= new RecordList();
        DataModel record1= new DataModel("12-01-2022","8:20",80, 120, 64, "Normal");
        recordList.addRecord(record1);
        assertEquals(1,recordList.cardiacRecordList.size());

        DataModel record2= new DataModel("11-02-2022","8:30",70, 160, 77, "Not Normal");
        recordList.addRecord(record2);
        assertEquals(2, recordList.cardiacRecordList.size());

        assertTrue(recordList.cardiacRecordList.contains(record1));
        assertTrue(recordList.cardiacRecordList.contains(record2));

        recordList.deleteRecord(0);
        assertEquals(1, recordList.cardiacRecordList.size());
        assertFalse(recordList.cardiacRecordList.contains(record1));
        assertThrows(IllegalArgumentException.class, () -> recordList.deleteRecord(1));
    }

    @Test
    public void testCount(){
        RecordList recordList= new RecordList();
        DataModel record= new DataModel("11-02-2022","8:30",70, 160, 77, "Not Normal");
        recordList.addRecord(record);

        assertEquals(1,recordList.count());
    }
    

}
