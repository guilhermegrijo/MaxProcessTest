package com.example.maxprocesstest.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = "contact")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    private Long contactId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "cpf")
    private String cpf;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "birthday")
    private Date birthday;

    @ColumnInfo(name = "uf")
    private String uf;


}
