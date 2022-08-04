<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>交易数据检索</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="">
                    <el-checkbox-group v-model="statuss" :max="1">
                        <el-checkbox  label="">全部订单</el-checkbox>
                        <el-checkbox  label="1">进行中订单</el-checkbox>
                        <el-checkbox  label="2">已完成订单</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="formInline.orderNum" placeholder="请输入订单号/订单流水号/关键词">
                        <el-button class="btnColorOrg btnQueryList" slot="append" @click="backQryOrderList">查询</el-button>
                    </el-input>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-table :data="orderList" class="allTable">
                        <el-table-column  label="ID" type="index"
                              align="center">
                        </el-table-column>
                        <el-table-column  label="订单生成时间" prop="createTime"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="订单号" prop="orderId"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="产品" prop="itemName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="流水号" prop="orderNum"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="单价/元/面议" prop="unitPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="数量/件" prop="itemNumber"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="订单金额/元" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="订单状态"
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
                        <el-table-column  label="退货退款"
                                          align="center">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.refundStatus==1">
                                    审批中
                                </template>
                                <template v-else-if="scope.row.refundStatus==2">
                                    退款成功
                                </template>
                                <template v-else-if="scope.row.refundStatus==3">
                                    退款失败
                                </template>
                                <template v-else>
                                    暂无
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column  label="卖家" prop="sellerName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="买家" prop="buyerName"
                                          align="center">
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row style="text-align: center;margin: 30px auto 50px;">
                <div class="pageWrap">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="formInline.pageSize"
                            :current-page="formInline.curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </div>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {backQryOrderList} from "../../../api/financingOrder/orderList";
    export default {
        name: "orderList",
        data() {
            return {
                statuss:[],
                formInline:{
                    orderNum:null,
                    pageSize:10,
                    curPage:1,
                    status:null
                },
                orderList:[],
                pageTotal:0
            }
        },
        created(){
            this.backQryOrderList();
        },
        methods:{
            backQryOrderList(){
                if(this.statuss.length>0){
                    this.formInline.status = this.statuss[0];
                    if(this.statuss[0]==''){
                        this.formInline.status=null;
                    }
                }
                backQryOrderList(this.formInline).then(response=>{
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryOrderList();
            }
        }
    }
</script>
