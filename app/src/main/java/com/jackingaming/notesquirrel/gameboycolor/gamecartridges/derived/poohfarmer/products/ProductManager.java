package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products;

import android.graphics.Canvas;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductManager
        implements Serializable {

    private ArrayList<Product> products;

    public ProductManager() {
        products = new ArrayList<Product>();
    }

    public void render(Canvas canvas) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            product.render(canvas);
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        if (products.contains(product)) {
            products.remove(product);
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}