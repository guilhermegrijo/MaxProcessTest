package com.example.maxprocesstest.repository;


import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.ContactPhones;
import com.example.maxprocesstest.model.Phone;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


public interface ContactRepository {

    Maybe<List<Contact>> getAll();

    Observable<ContactPhones> loadById(Long contactId);

    Single<Long> insert(Contact contact);

    void insert(Contact contact, Phone... phones);

    Completable insert(Phone... phone);

    Completable delete(Contact contact);

}
