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

    @ColumnInfo(name = "name")
    private String name;

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
