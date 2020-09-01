package com.example.maxprocesstest;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.ContactDao;
import com.example.maxprocesstest.model.ContactPhones;
import com.example.maxprocesstest.model.Phone;
import com.example.maxprocesstest.repository.AppDatabase;
import com.example.maxprocesstest.repository.ContactDataSource;
import com.example.maxprocesstest.repository.ContactRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private ContactRepository repository;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        ContactDao userDao = db.contactDao();
        repository = new ContactDataSource(userDao);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void shouldGetContacts() throws Exception {
        Contact user = new Contact();
        user.setName("george");
        user.setCreatedAt(new Date());

        Phone phone = new Phone();
        List<Phone> phoneList = new ArrayList<>();
        phone.setPhone("31987950962");

        phoneList.add(phone);


        repository.insert(user).flatMapCompletable(id -> repository.insert(phoneList.stream().toArray(Phone[]::new))).test();


        ContactPhones contactPhones = repository.loadById((long) 1).blockingFirst();

        assertThat(contactPhones.phoneList.size(), equalTo(1));
    }
}