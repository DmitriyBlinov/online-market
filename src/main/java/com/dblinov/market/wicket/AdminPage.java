package com.dblinov.market.wicket;

import com.dblinov.market.controller.ProductController;
import com.dblinov.market.dao.ProductDao;
import com.dblinov.market.entity.Product;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.*;

public class AdminPage extends WebPage {
    private static final long serialVersionUID = 1L;
    private final ProductDao productDao = new ProductDao();
    private final ProductController productController = new ProductController();
    private final List<Product> products = productDao.findAll();


    public AdminPage(final PageParameters parameters) {
        super(parameters);
        add(new ProductsForm("productsForm"));
    }

    class ProductsForm extends Form<Iterable> {
        public ProductsForm(String id) {
            super(id);

            add(new ListView<Product>("products", products) {
                @Override
                protected void populateItem(ListItem<Product> item) {
                    TextField<String> name = new TextField<>("name", new PropertyModel(item.getModel(), "Name"));
                    TextField<String> quantity = new TextField<>("quantity", Model.of(item.getModelObject().getQuantity() + ""));
                    TextField<Integer> price = new TextField<>("price", new PropertyModel(item.getModel(), "Price"));
                    TextField<String> description = new TextField<>("description", new PropertyModel(item.getModel(), "Description"));
                    item.add(name);
                    item.add(quantity);
                    item.add(price);
                    item.add(description);
                    item.add(new Button("update") {
                        public void onSubmit() {
                            //ИЗМЕНИЛИ ПРОДУКТ ПО НАЖАТИЮ КНОПКИ, ПРОАПДЕЙТИЛИ QUANTITY
                            Product product = productDao.findById(item.getModelObject().getId());
                            product.setName(name.getModel().getObject());
                            product.setQuantity(Integer.parseInt(quantity.getModel().getObject()));
                            product.setPrice(price.getModel().getObject());
                            product.setDescription(description.getModel().getObject());
                            productController.updateProduct(product);
                            item.getModelObject().setQuantity(Integer.parseInt(quantity.getModel().getObject()));
                        }
                    });
                }
            });
        }
    }
}