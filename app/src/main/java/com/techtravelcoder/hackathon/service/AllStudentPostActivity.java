package com.techtravelcoder.hackathon.service;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.adapter.PostAdapter;
import com.techtravelcoder.hackathon.model.StudentRequestModel;

import java.util.ArrayList;

public class AllStudentPostActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostAdapter postAdapter;
    DatabaseReference mbase;
    ArrayList<StudentRequestModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_post);

        recyclerView = findViewById(R.id.all_post_recycler_id);
        list = new ArrayList<>();

        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mbase = FirebaseDatabase.getInstance().getReference("P_Teacher").child(currentUserUid);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this, list,currentUserUid);
        recyclerView.setAdapter(postAdapter);

        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StudentRequestModel studentRequestModel = dataSnapshot.getValue(StudentRequestModel.class);
                    if (studentRequestModel != null) {
                        list.add(studentRequestModel);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event if needed
            }
        });
    }
}
