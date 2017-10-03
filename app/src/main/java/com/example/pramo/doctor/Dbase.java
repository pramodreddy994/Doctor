package com.example.pramo.doctor;

/**
 * Created by pramo on 4/20/2017.
 */

public class Dbase {
    String email;
    String name;
    String mobile;
    String hospital;
    String speculation;
    String password;

    public Dbase(String email, String name, String mobile, String hospital, String speculation, String password) {
        this.email = email;
        this.name = name;
        this.mobile = mobile;
        this.hospital = hospital;
        this.speculation = speculation;
        this.password = password;
    }
    public Dbase(){}

    public Dbase(String email) {
        this.email = email;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getSpeculation() {
        return speculation;
    }

    public void setSpeculation(String speculation) {
        this.speculation = speculation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
