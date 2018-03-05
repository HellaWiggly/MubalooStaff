package com.matt_adshead.mubaloostaff.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.view.adapter.EmployeeListAdapter;

import java.util.List;

/**
 * Custom RecyclerView for Employees.
 *
 * @author matta
 * @date 04/03/2018
 */

public class EmployeeList extends RecyclerView {
    private EmployeeListAdapter employeeListAdapter;

    public EmployeeList(Context context) {
        super(context);
        init(null);
    }

    public EmployeeList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        setLayoutManager(new LinearLayoutManager(getContext()));

        employeeListAdapter = new EmployeeListAdapter(getContext());

        setAdapter(employeeListAdapter);
    }

    public void loadEmployees(List<Employee> employeeList) {
        employeeListAdapter.setEmployeeList(employeeList);
    }
}
