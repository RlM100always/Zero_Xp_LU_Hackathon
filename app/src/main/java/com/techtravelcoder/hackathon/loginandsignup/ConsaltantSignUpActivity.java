package com.techtravelcoder.hackathon.loginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techtravelcoder.hackathon.MainActivity;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.model.TeacherModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ConsaltantSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference,databaseReference1,databaseReference2;
    private ProgressDialog progressDialog;
    TeacherModel teacherModel;
    private FirebaseAuth auth;
    private String uid;
    private EditText teacherName,email,password,reEnterPassword,phone,tecacherId;
    AppCompatSpinner universitySpinner,expertiseSpinner,deptSpinner ;
    private List<String> uniName,deptList,expertList;
    private String userUniversity,userDepartment,expertCategory ;
    AppCompatButton techerSign;
    TextView alreadysign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consaltant_sign_up);

        auth=FirebaseAuth.getInstance();

       // firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("Teacher Info");
        databaseReference1=FirebaseDatabase.getInstance().getReference("Teacher Info");
        databaseReference2=FirebaseDatabase.getInstance().getReference("Teacher Info");

        techerSign=findViewById(R.id.teachersignUpButton);
        alreadysign=findViewById(R.id.alreadySignUpTeacher);

        teacherName=findViewById(R.id.teachersignUpName);
        email=findViewById(R.id.teachersignUpEmail);
        password=findViewById(R.id.teachersignUpPassword);
        reEnterPassword=findViewById(R.id.teachersignUpReenterPassword);
        phone=findViewById(R.id.teachersignUpPhone);
        tecacherId=findViewById(R.id.teachersignupId);

        universitySpinner = findViewById(R.id.teachersignUpUniversityName);
        deptSpinner = findViewById(R.id.teachersignUpDeptName);
        expertiseSpinner = findViewById(R.id.teachersignUpExpertise);
        techerSign.setOnClickListener(this);
        alreadysign.setOnClickListener(this);



        int colorPrimary = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            colorPrimary = getColor(R.color.back);
        }

        getWindow().setStatusBarColor(colorPrimary);
        manageSpinner();


    }





    private void manageSpinner() {
        uniName=new ArrayList<>();
        deptList=new ArrayList<>();
        expertList=new ArrayList<>();

        uniName.add("Choose your University");
        uniName.add("Leading University");
        uniName.add("University of Dhaka");uniName.add("University of Barisal"); uniName.add("Chittagong University");uniName.add("Jahangirnagar University");uniName.add("Rajshahi University");uniName.add("Khulna University");uniName.add("Islamic University, Bangladesh");uniName.add("University of Dhaka");uniName.add("Comilla University");
        uniName.add("Bangladesh Open University");uniName.add("Jagannath University");uniName.add("Jatiya Kabi Kazi Nazrul Islam University");uniName.add("Begum Rokeya University, Rangpur");uniName.add("Begum Rokeya University, Rangpur");uniName.add("Rabindra University, Bangladesh");uniName.add("Sheikh Hasina University");




        deptList.add("Choose your Department");
        deptList.add("CSE");deptList.add("EEE");deptList.add("ME");

        expertList.add("Choose your expertise topic");
        expertList.add("Study Consultant");expertList.add("Guideline Consultant");



        ArrayAdapter uniAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,uniName);
        ArrayAdapter deptAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,deptList);
        ArrayAdapter expertiseAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,expertList);

       // Toast.makeText(this, "dddddddd", Toast.LENGTH_SHORT).show();

        universitySpinner.setAdapter(uniAdapter);
        deptSpinner.setAdapter(deptAdapter);
         expertiseSpinner.setAdapter(expertiseAdapter);


        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userUniversity= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please Choose a Category", Toast.LENGTH_SHORT).show();

            }
        });

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userDepartment= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please Choose a department", Toast.LENGTH_SHORT).show();

            }
        });

        expertiseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expertCategory= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please Choose your expertise topic", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.alreadySignUpTeacher){
            Intent intent = new Intent(getApplicationContext(),ConsaltantLoginActivity.class);
            startActivity(intent);
        }
        if (v.getId()==R.id.teachersignUpButton){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Registration is Processing....");
            progressDialog.setTitle("Please Wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            registerNewUser();
        }

    }

    private void registerNewUser() {
        String teacherNames = teacherName.getText().toString().trim();
        String teacherPassword = password.getText().toString();
        String teacherEmail = email.getText().toString().trim();
        String teacherReenterPassword = reEnterPassword.getText().toString();
        String teacherPhoneNumber = phone.getText().toString().trim();
        String teacherId=tecacherId.getText().toString().trim();


        String regex = "^01[3-9]\\d{8}$";



        // Input validation
        if (teacherNames.isEmpty() || teacherEmail.isEmpty() || teacherPassword.isEmpty() || teacherReenterPassword.isEmpty() || teacherPhoneNumber.isEmpty() || userUniversity.equals("Choose your University") ||userDepartment.equals("Choose your Department") || teacherId.isEmpty() ||expertCategory.equals("Choose your expertise topic") ) {
            progressDialog.dismiss();
            Toasty.info(this, "All fields are Required...", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (!teacherPhoneNumber.matches(regex)) {
            progressDialog.dismiss();
            phone.setError("Invalid phone number");
            phone.requestFocus();
            return;
        }

        if (!teacherPassword.equals(teacherReenterPassword)) {
            progressDialog.dismiss();
            Toasty.error(this, "Passwords do not match..", Toast.LENGTH_SHORT, true).show();
            return;
        }

        //progressDialog.show(); // Show the ProgressDialog during registration


        //teacherModel.setTeacherUid(uid);



        auth.createUserWithEmailAndPassword(teacherEmail, teacherPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss(); // Dismiss the ProgressDialog once registration is complete

                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();

                    if (user != null) {
                        user.sendEmailVerification().addOnCompleteListener(emailTask -> {
                            if (emailTask.isSuccessful()) {
                                Toasty.success(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT, true).show();
                                // Toasty.info(getApplicationContext(), "Verification email sent. Please check your email.", Toast.LENGTH_SHORT, true).show();


                              //  addDataToFireBase(teacherModel);
                                uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Toast.makeText(getApplicationContext(), ""+uid, Toast.LENGTH_SHORT).show();
                                teacherModel = new TeacherModel(teacherNames,teacherPassword,teacherEmail,teacherPhoneNumber,userUniversity,userDepartment,teacherId,expertCategory,uid);

                                databaseReference.child("All").child(uid).setValue(teacherModel);
                                if(expertCategory.equals("Guideline Consultant")){
                                    databaseReference1.child("Guideline").child(uid).setValue(teacherModel);
                                }
                                if(expertCategory.equals("Study Consultant")){
                                    databaseReference1.child("Study").child(uid).setValue(teacherModel);
                                }



                                auth.signOut();
                                Intent intent = new Intent(getApplicationContext(), ConsaltantLoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toasty.error(getApplicationContext(), "Error sending verification email.", Toast.LENGTH_SHORT, true).show();
                            }
                        });
                    }
                } else {
                    Toasty.error(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            }
        });


        Toasty.success(this, "Information Added Successfully", Toast.LENGTH_SHORT, true).show();
    }

    private void addDataToFireBase(TeacherModel teacherModel) {
        uid=FirebaseAuth.getInstance().getUid();
        databaseReference.child("All").child(uid).setValue(teacherModel);
        if(expertCategory.equals("Guideline Consultant")){
            databaseReference1.child("Guideline").child(uid).setValue(teacherModel);
        }
        if(expertCategory.equals("Study Consultant")){
            databaseReference1.child("Study").child(uid).setValue(teacherModel);
        }



        Toasty.success(this, "Information Added Successfully", Toast.LENGTH_SHORT, true).show();
    }

}