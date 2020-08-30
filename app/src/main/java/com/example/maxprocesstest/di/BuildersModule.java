package com.example.maxprocesstest.di;



import com.example.maxprocesstest.ui.contactList.ContactListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract ContactListFragment bindMainFragment();


}