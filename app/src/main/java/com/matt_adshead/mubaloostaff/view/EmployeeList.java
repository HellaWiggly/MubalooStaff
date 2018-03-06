package com.matt_adshead.mubaloostaff.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.view.adapter.EmployeeListAdapter;
import com.matt_adshead.mubaloostaff.view.listener.OnItemClickListener;

import java.util.List;

/**
 * Custom RecyclerView for Employees.
 *
 * @author matta
 * @date 04/03/2018
 */

public class EmployeeList extends RecyclerView {

    // ********************************************************************************************
    // * Variables
    // ********************************************************************************************

    /**
     * {@link RecyclerView.Adapter} implementation, handles binding entities to list item views.
     */
    private EmployeeListAdapter employeeListAdapter;

    // ********************************************************************************************
    // * Constructors
    // ********************************************************************************************

    /**
     * Constructor.
     *
     * @param context Calling context.
     */
    public EmployeeList(Context context) {
        super(context);
        init(null);
    }

    /**
     * Constructor with attributes.
     *
     * @param context Calling context.
     * @param attrs   Attributes.
     */
    public EmployeeList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    // ********************************************************************************************
    // * Initialization
    // ********************************************************************************************

    /**
     * Initialise the view, using any relevant styleable attributes.
     *
     * @param attrs Styleable attributes.
     */
    private void init(@Nullable AttributeSet attrs) {
        setLayoutManager(new LinearLayoutManager(getContext()));

        employeeListAdapter = new EmployeeListAdapter(getContext());

        setAdapter(employeeListAdapter);
    }

    /**
     * Set the OnItemClickListener which will be triggered when an item is clicked.
     *
     * @param itemClickListener Item click listener.
     */
    public void setItemClickListener(OnItemClickListener<Employee> itemClickListener) {
        employeeListAdapter.setItemClickListener(itemClickListener);
    }

    // ********************************************************************************************
    // * View Methods
    // ********************************************************************************************

    /**
     * Pass a list of employees to the adapter so that they can become the new list data-set.
     *
     * @param employeeList List of {@link Employee}.
     */
    public void loadEmployees(List<Employee> employeeList) {
        employeeListAdapter.setEmployeeList(employeeList);
    }

    /**
     * @return The number of items currently in the list.
     */
    public int countEmployees() {
        return employeeListAdapter.getItemCount();
    }
}
