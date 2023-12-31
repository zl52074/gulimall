package com.zl52074.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zl52074.gulimall.common.valid.AddGroup;
import com.zl52074.gulimall.common.valid.UpdateGroup;
import com.zl52074.gulimall.common.valid.UpdateStatusGroup;
import com.zl52074.gulimall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zl52074.gulimall.product.entity.BrandEntity;
import com.zl52074.gulimall.product.service.BrandService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
        public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    @RequestMapping("/infos")
    public R infos(@RequestParam List<Long> ids){
        List<BrandEntity> brandList = brandService.listByIds(ids);
        List<BrandVo> collect = brandList.stream().map(brandEntity -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(brandEntity.getBrandId());
            brandVo.setBrandName(brandEntity.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("brand", collect);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@Validated(AddGroup.class) @RequestBody BrandEntity brand/*, BindingResult result*/){
/*        Map<String,String> map = new HashMap<>();
        if(result.hasErrors()){
            result.getFieldErrors().forEach(error->{
                map.put(error.getField(),error.getDefaultMessage());
            });
            return R.error(400,"数据不合法").put("data",map);
        }else{
            brandService.save(brand);
            return R.ok();
        }*/

        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@Validated(UpdateGroup.class)@RequestBody BrandEntity brand){
		brandService.updateCascade(brand);

        return R.ok();
    }

    @RequestMapping("/updateStatus")
    public R updateStatus(@Validated(UpdateStatusGroup.class)@RequestBody BrandEntity brand){
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
