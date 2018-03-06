package com.matt_adshead.mubaloostaff.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.presenter.IListPresenter;
import com.matt_adshead.mubaloostaff.presenter.ListPresenter;
import com.matt_adshead.mubaloostaff.view.EmployeeList;
import com.matt_adshead.mubaloostaff.view.IListView;
import com.matt_adshead.mubaloostaff.view.LoadingSpinner;
import com.matt_adshead.mubaloostaff.view.listener.OnItemClickListener;

import java.util.List;

import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.EMPTY;
import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.HAS_CONTENT;
import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.LOADING;
import static com.matt_adshead.mubaloostaff.view.activity.EmployeeActivity.KEY_EMPLOYEE_ID;

public class ListActivity extends AppCompatActivity implements IListView,
                                                               OnItemClickListener<Employee> {

    private LoadingSpinner loadingSpinner;
    private TextView       errorText;
    private EmployeeList   employeeList;
    private View           noContentView;

    private IListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initViews();

        presenter = createPresenter();

        setState(LOADING);

        setLoadingMessage(
                getString(R.string.loading_from_local_storage_elipsis)
        );

        presenter.getStaffFromDatabase();

        presenter.getStaffFromNetworkResource();
    }

    private void initViews() {
        loadingSpinner = findViewById(R.id.loading_spinner);
        noContentView  = findViewById(R.id.no_content_view);
        errorText      = findViewById(R.id.error_message);
        employeeList   = findViewById(R.id.mubaloo_employee_list);

        employeeList.setItemClickListener(this);
    }

    private IListPresenter createPresenter() {
        return new ListPresenter(this, this);
    }

    @Override
    public void setEmployees(final List<Employee> employees) {
        if (employees == null || employees.size() == 0) {
            if (employeeList.countEmployees() > 0) {
                setState(HAS_CONTENT);
            } else {
                setState(EMPTY);
            }
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                employeeList.loadEmployees(employees);
                setState(HAS_CONTENT);
            }
        });
    }

    @Override
    public void setErrorMessage(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                errorText.setText(errorMessage);
                errorText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void clearErrorMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                errorText.setText(null);
                errorText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void setLoadingMessage(final String loadingMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingSpinner.setMessage(loadingMessage);
            }
        });
    }

    @Override
    public void setState(@ContentState final int state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state) {
                    case LOADING:     loadingSpinner.setVisibility(View.VISIBLE);
                                      employeeList.setVisibility(View.GONE);
                                      noContentView.setVisibility(View.GONE);
                                      break;
                    case HAS_CONTENT: loadingSpinner.setVisibility(View.GONE);
                                      employeeList.setVisibility(View.VISIBLE);
                                      noContentView.setVisibility(View.GONE);
                                      break;
                    case EMPTY:       loadingSpinner.setVisibility(View.GONE);
                                      employeeList.setVisibility(View.GONE);
                                      noContentView.setVisibility(View.VISIBLE);
                                      break;
                }
            }
        });
    }

    @Override
    public void onItemClick(Employee item) {
        final Intent intent = new Intent(this, EmployeeActivity.class);

        intent.putExtra(KEY_EMPLOYEE_ID, item.getId());

        startActivity(intent);
    }
}
