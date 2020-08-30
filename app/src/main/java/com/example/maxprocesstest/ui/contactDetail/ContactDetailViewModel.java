package com.example.maxprocesstest.ui.contactDetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.Phone;
import com.example.maxprocesstest.model.Response;
import com.example.maxprocesstest.repository.ContactRepository;
import com.example.maxprocesstest.scheduler.IScheduleProvider;

import java.util.Arrays;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ContactDetailViewModel extends ViewModel {



    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();
    private ContactRepository repository;
    private IScheduleProvider scheduleProvider;

    public ContactDetailViewModel(ContactRepository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }
    public MutableLiveData<Response> response() {
        return response;
    }


    public void createNewContact(Contact contact, Phone... phones){
        disposables.add(repository.insert(contact)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    //EspressoTestingIdlingResource.increment();
                }).subscribe(result -> {
                    repository.insert(phones).doOnComplete(() -> {
                        response.setValue(Response.completed());
                    }).doOnError(error -> {
                        response.setValue(Response.error(error));
                    });
                }, error -> response.setValue(Response.error(error))
                ));
    }
}