package com.example.demo.model;

import com.example.demo.web.vo.Tag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product {

    private Long productCode;
    private String name;
    private BigDecimal price;
    private List<Tag> tags;

    public Product(Long productCode, String name, BigDecimal price) {
        updateCode(productCode);
        updateName(name);
        updatePrice(price);
        tags = new ArrayList<>();
    }

    public void updateCode(Long productCode) {
        if(productCode == null) {
            throw new IllegalArgumentException("product code cannot be null");
        }

        if(productCode <= 0) {
            throw new IllegalArgumentException("product code must be greater than 0");
        }

        this.productCode = productCode;
    }

    public void updateName(String name) {
        if(name == null) {
            throw new IllegalArgumentException("product name cannot be null");
        }

        String pattern = "^[\\w\\s]{5,100}$";

        if(!matches(name, pattern)) {
            throw new IllegalArgumentException("product name does not match with pattern " + pattern);
        }

        this.name = name;
    }

    public void updatePrice(BigDecimal price) {
        if(price == null) {
            throw new IllegalArgumentException("product price cannot be null");
        }

        String pattern = "^\\d{1,15}\\.\\d{2}$";

        if(!matches(price.toString(), pattern)) {
            throw new IllegalArgumentException("product price does not match with pattern " + pattern);
        }

        this.price = price;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public Long getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Tag> getTags() {
        return tags;
    }

    private boolean matches(String value, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return  m.matches();
    }
}
