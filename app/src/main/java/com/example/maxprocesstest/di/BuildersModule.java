package com.example.maxprocesstest.di;



import com.example.maxprocesstest.ui.createContact.CreateContactFragment;
import com.example.maxprocesstest.ui.contactList.ContactListFragment;
import com.example.maxprocesstest.ui.updateContact.UpdateContactFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract ContactListFragment bindContactListFragment();


    @ContributesAndroidInjector
    abstract CreateContactFragment bindContactDetailFragment();


    @ContributesAndroidInjector
    abstract UpdateContactFragment bindUpdateFragment();




}