package com.techtravelcoder.hackathon.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.adapter.QuestionAdapter;
import com.techtravelcoder.hackathon.model.StudentRequestModel;

import java.util.ArrayList;

public class QuesAnsActivity extends AppCompatActivity {

    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private DatabaseReference mbase;
    private QuestionAdapter questionAdapter;
    private ArrayList<StudentRequestModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans);

        recyclerView=findViewById(R.id.recycler_id_ques);

        mbase = FirebaseDatabase.getInstance().getReference("News").child("All News");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        questionAdapter=new QuestionAdapter(this,list);
        recyclerView.setAdapter(questionAdapter );
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    StudentRequestModel studentRequestModel = dataSnapshot.getValue(StudentRequestModel.class);
                    if(studentRequestModel != null){

                        list.add(0,studentRequestModel);


                    }


                }

                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}