package com.example.maxprocesstest.ui.updateContact;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.Phone;
import com.example.maxprocesstest.model.Response;
import com.example.maxprocesstest.repository.ContactRepository;
import com.example.maxprocesstest.scheduler.IScheduleProvider;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class UpdateContactViewModel extends ViewModel {


    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();
    private ContactRepository repository;
    private IScheduleProvider scheduleProvider;

    public UpdateContactViewModel(ContactRepository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }

    public MutableLiveData<Response> response() {
        return response;
    }


    public void getContact(Long contact) {
        disposables.add(repository.loadById(contact)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    //EspressoTestingIdlingResource.increment();
                }).doOnSuccess(result -> {
                            response.setValue(Response.success(result));
                        }
                ).doOnError(error -> response.setValue(Response.error(error)))
                .subscribe());
    }

    public void updateContact(Contact contact, List<Phone> phones) {
        for (Phone phone : phones) {
            phone.setPhonesContactId(contact.getContactId());
        }

        disposables.add(repository.update(contact)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    //EspressoTestingIdlingResource.increment();
                })
                .concatWith(repository.deletePhone(contact.getContactId()).subscribeOn(scheduleProvider.io()).observeOn(scheduleProvider.ui()))
                .concatWith(repository.insert(phones.toArray(new Phone[phones.size()])).subscribeOn(scheduleProvider.io()).observeOn(scheduleProvider.ui()))
                .doOnComplete(() -> response.setValue(Response.updated()))
                .doOnError(error -> response.setValue(Response.error(error)))
                .subscribe());
    }

    public void deleteContact(Long contactId){
        disposables.add(repository.deleteContact(contactId)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .concatWith(repository.deletePhone(contactId).subscribeOn(scheduleProvider.io()).observeOn(scheduleProvider.ui()).doOnError(error -> response.setValue(Response.error(error))))
                .doOnComplete(() -> response.setValue(Response.removed()))
                .doOnError(error -> response.setValue(Response.error(error)))
                .subscribe());
    }
}