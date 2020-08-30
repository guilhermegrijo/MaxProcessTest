package com.example.maxprocesstest.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(
        tableName = "phone",
        foreignKeys = @ForeignKey (
                                  entity = Contact.class,
                                  parentColumns = "contact_id",
                                  childColumns = "phones_contact_id",
                                  onDelete = ForeignKey.CASCADE
                                    )
        )
public class Phone {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "phone_id")
    private Long PhoneId;


    @ColumnInfo(name = "phones_contact_id")
    private Long PhonesContactId;

    @ColumnInfo(name = "phone")
    private String phone;

}
