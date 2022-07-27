package com.dblinov.market.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class Application extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return LoginPage.class;
    }

    @Override
    public void init()
    {
        super.init();
        // add your configuration here
        getApplicationSettings().setPageExpiredErrorPage(LoginPage.class);
    }
}