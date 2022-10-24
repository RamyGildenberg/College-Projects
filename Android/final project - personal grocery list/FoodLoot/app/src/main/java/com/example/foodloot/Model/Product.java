package com.example.foodloot.Model;

public class Product {
    private String productId, productName;
    private Boolean izInShoppingList;

    public Product() {}

    public Product(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;

    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }


}
