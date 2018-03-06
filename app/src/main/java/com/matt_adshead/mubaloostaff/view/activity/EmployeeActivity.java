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

    public final static String KEY_EMPLOYEE_ID = "employee_id";

    private LoadingSpinner loadingSpinner;
    private ImageView      profileImage;
    private TextView       nameText, teamText, roleText, errorText;
    private View           contentView;

    private IEmployeePresenter presenter;
    private int                employeeId;

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

    private IEmployeePresenter createPresenter() {
        return new EmployeePresenter(this, this);
    }

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

    @Override
    public void setErrorMessage(String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearErrorMessage() {
        errorText.setText(null);
        errorText.setVisibility(View.GONE);
    }

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
