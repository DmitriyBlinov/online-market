package com.dblinov.market.wicket;

import com.dblinov.market.dao.ProductDao;
import com.dblinov.market.entity.Product;
import com.dblinov.market.entity.Purchase;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.*;

public class ProductsPage extends WebPage {

    private final ProductDao productDao = new ProductDao();
    private final List<Product> products = productDao.findAll();

    public ProductsPage(final PageParameters parameters) {
        super(parameters);
        add(new ProductsForm("productsForm"));
    }

    class ProductsForm extends Form<Iterable> {
        public ProductsForm(String id) {
            super(id);

            add(new ListView<Product>("products", products) {
                @Override
                protected void populateItem(ListItem<Product> item) {
                    TextField<String> quantity = new TextField<>("quantity", Model.of("0"));
                    item.add(quantity);
                    item.add(new Label("name", new PropertyModel(item.getModel(), "Name")));
                    item.add(new Label("price", new PropertyModel(item.getModel(), "Price")));
                    item.add(new Label("description", new PropertyModel(item.getModel(), "Description")));
                    item.add(new Button("buy") {
                        public void onSubmit() {
                            //ЗДЕСЬ СОЗДАЕМ ОТДЕЛЬНУЮ ПОКУПКУ НА КАЖДЫЙ ТОВАР, ДОБАВЛЯЕМ ЕЕ В ОБЩИЙ СПИСОК
                            Purchase purchase = new Purchase();
                            purchase.setProductId(item.getModelObject().getId());
                            purchase.incrementQuantity();

                            Date date = new Date();
                            purchase.setDateOfPurchase(date);

                            Product product = productDao.findById(item.getModelObject().getId());

                            int finalQuantity = Integer.parseInt(quantity.getModel().getObject());
                            int price = product.getPrice();
                            purchase.setSum(finalQuantity * price);
                            //TODO добавить UserID
                            //purchase.setUserId();
                            product.decreaseQuantity(finalQuantity);
                            //TODO сделать update purchase в базе
                        }
                    });
                }
            });
        }
    }
}