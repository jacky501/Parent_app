package org.atctech.parent.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.atctech.parent.R;
import org.atctech.parent.fragments.TeacherDetailsFragment;
import org.atctech.parent.interfaces.ItemClickListerner;
import org.atctech.parent.model.CoursesTeacher;
import org.atctech.parent.utils.ConstantValue;

import java.util.List;

/**
 * Created by Jacky on 3/29/2018.
 */

public class CourseTeacherAdapter extends RecyclerView.Adapter<CourseTeacherAdapter.CourseTeacherViewHolder> {

    Context context;
    List<CoursesTeacher> coursesTeachers;

    public CourseTeacherAdapter(Context context, List<CoursesTeacher> coursesTeachers) {
        this.context = context;
        this.coursesTeachers = coursesTeachers;
    }

    @NonNull
    @Override
    public CourseTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_course_teacher,null,false);

        return new CourseTeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTeacherViewHolder holder,final int position) {

        holder.teacherName.setText(coursesTeachers.get(position).getFname()+" "+coursesTeachers.get(position).getLname());
        holder.teacherSubject.setText(coursesTeachers.get(position).getSub1());

        try {
            if (coursesTeachers.get(position).getPro_pic().equals("") && coursesTeachers.get(position).getPro_pic().isEmpty()) {
                holder.profileImage.setImageResource(R.drawable.profile);
            }else{
                Picasso.with(context).load(ConstantValue.IMAGE_URL+ coursesTeachers.get(position).getPro_pic()).placeholder(R.drawable.placeholder).into(holder.profileImage);

            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        holder.setItemClickListerner(new ItemClickListerner() {
            @Override
            public void onItemClick(View v) {

                TeacherDetailsFragment fragment = new TeacherDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("fname", coursesTeachers.get(position).getFname());
                bundle.putString("lname", coursesTeachers.get(position).getLname());
                bundle.putString("email", coursesTeachers.get(position).getEmail());
                bundle.putString("phone", coursesTeachers.get(position).getPhone());
                bundle.putString("address", coursesTeachers.get(position).getAddress());
                bundle.putString("details", coursesTeachers.get(position).getDetails());
                bundle.putString("bdate", coursesTeachers.get(position).getBdate());
                bundle.putString("blood", coursesTeachers.get(position).getBlood());
                bundle.putString("sex", coursesTeachers.get(position).getSex());
                bundle.putString("paddress", coursesTeachers.get(position).getPaddress());
                bundle.putString("pro_pic", coursesTeachers.get(position).getPro_pic());
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack("Teachers").commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return coursesTeachers != null ? coursesTeachers.size() : 0;
    }

    public class CourseTeacherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
                private TextView teacherName,teacherSubject;
                private ImageView profileImage;
                private ItemClickListerner itemClickListerner;

        public CourseTeacherViewHolder(View itemView) {
            super(itemView);

            teacherName = itemView.findViewById(R.id.cTeacherName);
            teacherSubject = itemView.findViewById(R.id.cTeacherSubject);
            profileImage = itemView.findViewById(R.id.cProfileImage);

                itemView.setOnClickListener(this);

        }

        public void setItemClickListerner(ItemClickListerner itemClickListerner)
        {
            this.itemClickListerner = itemClickListerner;
        }


        @Override
        public void onClick(View view) {
            this.itemClickListerner.onItemClick(view);
        }
    }
}
