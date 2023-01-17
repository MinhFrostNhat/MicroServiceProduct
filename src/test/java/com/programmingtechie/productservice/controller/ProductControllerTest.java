package com.programmingtechie.productservice.controller;

import com.example.model.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjasquad.springmockk.MockkBean;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ProductControllerTest {

    @LocalServerPort
    private Integer port;


    private final String CALL_URI_POST_PRODUCT = "/v1/api/product";
    private final String CALL_URI_GET_PRODUCT = "/v1/api/checkproduct";

    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .setCustomArgumentResolvers(pageableHandlerMethodArgumentResolver)
                .build();

    }

    public ProductRequest initProduct() {
        ProductRequest product = new ProductRequest();
        product.setNameOfProduct("product pixel");
        product.setDescriptionProduct("this is product");
        product.setPrice(100.2);
        return product;
    }

    public ProductRequest initProduct1(){
        ProductRequest product = new ProductRequest();
        product.setNameOfProduct("product pixel");
        product.setDescriptionProduct("this is product");
        product.setPrice(100.2);
        return product;
    }

    public ProductRequest initProductMissingField() {
        ProductRequest product = new ProductRequest();
        product.setDescriptionProduct("haha");
        return product;
    }

    @Test
    public void shouldReturnSuccessWhenPostNewProduct() throws Exception {
        String productAsString = objectMapper.writeValueAsString(initProduct());
        System.out.println(productAsString);
        mockMvc.perform(MockMvcRequestBuilders.post(CALL_URI_POST_PRODUCT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productAsString))
                .andExpect(status().is(200));
        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    public void shouldReturnFailWhenMissingFieldName() throws Exception{
        String productAsString = objectMapper.writeValueAsString(initProductMissingField());

        mockMvc.perform(MockMvcRequestBuilders.post(CALL_URI_POST_PRODUCT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productAsString))
                .andExpect(status().is(400));
        Assertions.assertEquals(0, productRepository.findAll().size());
    }


    @Test
    public void shouldReturnSuccessWhenGetProduct() throws Exception {
        String productAsString = objectMapper.writeValueAsString(initProduct());
        String productAsString1 = objectMapper.writeValueAsString(initProduct1());
        mockMvc.perform(MockMvcRequestBuilders.post(CALL_URI_POST_PRODUCT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productAsString))
                .andExpect(status().is(200));
        mockMvc.perform(MockMvcRequestBuilders.post(CALL_URI_POST_PRODUCT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productAsString1))
                .andExpect(status().is(200));
        mockMvc.perform(MockMvcRequestBuilders.get(CALL_URI_GET_PRODUCT))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.getProduct").isArray());
    }
}
