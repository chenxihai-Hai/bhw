<template>
    <div class="productOrder">
        <el-row style="padding: 0 20px;">
            <el-row >
                <el-input  placeholder="请输入订单号/订单流水号" v-model="queryParam.orderNum" style="width: 300px;margin-right: 14px;"></el-input>
                <el-button @click="loadOrderData">订单搜索</el-button>
            </el-row>
            <el-row>
                <el-col style="text-align: center;margin-top: 71px;">
                    <template v-for="ol in orderList"><!--:span-method="arraySpanMethod"-->
                        <el-table
                                :data="[ol]"
                                :key="ol.orderNum"
                                class="user-table">
                            <el-table-column
                                    label="商品"
                                    align="center" width="300">
                                <template slot-scope="scope" >
                                    <el-row>
                                        <el-col :span="10">
                                            <el-image
                                                    :src="scope.row.itemLogo"
                                                    fit="fit"></el-image>
                                        </el-col>
                                        <el-col :span="14" :push="2">
                                            商品名称:{{scope.row.itemName.substring(0,5)+"..."}}
                                        </el-col>
                                        <el-col :span="14" :push="2" style="margin-top: 10px;">
                                            商品规格:{{scope.row.itemName.substring(0,5)+"..."}}
                                        </el-col>
                                    </el-row>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="单价"
                                    align="center">
                                <template slot-scope="scope" >
                                    ￥{{scope.row.unitPrice}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="数量"
                                    prop="itemNumber" align="center">
                            </el-table-column>
                            <el-table-column
                                    label="买家"
                                    prop="buyerName" align="center">
                            </el-table-column>
                            <el-table-column
                                    label="订单状态"
                                align="center">
                                <template slot-scope="scope" >
                                    <template v-if="scope.row.status==1">
                                        进行中
                                    </template>
                                    <template v-if="scope.row.status==2">
                                        已完成
                                    </template>
                                    <template v-if="scope.row.status==3">
                                        关闭
                                    </template>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="实收款"
                                    prop="totalPrice" align="center" width="200">
                                <template slot-scope="scope" >
                                    ￥{{scope.row.totalPrice}}
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" align="center">
                                <template slot-scope="scope">
                                    <!--<el-link type="info"  v-if="undefined != scope.row.protocolUrl && null != scope.row.protocolUrl" @click="downLoadPt(scope.row.protocolUrl)">下载买家相关协议</el-link>？-->
                                    <a  v-if="undefined != scope.row.protocolUrl && null != scope.row.protocolUrl"
                                        :href="scope.row.protocolUrl" download="协议附件"
                                        style="text-decoration:none;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:#909399;">
                                        下载买家相关协议
                                    </a>
                                    <br/>
                                    <el-link type="info" :underline="false">评价</el-link>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>
                    <template v-if="orderList.length<1">
                        暂无数据
                    </template>
                    <el-row style="margin: 30px 0 50px;">
                        <el-col :span="10" style="text-align: center;float: inherit;margin: 0 auto;">
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
        </el-row>

    </div>
</template>

<script>
    import {qryProjectOrderList} from "../../../api/projectDw/projectDw";
    import {upOrderSt} from "../../../api/pay/pay";
    export default {
        name: "productOrder",
        data() {
            return {
                orderList:[],
                queryParam:{
                    pageSize:6,
                    curPage:1,
                    status:null,
                    orderNum:null,
                    userId:1
                },
                pageTotal:0
            }
        },
        created(){
            this.queryParam.userId = this.$store.getters.userInfo.id;
           this.loadOrderData();
        },
        methods: {
            comitSt(orderId){
                this.$confirm('确认该交易已正常完成?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    upOrderSt({orderId:orderId}).then(response=>{
                        this.$message({
                            message: '操作成功!',
                            type: 'success'
                        });
                        this.loadOrderData();
                    });
                });
            },
            arraySpanMethod({row, column, rowIndex, columnIndex}) {
                if (rowIndex  === 0) {
                    if (columnIndex === 0) {
                        return [1,7];
                    }else if (columnIndex >0) {
                        return [0, 0]
                    }
                }
            },
            tabsClick(tab){
                if(tab.name!='0'){
                    this.queryParam.status = tab.name;
                }else{
                    this.queryParam.status = null;
                }
            },
            currentChange(val){
                this.queryParam.curPage = val;
                this.loadOrderData();
            },
            loadOrderData(){
                qryProjectOrderList(this.queryParam).then(response => {
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>
    .el-button.el-button--default span {
        color: rgba(29,29,29,1);
    }
</style>