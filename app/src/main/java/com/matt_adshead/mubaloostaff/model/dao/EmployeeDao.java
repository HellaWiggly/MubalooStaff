package com.matt_adshead.mubaloostaff.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.matt_adshead.mubaloostaff.model.Employee;

import java.util.List;

/**
 * DAO interface for Employee.
 *
 * @author Matt
 * @date 05/03/2018
 */
@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Query("SELECT * FROM employee WHERE id = :id LIMIT 1")
    Employee getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Employee... employees);

    @Delete
    void delete(Employee employee);
}
