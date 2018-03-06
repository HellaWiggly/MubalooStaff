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

    // ********************************************************************************************
    // * Views
    // ********************************************************************************************
    
    private LoadingSpinner loadingSpinner;
    private TextView       errorText;
    private EmployeeList   employeeList;
    private View           noContentView;

    // ********************************************************************************************
    // * Variables
    // ********************************************************************************************

    /**
     * The presenter for this view.
     */
    private IListPresenter presenter;

    // ********************************************************************************************
    // * Lifecycle Callbacks
    // ********************************************************************************************
    
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

    // ********************************************************************************************
    // * Initialisation
    // ********************************************************************************************

    /**
     * Initialise the activity's views.
     */
    private void initViews() {
        loadingSpinner = findViewById(R.id.loading_spinner);
        noContentView  = findViewById(R.id.no_content_view);
        errorText      = findViewById(R.id.error_message);
        employeeList   = findViewById(R.id.mubaloo_employee_list);

        employeeList.setItemClickListener(this);
    }

    /**
     * @return The presenter for this view.
     * todo Inject.
     */
    private IListPresenter createPresenter() {
        return new ListPresenter(this, this);
    }

    // ********************************************************************************************
    // * View Methods
    // ********************************************************************************************

    /**
     * Set the list of employees displayed by the view.
     * @param employees
     */
    @Override
    public void setEmployees(final List<Employee> employees) {
        /*
         * We might already have the locally loaded employees, so don't get rid of them.
         * If the input is empty then just set state according to the current contents of the list.
         */
        if (employees == null || employees.size() == 0) {
            if (employeeList.countEmployees() > 0) {
                setState(HAS_CONTENT);
            } else {
                setState(EMPTY);
            }
            return;
        }

        //Load the employees into the list.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                employeeList.loadEmployees(employees);
                setState(HAS_CONTENT);
            }
        });
    }

    /*
     * todo Could move the error message and loading message methods up to a base activity class
     *      since they apply universally atm.
     */

    /**
     * Set the view error message.
     *
     * @param errorMessage Error message string.
     */
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

    /**
     * Clear the view's error message and hide the component.
     */
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

    /**
     * Set the text displayed in the loading state.
     *
     * @param loadingMessage Loading message string.
     */
    @Override
    public void setLoadingMessage(final String loadingMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingSpinner.setMessage(loadingMessage);
            }
        });
    }

    /**
     * Set the content state of the view.
     *
     * @param state Whether the view has loaded content yet, and if it has whether it is empty.
     */
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

    // ********************************************************************************************
    // * Events & Listeners
    // ********************************************************************************************

    /**
     * Click handler function for items in the {@link EmployeeList}.
     *
     * Opens the {@link EmployeeActivity} with the corresponding {@link Employee}
     *
     * @param item Employee bound to clicked item.
     */
    @Override
    public void onItemClick(Employee item) {
        final Intent intent = new Intent(this, EmployeeActivity.class);

        intent.putExtra(KEY_EMPLOYEE_ID, item.getId());

        startActivity(intent);
    }
}
