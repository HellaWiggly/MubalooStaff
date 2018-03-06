package com.matt_adshead.mubaloostaff.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.model.JsonDataPayload;
import com.matt_adshead.mubaloostaff.model.Team;
import com.matt_adshead.mubaloostaff.model.database.StaffDatabase;
import com.matt_adshead.mubaloostaff.utils.StaffJsonParser;
import com.matt_adshead.mubaloostaff.view.IListView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.LOADING;

/**
 * Presenter for the List Activity.
 *
 * @author Matt
 * @date 05/03/2018
 */
public class ListPresenter extends BasePresenter implements IListPresenter {

    /**
     * URL location of the remote staff JSON resource.
     * todo Make this configurable.
     */
    private final static String STAFF_JSON_RESOURCE_URL =
            "https://developers.mub.lu/resources/team.json";

    /**
     * Context.
     */
    private Context         context;

    /**
     * The list view as an interface.
     */
    private IListView       view;

    /**
     * HTTP client.
     */
    private OkHttpClient    httpClient;

    /**
     * JSON parsing tool.
     */
    private Gson            gson;

    /**
     * Staff JSON resource reader.
     */
    private StaffJsonParser staffJsonParser;

    /**
     * Staff database.
     */
    private StaffDatabase   staffDatabase;

    /**
     * Constructor.
     *
     * @param context Calling context.
     * @param view    View for this presenter.
     */
    public ListPresenter(Context context, IListView view) {
        this.context = context;
        this.view    = view;
    }

    /**
     * @return Lazy loaded HTTP client.
     */
    private OkHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }

        return httpClient;
    }

    /**
     * @return Lazy loaded Gson JSON parser.
     */
    private Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }

        return gson;
    }

    /**
     * @return Lazy loaded staff JSON resource reader.
     */
    private StaffJsonParser getStaffJsonParser() {
        if (staffJsonParser == null) {
            staffJsonParser = new StaffJsonParser(context, getGson());
        }

        return staffJsonParser;
    }

    /**
     * @return Singleton staff database object.
     */
    private StaffDatabase getStaffDatabase() {
        if (staffDatabase == null) {
            staffDatabase = StaffDatabase.getInstance(context);
        }

        return staffDatabase;
    }

    /**
     * Get locally stored staff records from the database.
     */
    @Override
    public void getStaffFromDatabase() {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final StaffDatabase database = getStaffDatabase();

                final List<Employee> employees = database.employeeDao().getAll();

                view.setEmployees(employees);

                if (employees.size() == 0) {
                    view.setState(LOADING);

                    view.setLoadingMessage(
                            context.getString(R.string.loading_from_online_source_elipsis)
                    );
                }
            }
        });
    }

    /**
     * Asyncronously:
     *  - Download JSON via HTTP.
     *  - Parse JSON into {@link Employee} and {@link Team}.
     *  - Save Employee and Team to local storage.
     *  - Return to view with List<Employee>
     */
    @Override
    public void getStaffFromNetworkResource() {
        if (!haveNetwork()) {
            view.setErrorMessage(context.getString(R.string.no_internet_connection));
            view.setEmployees(null);
        }

        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final String          staffJson  = getStaffJsonFromNetwork();
                    final StaffJsonParser jsonParser = getStaffJsonParser();
                    final JsonDataPayload payload    = jsonParser.parseJson(staffJson);
                    final StaffDatabase   database   = getStaffDatabase();

                    /*
                     * An empty team for the CEO to belong to.
                     * todo Can foreign keys be optional when using Room?
                     *      Then a team wouldn't be required.
                     */
                    final Team nullTeam = new Team(0, null);
                    database.teamDao().save(nullTeam);

                    //Save the contents of the JSON payload to the database.
                    database.teamDao().save(payload.getTeams().toArray(new Team[]{}));
                    database.employeeDao().save(payload.getEmployees().toArray(new Employee[]{}));

                    //Return to the view with the new employee list.
                    view.setEmployees(payload.getEmployees());
                } catch (IOException exception) {
                    handleNetworkError(
                            context.getString(R.string.error_fetching_staff_json),
                            exception
                    );
                } catch (JsonParseException exception) {
                    handleNetworkError(
                            context.getString(R.string.error_parsing_staff_json),
                            exception
                    );
                }
            }
        });
    }

    /**
     * Utility function to check if we have a network connection.
     *
     * @return Do we have a network connection?
     */
    private boolean haveNetwork() {
        final ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Utility function to handle network errors. Logs the error and exception and displays
     * the error message to the user.
     *
     * @param errorMessage Error message. (Plain english, shown to user.)
     * @param exception    Exception object for logging only.
     */
    private void handleNetworkError(final String errorMessage, final Throwable exception) {
        Log.e(
                ListPresenter.class.getSimpleName(),
                errorMessage,
                exception
        );

        exception.printStackTrace();

        view.setErrorMessage(errorMessage);
    }

    /**
     * Use a HTTP client to request the JSON resource from the server.
     *
     * @return             Serialized Staff JSON.
     * @throws IOException On network error.
     */
    private String getStaffJsonFromNetwork() throws IOException {
        final OkHttpClient http = getHttpClient();

        final Request request = new Request.Builder()
                .url(STAFF_JSON_RESOURCE_URL)
                .build();

        final Response response = http.newCall(request).execute();

        return response.body().string();
    }
}
