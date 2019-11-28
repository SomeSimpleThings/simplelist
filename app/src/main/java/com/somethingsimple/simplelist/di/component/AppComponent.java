package com.somethingsimple.simplelist.di.component;

import android.app.Application;

import com.somethingsimple.simplelist.NoteApplication;
import com.somethingsimple.simplelist.di.builder.ActivityBuilderModule;
import com.somethingsimple.simplelist.di.builder.FragmentBuilderModule;
import com.somethingsimple.simplelist.di.module.AppModule;
import com.somethingsimple.simplelist.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        FragmentBuilderModule.class,
        AppModule.class,
        RepositoryModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }


    void inject(NoteApplication noteApplication);
}
