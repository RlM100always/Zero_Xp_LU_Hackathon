package com.techtravelcoder.hackathon.service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techtravelcoder.hackathon.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConsaltantDetailsActivity extends AppCompatActivity {

    CircleImageView image;
    TextView name,dept,expert,description ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consaltant_details);

        image=findViewById(R.id.dt_image_id);
        name=findViewById(R.id.dt_name_id);
        dept=findViewById(R.id.dt_dept_id);
        expert=findViewById(R.id.dt_expert_id);
        description=findViewById(R.id.dt_description_id);

        String c_name=getIntent().getStringExtra("name");
        String c_dept=getIntent().getStringExtra("dept");
        String c_bio=getIntent().getStringExtra("bio");
        String c_image=getIntent().getStringExtra("image");
        String c_exp=getIntent().getStringExtra("exp");
        name.setText(c_name);
        dept.setText(c_dept);
        expert.setText(c_exp);
        description.setText(c_bio);
        Glide.with(getApplicationContext()).load(c_image).into(image);




    }
}