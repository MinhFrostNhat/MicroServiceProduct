package com.programmingtechie.productservice.controller;

import com.example.api.ProductApi;
import com.example.model.CreateNewResponse;
import com.example.model.GetAllProduct;
import com.example.model.ProductRequest;
import com.programmingtechie.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<CreateNewResponse> createProduct(ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }


    @Override
    public ResponseEntity<GetAllProduct> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }
}
