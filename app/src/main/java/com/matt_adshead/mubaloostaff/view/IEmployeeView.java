package com.matt_adshead.mubaloostaff.view;

import android.content.Context;

import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.model.Team;

/**
 * Interface for the employee detail view.
 *
 * @author matta
 * @date 05/03/2018
 */

public interface IEmployeeView extends ContentView {

    void setEmployee(Employee employee, Team team);

    void setErrorMessage(String errorMessage);

    void clearErrorMessage();
}
