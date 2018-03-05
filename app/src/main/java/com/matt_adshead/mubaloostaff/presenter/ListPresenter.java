package com.matt_adshead.mubaloostaff.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.model.JsonDataPayload;
import com.matt_adshead.mubaloostaff.model.Team;
import com.matt_adshead.mubaloostaff.model.database.StaffDatabase;
import com.matt_adshead.mubaloostaff.utils.StaffJsonParser;
import com.matt_adshead.mubaloostaff.view.activity.IListView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Presenter for the List Activity.
 *
 * @author Matt
 * @date 05/03/2018
 */
public class ListPresenter extends BasePresenter implements IListPresenter {

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
            staffJsonParser = new StaffJsonParser(getGson());
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
     * Asyncronously:
     *  - Download JSON via HTTP.
     *  - Parse JSON into {@link Employee} and {@link Team}.
     *  - Save Employee and Team to local storage.
     *  - Return to view with List<Employee>
     */
    @Override
    public void getStaffFromNetworkResource() {
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

                    final List<Employee> allEmployees = database.employeeDao().getAll();

                    //Return to the view with the new employee list.
                    view.setEmployees(payload.getEmployees());
                } catch (IOException exception) {
                    Log.e(
                            ListPresenter.class.getSimpleName(),
                            "Error fetching staff JSON resource from the server.",
                            exception
                    );
                    return;
                    //todo handle error case.
                } catch (JsonParseException exception) {
                    Log.e(
                            ListPresenter.class.getSimpleName(),
                            "Error parsing the JSON retrieved from the server.",
                            exception
                    );
                    return;
                    //todo handle error case.
                }
            }
        });
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
