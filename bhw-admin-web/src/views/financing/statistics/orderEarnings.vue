<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>订单收益统计</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin: 33px 40px;">
                <el-form-item label="日期选择">
                    <el-checkbox-group v-model="statuss" :max="1" @change="checkboxGroupEv">
                        <el-checkbox  label="0">今天</el-checkbox>
                        <el-checkbox  label="1">本周</el-checkbox>
                        <el-checkbox  label="2">本月</el-checkbox>
                        <el-checkbox  label="3">今年</el-checkbox>
                        <el-checkbox  label="4">全部</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  @click="excelBtn">导出excel</el-button>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-table :data="orderList" class="allTable">
                        <el-table-column
                                type="index"
                                width="50">
                        </el-table-column>
                        <el-table-column  label="订单号" prop="orderId"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="购买人" prop="buyerName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="订单金额/元" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="产品" prop="itemName"
                                          align="center">
                            <template slot-scope="scope">
                                <template v-if="scope.row.dealType==0">
                                    {{scope.row.itemName}}
                                </template>
                                <template v-else-if="scope.row.dealType==1">
                                    T币
                                </template>
                                <template v-else-if="scope.row.dealType==3">
                                    开通增值服务
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column  label="商家名称" prop="sellerName"
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
    import {backQryPayDealList} from "../../../api/financingOrder/orderList";
    export default {
        name: "orderEarnings",
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
                backQryPayDealList({pageSize:1000000}).then(response=>{
                    var allData = response.data;
                    allData.forEach(item=>{
                        if(item.dealType==1){
                            item.itemName = "T币";
                        }else if(item.dealType==2){
                            item.itemName = '开通增值服务';
                        }
                    })
                    require.ensure([], () => {
                        const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                        const tHeader = [
                            "订单号",
                            "购买人",
                            "订单金额",
                            "产品",
                            "商家名称"
                        ];
                        const filterVal = [
                            "orderId",
                            "buyerName",
                            "totalPrice",
                            "itemName",
                            "sellerName"
                        ];
                        const list = allData;
                        const data = this.formatJson(filterVal, list);
                        export_json_to_excel(tHeader, data, "订单收益统计");
                    });
                });
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]));
            },
            dateAddDays(dayCount,date) {
                var tempDate=date || new Date();//把日期字符串转换成日期格式
                var resultDate=new Date((tempDate/1000+(86400*dayCount))*1000);//增加n天后的日期
                return resultDate;
            },
            checkboxGroupEv(val){
                console.log(val);
                if(undefined == val || val.length<1){
                    this.formInline.createTime = null;
                    this.formInline.updateTime = null;
                }
                this.backQryOrderList();
            },
            backQryOrderList(){
                if(this.statuss.length>0){
                    if(this.statuss[0]=='0'){
                        this.formInline.createTime = this.dateAddDays(0).format('yyyy-MM-dd');
                        this.formInline.updateTime = this.dateAddDays(this.statuss[0]).format('yyyy-MM-dd');
                    }else if(this.statuss[0]=='1'){
                        var date = new Date();
                        var weekday = date.getDay()||7;
                        date.setDate(date.getDate()-weekday+1);
                        this.formInline.createTime = date.format('yyyy-MM-dd');
                        this.formInline.updateTime = this.dateAddDays(6,date).format('yyyy-MM-dd');
                    }else if(this.statuss[0]=='2'){
                        var d = this.dateAddDays(0).format('yyyy-MM-dd');
                        var dt = new Date();
                        this.formInline.createTime = d.substring(0,8)+'01';
                        this.formInline.updateTime =  d.substring(0,8)+new Date(dt.getFullYear(), dt.getMonth()+1, 0).getDate();
                    }else if(this.statuss[0]=='3'){
                        var d = this.dateAddDays(0).format('yyyy-MM-dd');
                        var dt = new Date();
                        this.formInline.createTime = d.substring(0,5)+'01-01';
                        this.formInline.updateTime =  d.substring(0,5)+'12-'+new Date(dt.getFullYear(), 12, 0).getDate();
                    }else{
                        this.formInline.createTime = null;
                        this.formInline.updateTime = null;
                    }
                }
                backQryPayDealList(this.formInline).then(response=>{
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

<style scoped>

</style>