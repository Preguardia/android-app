package com.preguardia.app.injection.module;

import android.content.Context;

import com.preguardia.app.injection.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author amouly on 3/14/16.
 */
@Module
public class ActivityModule {

    private Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return context;
    }

}
