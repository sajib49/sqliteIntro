package com.example.ssajib.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssajib.sqlite.Db.DBHelper;
import com.example.ssajib.sqlite.Model.Student;


public class MainActivity extends AppCompatActivity {

    EditText etName,etAddress,etEmail,etPhone;
    String name,email,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
    }

    private boolean validateUI(){
        if(etName.getText().toString().length() == 0 ){
            etName.setError("Please enter student name!" );
            return false;
        }
        if(etAddress.getText().toString().length() == 0 ){
            etAddress.setError("Please enter address!" );
            return false;
        }
        if(etEmail.getText().toString().length() == 0 ){
            etEmail.setError("Please enter email!" );
            return false;
        }
        if(etPhone.getText().toString().length() == 0 ){
            etPhone.setError("Please enter phone!" );
            return false;
        }
        return true;
    }
    public void Insert(View view) {
       if(validateUI()){
           DBHelper dbHelper =new DBHelper(this);
           Student student = new Student();

           student.setStudentName(etName.getText().toString());
           student.setAddress(etAddress.getText().toString());
           student.setPhone(etPhone.getText().toString());
           student.setEmail(etEmail.getText().toString());

           if(dbHelper.addStudent(student)>0){
               Toast.makeText(this,"Successfully Inserted",Toast.LENGTH_LONG).show();
               Intent intent = new Intent(MainActivity.this,StudentListActivity.class);
               startActivity(intent);
           }
           else
           {
               Toast.makeText(this,"Insertion Failed.Please try again.",Toast.LENGTH_LONG).show();
           }
       }

    }


}
