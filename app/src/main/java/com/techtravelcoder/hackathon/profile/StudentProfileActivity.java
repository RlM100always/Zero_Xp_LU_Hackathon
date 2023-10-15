package com.techtravelcoder.hackathon.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileActivity extends AppCompatActivity {

    TextView name,email,phone, university,department;

    CircleImageView profilepic;
    Button update ;
    UserModel userModel;
    CircleImageView stPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);




        name=findViewById(R.id.studentName);
        stPic=findViewById(R.id.studentPic);
        email=findViewById(R.id.studentEmail);
        phone=findViewById(R.id.studentPhone);
        university=findViewById(R.id.studentUni);
        department=findViewById(R.id.studentDept);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        profilepic=findViewById(R.id.studentPic);

        update=findViewById(R.id.userProfileUpdate);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();

            }

        });



        String currentUserId = auth.getCurrentUser().getUid();

        // Reference to the Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User Info").child(currentUserId);

        // Retrieve data from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String studentName = dataSnapshot.child("userName").getValue(String.class);
                    String studentEmail = dataSnapshot.child("userEmail").getValue(String.class);
                    String studentPhone = dataSnapshot.child("userPhoneNumber").getValue(String.class);
                    String studentUni = dataSnapshot.child("userUniversity").getValue(String.class);
                    String studentDept = dataSnapshot.child("userDepartment").getValue(String.class);
                    String imageUrl = dataSnapshot.child("img").getValue(String.class);

                    // Set the retrieved data to the TextViews
                    name.setText(studentName);
                    email.setText(studentEmail);
                    phone.setText(studentPhone);
                    university.setText(studentUni);
                    department.setText(studentDept);
                    if (imageUrl != null) {
                        Glide.with(stPic.getContext()).load(imageUrl).into(stPic);
                    } else {
                        // If imageUrl is null, you can set a default image or handle accordingly
                        Glide.with(stPic.getContext()).load(R.drawable.defaultprofile).into(stPic);
                    }


                    userModel=new UserModel(studentName,studentEmail,studentPhone,studentUni,studentDept);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error here if necessary
            }
        });







    }

    public void editProfile() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.edit_profile_st, null);
        dialogBuilder.setView(dialogView);

        EditText e_name = dialogView.findViewById(R.id.ed_name);
        EditText e_email = dialogView.findViewById(R.id.ed_email);
        EditText e_mobile = dialogView.findViewById(R.id.ed_mobile);
        EditText e_uni = dialogView.findViewById(R.id.ed_university);
        EditText e_dept = dialogView.findViewById(R.id.ed_dept);

        // Assuming userModel is an instance of a User class
        String namee=userModel.getUserName();
        e_name.setText(userModel.getUserName());
        Toast.makeText(this, ""+userModel.getUserName(), Toast.LENGTH_SHORT).show();
        e_email.setText(userModel.getUserEmail());
        e_mobile.setText(userModel.getUserPhoneNumber());
        e_uni.setText(userModel.getUserUniversity());
        e_dept.setText(userModel.getUserDepartment());

        AppCompatButton userProfileUpdate = dialogView.findViewById(R.id.ap_update);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        userProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the user info in the database
                String userId = FirebaseAuth.getInstance().getUid();
                if (userId != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userName", e_name.getText().toString());
                    map.put("userDepartment", e_dept.getText().toString());
                    map.put("userEmail", e_email.getText().toString());
                    map.put("userUniversity", e_uni.getText().toString());
                    map.put("userPhoneNumber", e_mobile.getText().toString());

                    // Update the user info in the database
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User Info").child(userId);
                    userRef.updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed to update: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "User ID is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}