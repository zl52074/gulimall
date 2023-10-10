<template>
  <div>
    <el-tree
      @node-click="nodeClick"
      :style="{paddingTop:'10px'}"
      :data="treeData"
      :props="defaultProps"
      :expand-on-click-node="false"
      node-key="catId"
      ref="categoryTree">
    </el-tree>
  </div>

</template>

<script>
  export default {
    name:"category",
    data() {
      return {
        treeData: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      };
    },
    methods: {
      nodeClick(data,node,component){
        this.$emit("tree-node-click",data,node,component)
      },
      getCategories() {
        this.$http({
          url: this.$http.adornUrl(`/product/category/list/tree`),
          method: 'get'
        }).then(({data}) => {
          console.log(data)
          this.treeData = data.data;
        })
      }
    },
    created() {
      this.getCategories();
    }
  }
</script>

