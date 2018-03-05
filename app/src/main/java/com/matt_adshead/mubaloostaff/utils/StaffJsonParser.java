package com.matt_adshead.mubaloostaff.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.model.JsonDataPayload;
import com.matt_adshead.mubaloostaff.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which uses GSON to extract entities from the staff JSON resource.
 *
 * @author matta
 * @date 05/03/2018
 */

public class StaffJsonParser {

    private final static String KEY_FIRSTNAME = "firstName",
                                KEY_LASTNAME  = "lastName",
                                KEY_TEAMNAME  = "teamName",
                                KEY_MEMBERS   = "members";

    private Gson gson;

    public StaffJsonParser(Gson gson) {
        this.gson = gson;
    }

    public JsonDataPayload parseJson(String json) throws JsonParseException {
        //Deserialize the top-level array.
        final JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

        //Create empty lists for teams and employees.
        final List<Team>     teams     = new ArrayList<>();
        final List<Employee> employees = new ArrayList<>();

        // We want a team ID for the database but it isn't set in the JSON so we generate it.
        int teamId = 1;

        for (int i = 0; i < jsonArray.size(); i++) {
            final JsonElement element = jsonArray.get(i);

            if (!element.isJsonObject()) {
                Log.e(
                        StaffJsonParser.class.getSimpleName(),
                        String.format(
                                "Expecting object, found array. Index: %d",
                                i
                        )
                );
                continue;
            }

            JsonObject jsonObject = element.getAsJsonObject();

            final boolean isEmployee = jsonObject.has(KEY_FIRSTNAME) &&
                                       jsonObject.has(KEY_LASTNAME),
                          isTeam     = jsonObject.has(KEY_TEAMNAME) &&
                                       jsonObject.has(KEY_MEMBERS);

            if (isEmployee) {
                //Deserialize employee from JSON.
                final Employee employee = gson.fromJson(jsonObject, Employee.class);

                //Add to the employee list.
                employees.add(employee);
            } else if (isTeam) {
                //Deserialize team from JSON.
                final Team team = gson.fromJson(jsonObject, Team.class);

                //Set the team ID
                team.setId(teamId);

                //Add to the team list.
                teams.add(team);

                // Get the team's members array, validate that it is indeed an array.
                final JsonElement membersElement = jsonObject.get(KEY_MEMBERS);
                if (!membersElement.isJsonArray()) {
                    Log.e(
                            StaffJsonParser.class.getSimpleName(),
                            String.format(
                                    "Expecting members array, found object. Index: %d",
                                    i
                            )
                    );
                    continue;
                }
                final JsonArray membersArray = membersElement.getAsJsonArray();

                //Deserialize members from JSON.
                final Employee[] members     = gson.fromJson(membersArray, Employee[].class);

                for (Employee member : members) {
                    //Set the team ID.
                    member.setTeamId(teamId);

                    //Add to the employee list.
                    employees.add(member);
                }

                //Increment to the next team ID.
                teamId++;
            }
        }

        return new JsonDataPayload(teams, employees);
    }
}
