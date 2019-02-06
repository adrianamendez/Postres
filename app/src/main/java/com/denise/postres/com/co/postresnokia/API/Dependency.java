package com.denise.postres.com.co.postresnokia.API;



import com.denise.postres.com.co.postresnokia.MVP.View.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class,})
public interface Dependency {
    void inject(MainActivity dessertActivity);
}
