package com.keraisoft.fd.pt.Product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Could not find Product " + id);
    }
}
