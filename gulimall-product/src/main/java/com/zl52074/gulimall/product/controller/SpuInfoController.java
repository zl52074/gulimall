package com.zl52074.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.zl52074.gulimall.product.vo.BaseAttrs;
import com.zl52074.gulimall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl52074.gulimall.product.entity.SpuInfoEntity;
import com.zl52074.gulimall.product.service.SpuInfoService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.R;



/**
 * spu信息
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 根据skuId查询spu的信息
     * @param skuId
     * @return
     */
    @GetMapping(value = "/skuId/{skuId}")
    public R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId) {

        SpuInfoEntity spuInfoEntity = spuInfoService.getSpuInfoBySkuId(skuId);

        return R.ok().setData(spuInfoEntity);
    }

    //商品上架
    ///product/spuinfo/{spuId}/up
    @PostMapping(value = "/{spuId}/up")
    public R spuUp(@PathVariable("spuId") Long spuId) {

        spuInfoService.up(spuId);

        return R.ok();
    }

    /**
     * 列表
     */
    //product/spuinfo/list?t=1697072166138&status=0&key=1111&brandId=19&catelogId=225&page=1&limit=10
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody SpuSaveVo spuSaveVo){
        spuInfoService.saveSupInfo(spuSaveVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
