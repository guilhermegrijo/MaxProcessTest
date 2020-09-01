package com.example.maxprocesstest.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(
        tableName = "phone"
)
public class Phone {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "phone_id")
    private Long phoneId;


    @ColumnInfo(name = "phones_contact_id")
    private Long phonesContactId;

    @ColumnInfo(name = "phone")
    private String phone;

}
