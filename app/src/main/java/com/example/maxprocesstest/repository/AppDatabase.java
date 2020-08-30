package com.example.maxprocesstest.repository;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.ContactDao;
import com.example.maxprocesstest.model.Phone;
import com.example.maxprocesstest.utils.DateConverter;

@Database(entities = {Contact.class, Phone.class}, version = AppDatabase.VERSION)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract ContactDao contactDao();

}