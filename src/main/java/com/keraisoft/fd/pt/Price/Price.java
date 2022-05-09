package com.keraisoft.fd.pt.Price;
import com.keraisoft.fd.pt.Product.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "price")
public class Price {
    private @Id
    @GeneratedValue
    Long id;
    private String price;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "products_id")
    private Product product;

    public Price() {
    }

    public Price(String price, Date date, Product product) {
        this.price = price;
        this.date = date;
        this.product = product;
    }

    public Price(String price, Date date) {
        this.price = price;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(id, price1.id) &&
                Objects.equals(price, price1.price) &&
                Objects.equals(date, price1.date) &&
                Objects.equals(product, price1.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, date, product);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", date=" + date +
                ", product=" + product +
                '}';
    }
}




