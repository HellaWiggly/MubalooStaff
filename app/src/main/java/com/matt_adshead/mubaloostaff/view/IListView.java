package com.matt_adshead.mubaloostaff.view;

import com.matt_adshead.mubaloostaff.model.Employee;

import java.util.List;

/**
 * List View interface.
 *
 * @author Matt
 * @date 05/03/2018
 */
public interface IListView extends ContentView {

    void setEmployees(List<Employee> employeeList);

    void setErrorMessage(String errorMessage);

    void clearErrorMessage();

    void setLoadingMessage(String loadingMessage);
}
