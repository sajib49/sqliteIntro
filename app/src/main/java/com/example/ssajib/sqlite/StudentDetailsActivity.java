package com.example.ssajib.sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ssajib.sqlite.Db.DBHelper;
import com.example.ssajib.sqlite.Model.Student;

public class StudentDetailsActivity extends AppCompatActivity {

    EditText etName,etAddress,etEmail,etPhone;
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);

        student = (Student) getIntent().getSerializableExtra("EDIT_STUDENT");

        if(student != null){
            etName.setText(student.getStudentName());
            etAddress.setText(student.getAddress());
            etEmail.setText(student.getEmail());
            etPhone.setText(student.getPhone());
        }

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
    public void Edit(View view) {
        if(validateUI()){
            if(student != null){
                final DBHelper dbHelper = new DBHelper(this);
                AlertDialog.Builder altdial = new AlertDialog.Builder(this);
                                altdial.setMessage("Do you want to update this student?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                student.setEmail(etEmail.getText().toString());
                                student.setStudentName(etName.getText().toString());
                                student.setPhone(etPhone.getText().toString());
                                student.setAddress(etAddress.getText().toString());

                                if(dbHelper.updateStudent(student)>0){

                                    Toast.makeText(getApplicationContext(),"Successfully updated",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),StudentListActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Failed,try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle(student.getStudentName());
                alert.show();
            } else{
                Toast.makeText(this,"Select student first",Toast.LENGTH_LONG).show();
            }
        }


    }
}
