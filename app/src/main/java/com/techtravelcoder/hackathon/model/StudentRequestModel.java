package com.techtravelcoder.hackathon.model;

public class StudentRequestModel {
    String s_name,s_email,s_phone,s_description;
    String s_image,uid1;

    public StudentRequestModel(){

    }

    public StudentRequestModel(String s_name, String s_email, String s_phone, String s_description,String uid1) {
        this.s_name = s_name;
        this.s_email = s_email;
        this.s_phone = s_phone;
        this.s_description = s_description;
        this.uid1 = uid1;
    }

    public String getUid1() {
        return uid1;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1;
    }

    public String getS_image() {
        return s_image;
    }

    public void setS_image(String s_image) {
        this.s_image = s_image;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_email() {
        return s_email;
    }

    public void setS_email(String s_email) {
        this.s_email = s_email;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getS_description() {
        return s_description;
    }

    public void setS_description(String s_description) {
        this.s_description = s_description;
    }
}
