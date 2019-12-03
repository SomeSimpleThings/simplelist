package com.somethingsimple.simplelist.db.dao;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T entity);

    @Update
    void update(T... entity);

    @Delete
    void delete(T... entity);


}
