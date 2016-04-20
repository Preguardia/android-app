package com.preguardia.app.injection.component;

import android.content.Context;

import com.preguardia.app.injection.ActivityScope;
import com.preguardia.app.injection.module.ActivityModule;

import dagger.Component;

/**
 * @author amouly on 3/14/16.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    Context context();

}
