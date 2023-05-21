package com.dblinov.market.servlet;

import com.dblinov.market.dao.ProductDao;
import com.dblinov.market.dao.impl.ProductDaoImpl;
import com.dblinov.market.entity.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "updateProduct", value = "/updateProduct")
public class UpdateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateServlet.class);
    private final static ProductDao productDao = new ProductDaoImpl();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader jsonReader = request.getReader();
        if (Objects.equals(jsonReader, null)) {
            return;
        }
        Gson gson = new Gson();
        Product product = gson.fromJson(gson.newJsonReader(jsonReader), new TypeToken<Product>() {
        }.getType());
        final boolean isUpdated = decreaseQuantity(product, 1);

        PrintWriter writer = response.getWriter();
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        JsonObject jsonObject = new JsonObject();

        if (isUpdated) {
            jsonObject.addProperty("status", "The product updated successfully");
        } else {
            jsonObject.addProperty("status", "The product was not updated");
        }
        writer.print(jsonObject);
        writer.flush();
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