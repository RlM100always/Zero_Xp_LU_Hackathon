package com.techtravelcoder.hackathon.service;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.techtravelcoder.hackathon.MainActivity;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.loginandsignup.ConsaltantLoginActivity;
import com.techtravelcoder.hackathon.profile.TeacherProfileActivity;

public class TeacherDashboardActivity extends AppCompatActivity {
    CardView Allpost,techerProfile,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        FirebaseApp.initializeApp(this);
        Allpost=findViewById(R.id.allpost_id);
        techerProfile=findViewById(R.id.teacher_profile_id_cv);
        logout=findViewById(R.id.logout);

        FirebaseAuth auth=FirebaseAuth.getInstance();



        Allpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),AllStudentPostActivity.class);
                startActivity(intent);
            }
        });
        techerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), TeacherProfileActivity.class);
                startActivity(intent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent =new Intent(getApplicationContext(), ConsaltantLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}