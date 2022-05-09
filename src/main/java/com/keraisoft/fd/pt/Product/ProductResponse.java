package com.keraisoft.fd.pt.Product;

import com.keraisoft.fd.pt.Price.Price;

import java.util.List;


public class ProductResponse {
    private Product product;
    private List<Price> prices;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public ProductResponse(Product product, List<Price> prices) {
        this.product = product;
        this.prices = prices;
    }
    public  ProductResponse() {
    }

    @Override
    public String toString() {
        return "ProductResponse{" + "product=" + product + ", prices=" + prices + '}';
    }
}
