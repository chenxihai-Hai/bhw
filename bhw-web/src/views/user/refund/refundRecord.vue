<template>
    <div class="refundRecord" style="padding-bottom: 30px;">
        <el-row v-if="!showDet">
            <template v-for="ol in orderList">
                <el-table
                        :data="[ol]"
                        :key="ol.orderNum"
                        :header-cell-style="headerRowStyle"
                        style="width: 100%;margin-top: 15px;" class="user-table">
                    <el-table-column
                            type="selection"
                            width="55">
                    </el-table-column>
                    <el-table-column
                            label="产品"
                            align="center" width="300">
                        <template slot-scope="scope" >
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
                    </el-table-column>
                    <el-table-column
                            label="交易金额"
                            align="center">
                        <template slot-scope="scope" >
                            <em>￥{{scope.row.refundPrice}}</em>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="退款金额"
                            prop="totalPrice" align="center" width="200">
                        <template slot-scope="scope" >
                            总金额: <em>￥{{scope.row.refundPrice}}</em>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="申请时间"
                            prop="createTime" align="center">
                    </el-table-column>
                    <el-table-column
                            label="退款状态"
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
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-link :underline="false" @click="toRefundDet(scope.row)" type="primary">服务详情</el-link>
                        </template>

                    </el-table-column>
                </el-table>
            </template>
            <div v-if="orderList.length<1" style="text-align: center;">
                暂无数据
            </div>
            <el-row :gutter="20" style="margin-top: 20px;">
                <el-col :span="10" :offset="12">
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
        </el-row>
        <template v-if="showDet">
           <REFUNDDETAIL :refundDetInfo="refundDetInfo" v-on:backlist-event="backList" ></REFUNDDETAIL>
        </template>
    </div>
</template>

<script>
    import {qryRefundRecordList} from "../../../api/user/userOrder";
    import REFUNDDETAIL from "../../../views/user/refund/refundDetail";
    export default {
        name: "refundRecord",
        props:["userInfo"],
        components: {
            REFUNDDETAIL
        },
        data() {
            return {
                showDet:false,
                headerRowStyleFlag:true,
                refundDetInfo:null,
                orderList:[{
                    sellerName:'北部湾国际技术',
                    orderId: '12987122',
                    orderNum:'238920309823',
                    itemName: '好滋好味鸡蛋仔',
                    itemLog: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
                    unitPrice: '287487',
                    itemNumber: '2',
                    totalPrice: '2874892',
                    status: '1',
                    createTime:'2020-03-10'
                }],
                queryParam:{
                    pageSize:6,
                    curPage:1,
                    status:null,
                    orderVague:null,
                    refundUserId:this.userInfo.id
                },
                pageTotal:0
            }
        },
        created(){
            this.loadOrderData();
        },
        methods: {
            backList(){
                this.showDet = false;
            },
            toRefundDet(obj){
                this.showDet = true;
                this.refundDetInfo = obj;
            },
            headerRowStyle({row,rowIndex}){
              return "border-top:1px solid #CCCCCC";
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
                qryRefundRecordList(this.queryParam).then(response => {
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            }
        }
    }
</script>

<style scoped>

</style>