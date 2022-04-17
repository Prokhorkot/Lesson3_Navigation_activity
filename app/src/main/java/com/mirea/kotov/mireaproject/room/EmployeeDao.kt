package com.mirea.kotov.mireaproject.room

import androidx.room.*


@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee?>?

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: Employee?)

    @Update
    fun update(employee: Employee?)

    @Delete
    fun delete(employee: Employee?)

}