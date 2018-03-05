package com.matt_adshead.mubaloostaff.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Team model.
 *
 * @author matta
 * @date 04/03/2018
 */
@Entity
public class Team {
    @PrimaryKey
    private int        id;

    @ColumnInfo(name = "team_name")
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
