package com.matt_adshead.mubaloostaff.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.presenter.IListPresenter;
import com.matt_adshead.mubaloostaff.presenter.ListPresenter;
import com.matt_adshead.mubaloostaff.view.EmployeeList;

import java.util.List;

public class ListActivity extends AppCompatActivity implements IListView {

    private EmployeeList   employeeList;

    private IListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initViews();

        presenter = createPresenter();

        presenter.getStaffFromNetworkResource();
    }

    private void initViews() {
        employeeList = findViewById(R.id.mubaloo_employee_list);
    }

    private ListPresenter createPresenter() {
        return new ListPresenter(this, this);
    }

    @Override
    public void setEmployees(final List<Employee> employees) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                employeeList.loadEmployees(employees);
            }
        });
    }
}
