package com.example.maxprocesstest.ui.contactList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maxprocesstest.model.Response;
import com.example.maxprocesstest.repository.ContactRepository;
import com.example.maxprocesstest.scheduler.IScheduleProvider;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class ContactListViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();
    private ContactRepository repository;
    private IScheduleProvider scheduleProvider;

    public ContactListViewModel(ContactRepository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }
    public MutableLiveData<Response> response() {
        return response;
    }


    public void getAllContacts() {
        disposables.add(repository.getAll()
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    //EspressoTestingIdlingResource.increment();
                })
                .subscribe(
                        result -> {
                            if(result.isEmpty()) response.setValue(Response.empty());

                                else
                            response.setValue(Response.success(result));
                            // EspressoTestingIdlingResource.decrement();
                        },
                        error -> {
                                response.setValue(Response.error(error));
                            // EspressoTestingIdlingResource.decrement();
                        }
                ));
    }

    public void filterContacts(String name){
        disposables.add(repository.getAll()
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    //EspressoTestingIdlingResource.increment();
                })
                .flatMapObservable(Observable::fromIterable)
                .filter(contact -> contact.getName().startsWith(name))
                .toList()
                .subscribe(
                        result -> {
                            if(result.isEmpty()) response.setValue(Response.empty());

                            else
                                response.setValue(Response.success(result));
                            // EspressoTestingIdlingResource.decrement();
                        },
                        error -> {
                            response.setValue(Response.error(error));
                            // EspressoTestingIdlingResource.decrement();
                        }
                ));
    }


}