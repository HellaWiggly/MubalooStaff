package com.matt_adshead.mubaloostaff.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Custom RecyclerView for Employees.
 *
 * @author matta
 * @date 04/03/2018
 */

public class EmployeeList extends RecyclerView {
    public EmployeeList(Context context) {
        super(context);
    }

    public EmployeeList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
