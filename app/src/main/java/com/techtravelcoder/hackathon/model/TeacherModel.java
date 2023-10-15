package com.techtravelcoder.hackathon.model;

public class TeacherModel {
    String teacherNames,teacherPassword,teacherEmail,teacherPhoneNumber,userUniversity,userDepartment,teacherId,expertCategory,teacherImage,teacherbio,uid;


    public TeacherModel(String teacherNames, String teacherPassword, String teacherEmail, String teacherPhoneNumber, String userUniversity, String userDepartment, String teacherId, String expertCategory, String uid) {
        this.teacherNames = teacherNames;
        this.teacherPassword = teacherPassword;
        this.teacherEmail = teacherEmail;
        this.teacherPhoneNumber = teacherPhoneNumber;
        this.userUniversity = userUniversity;
        this.userDepartment = userDepartment;
        this.teacherId = teacherId;
        this.expertCategory = expertCategory;
        this.uid = uid;
    }

    public TeacherModel(String teacherNames, String teacherPassword, String teacherEmail, String teacherPhoneNumber, String userUniversity, String userDepartment, String teacherId, String expertCategory) {
        this.teacherNames = teacherNames;
        this.teacherPassword = teacherPassword;
        this.teacherEmail = teacherEmail;
        this.teacherPhoneNumber = teacherPhoneNumber;
        this.userUniversity = userUniversity;
        this.userDepartment = userDepartment;
        this.teacherId = teacherId;
        this.expertCategory = expertCategory;

    }

    public TeacherModel(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTeacherbio() {
        return teacherbio;
    }

    public void setTeacherbio(String teacherbio) {
        this.teacherbio = teacherbio;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }


    public String getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPhoneNumber() {
        return teacherPhoneNumber;
    }

    public void setTeacherPhoneNumber(String teacherPhoneNumber) {
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public String getUserUniversity() {
        return userUniversity;
    }

    public void setUserUniversity(String userUniversity) {
        this.userUniversity = userUniversity;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getExpertCategory() {
        return expertCategory;
    }

    public void setExpertCategory(String expertCategory) {
        this.expertCategory = expertCategory;
    }
}
