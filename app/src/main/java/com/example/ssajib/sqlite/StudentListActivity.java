package com.example.ssajib.sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ssajib.sqlite.Adapter.StudentAdapter;
import com.example.ssajib.sqlite.Db.DBHelper;
import com.example.ssajib.sqlite.Model.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    ListView lv_Students;
    ArrayList<Student> studentList;
    StudentAdapter adapter;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        dbHelper = new DBHelper(this);

        studentList = new ArrayList<>();
        studentList = dbHelper.getAllStudent();

         adapter = new StudentAdapter(studentList,this);
        lv_Students = (ListView) findViewById(R.id.lvStudents);


        lv_Students.setAdapter(adapter);

        registerForContextMenu(lv_Students);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(studentList.get(info.position).getStudentName());

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

         switch(item.getItemId()){
            case R.id.delete_id:

                AlertDialog.Builder altdial = new AlertDialog.Builder(this);
                altdial.setMessage("Do you want to delete this student?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(dbHelper.deleteStudent(studentList.get(info.position).getStudentId())>0){
                                    studentList.remove(info.position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(),"Successfully deleted",Toast.LENGTH_LONG).show();
                                    }
                                else{
                                    Toast.makeText(getApplicationContext(),"Failed to delete.Try again",Toast.LENGTH_LONG).show();

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
                alert.setTitle(studentList.get(info.position).getStudentName());
                alert.show();
                return false;

            case R.id.edit_id:
                Intent intent = new Intent(this,StudentDetailsActivity.class);
                intent.putExtra("EDIT_STUDENT",studentList.get(info.position));
                startActivity(intent);
                return false;
            default:
                return super.onContextItemSelected(item);
        }

    }
}
