package com.example.ssajib.sqlite.Db;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.ssajib.sqlite.Model.Student;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudentDb.db";
    public static final String TABLE_STUDENT_NAME = "t_Student";
    public static final String STUDENT_COLUMN_ID = "StudentId";
    public static final String STUDENT_COLUMN_NAME = "StudentName";
    public static final String STUDENT_COLUMN_EMAIL = "Email";
    public static final String STUDENT_COLUMN_ADDRESS = "Address";
    public static final String STUDENT_COLUMN_PHONE = "Phone";
    private static final int DATABASE_VERSION = 4;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table "+TABLE_STUDENT_NAME+" " +
                        "(" +
                        ""+STUDENT_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ""+STUDENT_COLUMN_NAME+" text NOT NULL," +
                        ""+STUDENT_COLUMN_EMAIL+" text NOT NULL," +
                        ""+STUDENT_COLUMN_PHONE+" text NOT NULL," +
                        ""+STUDENT_COLUMN_ADDRESS+" text NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STUDENT_NAME);
        onCreate(db);
    }

    public long addStudent (Student aStudent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(STUDENT_COLUMN_ID, aStudent.getStudentId());
        contentValues.put(STUDENT_COLUMN_NAME,aStudent.getStudentName());
        contentValues.put(STUDENT_COLUMN_EMAIL, aStudent.getEmail());
        contentValues.put(STUDENT_COLUMN_ADDRESS, aStudent.getAddress());
        contentValues.put(STUDENT_COLUMN_PHONE,aStudent.getPhone());
        try{
            return db.insert(""+TABLE_STUDENT_NAME+"", null, contentValues);
        }
        catch (SQLException e){
            return -1;
        }

    }

    public Cursor getData(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_STUDENT_NAME+" where "+STUDENT_COLUMN_ID+" ="+studentId+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_STUDENT_NAME);
        return numRows;
    }

    public Integer updateStudent (Student aStudent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_COLUMN_NAME, aStudent.getStudentName());
        contentValues.put(STUDENT_COLUMN_PHONE, aStudent.getPhone());
        contentValues.put(STUDENT_COLUMN_EMAIL, aStudent.getEmail());
        contentValues.put(STUDENT_COLUMN_ADDRESS, aStudent.getAddress());
        try {
            return db.update(""+TABLE_STUDENT_NAME+"", contentValues, " "+STUDENT_COLUMN_ID+" = ? ", new String[] { Integer.toString(aStudent.getStudentId()) } );
        }
        catch (Exception ex){
            return -1;
        }

    }

    public Integer deleteStudent (Integer studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            return db.delete(""+TABLE_STUDENT_NAME+""," "+STUDENT_COLUMN_ID+" = ? ",
                    new String[] { Integer.toString(studentId) });
        }
        catch (SQLException e){
            return -1;
        }
    }

    public ArrayList<Student> getAllStudent() {
        ArrayList<Student> array_list = new ArrayList<Student>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM  "+TABLE_STUDENT_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Student aStudent = new Student();
            aStudent.setStudentId(res.getInt(res.getColumnIndex(STUDENT_COLUMN_ID)));
            aStudent.setStudentName(res.getString(res.getColumnIndex(STUDENT_COLUMN_NAME)));
            aStudent.setEmail(res.getString(res.getColumnIndex(STUDENT_COLUMN_EMAIL)));
            aStudent.setPhone(res.getString(res.getColumnIndex(STUDENT_COLUMN_PHONE)));
            aStudent.setAddress(res.getString(res.getColumnIndex(STUDENT_COLUMN_ADDRESS)));
            array_list.add(aStudent);
            res.moveToNext();
        }
        return array_list;
    }
}