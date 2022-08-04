<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>增值服务（会员）订单列表</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="会员名称">
                    <el-select  v-model="formInline.vipType">
                        <el-option value="2888" label="标准会员"></el-option>
                        <el-option value="8888" label="高级会员"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="formInline.orderId" placeholder="请输入订单号">
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="btnColorOrg btnAdvert" @click="backQryWalletOrderList">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  @click="excelBtn">导出excel</el-button>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-table :data="orderList"  class="allTable">
                        <el-table-column
                                type="index"
                                width="50">
                        </el-table-column>
                        <el-table-column  label="订单生成时间" prop="createTime"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="订单号" prop="orderId"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="用户名称" prop="buyerName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="流水号" prop="orderNum"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="应收金额" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="实收金额/元" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="支付方式"
                                          align="center">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.payType==1">
                                    微信支付
                                </template>
                                <template v-if="scope.row.payType==2">
                                    支付宝支付
                                </template>
                                <template v-if="scope.row.payType==3">
                                    银联
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row style="text-align: center;margin: 30px auto 50px;">
                <el-col class="pageWrap">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="formInline.pageSize"
                            :current-page="formInline.curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {backQryWalletOrderList} from "../../../api/financingOrder/serviceOrderList";
    export default {
        name: "serviceOrderList",
        data() {
            return {
                formInline:{
                    orderId:null,
                    pageSize:5,
                    curPage:1,
                    vipType:null,
                    dealType:3
                },
                orderList:[],
                pageTotal:0
            }
        },
        created(){
            this.backQryWalletOrderList();
        },
        methods:{
            excelBtn(){
                backQryWalletOrderList({pageSize:1000000, dealType:3}).then(response=>{
                    var allData = response.data;
                    allData.forEach(item=>{
                        if(item.payType==1){
                            item.payType = '微信';
                        }else if(item.payType==2){
                            item.payType = '支付宝';
                        }else{
                            item.payType = '银联';
                        }
                    })
                    require.ensure([], () => {
                        const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                        const tHeader = [
                            "订单生成时间",
                            "订单号",
                            "用户名称",
                            "流水号",
                            "应收金额",
                            "实收金额",
                            "支付方式"
                        ];
                        const filterVal = [
                            "createTime",
                            "orderId",
                            "buyerName",
                            "orderNum",
                            "totalPrice",
                            "totalPrice",
                            "payType"
                        ];
                        const list = allData;
                        const data = this.formatJson(filterVal, list);
                        export_json_to_excel(tHeader, data, "资金结算清单");
                    });
                });
            },
            backQryWalletOrderList(){
                backQryWalletOrderList(this.formInline).then(response=>{
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryWalletOrderList();
            }
        }
    }
</script>

<style scoped>

</style>