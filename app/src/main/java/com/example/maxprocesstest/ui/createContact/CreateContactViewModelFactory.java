package com.example.maxprocesstest.ui.createContact;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maxprocesstest.repository.ContactRepository;
import com.example.maxprocesstest.scheduler.IScheduleProvider;

public class CreateContactViewModelFactory implements ViewModelProvider.Factory {


    private final ContactRepository repository;

    private final IScheduleProvider scheduleProvider;

    public CreateContactViewModelFactory(ContactRepository repository, IScheduleProvider scheduleProvider) {
        this.repository = repository;
        this.scheduleProvider = scheduleProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateContactViewModel.class)) {
            return (T) new CreateContactViewModel(repository, scheduleProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}
