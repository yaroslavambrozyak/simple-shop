package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.service.CategoryService;
import com.study.yaroslavambrozyak.simpleshop.testutil.CategoryBuilder;
import com.study.yaroslavambrozyak.simpleshop.testutil.ExceptionResolverHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;
    @Mock
    private CategoryService categoryService;
    private MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setHandlerExceptionResolvers(ExceptionResolverHolder.getExceptionResolver())
                .build();
    }

    @Test
    public void getCategoriesSuccess() throws Exception {
        SubCategory subCategory1 = new CategoryBuilder()
                .id(1L)
                .name("category1")
                .imagePath("img_category1")
                .build();
        SubCategory subCategory2 = new CategoryBuilder()
                .id(2L)
                .name("category2")
                .imagePath("img_category2")
                .build();

        when(categoryService.getSubCategoriesById(1L)).thenReturn(Arrays.asList(subCategory1, subCategory2));

        mockMvc.perform(get("/category?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories"))
                .andExpect(model().attribute("categories", hasSize(2)))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("category1")),
                                hasProperty("imagePath", is("img_category1"))
                        ))))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is("category2")),
                                hasProperty("imagePath", is("img_category2"))
                        ))));

        verify(categoryService, times(1)).getSubCategoriesById(1L);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getSubCategoriesFail() throws Exception {
        when(categoryService.getSubCategoriesById(1L)).thenThrow(new NotFoundException());

        mockMvc.perform(get("/category?id=1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));

        verify(categoryService,times(1)).getSubCategoriesById(1L);
        verifyNoMoreInteractions(categoryService);
    }

}
