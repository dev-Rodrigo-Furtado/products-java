package com.example.demo.web.repository;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepositoryInterface;
import com.example.demo.web.entity.ProductEntity;
import com.example.demo.web.entity.TagEntity;
import com.example.demo.web.exception.RegistryFoundException;
import com.example.demo.web.vo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepository implements ProductRepositoryInterface {

    @Autowired
    private ProductJPARepository productJPARepository;

    @Override
    public void save(Product product) {
        ProductEntity productEntity = getProductEntity(product);
        if(productJPARepository.existsByProductCode(product.getProductCode())) {
            throw new RegistryFoundException("A product with code " + product.getProductCode() + " already exists");
        }
        productJPARepository.save(productEntity);
    }

    @Override
    public List<Product> findAllProducts() {
        return productJPARepository.findAll().stream()
                .map(this::getProduct).collect(Collectors.toList());
    }

    private ProductEntity getProductEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductCode(product.getProductCode());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());

        List<TagEntity> tagEntityList = product.getTags().stream()
                .map(t -> {
                    TagEntity tagEntity = new TagEntity();
                    tagEntity.setName(t.getName());
                    tagEntity.setProduct(productEntity);

                    return  tagEntity;
                })
                .collect(Collectors.toList());

        productEntity.setTags(tagEntityList);

        return productEntity;
    }

    private Product getProduct(ProductEntity productEntity) {
        Product product = new Product(productEntity.getProductCode(), productEntity.getName(),
                productEntity.getPrice());

        productEntity.getTags().forEach(t -> {
            Tag tag = new Tag(t.getName());
            product.addTag(tag);
        });

        return product;
    }
}
