package com.example.maxprocesstest.ui.createContact;

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

public class CreateContactViewModel extends ViewModel {



    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();
    private ContactRepository repository;
    private IScheduleProvider scheduleProvider;

    public CreateContactViewModel(ContactRepository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }
    public MutableLiveData<Response> response() {
        return response;
    }


    public void createNewContact(Contact contact, List<String> phones){
        contact.setCreatedAt(new Date());
        disposables.add(repository.insert(contact)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe(__ -> {
                    //EspressoTestingIdlingResource.increment();
                }).doOnSuccess(result -> {
                            List<Phone> phoneList = new ArrayList<>();

                            for(String number : phones) {
                                Phone phone = new Phone();
                                phone.setPhone(number);
                                phone.setPhonesContactId(result);
                                phoneList.add(phone);
                            }

                    repository.insert(phoneList.toArray(new Phone[phoneList.size()]))
                            .subscribeOn(scheduleProvider.io())
                            .observeOn(scheduleProvider.ui())
                            .doOnComplete(() -> {
                        response.setValue(Response.completed());
                    }).doOnError(error -> {
                        response.setValue(Response.error(error));
                    }).subscribe();
                }
                ).doOnError(error -> response.setValue(Response.error(error))).subscribe());
    }
}