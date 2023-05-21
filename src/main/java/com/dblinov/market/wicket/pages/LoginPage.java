package com.dblinov.market.wicket.pages;

import com.dblinov.market.dao.UserDao;
import com.dblinov.market.dao.impl.UserDaoImpl;
import com.dblinov.market.entity.User;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LoginPage extends WebPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(final PageParameters parameters) {
        super(parameters);
        add(new LoginForm("loginForm"));
    }

    class LoginForm extends Form<Iterable> {
        private TextField usernameField;
        private PasswordTextField passwordField;
        private Label loginStatus;

        private final UserDao userDao = new UserDaoImpl();

        public LoginForm(String id) {
            super(id);

            usernameField = new TextField("username", Model.of(""));
            passwordField = new PasswordTextField("password", Model.of(""));
            loginStatus = new Label("loginStatus", Model.of(""));

            add(usernameField);
            add(passwordField);
            add(loginStatus);
            add(new Button("register") {
                public void onSubmit() {
                    String username = (String) usernameField.getDefaultModelObject();
                    String password = (String) passwordField.getDefaultModelObject();

                    User user = userDao.findByName(username);

                    if (Objects.equals(password, null) || Objects.equals(username, null)) {
                        loginStatus.setDefaultModelObject("The username or password is empty!");
                    } else if (!Objects.equals(null, user)) {
                        loginStatus.setDefaultModelObject("This user already exists!");
                        logger.info("User with username '{}' already exists", username);
                    } else {
                        user = new User();
                        user.setAdmin(false);
                        user.setName(username);
                        user.setPassword(password);
                        userDao.save(user);
                        loginStatus.setDefaultModelObject("The user " + username + "was registered successfully!");
                        logger.info("Successfully registered user: {}", username);
                    }
                }
            });

            add(new Button("login") {
                public void onSubmit() {
                    String username = (String) usernameField.getDefaultModelObject();
                    String password = (String) passwordField.getDefaultModelObject();

                    User user = userDao.findByName(username);

                    if (Objects.equals(null, user)) {
                        loginStatus.setDefaultModelObject("Wrong username or password!");
                    } else {
                        if (user.getPassword().equals(password) && user.isAdmin()) {
                            loginStatus.setDefaultModelObject("Congratulations! You are an admin");
                            setResponsePage(AdminPage.class, new PageParameters());
                            logger.info("Successfully logged in admin user: {}", username);
                        } else if (user.getPassword().equals(password)) {
                            loginStatus.setDefaultModelObject("Congratulations! You are a user");
                            PageParameters parameters = new PageParameters();
                            parameters.add("userId", user.getId());
                            setResponsePage(ProductsPage.class, parameters);
                            logger.info("Successfully logged in regular user: {}", username);
                        } else {
                            loginStatus.setDefaultModelObject("Wrong username or password!");
                            logger.info("User {} has entered wrong login credentials", username);
                        }
                    }
                }
            });
        }
    }
}