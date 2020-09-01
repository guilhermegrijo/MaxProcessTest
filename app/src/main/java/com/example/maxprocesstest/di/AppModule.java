package com.example.maxprocesstest.di;




import android.app.Application;

import androidx.room.Room;

import com.example.maxprocesstest.App;
import com.example.maxprocesstest.model.ContactDao;
import com.example.maxprocesstest.repository.AppDatabase;
import com.example.maxprocesstest.repository.ContactDataSource;
import com.example.maxprocesstest.repository.ContactRepository;
import com.example.maxprocesstest.scheduler.IScheduleProvider;
import com.example.maxprocesstest.scheduler.SchedulerProvider;
import com.example.maxprocesstest.ui.createContact.CreateContactViewModelFactory;
import com.example.maxprocesstest.ui.contactList.ContactListViewModelFactory;
import com.example.maxprocesstest.ui.updateContact.UpdateContactViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {



    @Singleton
    @Provides
    Application provideContext(App application) {
        return application;
    }

    @Provides
    @Singleton
    IScheduleProvider scheduleProvider() {
        return new SchedulerProvider();
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, "demo-db").build();
    }

    @Singleton
    @Provides
    ContactDao providesProductDao(AppDatabase appDatabase) {
        return appDatabase.contactDao();
    }

    @Singleton
    @Provides
    ContactRepository contactRepository(ContactDao productDao) {
        return new ContactDataSource(productDao);
    }

    @Provides
    ContactListViewModelFactory provideContactListViewModelFactory(ContactRepository repository, IScheduleProvider scheduleProvider) {
        return new ContactListViewModelFactory(repository, scheduleProvider);
    }


    @Provides
    CreateContactViewModelFactory provideCreateContactViewModelFactory(ContactRepository repository, IScheduleProvider scheduleProvider) {
        return new CreateContactViewModelFactory(repository, scheduleProvider);
    }

    @Provides
    UpdateContactViewModelFactory provideUpdateViewModelFactory(ContactRepository repository, IScheduleProvider scheduleProvider) {
        return new UpdateContactViewModelFactory(repository, scheduleProvider);
    }
}
