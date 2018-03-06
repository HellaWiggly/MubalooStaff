package com.matt_adshead.mubaloostaff.view.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.model.Team;
import com.matt_adshead.mubaloostaff.presenter.EmployeePresenter;
import com.matt_adshead.mubaloostaff.presenter.IEmployeePresenter;
import com.matt_adshead.mubaloostaff.view.IEmployeeView;
import com.matt_adshead.mubaloostaff.view.LoadingSpinner;

import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.EMPTY;
import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.HAS_CONTENT;
import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.LOADING;

public class EmployeeActivity extends AppCompatActivity implements IEmployeeView {

    // ********************************************************************************************
    // * Constants
    // ********************************************************************************************
    
    /**
     * Key for the employee ID, used to store it in the Intent {@link Bundle}.
     */
    public final static String KEY_EMPLOYEE_ID = "employee_id";

    // ********************************************************************************************
    // * Views
    // ********************************************************************************************
    
    private LoadingSpinner loadingSpinner;
    private ImageView      profileImage;
    private TextView       nameText, teamText, roleText, errorText;
    private View           contentView;

    // ********************************************************************************************
    // * Variables
    // ********************************************************************************************

    /**
     * The presenter for this view.
     */
    private IEmployeePresenter presenter;

    /**
     * Employee unique ID.
     */
    private int                employeeId;

    // ********************************************************************************************
    // * Lifecycle Callbacks
    // ********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        initViews();

        presenter = createPresenter();

        employeeId = getIntent().getIntExtra(KEY_EMPLOYEE_ID, -1);

        if (employeeId == -1) {
            setErrorMessage(getString(R.string.something_went_wrong));
        }

        setState(LOADING);

        presenter.getEmployee(employeeId);
    }

    // ********************************************************************************************
    // * Initialisation
    // ********************************************************************************************

    /**
     * Initialise the activity's views.
     */
    private void initViews() {
        profileImage = findViewById(R.id.employee_profile_image);
        profileImage.setImageDrawable(new ColorDrawable(
                ResourcesCompat.getColor(
                        getResources(),
                        R.color.color_accent,
                        null
                )
        ));

        contentView    = findViewById(R.id.content_view);
        loadingSpinner = findViewById(R.id.loading_spinner);
        nameText       = findViewById(R.id.employee_name);
        teamText       = findViewById(R.id.employee_team);
        roleText       = findViewById(R.id.employee_role);
        errorText      = findViewById(R.id.error_message);
    }

    /**
     * @return The presenter for this view.
     * todo Inject
     */
    private IEmployeePresenter createPresenter() {
        return new EmployeePresenter(this, this);
    }

    // ********************************************************************************************
    // * View Methods
    // ********************************************************************************************

    /**
     * Set the employee this view displays.
     *
     * @param employee Employee.
     * @param team     Associated Team.
     */
    @Override
    public void setEmployee(final Employee employee, final Team team) {
        final Context context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context)
                        .load(employee.getProfileImageUrl())
                        .into(profileImage);

                nameText.setText(employee.getFullName());

                final String teamName = team.getName();

                if (TextUtils.isEmpty(teamName)) {
                    teamText.setVisibility(View.GONE);
                } else {
                    teamText.setText(context.getString(R.string.team_detail_text, teamName));
                }

                roleText.setText(context.getString(R.string.role_detail_text, employee.getRole()));
            }
        });
    }

    /**
     * Set and show the view error message.
     *
     * @param errorMessage Error message string.
     */
    @Override
    public void setErrorMessage(String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisibility(View.VISIBLE);
    }

    /**
     * Clear the view error message and hide the component.
     */
    @Override
    public void clearErrorMessage() {
        errorText.setText(null);
        errorText.setVisibility(View.GONE);
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
                                      contentView.setVisibility(View.GONE);
                                      break;
                    case HAS_CONTENT: loadingSpinner.setVisibility(View.GONE);
                                      contentView.setVisibility(View.VISIBLE);
                                      break;
                    case EMPTY:       loadingSpinner.setVisibility(View.GONE);
                                      contentView.setVisibility(View.GONE);
                                      break;
                }
            }
        });
    }
}
