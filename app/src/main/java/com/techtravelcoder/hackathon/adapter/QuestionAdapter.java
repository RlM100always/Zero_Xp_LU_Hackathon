package com.techtravelcoder.hackathon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techtravelcoder.hackathon.R;
import com.techtravelcoder.hackathon.model.StudentRequestModel;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentRequestModel> list;

    public QuestionAdapter(Context context, ArrayList<StudentRequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ques_design,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyViewHolder holder, int position) {
        StudentRequestModel obj =list.get(position);
        holder.desc.setText(obj.getS_description());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView desc ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc=itemView.findViewById(R.id.question_id);
        }
    }
}
