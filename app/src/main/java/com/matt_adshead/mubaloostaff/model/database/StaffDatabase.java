package com.matt_adshead.mubaloostaff.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.model.Team;
import com.matt_adshead.mubaloostaff.model.dao.EmployeeDao;
import com.matt_adshead.mubaloostaff.model.dao.TeamDao;

/**
 * App database object.
 *
 * @author Matt
 * @date 05/03/2018
 */
@Database(entities = {Employee.class, Team.class}, version = 1)
public abstract class StaffDatabase extends RoomDatabase {
    public abstract EmployeeDao employeeDao();

    public abstract TeamDao     teamDao();
}
