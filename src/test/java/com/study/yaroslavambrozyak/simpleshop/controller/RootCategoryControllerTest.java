package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.entity.RootCategory;
import com.study.yaroslavambrozyak.simpleshop.entity.SubCategory;
import com.study.yaroslavambrozyak.simpleshop.service.RootCategoryService;
import com.study.yaroslavambrozyak.simpleshop.testutil.CategoryBuilder;
import com.study.yaroslavambrozyak.simpleshop.testutil.RootCategoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RootCategoryControllerTest {

    @InjectMocks
    private RootCategoryController rootController;
    @Mock
    private RootCategoryService rootCategoryService;
    private MockMvc mockMvc;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rootController).build();
    }

    @Test
    public void getCategoriesSuccess() throws Exception {
        RootCategory category1 = new RootCategoryBuilder()
                .id(1L)
                .name("category1")
                .imagePath("img_category1")
                .build();

        RootCategory category2 = new RootCategoryBuilder()
                .id(2L)
                .name("category2")
                .imagePath("img_category2")
                .build();

        when(rootCategoryService.getRootCategories()).thenReturn(Arrays.asList(category1,category2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("categories",hasSize(2)))
                .andExpect(model().attribute("categories",hasItem(
                        allOf(
                                hasProperty("id",is(1L)),
                                hasProperty("name",is("category1")),
                                hasProperty("imagePath",is("img_category1"))
                        )
                )))
                .andExpect(model().attribute("categories",hasItem(
                        allOf(
                                hasProperty("id",is(2L)),
                                hasProperty("name",is("category2")),
                                hasProperty("imagePath",is("img_category2"))
                        )
                )));

        verify(rootCategoryService,times(1)).getRootCategories();
        verifyNoMoreInteractions(rootCategoryService);
    }
}
