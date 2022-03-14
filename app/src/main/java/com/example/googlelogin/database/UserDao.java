package com.example.googlelogin.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
    public interface UserDao {
    // define instances here
    // generally in interfaces we define variables and declare methods

//        @Query("SELECT * FROM user")
//        List<User> getAll();

//        @Query("")
//        List<User> loadAllByIds(int[] userIds);

//        @Query("SELECT *from user where id=:id")
//        User getCurrentUser(int id);

        @Query("SELECT * FROM user")
        LiveData<List<User>> getUsersLiveData();

//        @Query("")
//        User findByName(String first, String last);

        @Insert
        long insertAll(User users);

        @Delete
        void delete(User user);

        @Update
        void update(User user);

    }


