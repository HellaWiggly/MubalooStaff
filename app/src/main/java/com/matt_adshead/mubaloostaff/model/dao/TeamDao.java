package com.matt_adshead.mubaloostaff.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.matt_adshead.mubaloostaff.model.Team;

import java.util.List;

/**
 * DAO interface for Team.
 *
 * @author Matt
 * @date 05/03/2018
 */
@Dao
public interface TeamDao {

    @Query("SELECT * FROM team")
    List<Team> getAll();

    @Insert
    void save(Team... teams);

    @Delete
    void delete(Team team);
}
