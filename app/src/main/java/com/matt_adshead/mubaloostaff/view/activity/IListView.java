package com.matt_adshead.mubaloostaff.view.activity;

import com.matt_adshead.mubaloostaff.model.Employee;

import java.util.List;

/**
 * List View interface.
 *
 * @author Matt
 * @date 05/03/2018
 */
public interface IListView {

    void setEmployees(List<Employee> employeeList);
}
