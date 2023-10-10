<template>
  <div>
    <el-switch
      v-model="draggable"
      active-text="开启拖拽"
      inactive-text="关闭拖拽">
    </el-switch>
    &nbsp;&nbsp;
    <el-button v-if="draggable" size="small" type="primary" @click="batchSave">批量保存</el-button>
    <el-button size="small" type="danger" @click="batchDelete">批量删除</el-button>
    <el-tree
      :style="{paddingTop:'10px'}"
      :draggable="draggable"
      :allow-drop="allowDrop"
      :data="treeData"
      :props="defaultProps"
      show-checkbox
      :expand-on-click-node="false"
      node-key="catId"
      :default-expanded-keys="expandedKeys"
      @node-expand="handleNodeExpand"
      @node-collapse="handleNodeCollapse"
      @node-drop="handleDrop"
      ref="categoryTree">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          &nbsp;
          <el-button
            v-if="node.level<=2"
            type="text"
            size="mini"
            @click="() => append(data)">
            <i class="el-icon-circle-plus-outline"></i>
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => edit(data)">
            <i class="el-icon-edit"></i>
          </el-button>
          <el-button
            v-if="node.childNodes.length==0"
            type="text"
            size="mini"
            @click="() => remove(node, data)">
            <i class="el-icon-delete"></i>
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog
      :title="dialogType=='add'?'添加':'修改'"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="30%">
      <el-form ref="form" :model="category" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="category.name"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="category.icon"></el-input>
        </el-form-item>
        <el-form-item label="商品单位">
          <el-input v-model="category.productUnit"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button v-if="dialogType=='add'" type="primary" @click=addCategory>保 存</el-button>
        <el-button v-if="dialogType=='edit'" type="primary" @click=updateCategory>保 存</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
  export default {
    data() {
      return {
        draggable:false,
        updateNodes:[],
        maxLevel:0,
        dialogType:"",
        category:{
          catId:0,
          name:'',
          showStatus:0,
          icon:'',
          productUnit:''
        },
        dialogVisible:false,
        treeData: [],
        expandedKeys:[],
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      };
    },
    methods: {
      batchSave(){
        //提交更新
        this.$http({
          url: this.$http.adornUrl('/product/category/updateSort'),
          method: 'post',
          data: this.$http.adornData(this.updateNodes, false)
        }).then(({data}) => {
          if(data.msg="success"){
            this.$message.success("修改成功");
            this.getCategories();
            //重置拖拽相关的计算
            this.updateNodes=[],
            this.maxLevel=0
          }else{
            this.$message.error("修改失败，code=",data.code);
          }
        })
      },
      batchDelete(){
        let checkedNodes = this.$refs.categoryTree.getCheckedNodes();
        let ids = checkedNodes.map(data => data.catId)
        console.log("batchDelete",ids)
        this.$confirm(`确认批量删除？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/product/category/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if(data.msg="success"){
              this.$message.success("删除成功");
              // this.expandedKeys=[node.parent.data.catId];
              this.getCategories();
            }else{
              this.$message.error("删除失败，code=",data.code);
            }
          })
        }).catch(() => {

        });
      },
      handleDrop(draggingNode, dropNode, dropType, ev) {
        console.log('handleDrop: ',draggingNode, dropNode, dropType);
        //新父节点
        let parentCid = 0;
        //新兄弟节点
        let siblings = [];
        if (dropType == "before" || dropType == "after") {
          parentCid = dropNode.parent.data.catId == undefined ? 0 : dropNode.parent.data.catId;
          siblings = dropNode.parent.childNodes;
        } else if (dropType == "inner") {
          parentCid = dropNode.data.catId;
          siblings = dropNode.childNodes;
          //展开拖进了新元素的父节点
          this.expandedKeys.push(dropNode.data.catId);
        }
        //修改拖拽涉及的节点的顺序和父id
        for (let i = 0; i < siblings.length; i++) {
          if (siblings[i].data.catId == draggingNode.data.catId) {
            //主动拖动的节点
            //旧的层级
            let catLevel = draggingNode.level;
            //如果层级发生改变
            if (draggingNode.level != siblings[i].level) {
              catLevel = siblings[i].level;
              //递归更新子节点层级
              this.updateChildNodeLevel(siblings[i]);
            }
            //更新排序、层级、父id
            this.updateNodes.push({catId: siblings[i].data.catId, sort: i, catLevel: catLevel, parentCid: parentCid})
          } else {
            //被动改顺序的兄弟节点
            this.updateNodes.push({catId: siblings[i].data.catId, sort: i})
          }
        }
        console.log("updateNodes",this.updateNodes)

      },
      updateChildNodeLevel(node){
        if(node.childNodes.length>0){
          for (let i = 0; i < node.childNodes.length; i++) {
            let data = node.childNodes[i].data;
            //更新成实际层级
            this.updateNodes.push({catId: data.catId, catLevel: node.childNodes[i].level})
            this.updateChildNodeLevel(node.childNodes[i]);
          }
        }
      },

      allowDrop(draggingNode, dropNode, type) {
        // console.log(draggingNode,draggingNode)
        //draggingNode当前拖拽节点
        //dropNode目标位置
        //判断被拖动节点与目标节点总深度不大于3
        this.countNodeLevel(draggingNode)
        //当前节点和最大子节点层数相对层数
        let relativeLevel = this.maxLevel-draggingNode.data.catLevel+1;
        if (type=='inner') {
          return (dropNode.level+relativeLevel)<=3;
        } else {
          return (dropNode.parent.level+relativeLevel)<=3;
        }
      },

      countNodeLevel(node){
        //找出目标节点最深子节点level
        if(node.childNodes!=null&&node.childNodes.length>0){
          //遍历所有子节点，找到深度最大的子节点
          for (let i = 0; i < node.childNodes.length; i++) {
            if (node.childNodes[i].level > this.maxLevel) {
              this.maxLevel = node.childNodes[i].level;
            }
            this.countNodeLevel(node.childNodes[i])
          }
        }
      },

      handleNodeExpand(data,node){
        this.expandedKeys.push(data.catId);
      },
      handleNodeCollapse(data,node){
        var index = this.expandedKeys.findIndex((item) => item = data.catId);
        this.expandedKeys.splice(index,1)
      },
      getCategories() {
        this.$http({
          url: this.$http.adornUrl(`/product/category/list/tree`),
          method: 'get'
        }).then(({data}) => {
          console.log(data)
          this.treeData = data.data;
        })
      },

      addCategory() {
        this.dialogVisible=false;
        console.log("addCategory",this.category)
        this.$http({
          url: this.$http.adornUrl('/product/category/save'),
          method: 'post',
          data: this.$http.adornData(this.category, false)
        }).then(({data}) => {
          if(data.msg="success"){
            this.$message.success("保存成功");
            this.getCategories();
          }else{
            this.$message.error("保存失败，code=",data.code);
          }
        })
      },

      updateCategory() {
        this.dialogVisible=false;
        console.log("updateCategory",this.category)
        this.$http({
          url: this.$http.adornUrl('/product/category/update'),
          method: 'post',
          data: this.$http.adornData(this.category, false)
        }).then(({data}) => {
          if(data.msg="success"){
            this.$message.success("修改成功");
            this.getCategories();
          }else{
            this.$message.error("修改失败，code=",data.code);
          }
        })
      },

      edit(data) {
        this.dialogType="edit";
        this.dialogVisible=true;
        // this.expandedKeys=[data.catId];
        //查询获取category信息
        this.$http({
          url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
          method: 'get'
        }).then(({data}) => {
          let category = data.category;
          this.category = {
            catId:category.catId,
            name:category.name,
            showStatus:category.showStatus,
            icon:category.icon,
            productUnit:category.productUnit
          }
        })

      },

      append(data) {
        this.dialogType="add"
        //展开添加了新元素的父节点
        this.expandedKeys.push(data.catId);
        this.category={}
        this.dialogVisible=true;
        console.log("append", data)
        this.category={
          parentCid:data.catId,
          catLevel:data.catLevel*1+1,
          showStatus:1,
          sort:0
        }
      },

      remove(node, data) {
        console.log("node", node, data)
        let ids = [data.catId]
        this.$confirm(`确认删除【${data.name}】？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/product/category/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if(data.msg="success"){
              this.$message.success("删除成功");
              // this.expandedKeys=[node.parent.data.catId];
              this.getCategories();
            }else{
              this.$message.error("删除失败，code=",data.code);
            }
          })
        }).catch(() => {

        });
      },
    },
    created() {
      this.getCategories();
    }
  }
</script>

