package com.techtravelcoder.hackathon.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtravelcoder.hackathon.R;

public class TeacherProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    TextView name,email,phone, university,department,bio,expertise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        name = findViewById(R.id.teacherName);
        email = findViewById(R.id.teacherEmail);
        phone = findViewById(R.id.teacherPhone);
        university = findViewById(R.id.teacherUni);
        department = findViewById(R.id.teacherDept);
        bio = findViewById(R.id.techerBio);
        expertise = findViewById(R.id.teacherExpert);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String currentUserId = auth.getCurrentUser().getUid();


        // Reference to the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Teacher Info").child("Study").child(currentUserId);



        // Retrieve data from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(),currentUserId,Toast.LENGTH_SHORT).show();
                    // Retrieve data from the dataSnapshot
                    String studentName = dataSnapshot.child("teacherNames").getValue(String.class);
                    Toast.makeText(getApplicationContext(),studentName,Toast.LENGTH_SHORT).show();
                    String studentEmail = dataSnapshot.child("teacherEmail").getValue(String.class);
                    String studentPhone = dataSnapshot.child("teacherPhoneNumber").getValue(String.class);
                    String studentUni = dataSnapshot.child("userUniversity").getValue(String.class);
                    String studentDep = dataSnapshot.child("userDepartment").getValue(String.class);
                    String studentBio = dataSnapshot.child("teacherbio").getValue(String.class);
                    String studentExpert = dataSnapshot.child("expertCategory").getValue(String.class);

                    // Set the retrieved data to the TextViews
                    name.setText(studentName);
                    email.setText(studentEmail);
                    phone.setText(studentPhone);
                    university.setText(studentUni);
                    department.setText(studentDep);
                    expertise.setText(studentExpert);
                    bio.setText(studentBio);

                } else {
                    // Handle the case when data doesn't exist
                    Toast.makeText(TeacherProfileActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error here
                Toast.makeText(TeacherProfileActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}