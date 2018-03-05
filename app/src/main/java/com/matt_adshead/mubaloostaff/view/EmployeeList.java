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
    /**
     * {@link RecyclerView.Adapter} implementation, handles binding entities to list item views.
     */
    private EmployeeListAdapter employeeListAdapter;

    public EmployeeList(Context context) {
        super(context);
        init(null);
    }

    public EmployeeList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Initialise the view, using any relevant styleable attributes.
     *
     * @param attrs Attribute set.
     */
    private void init(@Nullable AttributeSet attrs) {
        setLayoutManager(new LinearLayoutManager(getContext()));

        employeeListAdapter = new EmployeeListAdapter(getContext());

        setAdapter(employeeListAdapter);
    }

    /**
     * Pass a list of employees to the adapter so that they can become the new list dataset.
     *
     * @param employeeList List of {@link Employee}.
     */
    public void loadEmployees(List<Employee> employeeList) {
        employeeListAdapter.setEmployeeList(employeeList);
    }
}
