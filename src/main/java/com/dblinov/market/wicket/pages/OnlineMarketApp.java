package com.dblinov.market.wicket.pages;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineMarketApp extends WebApplication {
    private static final Logger logger = LoggerFactory.getLogger(OnlineMarketApp.class);

    public OnlineMarketApp() {
        logger.info("Creating online market application");
    }
    @Override
    public Class<? extends Page> getHomePage() {
        return LoginPage.class;
    }

    @Override
    public void init() {
        super.init();
        getApplicationSettings().setPageExpiredErrorPage(LoginPage.class);
        mountPage("/login", LoginPage.class);
        mountPage("/products", ProductsPage.class);
        mountPage("/admin-panel", AdminPage.class);
    }
}