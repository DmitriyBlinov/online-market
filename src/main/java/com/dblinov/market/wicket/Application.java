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
        getApplicationSettings().setPageExpiredErrorPage(LoginPage.class);
        mountPage("/login", LoginPage.class);
        mountPage("/products", ProductsPage.class);
        mountPage("/admin-panel", AdminPage.class);
    }
}