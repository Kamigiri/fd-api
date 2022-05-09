package com.keraisoft.fd.pt.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.keraisoft.fd.pt.Price.Price;
import com.keraisoft.fd.pt.Price.PriceRepository;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.EntityModel;

@RestController
public class ProductController {
    private final ProductRepository repository;
    private final PriceRepository priceRepository;
    private final ProductModelAssembler assembler;

    ProductController(ProductRepository repository, ProductModelAssembler assembler, PriceRepository priceRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.priceRepository = priceRepository;
    }

    // get call for products with mapping for prices

    @GetMapping("/api/products")
    List<ProductResponse> all() {
        List<ProductResponse> response = new ArrayList<>();
        List<Product> products = repository.findAll();

        for (Product product : products) {
            List<Price> prices = priceRepository.findByProductId(product.getId());
            response.add(new ProductResponse(product, prices));
        }

        return response;

    }

    @GetMapping("/api/productCheckPrice/{id}")
    Price checkProductPrice(@PathVariable Long id) throws IOException {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        ProductService scraper = new ProductService();
        Price price = scraper.getPrice(product);
        if(price != null) {
            priceRepository.save(price);
        }
        return price;

    }

    @GetMapping("/productLastPrice/{id}")
    Price lastProductPrice(@PathVariable Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        List<Price> prices = priceRepository.findByProductId(product.getId());

        return prices.get(0) != null ? prices.get(0) : null;
    }

    @GetMapping("/products/{id}")
    EntityModel<Product> one(@PathVariable Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toModel(product);
    }

    @PostMapping("/api/products")
    ResponseEntity<?> newProduct(@RequestBody Product newProduct) throws IOException {

        ProductService scraper = new ProductService();
        Price price = scraper.getPrice(newProduct);
        if(price != null) {
            EntityModel<Product> entityModel = assembler.toModel(repository.save(newProduct));
            priceRepository.save(price);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }
        return null;
    }

    @PutMapping("/products/{id}")
    ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product updatedProduct = repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setAmazonId(newProduct.getAmazonId());
                    product.setUpdatedAt(new Date());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });

        EntityModel<Product> entityModel = assembler.toModel(updatedProduct);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
