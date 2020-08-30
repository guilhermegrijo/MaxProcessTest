package com.example.maxprocesstest.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


@Dao
public abstract class ContactDao {



        @Query("SELECT contact_id, name, uf FROM contact")
        public abstract Maybe<List<Contact>> getAll();

        @Transaction
        @Query("SELECT * FROM contact WHERE contact_id = :contactId")
        public abstract Observable<ContactPhones> loadById(Long contactId);

        @Insert
        public abstract Single<Long> insert(Contact contact);

        /* @Transaction
        public void insert(Contact contact, Phone... phones) {

                final Single<Long> result = insert(contact);

               result.doOnSuccess( id -> {
                        for (Phone phone : phones) {
                                phone.setPhonesContactId(id);
                                insert(phone);
                        }
                });
        return null;
        };
*/
        @Insert
        public abstract Completable insert(Phone phone);

        @Delete
        public abstract Completable delete(Contact contact);

}
