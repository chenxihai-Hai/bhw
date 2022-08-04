<template>
    <div class="productList">
        <div class="productMenu">
            <el-menu :default-active="productManageMenuActive" mode="horizontal" @select="productManageMenuSelect">
                <el-menu-item index="1">产品管理</el-menu-item>
                <el-menu-item index="2">需求管理</el-menu-item>
                <el-menu-item index="3">谁联系了我</el-menu-item>
            </el-menu>
        </div>
        <template v-if="productManageMenuActive=='1'">
            <el-row style="padding: 0 20px;">
                <el-row >
                    <el-input placeholder="请输入产品名称" v-model="queryParam.projectName" style="width: 300px;margin-right: 14px;"></el-input>
                    <el-button @click="qryProjectList">产品搜索</el-button>
                </el-row>
                <el-row style="margin-top: 41px;">
                    <el-col>
                        <el-row style="margin-bottom: 20px;">
                            <el-col>
                                <el-button @click="selectAll">批量标记</el-button>
                                <el-button @click="batchUp(3)">批量下架</el-button>
                            </el-col>
                        </el-row>
                        <el-table
                            :data="productList"
                            ref="multipleTable"
                            @selection-change="handleSelectionChange"
                            @cell-click="toDetail"
                            class="user-table">
                        <el-table-column
                                type="selection"
                                width="55">
                        </el-table-column>
                        <el-table-column
                                label="产品名称"
                                prop="projectName"
                                align="center" width="200" :show-overflow-tooltip="true">
                        </el-table-column>
                        <el-table-column
                                label="单价"
                                align="center" width="80">
                            <template slot-scope="scope" >
                                <template v-if="undefined == scope.row.unitPrice || ''==scope.row.unitPrice">
                                    面议
                                </template>
                                <template v-else>
                                    ￥{{scope.row.unitPrice}}
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="库存"
                                prop="itemNumber" align="center" width="80">
                        </el-table-column>
                        <el-table-column
                                label="销量"
                                prop="buyerName" align="center" width="80">
                        </el-table-column>
                        <el-table-column
                                label="发布状态"
                                prop="status" align="center" width="80">
                            <template slot-scope="scope">
                                <template  v-if="1 == scope.row.status">
                                    <span style="color: red">草稿</span>
                                </template>
                                <template  v-if="1 != scope.row.status">
                                    已发布
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="创建时间"
                                prop="createTime"
                                align="center" width="160">
                        </el-table-column>
                        <el-table-column
                                label="发布时间"
                                prop="updateTime" align="center" width="160">
                            <template slot-scope="scope">
                                <template  v-if="undefined == scope.row.updateTime">
                                    {{scope.row.createTime}}
                                </template>
                                <template v-else>
                                    {{scope.row.updateTime}}
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" align="center">
                            <template slot-scope="scope">
                                <el-link type="danger" @click="delProduct(scope.row)">删除</el-link>
                                <br/>
                                <template  v-if="1 == scope.row.status">
                                     <el-link type="primary" @click="upProduct(scope.row)">编辑</el-link>
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                        <el-row style="text-align: center;margin-top: 30px;">
                            <el-col>
                                <el-pagination
                                        background
                                        layout="prev, pager, next"
                                        :page-size="queryParam.pageSize"
                                        :current-page="queryParam.curPage"
                                        @current-change="currentChange"
                                        :total="pageTotal" class="pageBox">
                                </el-pagination>
                            </el-col>
                        </el-row>
                     </el-col>
                 </el-row>
                <el-row style="margin-top: 50px;">
                    <el-col>全部下架商品</el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-row style="margin: 20px 0;">
                            <el-col>
                                <el-button @click="selectUnAll">批量标记</el-button>
                                <el-button @click="batchUp(2)">批量上架</el-button>
                            </el-col>
                        </el-row>
                        <el-table
                                :data="unshelveList"
                                ref="multipleTableUn"
                                @selection-change="handleSelectionUnChange"
                                class="user-table">
                            <el-table-column
                                    type="selection"
                                    width="55">
                            </el-table-column>
                            <el-table-column
                                    label="产品名称"
                                    prop="projectName"
                                    align="center" width="200"  :show-overflow-tooltip="true">
                            </el-table-column>
                            <el-table-column
                                    label="单价"
                                    align="center" width="80">
                                <template slot-scope="scope" >
                                    <template v-if="undefined == scope.row.unitPrice">
                                        面议
                                    </template>
                                    <template v-else>
                                        ￥{{scope.row.unitPrice}}
                                    </template>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="库存"
                                    prop="itemNumber" align="center" width="80">
                            </el-table-column>
                            <el-table-column
                                    label="销量"
                                    prop="buyerName" align="center" width="80">
                            </el-table-column>
                            <el-table-column
                                    label="创建时间"
                                    prop="createTime"
                                    align="center" width="160">
                            </el-table-column>
                            <el-table-column
                                    label="发布时间"
                                    prop="updateTime" align="center" width="160">
                                <template slot-scope="scope">
                                    <template  v-if="undefined == scope.row.updateTime">
                                        {{scope.row.createTime}}
                                    </template>
                                    <template v-else>
                                        {{scope.row.updateTime}}
                                    </template>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" align="center">
                                <template slot-scope="scope">
                                    <el-link type="danger" @click="delProduct(scope.row)">删除</el-link>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-row style="text-align: center;margin: 30px auto 50px;">
                            <el-col>
                                <el-pagination
                                        background
                                        layout="prev, pager, next"
                                        :page-size="unshelveParam.pageSize"
                                        :current-page="unshelveParam.curPage"
                                        @current-change="currentChange"
                                        :total="unshelvePageTotal" class="pageBox">
                                </el-pagination>
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-row>
        </template>
        <template v-else-if="productManageMenuActive=='2'">
            <div style="padding-left: 18px;">
                <el-form ref="form" :model="seachInfo" :inline="true" class="produceSeach">
                    <el-form-item label="需求标题：">
                        <el-input placeholder="选填" v-model="seachInfo.projectName"></el-input>
                    </el-form-item>
                    <el-form-item label="计价方式：">
                        <el-radio v-model="seachInfo.priceType" label="1">面议</el-radio>
                        <el-radio v-model="seachInfo.priceType" label="2">预算</el-radio>
                    </el-form-item>
                    <el-form-item label="需求状态：">
                        <el-select v-model="seachInfo.status" placeholder="请选择 ">
                            <el-option
                                    v-for="sItem in statusList"
                                    :key="sItem.id"
                                    :label="sItem.name"
                                    :value="sItem.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="预算：">
                        <el-input placeholder="选填" v-model="seachInfo.priceFilter" style="width: 97px;"></el-input>
                        -
                        <el-input placeholder="选填" v-model="seachInfo.priceFilterEnd" style="width: 97px;"></el-input>
                    </el-form-item>
                    <el-form-item label="需求类型：">
                        <el-select v-model="seachInfo.industryId" placeholder="请选择 ">
                            <el-option
                                    v-for="rItem in requireTypeList"
                                    :key="rItem.id"
                                    :label="rItem.name"
                                    :value="rItem.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <div style="text-align: center;margin-top: 40px;">
                <el-button @click="getServerListPage" class="btnColorOrg btnSch" >搜索</el-button>
                <el-button class="btnColorBlue btnSch" @click="btnCancel" style="margin-left: 31px;">重置</el-button>
            </div>
            <div class="showOrHide" style="margin: 29px auto 35px"><span @click="openOrHide" :class="toggle==false?'hideS':''" style="position: relative;">{{toggle==true?'收起':'展开'}}</span></div>
            <div v-if="toggle" style="padding: 0 20px;">
                <div style="margin-bottom: 40px;">
                    <el-checkbox class="check" v-model="allCk" @change="allCheck(1)"></el-checkbox>
                    <el-button class="btnNoBgd btnMore" @click="allCheck" style="margin-left: 20px;">批量标记</el-button>
                    <el-button class="btnNoBgd btnMore" @click="xiajia">批量下架</el-button>
                    <!--<span style="margin-left: 20px;margin-right:300px;">已读（0）</span>-->
                </div>
                <template v-for="temp in requireList">
                    <div class="gongxu" :key="temp.id" style="margin-top: 30px;">
                        <el-row>
                            <el-col :span="12">
                                <el-checkbox class="check" v-model="temp.checkboxFlag"></el-checkbox>
                                <span>选择</span>
                            </el-col>
                            <el-col :span="12" style="text-align: right;"></el-col>
                        </el-row>
                        <el-row type="flex" justify="space-between" align="middle" class="tInfo" style="margin: 31px 0 38px;">
                            <el-col :span="12">
                                <span class="title">{{temp.projectName}}</span>
                            </el-col>
                            <el-col :span="12">
                                <template v-if="temp.status==1"><span class="jiejue" style="color: red;">草稿</span></template>
                                <template v-if="temp.status==2"><span class="jiejue">待解决</span></template>
                                <template v-if="temp.status==4"><span class="jiejue">已承接</span></template>
                                <template v-if="temp.status==5"><span class="jiejue">已解决</span></template>
                            </el-col>
                        </el-row>
                        <el-row class="tInfo mark">
                            <el-col>
                                <span>行业类别|<em>{{temp.industryName}}</em></span>
                            </el-col>
                            <el-col style="margin-top: 10px;">
                                <span>预算：</span><em style="color: #ca0000;">{{undefined==temp.unitPrice || ''==temp.unitPrice?'面议':temp.unitPrice+'元'}}</em>
                            </el-col>
                            <el-col>
                                <el-divider></el-divider>
                            </el-col>
                            <el-col class="jianjie">
                                <span>简介：</span><span v-html="temp.details"></span>
                            </el-col>
                        </el-row>
                        <el-row type="flex" align="middle" class="tInfo" style="margin: 61px 0 0;">
                            <el-col :span="8">
                                <span style="color: #024ECC;">需求类型 | 知识产权 </span>
                            </el-col>
                            <el-col :span="8" class="area-col">
                                <img src="../../../images/property/dizhi.png" class="dingwei">
                                <span>定位：{{temp.provinceName}}&nbsp;{{temp.cityName}}</span>
                            </el-col>
                            <el-col :span="8" v-if="temp.status!=1">
                                <el-button class="btnColorOrg" @click="upTechologyInfo(temp.id,4)">
                                    已承接
                                </el-button>
                                <el-button class="btnColorBlue" @click="upTechologyInfo(temp.id,5)">
                                    已解决
                                </el-button>
                            </el-col>
                            <el-col v-else>
                                <el-button class="btnColorOrg" @click="upProduct(temp)">
                                    编辑
                                </el-button>
                                <el-button class="btnColorBlue" @click="delProduct(temp)">
                                    删除
                                </el-button>
                            </el-col>
                            <el-col :span="8" style="text-align: right;">
                                <el-button class="btnColorOrg  btnAskMe" @click="productManageMenuActive='3'">
                                    谁联系了我
                                </el-button>
                            </el-col>
                        </el-row>
                    </div>
                </template>
                <el-row style="text-align: center;margin-top: 50px;">
                    <el-col>
                        <el-pagination
                                background
                                layout="prev, pager, next"
                                :page-size="seachInfo.pageSize"
                                :current-page="seachInfo.curPage"
                                @current-change="currentRequireChange"
                                :total="pageRequireTotal" class="pageBox">
                        </el-pagination>
                    </el-col>
                </el-row>
            </div>
        </template>
        <template v-else>
            <relation :serverType="serverType"></relation>
        </template>
    </div>
</template>

<script>
    import relation from "../../../views/user/property/relation";
    import {saveOperateRecord} from "../../../api/intellectual/operateRecord";
    import {qryProjectList,qryIntellectualType} from "../../../api/projectDw/projectDw";
    import {batchUpStatus,delServerProject} from "../../../api/intellectual/intellectual";
    export default {
        name: "productList",
        components:{"relation":relation},
        data() {
            return {
                serverType:1,
                productList:[],
                unshelveList:[],
                productSelect:[],
                unshelveSelect:[],
                queryParam:{
                    pageSize:6,
                    curPage:1,
                    status:null,
                    projectName:null,
                    userId:1,
                    needType:1
                },
                unshelveParam:{
                    pageSize:6,
                    curPage:1,
                    userId:1,
                    status:3,
                    needType:1
                },
                pageTotal:0,
                unshelvePageTotal:0,
                productManageMenuActive:'1',
                pageRequireTotal:0,
                toggle: true,
                seachInfo:{projectName:null,priceType:null,status:null,produceNo:'',
                    priceFilter:null,priceFilterEnd:null,industryId:null,pageSize:10,curPage:1,
                    userId:this.$store.getters.userInfo.id,needType:2
                },
                statusList:[{id:null,name:'请选择'},{id:1,name:'待解决'},{id:2,name:'已承接'},{id:3,name:'已解决'}],
                requireTypeList:[],
                requireList:[],
                allCk:false
            }
        },
        created(){
            this.unshelveParam.userId = this.$store.getters.userInfo.id;
            this.queryParam.userId = this.$store.getters.userInfo.id;
            this.qryProjectList();
            this.qryUnshelveProjectList();
            this.qryIntellectualType();
        },
        methods:{
            qryIntellectualType(){
                qryIntellectualType({"intellectualType":1}).then(response => {
                    this.requireTypeList = response.data;
                });
            },
            toDetail(row, column, cell, event){
                if(column.property=='projectName'){
                    if(row.needType==2){
                        this.$router.push({path:"/kjsc",query:{iconToggleFlag:1}});
                    }else{
                        this.$router.push({path:"/kjsc/detail",query:{projectObj:row}});
                    }
                }
            },
            qryProjectList(){
                qryProjectList(this.queryParam).then(response=>{
                    this.productList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            qryUnshelveProjectList(){
                qryProjectList(this.unshelveParam).then(response=>{
                    this.unshelveList = response.data;
                    this.unshelvePageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.queryParam.curPage = val;
                this.qryProjectList();
            },
            selectUnAll(){
                this.unshelveList.forEach(row => {
                    this.$refs.multipleTableUn.toggleRowSelection(row);
                });
            },
            selectAll(){
                this.productList.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            },
            handleSelectionChange(val){
                this.productSelect = val;
            },
            handleSelectionUnChange(val){
                this.unshelveSelect = val;
            },
            upProduct(obj){
                this.$emit("upInfoAc-event",obj);
            },
            delProduct(obj){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delServerProject({id:obj.id}).then(response=>{
                        if(response.retCode==-1){
                            this.$message.warning("该产品"+obj.projectName+"还有未完成订单,不可删除!");
                            return;
                        }
                        saveOperateRecord({userId:obj.userId,title:obj.projectName,
                            content:obj.projectType==1?"商标":"专利",type:5}).then(resp=>{});
                        this.qryProjectList();
                        this.qryUnshelveProjectList();
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    });
                }).catch(() => {
                });
            },
            batchUp(st){
                var pIds = '';
                if(st==3){
                    this.productSelect.forEach(p => {
                        pIds=pIds+p.id+","
                    })
                }else{
                    this.unshelveSelect.forEach(p => {
                        pIds=pIds+p.id+","
                    })
                }
                this.$confirm('此操作将'+(st==3?'下架':'上架')+'选择信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    batchUpStatus({"pIds":pIds,status:st}).then(response=>{
                        if(response.retCode==-1){
                            var pn = '';
                            this.productList.forEach(item=>{
                                if(item.id == response.message){
                                    pn = item.projectName;
                                    this.$message.warning("该产品"+pn+"还有未完成订单,不可下架!");
                                    return;
                                }
                            })
                            return;
                        }
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        saveOperateRecord({userId:this.$store.getters.userInfo.id,title:st==3?'批量下架':'批量上架',
                            content:st==3?'批量下架':'批量上架',type:st}).then(resp=>{});
                        this.qryProjectList();
                        this.qryUnshelveProjectList();
                    });
                }).catch(() => {
                });
            },
            //需求管理
            currentRequireChange(val){
                this.seachInfo.curPage = val;
                this.getServerListPage();
            },
            btnCancel(){
                this.seachInfo={projectName:null,priceType:1,status:null,produceNo:'',
                    priceFilter:null,priceFilterEnd:null,industryId:null,pageSize:10,curPage:1,
                    userId:this.$store.getters.userInfo.id,needType:2
                };
                this.getServerListPage();
            },
            xiajia() {
                this.idList = [];
                this.requireList.forEach(row => {
                    if(row.checkboxFlag){
                        this.idList.push(row.id);
                    }
                });
                if(this.idList.length<1){
                    this.$message.warning("请勾选后再操作!");
                    return;
                }
                batchUpStatus({pIds: this.idList.join(','),status:3}).then(response => {
                    this.$message({
                        type: 'success',
                        message: '操作成功!'
                    });
                })
            },
            allCheck(flag){
                if(flag!=1){
                    this.allCk = !this.allCk;
                }
                this.requireList.forEach(row => {
                    row.checkboxFlag = !row.checkboxFlag;
                });
            },
            upTechologyInfo(id,st){
                batchUpStatus({pIds:id,status:st}).then(response=>{
                    this.$message({
                        type: 'success',
                        message: '操作成功!'
                    });
                    this.getServerListPage();
                });
            },
            openOrHide(){
                this.toggle = !this.toggle;
            },
            productManageMenuSelect(index,indexPath){
                this.productManageMenuActive = index;
                if(this.productManageMenuActive=='1'){
                    this.qryProjectList();
                    this.qryUnshelveProjectList();
                }else if(this.productManageMenuActive=='2'){
                    this.getServerListPage();
                }
            },
            getServerListPage(){
                qryProjectList(this.seachInfo).then(response=>{
                    this.requireList = response.data;
                    this.pageRequireTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    .btnColorBlue.btnSch,.btnColorOrg.btnSch{width: 60px;height: 35px;padding: 0;border: none;}
    .productMenu{width: 380px;margin: 0 auto 60px;}
    .productMenu /deep/ .el-menu.el-menu--horizontal{border-bottom: none;}
    .productMenu /deep/ .el-menu-item{font-size: 16px;height: 40px;line-height: 40px;padding: 0 10px;margin: 0 15px;}
    .productMenu /deep/ .el-menu-item.is-active{color: #1D1D1D;}
    .productMenu /deep/ .el-menu--horizontal > .el-menu-item.is-active{border-bottom: 2px solid #ca0000;color: #1D1D1D;}
    .produceSeach /deep/ .el-form-item{width: 31%;margin-bottom: 18px;}
</style>