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
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ProductsPage extends WebPage {
    private static final Logger logger = LoggerFactory.getLogger(ProductsPage.class);
    private final ProductDao productDao = new ProductDaoImpl();
    private final PurchaseDao purchaseDao = new PurchaseDaoImpl();
    private final List<Product> products = productDao.findAll();
    private final Integer userId;
    private Label purchaseStatus;

    public ProductsPage(final PageParameters parameters) {
        super(parameters);
        add(new ProductsForm("productsForm"));
        StringValue userId = parameters.get("userId");
        this.userId = userId.toInteger();
    }

    class ProductsForm extends Form<Iterable> {
        public ProductsForm(String id) {
            super(id);
            purchaseStatus = new Label("purchaseStatus", Model.of(""));
            add(purchaseStatus);
            add(new ListView<Product>("products", products) {
                @Override
                protected void populateItem(ListItem<Product> item) {
                    TextField<String> quantityTextField = new TextField<>("quantity", Model.of("0"));
                    item.add(quantityTextField);
                    item.add(new Label("name", new PropertyModel(item.getModel(), "Name")));
                    item.add(new Label("price", new PropertyModel(item.getModel(), "Price")));
                    item.add(new Label("description", new PropertyModel(item.getModel(), "Description")));
                    item.add(new Button("buy") {
                        public void onSubmit() {
                            purchaseStatus.setDefaultModelObject("");
                            int quantity = Integer.parseInt(quantityTextField.getModel().getObject());
                            if (quantity != 0) {
                                makePurchase(item, quantity);
                            }
                        }
                    });
                }
            });
        }
    }

    public void makePurchase(ListItem<Product> item, int quantity) {
        Purchase purchase = new Purchase();
        purchase.setUserId(userId);
        purchase.setProductId(item.getModelObject().getId());
        purchase.setQuantity(quantity);
        Date date = new Date();
        purchase.setDateOfPurchase(date);

        Optional<Product> productOptional = productDao.findById(item.getModelObject().getId());
        if (!productOptional.isPresent()) {
            purchaseStatus.setDefaultModelObject("Item was not bought!");
            return;
        }
        purchase.setSum(quantity * productOptional.get().getPrice());

        final boolean isProductUpdated = decreaseQuantity(productOptional.get(), quantity);
        if (isProductUpdated) {
            purchaseStatus.setDefaultModelObject("You've bought the item!");
            purchaseDao.save(purchase);
        } else {
            purchaseStatus.setDefaultModelObject("Item was not bought!");
        }
    }

    private boolean decreaseQuantity(Product product, int amount) {
        if (product.getQuantity() - amount > 0) {
            int quantity = product.getQuantity() - amount;
            product.setQuantity(quantity);
            return productDao.update(product);
        }
        return false;
    }
}