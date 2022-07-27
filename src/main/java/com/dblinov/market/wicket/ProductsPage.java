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
import org.apache.wicket.util.string.StringValue;

import java.util.*;

public class ProductsPage extends WebPage {

    private final ProductController productController = new ProductController();
    private final PurchaseController purchaseController = new PurchaseController();
    private final List<Product> products = productController.findAllProducts();
    private final Integer userId;

    public ProductsPage(final PageParameters parameters) {
        super(parameters);
        add(new ProductsForm("productsForm"));
        StringValue userId = parameters.get("userId");
        this.userId = userId.toInteger();
    }

    class ProductsForm extends Form<Iterable> {
        public ProductsForm(String id) {
            super(id);

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
                            //ЗДЕСЬ СОЗДАЕМ ОТДЕЛЬНУЮ ПОКУПКУ НА КАЖДЫЙ ТОВАР, ДОБАВЛЯЕМ ЕЕ В ОБЩИЙ СПИСОК
                            int quantity = Integer.parseInt(quantityTextField.getModel().getObject());
                            if (quantity != 0) {
                                Purchase purchase = new Purchase();
                                purchase.setUserId(userId);
                                purchase.setProductId(item.getModelObject().getId());
                                purchase.setQuantity(quantity);
                                Date date = new Date();
                                purchase.setDateOfPurchase(date);
                                //TODO нужно проапдейтить продукт, убедиться, что он проапдейтился и только потом совершать purchase
                                Product product = productController.findById(item.getModelObject().getId());
                                purchase.setSum(quantity * product.getPrice());
                                purchaseController.savePurchase(purchase);
                                product.decreaseQuantity(quantity);
                                productController.updateProduct(product);
                            }
                        }
                    });
                }
            });
        }
    }
}