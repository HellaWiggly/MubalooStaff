package com.matt_adshead.mubaloostaff.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.model.Employee;

import java.util.List;

/**
 * RecyclerView.Adapter for EmployeeList.
 *
 * @author Matt
 * @date 05/03/2018
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    private Context        context;

    @Nullable
    private List<Employee> employeeList;

    public EmployeeListAdapter(Context context, @Nullable List<Employee> employeeList) {
        this.context      = context;
        this.employeeList = employeeList;
    }

    public EmployeeListAdapter(Context context) {
        this(context, null);
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.employee_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);

        Glide.with(context)
             .load(employee.getProfileImageUrl())
             .into(holder.profileImage);

        holder.fullNameText.setText(employee.getFirstName());
        holder.roleText.setText(employee.getRole());
    }

    @Override
    public int getItemCount() {
        return employeeList != null ? employeeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View      container;
        public ImageView profileImage;
        public TextView  fullNameText, roleText;

        public ViewHolder(View itemView) {
            super(itemView);
            container    = itemView.findViewById(R.id.employee_container);
            profileImage = itemView.findViewById(R.id.employee_profile_image);
            fullNameText = itemView.findViewById(R.id.employee_name);
            roleText     = itemView.findViewById(R.id.employee_role);
        }
    }
}
