package com.zl52074.gulimall.search.service;

import com.zl52074.gulimall.search.vo.SearchParam;
import com.zl52074.gulimall.search.vo.SearchResult;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/19 10:26
 */
public interface MallSearchService {

    SearchResult search(SearchParam param);
}
