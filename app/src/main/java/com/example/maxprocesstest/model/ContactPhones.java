package com.example.maxprocesstest.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ContactPhones {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "contact_id",
            entityColumn = "phones_contact_id"
    )
    public List<Phone> phoneList;
}
