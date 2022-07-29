package com.dblinov.market.servlet;

import com.dblinov.market.controller.ProductController;
import com.dblinov.market.entity.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "updateProduct", value = "/updateProduct")
public class UpdateServlet extends HttpServlet {
    private static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader jsonReader = request.getReader();
        if (Objects.equals(jsonReader,null)) {
            return;
        }
        Gson gson = new Gson();
        Product product = gson.fromJson(gson.newJsonReader(jsonReader), new TypeToken<Product>(){}.getType());
        ProductController productController = new ProductController(product);
        productController.decreaseQuantity(1);
        boolean isUpdated = productController.updateProduct(product);

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
}