<template>
    <div  class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>资金明细检索</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin-top: 33px">
                <el-form-item label="时间">
                    <el-checkbox-group v-model="statuss" :max="1">
                        <el-checkbox  label="">所有</el-checkbox>
                        <el-checkbox  label="30">最近一个月</el-checkbox>
                        <el-checkbox  label="90">一个月前</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item>
                    <el-date-picker
                            format="yyyy-MM-dd"
                            v-model="Time"
                            type="daterange"
                            range-separator="-"
                            start-placeholder="请选择时间范围起始"
                            end-placeholder="请选择时间范围结束">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="流水号">
                    <el-input v-model="formInline.orderNum" placeholder="请输入订单流水号">
                    </el-input>
                </el-form-item>
                <el-form-item label="收入类型">
                    <el-select v-model="formInline.inType" placeholder="请选择" class="allSelect" style="width: 150px;">
                        <el-option v-for="(k,v) in outSelect" :key="v"
                                   :label="k"
                                   :value="v">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="支出类型">
                    <el-select v-model="formInline.outType" placeholder="请选择" class="allSelect" style="width: 150px;">
                        <el-option v-for="(k,v) in intSelect" :key="v"
                                   :label="k"
                                   :value="v">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  class="btnColorOrg btnQueryList" @click="backQryPayDealList">查询</el-button>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-table :data="orderList" class="allTable">
                        <el-table-column  type="index"
                                         width="50">
                        </el-table-column>
                        <el-table-column  label="收支时间" prop="updateTime"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="订单号" prop="orderId"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="流水号" prop="orderNum"
                                            align="center">
                        </el-table-column>
                        <el-table-column  label="应收金额/元"
                                          align="center">
                            <template slot-scope="scope">
                                <span style="color: #024ECC;">{{scope.row.totalPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column  label="实收金额/元"
                                          align="center">
                            <template slot-scope="scope">
                                <span style="color: #024ECC;">{{scope.row.totalPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column  label="收入类型"
                                          align="center">
                            <template slot-scope="scope">
                                <template v-if="scope.row.dealType==0">
                                    产品/服务交易
                                </template>
                                <template v-else-if="scope.row.dealType==1">
                                    T币
                                </template>
                                <template v-else-if="scope.row.dealType==3">
                                    开通增值服务
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column  label="应支出/元" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="实际支出/元" prop="totalPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="支出类型"
                                          align="center">
                            <template slot-scope="scope">
                                <template v-if="scope.row.dealType==0">
                                    产品/服务交易
                                </template>
                                <template v-else-if="scope.row.dealType==1">
                                    充值T币
                                </template>
                                <template v-else-if="scope.row.dealType==3">
                                    购买会员
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column  label="卖家"
                                          align="center">
                            <template slot-scope="scope">
                                <template v-if="scope.row.dealType==0">
                                    {{scope.row.sellerName}}
                                </template>
                                <template v-else>
                                    深圳市对接平台科技发展有限公司
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column  label="买家" prop="buyerName"
                                          align="center">
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
                            :total="pageTotal"  class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {backQryPayDealList} from "../../../api/financingOrder/orderList";
    export default {
        name: "fundDealList",
        data() {
            return {
                statuss:[],
                formInline:{
                    orderNum:null,
                    pageSize:10,
                    curPage:1,
                    status:null,
                    createTime:null,
                    updateTime:null,
                    outType:null,
                    inType:null
                },
                outSelect:{0:'产品/服务交易',1:'T币',2:'增值服务'},
                intSelect:{0:'产品/服务交易',1:'充值T币',2:'购买会员'},
                Time:[],
                orderList:[],
                pageTotal:0
            }
        },
        created(){
            this.backQryPayDealList();
        },
        methods:{
            dateAddDays(dayCount) {
                var tempDate=new Date();//把日期字符串转换成日期格式
                var resultDate=new Date((tempDate/1000+(86400*dayCount))*1000);//增加n天后的日期
                var resultDateStr=resultDate.getFullYear()+"-"+(resultDate.getMonth()+1)+"-"+(resultDate.getDate());//将日期转化为字符串格式
                return resultDateStr;
            },
            backQryPayDealList(){
                if(this.statuss.length>0){
                    this.formInline.createTime = this.dateAddDays(0);
                    this.formInline.updateTime = this.dateAddDays(this.statuss[0]);
                    if(this.statuss[0]==''){
                        this.formInline.createTime=null;
                        this.formInline.updateTime=null;
                    }
                }
                if(this.Time.length>1){
                    this.formInline.createTime=this.Time[0].format("yyyy-MM-dd");
                    this.formInline.updateTime=this.Time[1].format("yyyy-MM-dd");
                }
                backQryPayDealList(this.formInline).then(response=>{
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryPayDealList();
            }
        }
    }
</script>

<style scoped>

</style>