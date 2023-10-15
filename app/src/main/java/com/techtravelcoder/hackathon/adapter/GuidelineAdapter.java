package com.techtravelcoder.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.model.StudentRequestModel;
import com.techtravelcoder.hackathon.model.TeacherModel;
import com.techtravelcoder.hackathon.service.ConsaltantDetailsActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuidelineAdapter extends RecyclerView.Adapter<GuidelineAdapter.MyViewHolder> {
    Context context;
    ;
    ArrayList<TeacherModel> list;
    String uid1;


    public GuidelineAdapter(Context context, ArrayList<TeacherModel> list,String uid1) {
        this.context = context;
        this.list = list;
        this.uid1=uid1;
    }

    @NonNull
    @Override
    public GuidelineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.consaltant_design,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull GuidelineAdapter.MyViewHolder holder, int position) {
        TeacherModel obj=list.get(position);
        holder.name.setText(obj.getTeacherNames());
        holder.dept.setText(obj.getUserDepartment());
        holder.uni.setText(obj.getUserUniversity());
        holder.expert.setText(obj.getExpertCategory());
        Glide.with(holder.imageId.getContext()).load(obj.getTeacherImage()).into(holder.imageId);
        String uid =obj.getUid();
        // Toast.makeText(context, ""+uid, Toast.LENGTH_SHORT).show();

        holder.viewProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ConsaltantDetailsActivity.class);
                intent.putExtra("name",obj.getTeacherNames());
                intent.putExtra("exp",obj.getExpertCategory());

                intent.putExtra("dept",obj.getUserDepartment());
                intent.putExtra("image",obj.getTeacherImage());
                intent.putExtra("bio",obj.getTeacherbio());
                context.startActivity(intent);
            }
        });

        holder.requestTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRequestTeacher(uid);
            }
        });


    }

    public void handleRequestTeacher(String uid) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.request_pop_up, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        EditText e_name = dialogView.findViewById(R.id.ed_name);
        EditText e_email = dialogView.findViewById(R.id.ed_email);
        TextView e_phone = dialogView.findViewById(R.id.ed_phone);
        EditText e_description = dialogView.findViewById(R.id.ed_desc);
        AppCompatButton reqCons = dialogView.findViewById(R.id.ap_request_consaltancy_id);

        reqCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_name = e_name.getText().toString();
                String s_email = e_email.getText().toString();
                String s_phone = e_phone.getText().toString();
                String s_description = e_description.getText().toString();

                if (TextUtils.isEmpty(s_name) || TextUtils.isEmpty(s_email) || TextUtils.isEmpty(s_phone) || TextUtils.isEmpty(s_description)) {
                    Toast.makeText(context, "Please Fill up all data", Toast.LENGTH_SHORT).show();
                } else {
                    StudentRequestModel studentRequestModel = new StudentRequestModel(s_name, s_email, s_phone, s_description,uid1);
                    Toast.makeText(context, "" + uid, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference("P_Teacher").child(uid).child(uid1).setValue(studentRequestModel);
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();  // Dismiss the dialog after adding the request
                }
            }
        });
    }

    public void filterList(ArrayList<TeacherModel> filterListUni, ArrayList<TeacherModel> filterListDept) {
        // Update the dataset based on both university and department filters
        this.list.clear();
        this.list.addAll(filterListUni);
        this.list.addAll(filterListDept);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,uni,dept,expert;
        TextView viewProf,requestTeacher ;
        CircleImageView imageId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sd_name_id_tv);
            uni=itemView.findViewById(R.id.sd_university_id_tv);
            dept=itemView.findViewById(R.id.sd_dept_id_tv);
            expert=itemView.findViewById(R.id.sd_expert_id_tv);
            imageId=itemView.findViewById(R.id.teacherImageId);
            viewProf=itemView.findViewById(R.id.view_profile_id);
            requestTeacher=itemView.findViewById(R.id.requestConsultancyId);


        }
    }
}
