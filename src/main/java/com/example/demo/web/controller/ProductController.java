package com.example.demo.web.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepositoryInterface;
import com.example.demo.web.dto.ProductCreateDto;
import com.example.demo.web.dto.ProductDto;
import com.example.demo.web.repository.ProductRepository;
import com.example.demo.web.response.Response;
import com.example.demo.web.vo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductRepositoryInterface productRepository;

    @PostMapping("products")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<ProductDto> createProduct(@RequestBody ProductCreateDto productCreateDto) {
        Product product = getProduct(productCreateDto);
        productRepository.save(product);

        ProductDto productDto = getProductDto(product);
        Response<ProductDto> response = new Response<>(productDto);

        return response;
    }

    @GetMapping("products")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<ProductDto>> findAllProducts() {
        List<ProductDto> productDtoList = productRepository.findAllProducts()
                .stream().map(this::getProductDto).collect(Collectors.toList());

        Response<List<ProductDto>> response = new Response<>(productDtoList);

        return response;
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductCode(product.getProductCode());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());

        List<String> tags = product.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        productDto.setTags(tags);

        return productDto;
    }

    private Product getProduct(ProductCreateDto productCreateDto) {
        Product product = new Product(productCreateDto.getProductCode(), productCreateDto.getName(),
                productCreateDto.getPrice());

        productCreateDto.getTags().forEach(t -> {
            Tag tag = new Tag(t);
            product.addTag(tag);
        });

        return product;
    }

}
