package com.dblinov.market.wicket;

import com.dblinov.market.controller.UserController;
import com.dblinov.market.entity.User;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.model.Model;

import java.util.Objects;

public class LoginPage extends WebPage {


    public LoginPage(final PageParameters parameters) {
        super(parameters);
        add(new LoginForm("loginForm"));
    }

    class LoginForm extends Form<Iterable> {
        private TextField usernameField;
        private PasswordTextField passwordField;
        private Label loginStatus;

        private UserController userController = new UserController();

        public LoginForm(String id) {
            super(id);

            usernameField = new TextField("username", Model.of(""));
            passwordField = new PasswordTextField("password", Model.of(""));
            loginStatus = new Label("loginStatus", Model.of(""));

            add(usernameField);
            add(passwordField);
            add(loginStatus);
        }

        public final void onSubmit() {
            String username = (String) usernameField.getDefaultModelObject();
            String password = (String) passwordField.getDefaultModelObject();

            //TODO добавить регистрацию пользователя (отдельной кнопкой?)
            User user = userController.findUserByName(username);

            if (Objects.equals(null, user)) {
                loginStatus.setDefaultModelObject("Wrong username or password!");
            } else {
                if (user.getPassword().equals(password) && user.isAdmin()) {
                    loginStatus.setDefaultModelObject("Congratulations! You are an admin");
                    setResponsePage(AdminPage.class, new PageParameters());
                } else if (user.getPassword().equals(password)) {
                    loginStatus.setDefaultModelObject("Congratulations! You are a user");
                    setResponsePage(ProductsPage.class, new PageParameters());
                } else {
                    loginStatus.setDefaultModelObject("Wrong username or password!");
                }
            }
        }
    }
}