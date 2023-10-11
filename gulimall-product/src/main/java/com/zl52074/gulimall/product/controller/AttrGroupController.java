package com.zl52074.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.zl52074.gulimall.product.entity.AttrEntity;
import com.zl52074.gulimall.product.service.AttrAttrgroupRelationService;
import com.zl52074.gulimall.product.service.AttrService;
import com.zl52074.gulimall.product.service.CategoryService;
import com.zl52074.gulimall.product.vo.AttrAttrGorupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zl52074.gulimall.product.entity.AttrGroupEntity;
import com.zl52074.gulimall.product.service.AttrGroupService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.R;



/**
 * 属性分组
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private AttrService attrService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    ///product/attrgroup/attr/relation
    @RequestMapping("/attr/relation")
    public R attrRelation(@RequestBody List<AttrAttrGorupRelationVo> relationVos){
        attrAttrgroupRelationService.saveBatchRelation(relationVos);
        return R.ok();
    }

    ///product/attrgroup/1/noattr/relation?page=1&limit=10&key=
    @RequestMapping("/{groupId}/noattr/relation")
    public R noRelationAttrList(@RequestParam Map<String, Object> params,@PathVariable("groupId") Long groupId){
        PageUtils page = attrService.getNoRelationAttr(params,groupId);
        return R.ok().put("page", page);
    }

    ///product/attrgroup/attr/relation/delete
    @RequestMapping("/attr/relation/delete")
    public R attrRelationDelete(@RequestBody List<AttrAttrGorupRelationVo> relationVos){
        attrAttrgroupRelationService.deleteRelation(relationVos);
        return R.ok();
    }

    ///product/attrgroup/{groupId}/attr/relation
    @RequestMapping("/{groupId}/attr/relation")
    public R attrRelation(@PathVariable("groupId") Long groupId){
        List<AttrEntity> list = attrService.getRelationAttr(groupId);
        return R.ok().put("data", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
        public R list(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Long catId){
        PageUtils page = attrGroupService.queryPage(params,catId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
        public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long[] catelogPath = categoryService.findCatelogPath(attrGroup.getCatelogId());
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
