package com.example.maxprocesstest.ui.updateContact;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.Phone;
import com.example.maxprocesstest.model.Response;
import com.example.maxprocesstest.repository.ContactRepository;
import com.example.maxprocesstest.scheduler.IScheduleProvider;

import java.util.ArrayList;
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


    public void get(Long contact){
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
}