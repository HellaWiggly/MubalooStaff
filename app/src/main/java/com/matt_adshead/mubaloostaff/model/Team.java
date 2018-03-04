package com.matt_adshead.mubaloostaff.model;

/**
 * Team model.
 *
 * @author matta
 * @date 04/03/2018
 */

public class Team {
    private String     teamName;

    private Employee[] members;

    public Team(String teamName, Employee[] members) {
        this.teamName = teamName;
        this.members = members;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Employee[] getMembers() {
        return members;
    }

    public void setMembers(Employee[] members) {
        this.members = members;
    }
}
