package com.example.ssajib.sqlite.Model;

import java.io.Serializable;

public class Student  implements Serializable {
    int StudentId;
    String StudentName;
    String Email;
    String Address;
    String Phone;

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String name) {
        StudentName = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

   /* public Student(int studentId, String name, String email, String phone, String address) {
        this.StudentId = studentId;
        this.Name = name;
        this.Email = email;
        this.Phone = phone;
        this.Address = address;
    }*/

}
