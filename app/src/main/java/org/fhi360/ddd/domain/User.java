package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import kotlin.jvm.Transient;
import lombok.Data;

@Data
@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "role")
    private String role;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "district")
    private String district;
    @ColumnInfo(name = "province")
    private String province;
    @ColumnInfo(name = "contact")
    private String contactDetail;
    @ColumnInfo(name = "date_registration")
    private Date date;
    @Transient
    private String facilityName;
    @Transient
    Long facilityId;

    public User() {
    }

    public User(Long id, String username, String password, String name, String role, Long facilityId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.facilityId = facilityId;
    }

    public static User[] populateUser() {
        return new User[]{
                new User((long) 200, "jagba", "Exaucee2021", "Jules Djiby", "admin", (long) 1523)
//                new User((long) 82, "Nana Fosua", "Clement@fhi360", "Nana Fosua", "admin", (long) 1523),
//                new User((long) 83, "mreeves", "D!g!t@l", "Mercy Reeves", "admin", (long) 1523),
//                new User((long) 85, "redemption", "hospitalho", "Mercy Reeves", "admin", (long) 1523)

        };
    }
}
