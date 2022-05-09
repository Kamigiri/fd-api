package com.keraisoft.fd.pt.Product;

import java.io.IOException;
import java.util.Date;

import com.keraisoft.fd.pt.Price.Price;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ProductService {
    private Document doc;
    private String url;
    final static String[] PATH_OPTIONS = {".priceToPay", ".apexPriceToPay"};
    final static String BASE_URL = "https://www.amazon.de/dp/";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";

    public ProductService() throws IOException {
    }

    private void setDoc(String url) throws IOException {
        this.doc = Jsoup.connect(BASE_URL + url).userAgent(USER_AGENT).get();
    }

    public String getTitle(String url) throws IOException {
        return doc.title();
    }

   public Price getPrice(Product product) throws IOException {
       this.setDoc(product.getAmazonId());
       Elements price = null;
       for (String option : PATH_OPTIONS
       ) {
           price = doc.select(option).select(".a-offscreen");
           if(!price.isEmpty()) {
               break;
           }
       }
       return price.isEmpty() ? null : new Price(price.html(),new Date(), product);

    }
}
