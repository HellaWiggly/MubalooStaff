package com.matt_adshead.mubaloostaff.model;

import java.util.List;

/**
 * Class wrapping the {@link Employee} and {@link Team} objects parsed out of the JSON resource.
 *
 * Save the teams first as users have a foreign key constraint on teamId.
 *
 * @author matta
 * @date 05/03/2018
 */
public class JsonDataPayload {

    /**
     * Teams extracted from the JSON payload.
     */
    private List<Team>     teams;

    /**
     * Employees extracted from the JSON payload.
     */
    private List<Employee> employees;

    /**
     * Constructor.
     *
     * @param teams     Teams extracted from the JSON payload.
     * @param employees Employees extracted from the JSON payload.
     */
    public JsonDataPayload(List<Team> teams, List<Employee> employees) {
        this.teams = teams;
        this.employees = employees;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
