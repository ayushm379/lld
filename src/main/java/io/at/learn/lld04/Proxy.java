package io.at.learn.lld04;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Proxy {
    public static void main(String[] args) {
        ProductService productService = new ProductServiceProxy(
                new ProductServiceImpl(), new ProductCacheService()
        );

        Stream.of("Product1", "Product2", "Product2", "Product1", "Product3", "Product1")
                .map(productService::getSellerEmailByProductId)
                .forEach(System.out::println);
    }
}

interface ProductService {
    String getSellerEmailByProductId(String productId);
}

class ProductServiceImpl implements ProductService {

    private static final Map<String, String> dataSet = Map.of(
            "Product1", "Email1",
            "Product2", "Email2",
            "Product3", "Email3"
    );

    @Override
    public String getSellerEmailByProductId(String productId) {
        return dataSet.getOrDefault(productId, null);
    }
}

class ProductCacheService {

    private static final Map<String, String> cache = new HashMap<>();

    public String getEmail(String productId) {
        return cache.getOrDefault(productId, null);
    }

    public void setEmail(String productId, String email) {
        cache.put(productId, email);
    }

}

@RequiredArgsConstructor
class ProductServiceProxy implements ProductService {

    private final ProductService productService;
    private final ProductCacheService cacheService;

    @Override
    public String getSellerEmailByProductId(String productId) {
        String email = cacheService.getEmail(productId);
        if(Objects.isNull(email)) {
            System.out.println("Database Call");
            email = productService.getSellerEmailByProductId(productId);
        }
        cacheService.setEmail(productId, email);
        return email;
    }
}