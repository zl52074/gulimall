package com.zl52074.gulimall.search.web;

import com.zl52074.gulimall.search.service.MallSearchService;
import com.zl52074.gulimall.search.vo.SearchParam;
import com.zl52074.gulimall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/19 0:11
 */
@Controller
public class SearchController {
    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request){
        param.set_queryString(request.getQueryString());
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
