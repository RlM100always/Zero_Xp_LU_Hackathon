package com.techtravelcoder.hackathon.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.model.StudentRequestModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentRequestModel> list;
    String tUid;

    public PostAdapter(Context context, ArrayList<StudentRequestModel> list, String tUid) {
        this.context = context;
        this.list = list;
        this.tUid = tUid;
    }


    @NonNull
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_info_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyViewHolder holder, int position) {

        StudentRequestModel obj=list.get(position);

        holder.name.setText(obj.getS_name());
        holder.mobile.setText(obj.getS_phone());
        holder.email.setText(obj.getS_email());
        holder.problem.setText(obj.getS_description());
        Glide.with(holder.img.getContext()).load(obj.getS_image()).into(holder.img);
        String sUid=obj.getUid1();


        holder.emailService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (obj != null && holder.emailService != null) {
                    String to = obj.getS_email();
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", to, null));

                    try {
                        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        holder.emailService.setBackgroundColor(ContextCompat.getColor(context, R.color.green));

                    } catch (ActivityNotFoundException e) {

                        Toast.makeText(v.getContext(), "No email app found.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Are you delete this teacher information");
                builder.setMessage("Data can't be undo ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference("P_Teacher").child(tUid).
                                child(sUid).removeValue();

                        Toast.makeText(context, "Delete Successful...", Toast.LENGTH_SHORT).show();

                        //Intent intent = new Intent(context, .class);
                        //context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, mobile, problem;
        TextView emailService,delete;
        CircleImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.problemName);
            email = itemView.findViewById(R.id.problemEmail);
            mobile = itemView.findViewById(R.id.problemMobile);
            problem = itemView.findViewById(R.id.problemContext);
            emailService=itemView.findViewById(R.id.acceptButton);
            img=itemView.findViewById(R.id.ci_image_id);
            delete=itemView.findViewById(R.id.declineButton);
        }
    }
}
