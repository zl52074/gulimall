package com.zl52074.gulimall.product.web;



import com.zl52074.gulimall.product.entity.CategoryEntity;
import com.zl52074.gulimall.product.service.CategoryService;
import com.zl52074.gulimall.product.vo.Catalog2Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;




    @GetMapping(value = {"/","index.html"})
    private String indexPage(Model model) {
        //1、查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categories",categoryEntities);

        return "index";
    }


    @GetMapping(value = {"/index/catalog.json"})
    @ResponseBody
    private Map<String, List<Catalog2Vo>> getCatalogJson() {
        Map<String, List<Catalog2Vo>> map = categoryService.getCatalogJson();
        return map;
    }






}
