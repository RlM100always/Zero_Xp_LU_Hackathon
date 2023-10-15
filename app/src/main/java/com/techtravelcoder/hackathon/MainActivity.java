package com.techtravelcoder.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtravelcoder.hackathon.loginandsignup.UserLoginActivity;
import com.techtravelcoder.hackathon.profile.StudentProfileActivity;
import com.techtravelcoder.hackathon.service.AllStudentPostActivity;
import com.techtravelcoder.hackathon.service.GuidelineActivity;
import com.techtravelcoder.hackathon.service.StudyActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout; //drawerlayouuut
    LinearLayout contentView;
    CardView studyView,profile,guidelineview;
    FirebaseAuth auth;
    NavigationView navigationView;//navigation view
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studyView=findViewById(R.id.study_id);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        imageSlider=findViewById(R.id.image_slider);
        sliderSupport();
        profile=findViewById(R.id.profile_id);
        guidelineview=findViewById(R.id.guideline_id);

        guidelineview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), GuidelineActivity.class);
                startActivity(intent);
            }
        });



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), StudentProfileActivity.class);
                startActivity(intent);
            }
        });

        int colorPrimary = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            colorPrimary = getColor(R.color.service_bar);
        }

        getWindow().setStatusBarColor(colorPrimary);

        studyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), StudyActivity.class);
                startActivity(intent);
            }
        });


        managementNavigationDrawerItem();


    }



    public void managementNavigationDrawerItem() {
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_view);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.tolbar);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.home_Id) {
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //finish();
                }
                if(item.getItemId()==R.id.share_Id){
                    String textToShare = "Check out this awesome link:";
                    String linkToShare = "https://www.example.com";

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");

                    String message = textToShare + "\n" + linkToShare;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, message);

                    // Start the sharing activity
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                    finish();
                }

                if(item.getItemId()==R.id.exit_Id){
                    AlertDialog.Builder alertObj= new AlertDialog.Builder(MainActivity.this);
                    alertObj.setTitle("Confirm Exit...!");
                    alertObj.setMessage("Do you want to Exit this Application ?");

                    alertObj.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    });
                    alertObj.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = alertObj.create();
                    alertObj.show();
                }
                if(item.getItemId()==R.id.log_out_id){
                    auth= FirebaseAuth.getInstance();
                    auth.signOut();
                    Intent intent= new Intent(getApplicationContext(), UserLoginActivity.class);
                    startActivity(intent);
                    finish();

                }
                if(item.getItemId()==R.id.study_consaltancy_menu){
                    Intent intent= new Intent(getApplicationContext(), StudyActivity.class);
                    startActivity(intent);

                }
                if(item.getItemId()==R.id.guide_line_menu){
                    Intent intent= new Intent(getApplicationContext(), GuidelineActivity.class);
                    startActivity(intent);

                }





                return true;
            }
        });

        //  animateNavigationDrawer();

    }


    public  void sliderSupport(){
        imageSlider=findViewById(R.id.image_slider);
        final List<SlideModel> remoteimages=new ArrayList<>();//Slide model is an inbuilt model class made by github library provider
        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data:snapshot.getChildren())
                            remoteimages.add(new SlideModel(data.child("url").getValue().toString(),data.child("title").getValue().toString(), ScaleTypes.FIT));

                        imageSlider.setImageList(remoteimages,ScaleTypes.FIT);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertObj= new AlertDialog.Builder(MainActivity.this);
        alertObj.setTitle("Confirm Exit...!");
        alertObj.setMessage("Do you want to Exit this Application ?");

        alertObj.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alertObj.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alertObj.create();
        alertObj.show();



    }

}