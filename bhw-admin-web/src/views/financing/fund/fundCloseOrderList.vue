<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 20px 25px;">
            <el-form :inline="true" :model="formInline" class="demo-form-inline" >
               <!-- <el-form-item label="">
                    <el-checkbox-group v-model="statuss" :max="1">
                        <el-checkbox  label="">全部订单</el-checkbox>
                        <el-checkbox  label="1">进行中订单</el-checkbox>
                        <el-checkbox  label="2">已完成订单</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="formInline.orderNum" placeholder="请输入订单号/订单流水号/关键词">
                        <el-button type="primary" slot="append" @click="backQryOrderList">查询</el-button>
                    </el-input>
                </el-form-item>-->
                <el-form-item>
                    <el-button type="primary"  @click="excelBtn">导出excel</el-button>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-table class="allTable" style="width: 100%;" :data="orderList">
                        <el-table-column  label="ID" type="index"
                              align="center">
                        </el-table-column>
                        <el-table-column  label="卖家" prop="sellerName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="买家" prop="buyerName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="类型"
                                          align="center">
                            产品/服务交易
                        </el-table-column>
                        <el-table-column  label="订单金额/元" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="入账时间" prop="updateTime"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="支付方式"
                                          align="center">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.payType==1">
                                    微信
                                </template>
                                <template v-if="scope.row.payType==2">
                                    支付宝
                                </template>
                                <template v-if="scope.row.payType==3">
                                    银联
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column  label="合计/元" prop="totalPrice"
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
                        <el-table-column  label="操作"
                                          align="center">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.closeStatus==1 && scope.row.status==2">
                                    <span style="color: #CA0000;cursor: pointer;" @click="closeSt(scope.row)">款项处理</span>
                                </template>
                                <template v-else-if="scope.row.closeStatus==1 && scope.row.status==1">
                                    <span style="color: #024ECC;">款项待处理</span>
                                </template>
                                <template v-else>
                                    已处理
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-row style="text-align: center;margin: 30px auto 50px;">
                <el-col  class="pageWrap">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="formInline.pageSize"
                            :current-page="formInline.curPage"
                            @current-change="currentChange"
                            :total="pageTotal"  class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {backQryOrderList,upOrderCloseSt} from "../../../api/financingOrder/orderList";
    import {saveBackOperate} from "../../../api/admin/index";
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
            excelBtn(){
                backQryOrderList({pageSize:1000000}).then(response=>{
                    var allData = response.data;
                    allData.forEach(item=>{
                        item.type = '产品/服务交易';
                        if(item.payType==1){
                            item.payType = '微信';
                        }else if(item.payType==2){
                            item.payType = '支付宝';
                        }else{
                            item.payType = '银联';
                        }
                        if(item.status==1){
                            item.status = '进行中';
                        }else if(item.status==2){
                            item.status = '已完成';
                        }else{
                            item.status = '已关闭';
                        }
                    })
                    require.ensure([], () => {
                        const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                        const tHeader = [
                            "卖家",
                            "买家",
                            "类型",
                            "订单金额",
                            "入账时间",
                            "支付方式",
                            "合计",
                            "订单状态"
                        ];
                        const filterVal = [
                            "sellerName",
                            "buyerName",
                            "type",
                            "totalPrice",
                            "updateTime",
                            "payType",
                            "totalPrice",
                            "status"
                        ];
                        const list = allData;
                        const data = this.formatJson(filterVal, list);
                        export_json_to_excel(tHeader, data, "资金结算清单");
                    });
                });
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]));
            },
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
            },
            closeSt(obj){
                this.$confirm('本次操作结果为:该订单冻结金额即将解冻,商家即可提现是否确认?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    upOrderCloseSt({orderId:obj.orderId}).then(response=>{
                        this.$message.success("处理成功");
                        this.backQryOrderList();
                    });
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'将订单'+obj.orderId+'的款项确认转入了'+obj.sellerName+'账户'
                    };
                    saveBackOperate(par).then();
                }).catch(() => {
                });

            }
        }
    }
</script>

<style scoped>

</style>