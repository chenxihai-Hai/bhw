<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 20px 25px;">
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 23px 0 0;">
                <el-form-item>
                    <el-button type="primary"  @click="excelBtn">导出excel</el-button>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-table class="allTable" style="width: 100%;" :data="orderList">
                        <el-table-column  label="排序" type="index"
                              align="center">
                        </el-table-column>
                        <el-table-column  label="商家名称" prop="userName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="提现帐户" prop="bankCard"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="提现金额/元" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="手续费"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="可提现金额"  prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="提现发起时间" prop="createTime"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="操作人"  prop="operateName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="处理时间" prop="updateTime"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="操作"
                                          align="center">
                            <template slot-scope="scope" >
                                <template v-if=" scope.row.status==1">
                                    <span style="color: #CA0000;cursor: pointer;" @click="closeSt(scope.row)">确认提现</span>
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
    import {qryUserWithdrawList,upUserWithdraw} from "../../../api/financingOrder/orderList";
    import {saveBackOperate} from "../../../api/admin/index";
    export default {
        name: "withdrawList",
        data() {
            return {
                statuss:[],
                formInline:{
                    orderNum:null,
                    pageSize:5,
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
                qryUserWithdrawList({pageSize:1000000}).then(response=>{
                    var allData = response.data;
                    require.ensure([], () => {
                        const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                        const tHeader = [
                            "商家名称",
                            "提现账户",
                            "提现金额",
                            "可提现金额",
                            "提现发起时间",
                            "操作人",
                            "处理时间"
                        ];
                        const filterVal = [
                            "userName",
                            "bankCard",
                            "totalPrice",
                            "totalPrice",
                            "createTime",
                            "updateTime",
                            "operateName",
                            "updateTime"
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
                qryUserWithdrawList(this.formInline).then(response=>{
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryOrderList();
            },
            closeSt(obj){
                this.$confirm('是否确认 此款项转入“'+obj.userName+'”账户?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    upUserWithdraw({id:obj.id,status:2,operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId}).then(response=>{
                        this.$message.success("处理成功");
                        this.backQryOrderList();
                    });
                    var par = {
                        operateName:this.$store.getters.userName,
                        operateId:this.$store.getters.backUserId,
                        content:'将'+obj.userName+'的提现申请确认提现了'
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