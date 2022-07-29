package com.dblinov.market.wicket;

import com.dblinov.market.controller.ProductController;
import com.dblinov.market.controller.PurchaseController;
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

public class AdminPage extends WebPage {
    private static final long serialVersionUID = 1L;
    private final PurchaseController purchaseController = new PurchaseController();
    private final ProductController productController = new ProductController();
    private List<Product> products = productController.findAllProducts();
    private final List<Purchase> purchases = purchaseController.findAllPurchases();


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
        Product product = productController.findById(item.getModelObject().getId());
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setDescription(description);
        productController.updateProduct(product);
        item.getModelObject().setQuantity(quantity);
    }

    public void addProduct() {
        Product product = new Product();
        product.setName("Name");
        product.setPrice(0);
        product.setVersion(0);
        product.setQuantity(0);
        product.setDescription("Description");
        productController.saveProduct(product);
        setResponsePage(AdminPage.class, new PageParameters());
    }

    public void deleteProduct(ListItem<Product> item) {
        Product product = productController.findById(item.getModelObject().getId());
        productController.deleteProduct(product);
        setResponsePage(AdminPage.class, new PageParameters());
    }
}