package com.programmingtechie.productservice.service;

import com.example.model.CreateNewResponse;
import com.example.model.GetAllProduct;
import com.example.model.ProductRequest;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public CreateNewResponse createProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getNameOfProduct());
        product.setDescription(productRequest.getDescriptionProduct());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        log.info("Product {} is save",product.getId());
        CreateNewResponse createNewResponse = new CreateNewResponse();
        createNewResponse.setDescriptionProduct(product.getDescription());
        createNewResponse.setNameOfProduct(product.getName());
        createNewResponse.setPrice(product.getPrice());
        return createNewResponse;

    }

    public GetAllProduct getAllProduct(){
        List<Product> products = productRepository.findAll();
        GetAllProduct getAllProducts = new GetAllProduct();
        log.info("Query all product");
        return getAllProducts.getProduct(products.stream().map(this::mapToProductResponse)
                .collect(Collectors.toList()));
    }

    private ProductRequest mapToProductResponse(Product product){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescriptionProduct(product.getDescription());
        productRequest.setNameOfProduct(product.getName());
        productRequest.setPrice(product.getPrice());
        return productRequest;
    }

}
