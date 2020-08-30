package com.example.maxprocesstest.di;



import com.example.maxprocesstest.ui.contactDetail.ContactDetailFragment;
import com.example.maxprocesstest.ui.contactList.ContactListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract ContactListFragment bindContactListFragment();


    @ContributesAndroidInjector
    abstract ContactDetailFragment bindContactDetailFragment();



}