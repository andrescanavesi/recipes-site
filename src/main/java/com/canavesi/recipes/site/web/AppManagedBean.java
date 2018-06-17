package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author andrescanavesi
 */
@Named(value = "appManagedBean")
@ApplicationScoped
@ManagedBean
public class AppManagedBean {

    private final boolean enablePushEngage;

    public AppManagedBean() {
        this.enablePushEngage = DaoConfigs.getEnablePushEngage();
    }

    @PostConstruct
    public void init() {

    }

    public boolean isEnablePushEngage() {
        return enablePushEngage;
    }

}
