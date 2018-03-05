package com.matt_adshead.mubaloostaff.presenter;

import android.os.AsyncTask;

import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.view.activity.IListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the List Activity.
 *
 * @author Matt
 * @date 05/03/2018
 */
public class ListPresenter extends BasePresenter implements IListPresenter {

    private IListView view;

    public ListPresenter(IListView view) {
        this.view = view;
    }

    @Override
    public void getAllEmployees() {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final List<Employee> employeeList = new ArrayList<>();
                final Employee testEmployee = new Employee(
                        0,
                        "Mike",
                        "Mason",
                        Employee.Role.CEO,
                        "http://developers.mub.lu/resources/profilePlaceholder.png",
                        false
                );

                employeeList.add(testEmployee);

                view.setEmployees(employeeList);
            }
        });
    }
}
