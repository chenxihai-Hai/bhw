<template>
    <div class="shoppingCart" style="padding: 22px 18px 100px;">
        <el-row v-if="shoppingCartList.length>0">
            <el-table
                    :data="[1]"
                    style="width: 100%;border: 1px solid #EEE;border-bottom: none;"
                    :cell-style="rowStyle"
                    class="head-tab">
                <el-table-column
                        label="商品"
                        width="400">
                    <template slot="header" >
                        <el-row>
                            <el-col :span="10">
                                <el-checkbox class="allCheck" @change="handleSelectionChange">全选</el-checkbox>
                            </el-col>
                            <el-col :span="2">
                                商品
                            </el-col>
                        </el-row>
                    </template>
                </el-table-column>
                <el-table-column
                        label="单价"
                        width="120">
                </el-table-column>
                <el-table-column
                        label="数量" >
                </el-table-column>
                <el-table-column
                        label="金额" >
                </el-table-column>
                <el-table-column
                        label="操作" >
                </el-table-column>
            </el-table>
            <template v-for="sc in shoppingCartList">
                <el-table
                        :data="[sc,sc]"
                        :key="sc.id"
                        tooltip-effect="dark"
                        style="width: 100%;border: 1px solid #EEE;border-top: none;"
                        :span-method="arraySpanMethod"
                        :header-cell-style="headerRowStyle" class="chat-table">
                    <el-table-column
                            type="selection"
                            width="50">
                        <template slot-scope="scope">
                            <template v-if="scope.$index==0">
                                <el-row style="text-align: left;">
                                    <el-col :span="10">卖家名称:{{scope.row.sellerName}}</el-col>
                                </el-row>
                            </template>
                            <template v-else>
                                <el-checkbox class="allCheck" v-model="scope.row.checked" @change="checked=>checkboxChange(checked,scope.row)"></el-checkbox>
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column
                            width="350">
                        <template slot-scope="scope">
                            <template v-if="scope.$index==0">
                                <el-row style="text-align: left;">
                                    <el-col :span="10">卖家名称:{{scope.row.sellerName}}</el-col>
                                </el-row>
                            </template>
                            <template v-else>
                                <el-row>
                                    <el-col :span="10">
                                        <el-image
                                                :src="scope.row.itemLogo"
                                                fit="fit"></el-image>
                                    </el-col>
                                    <el-col :span="14" :push="2">
                                        商品名称:{{scope.row.itemName}}
                                    </el-col>
                                    <el-col :span="14" :push="2" style="margin-top: 10px;">
                                        商品规格:{{scope.row.itemName}}
                                    </el-col>
                                </el-row>
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="unitPrice"
                            width="120">
                        <template slot-scope="scope">
                            <span class="unitPrice-span"><em>￥{{scope.row.unitPrice}}</em></span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="itemNumber"
                             >
                    </el-table-column>
                    <el-table-column prop="totalPrice">
                        <template slot-scope="scope">
                            <span class="unitPrice-span"><em>￥{{scope.row.totalPrice*scope.row.itemNumber}}</em></span>
                        </template>
                    </el-table-column>
                    <el-table-column>
                        <template slot-scope="scope">
                            <el-row>
                                <el-col :span="6">
                                    <el-link type="danger" @click="delInfo(scope.row.id)" :underline="false">删除</el-link>
                                </el-col>
                                <el-col :span="12">
                                    <el-link :underline="false" @click="saveRecord(2,scope.row)">移到收藏</el-link>
                                </el-col>
                            </el-row>
                        </template>

                    </el-table-column>
                </el-table>
            </template>
            <el-row style="margin-top: 50px;" type="flex" justify="end" align="middle" class="row-total">
                <el-col :span="3">
                    已选 <em>{{selectCount}}</em> 件商品
                </el-col>
                <el-col :span="5">
                    合计(不含手续费): <em>￥{{price}}</em>
                </el-col>
                <el-col :span="2">
                    <el-button type="warning" class="btnColorOrg" @click="payOrder">结算</el-button>
                </el-col>
                <el-col :span="2">
                    <el-link :underline="false" @click="toKjsc">继续购物</el-link>
                </el-col>
                <el-col :span="3">
                    <el-link :underline="false">清理下架商品</el-link>
                </el-col>
                <el-col :span="3">
                    <el-link :underline="false" @click="delSelect">删除勾选商品</el-link>
                </el-col>
            </el-row>
        </el-row>
        <el-row type="flex" align="middle" v-else style="margin-top: 90px;">
            <el-col style="text-align: right;"><img src="../../../images/user/img-noData.png" alt=""></el-col>
            <el-col style="text-align: left;padding-left: 28px;"><span class="nodata">您还没有向我买产品哦~</span></el-col>
        </el-row>
    </div>
</template>

<script>
    import {qryUserShoppingCartList,delUserShopping} from "../../../api/user/shoppingCart";
    import {saveRecord} from "../../../api/user/serverOperate";
    export default {
        name: "shoppingCart",
        data() {
            return {
                selectCount:0,
                shoppingCartList:[],
                price:0
            }
        },
        created(){
            this.qryUserShoppingCartList();
        },
        methods:{
            toKjsc(){
                this.$router.push({path:"/kjsc",query:{iconToggleFlag:1}});
            },
            delSelect(){
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    var arr = [];
                    for(var i=0;i<this.shoppingCartList.length;i++){
                        if(this.shoppingCartList[i].checked){
                            arr.push(this.shoppingCartList[i].id);
                        }
                    }
                    this.shoppingCartList = this.shoppingCartList.filter(t => t.checked!=true);
                    delUserShopping({ids:arr.join(',')}).then(response=>{
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.qryUserShoppingCartList();
                    })
                }).catch(() => {
                });

            },
            saveRecord(operateType,projectDetail){
                var par ={
                    userId:this.$store.state.user.userInfo.id,
                    operateType:operateType,
                    serverId:projectDetail.projectId,
                    serverName:projectDetail.itemName,
                    img:projectDetail.itemLogo,
                    price:projectDetail.unitPrice,
                    type:'',
                    path:'/kjsc/detail'
                }
                saveRecord(par).then(response=>{
                    if(operateType==2){
                        this.$message({
                            message: '收藏成功',
                            type: 'success'
                        });
                    }
                });
            },
            delInfo(id){
                this.$confirm('确认删除该信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delUserShopping({ids:id}).then(response=>{
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.qryUserShoppingCartList();
                    })
                }).catch(() => {
                });
            },
            qryUserShoppingCartList(){
                qryUserShoppingCartList({userId:this.$store.state.user.userInfo.id}).then(response=>{
                    this.shoppingCartList = response.data;
                });
            },
            payOrder(){
                var arr = [];
                for(var i=0;i<this.shoppingCartList.length;i++){
                    if(this.shoppingCartList[i].checked){
                        arr.push(this.shoppingCartList[i]);
                    }
                }
                this.$router.push({path:"/orderDetail",query:{orderProjectList:arr}});
            },
            rowStyle({row,rowIndex}){
                return "display:none;";
            },
            headerRowStyle({row,rowIndex}){
                if(rowIndex==0){
                    return "display:none;";
                }
            },
            arraySpanMethod({row, column, rowIndex, columnIndex}) {
                if (rowIndex === 0) {
                    if (columnIndex === 0) {
                        return [1,7];
                    }else if (columnIndex >0) {
                        return [0, 0]
                    }
                }
            },
            handleSelectionChange(val){
                if(val){
                    for(var i=0;i<this.shoppingCartList.length;i++){
                        this.shoppingCartList[i].checked = true;
                        this.price = this.price+parseFloat(this.shoppingCartList[i].totalPrice);
                        this.selectCount++;
                    }
                }else{
                    this.shoppingCartList.forEach(function (item,index) {
                        item.checked = false;
                    })
                    this.selectCount = 0;
                    this.price = 0;
                }
            },
            checkboxChange(val,row){
                if(val){
                    this.selectCount++;
                    this.price = this.price+parseFloat(row.totalPrice);
                }else{
                    this.selectCount--;
                    this.price = this.price-parseFloat(row.totalPrice);
                }
            }
        }
    }
</script>

<style scoped>
    .head-tab /deep/ .el-table__header tr,/deep/ .head-tab.el-table th{background: rgba(2,78,204,0.05);}
    .chat-table /deep/ .el-table__body tr:first-child{background: #F8FFD0;color: #1D1D1D;}
    .chat-table em,.row-total em{color: #ca0000;font-style: normal;}
</style>