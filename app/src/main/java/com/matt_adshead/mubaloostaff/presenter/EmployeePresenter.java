package com.matt_adshead.mubaloostaff.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.model.Team;
import com.matt_adshead.mubaloostaff.model.database.StaffDatabase;
import com.matt_adshead.mubaloostaff.view.IEmployeeView;

import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.EMPTY;
import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.HAS_CONTENT;

/**
 * Presenter for the employee detail view.
 *
 * @author matta
 * @date 05/03/2018
 */
public class EmployeePresenter extends BasePresenter implements IEmployeePresenter {

    /**
     * Calling context.
     */
    private Context       context;

    /**
     * Employee view reference.
     */
    private IEmployeeView view;

    /**
     * Database reference for retrieving an Employee by ID.
     */
    private StaffDatabase staffDatabase;

    /**
     * Constructor.
     *
     * @param context Calling context.
     * @param view    Employee view reference.
     */
    public EmployeePresenter(Context context, IEmployeeView view) {
        this.context = context;
        this.view = view;
    }

    /**
     * @return Lazy loaded singleton staff database.
     */
    private StaffDatabase getStaffDatabase() {
        if (staffDatabase == null) {
            staffDatabase = StaffDatabase.getInstance(context);
        }

        return staffDatabase;
    }

    /**
     * Asynchronously fetch the employee and their corresponding team.
     *
     * @param id Employee ID.
     */
    @Override
    public void getEmployee(final int id) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final StaffDatabase staffDatabase = getStaffDatabase();

                final Employee employee = staffDatabase.employeeDao().getById(id);

                if (employee == null) {
                    view.setErrorMessage(context.getString(R.string.could_not_locate_employee));
                    view.setState(EMPTY);
                    return;
                }

                final Team team = staffDatabase.teamDao().getById(employee.getTeamId());

                if (team == null) {
                    view.setErrorMessage(context.getString(R.string.could_not_locate_team));
                    view.setState(EMPTY);
                    return;
                }

                view.setEmployee(employee, team);
                view.setState(HAS_CONTENT);
            }
        });
    }
}
