package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.PreLoadRegimen;
import org.fhi360.ddd.domain.User;

import java.util.List;

@Dao
public interface UserRepository {

    @Query("SELECT * FROM user")
    List<User> findByAll();

    @Query("SELECT * FROM user")
    User findByOne();

    @Query("SELECT * FROM user where username=:userName ")
    User findByOne(String userName);

    @Query("SELECT role FROM user where username LIKE :username and password LIKE:password and role =:role")
    String findByUsernameAndPassword(String username, String password, String role);

    @Query("SELECT * FROM user where username LIKE :username and password LIKE:password")
    User findByUsernameAndPassword(String username, String password);

    @Insert
    void save(User user);
    @Update
    void update(User user);


    @Insert
    void insertAll(User... users);
}

