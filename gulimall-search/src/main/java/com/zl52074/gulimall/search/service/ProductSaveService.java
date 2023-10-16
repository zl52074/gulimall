package com.zl52074.gulimall.search.service;

import com.zl52074.gulimall.common.es.SkuEsModel;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
