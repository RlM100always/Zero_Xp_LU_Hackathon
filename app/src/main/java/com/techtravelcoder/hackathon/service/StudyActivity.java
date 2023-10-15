package com.techtravelcoder.hackathon.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.adapter.StudyAdapter;
import com.techtravelcoder.hackathon.model.TeacherModel;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    TeacherModel teacherModel;
    DatabaseReference mbase;
    ArrayList<TeacherModel> itemlist ;
    private ArrayList<TeacherModel> universityList,deptList;
    StudyAdapter studyAdapter;
    Toolbar toolbar;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        int color= 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = getColor(R.color.service_bar);
        }
        getWindow().setStatusBarColor(color);
        editText=findViewById(R.id.edNewsSearch);
        String uid1= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(this, ""+uid1, Toast.LENGTH_SHORT).show();



        toolbar =  findViewById(R.id.tb_news);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.recyclerViewId);


        itemlist=new ArrayList<>();

        mbase = FirebaseDatabase.getInstance().getReference("Teacher Info").child("Study");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studyAdapter=new StudyAdapter(this,itemlist,uid1);

        recyclerView.setAdapter(studyAdapter );
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 itemlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    teacherModel = dataSnapshot.getValue(TeacherModel.class);
                    if(teacherModel != null){

                        itemlist.add(0,teacherModel);


                    }


                }

                studyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setAdapter(studyAdapter);


        universityList = new ArrayList<>(itemlist);
        deptList=new ArrayList<>(itemlist);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }
    private void filter(String text){
        List<TeacherModel> filterListUni= new ArrayList<>();
        List<TeacherModel> filterListDept= new ArrayList<>();
        for(TeacherModel obj : itemlist ){
            if(obj.getUserUniversity().toLowerCase().contains(text.toLowerCase())){
                filterListUni.add(obj);
            }
            if(obj.getUserDepartment().toLowerCase().contains(text.toLowerCase())){
                filterListDept.add(obj);
            }
        }
        studyAdapter.filterList((ArrayList<TeacherModel>) filterListUni, (ArrayList<TeacherModel>) filterListDept);
    }



}