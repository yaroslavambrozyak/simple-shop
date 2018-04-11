package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.search.ProductSpecification;
import com.study.yaroslavambrozyak.simpleshop.search.SearchCriteria;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.testutil.ProductBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;
    private MockMvc mockMvc;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }


    @Test
    public void getProductsWithOutFilterSuccess() throws Exception {
        Product product1 = new ProductBuilder()
                .id(1L)
                .name("prod1")
                .description("desc1")
                .quantity(1)
                .build();

        Product product2 = new ProductBuilder()
                .id(2L)
                .name("prod2")
                .description("desc2")
                .quantity(2)
                .build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product1,product2),pageRequest,10);

        when(productService.getProductsByCategory(1L,pageRequest)).thenReturn(productPage);

        mockMvc.perform(get("/products?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products-list"))
                .andExpect(model().attribute("products",hasItem(
                        allOf(
                                hasProperty("id",is(1L)),
                                hasProperty("name",is("prod1")),
                                hasProperty("description",is("desc1")),
                                hasProperty("quantity",is(1))
                        )
                        )))
                .andExpect(model().attribute("products",hasItem(
                        allOf(
                                hasProperty("id",is(2L)),
                                hasProperty("name",is("prod2")),
                                hasProperty("description",is("desc2")),
                                hasProperty("quantity",is(2))
                        )
                )));

        verify(productService,times(1)).getProductsByCategory(1L,pageRequest);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void getFilteredProducts() throws Exception {
        Product product1 = new ProductBuilder()
                .id(1L)
                .name("prod1")
                .description("desc1")
                .quantity(1)
                .build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product1),pageRequest,10);
        ProductSpecification productSpecification = new ProductSpecification(new SearchCriteria());

        when(productService.getFilteredProductsByCategory(productSpecification,pageRequest))
                .thenReturn(productPage);

        mockMvc.perform(get("/products?id=1&filter=name:prod1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products-list"))
                .andExpect(model().attribute("products",hasItem(
                        allOf(
                                hasProperty("id",is(1L)),
                                hasProperty("name",is("prod1")),
                                hasProperty("description",is("desc1")),
                                hasProperty("quantity",is(1))
                        )
                )));

        verify(productService,times(1))
                .getFilteredProductsByCategory(productSpecification,pageRequest);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void getProductSuccess() throws Exception {
        Product product = new ProductBuilder()
                .id(1L)
                .name("prod1")
                .description("desc1")
                .quantity(1)
                .build();

        when(productService.getProduct(1L)).thenReturn(product);

        mockMvc.perform(get("/product?id=1"))
                .andExpect(status().isOk());
    }
}
