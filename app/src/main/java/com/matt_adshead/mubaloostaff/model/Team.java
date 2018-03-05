package com.matt_adshead.mubaloostaff.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Team model, represents a team of employees at Mubaloo.
 *
 * @author matta
 * @date 04/03/2018
 */
@Entity(tableName = "team")
public class Team {
    /**
     * Unique ID for database purposes.
     */
    @PrimaryKey
    private int    id;

    /**
     * Name of the team.
     */
    @ColumnInfo(name = "name")
    @SerializedName("teamName")
    private String name;

    /**
     * Constructor.
     *
     * @param id   Unique ID for database purposes.
     * @param name Name of the team.
     */
    public Team(int id, String name) {
        this.id       = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
