package com.example.maxprocesstest.repository;


import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.ContactPhones;
import com.example.maxprocesstest.model.Phone;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;


public interface ContactRepository {

    Maybe<List<Contact>> getAll();

    Maybe<List<Contact>> searchByName(String name);

    Single<ContactPhones> loadById(Long contactId);

    Completable update(Contact contact);

    Single<Long> insert(Contact contact);

    Completable insert(Phone... phone);

    Completable deleteContact(Long contactId);

    Completable deletePhone(Long contactId);
}
