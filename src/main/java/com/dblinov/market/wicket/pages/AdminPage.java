package com.dblinov.market.wicket.pages;

import com.dblinov.market.dao.ProductDao;
import com.dblinov.market.dao.PurchaseDao;
import com.dblinov.market.dao.impl.ProductDaoImpl;
import com.dblinov.market.dao.impl.PurchaseDaoImpl;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AdminPage extends WebPage {
    private static final long serialVersionUID = 1L;
    private final PurchaseDao purchaseDao = new PurchaseDaoImpl();
    private final ProductDao productDao = new ProductDaoImpl();
    private final List<Product> products = productDao.findAll();
    private final List<Purchase> purchases = purchaseDao.findAll();
    private static final Logger logger = LoggerFactory.getLogger(AdminPage.class);

    public AdminPage(final PageParameters parameters) {
        super(parameters);
        add(new ProductsForm("productsForm"));
        add(new PurchasesForm("purchasesForm"));
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
                            String nameValue = name.getModel().getObject();
                            int quantityValue = Integer.parseInt(quantity.getModel().getObject());
                            int priceValue = price.getModel().getObject();
                            String descriptionValue = description.getModel().getObject();
                            updateProduct(item, nameValue, quantityValue, priceValue, descriptionValue);
                        }
                    });
                    item.add(new Button("delete") {
                        public void onSubmit() {
                            deleteProduct(item);
                        }
                    });
                }
            });
            add(new Button("add") {
                public void onSubmit() {
                    addProduct();
                }
            });
        }
    }

    class PurchasesForm extends Form<Iterable> {
        public PurchasesForm(String id) {
            super(id);

            add(new ListView<Purchase>("purchases", purchases) {
                @Override
                protected void populateItem(ListItem<Purchase> item) {
                    item.add(new Label("id", new PropertyModel(item.getModel(), "Id")));
                    item.add(new Label("userId", new PropertyModel(item.getModel(), "UserId")));
                    item.add(new Label("productId", new PropertyModel(item.getModel(), "ProductId")));
                    item.add(new Label("date", new PropertyModel(item.getModel(), "Date")));
                    item.add(new Label("quantity", new PropertyModel(item.getModel(), "Quantity")));
                    item.add(new Label("sum", new PropertyModel(item.getModel(), "Sum")));
                }
            });
        }
    }

    public void updateProduct(ListItem<Product> item, String name, int quantity, int price, String description) {
        Optional<Product> productOptional = productDao.findById(item.getModelObject().getId());
        if (!productOptional.isPresent()) {
            return;
        }
        Product product = productOptional.get();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setDescription(description);
        productDao.update(product);
        item.getModelObject().setQuantity(quantity);
    }

    public void addProduct() {
        Product product = new Product();
        product.setName("Name");
        product.setPrice(0);
        product.setVersion(0);
        product.setQuantity(0);
        product.setDescription("Description");
        productDao.save(product);
        setResponsePage(AdminPage.class, new PageParameters());
    }

    public void deleteProduct(ListItem<Product> item) {
        Optional<Product> productOptional = productDao.findById(item.getModelObject().getId());
        if (!productOptional.isPresent()) {
            return;
        }
        productDao.delete(productOptional.get());
        setResponsePage(AdminPage.class, new PageParameters());
    }
}