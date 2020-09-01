package com.example.maxprocesstest.repository;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.ContactDao;
import com.example.maxprocesstest.model.ContactPhones;
import com.example.maxprocesstest.model.Phone;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;


public class ContactDataSource implements ContactRepository {

    private ContactDao contactDao;

    @Inject
    public ContactDataSource(ContactDao contactDao) {
        this.contactDao = contactDao;
    }


    @Override
    public Maybe<List<Contact>> getAll() {
        return contactDao.getAll();
    }

    @Override
    public Maybe<List<Contact>> searchByName(String name) {
        return contactDao.searchByName(name);
    }

    @Override
    public Single<ContactPhones> loadById(Long contactId) {
        return contactDao.loadById(contactId);
    }

    @Override
    public Completable update(Contact contact) {
        return contactDao.update(contact);
    }

    @Override
    public Single insert(Contact contact) {
        return contactDao.insert(contact);
    }

    @Override
    public Completable insert(Phone... phone) {
        return contactDao.insert(phone);
    }


    @Override
    public Completable deleteContact(Long contactId) {
        return contactDao.delete(contactId);
    }

    @Override
    public Completable deletePhone(Long contactId) {
        return contactDao.deletePhones(contactId);
    }
}
