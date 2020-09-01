package com.example.maxprocesstest.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;


@Dao
public abstract class ContactDao {


    @Query("SELECT contact_id, name, uf FROM contact order by name asc")
    public abstract Maybe<List<Contact>> getAll();

    @Transaction
    @Query("SELECT  contact_id, name, uf FROM contact WHERE UPPER(name) LIKE UPPER(:name || '%') COLLATE NOCASE")
    public abstract Maybe<List<Contact>> searchByName(String name);

    @Transaction
    @Query("SELECT * FROM contact WHERE contact_id = :contactId")
    public abstract Single<ContactPhones> loadById(Long contactId);

    @Update
    public abstract Completable update(Contact contact);

    @Insert
    public abstract Single<Long> insert(Contact contact);

    @Insert
    public abstract Completable insert(Phone... phone);

    @Transaction
    @Query("DELETE FROM phone WHERE phones_contact_id = :contactId")
    public abstract Completable deletePhones(Long contactId);

    @Transaction
    @Query("DELETE FROM contact where contact_id = :contactId")
    public abstract Completable delete(Long contactId);

}
