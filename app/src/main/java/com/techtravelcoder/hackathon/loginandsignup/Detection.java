package com.techtravelcoder.hackathon.loginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtravelcoder.hackathon.MainActivity;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.service.TeacherDashboardActivity;
import com.techtravelcoder.hackathon.splashscreen.SplashActivity;

public class Detection extends AppCompatActivity {

    CardView student ;
    DatabaseReference databaseReference;
    int sami=0;
    CardView teacherDetect ;
    FirebaseAuth auth;
    FirebaseUser user;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);


        student=findViewById(R.id.student_login_id);
        teacherDetect=findViewById(R.id.teacher_id);




        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserLoginActivity.class);
                startActivity(intent);
            }
        });

        teacherDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ConsaltantLoginActivity.class);
                Toast.makeText(Detection.this, "Teacherd", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }









}