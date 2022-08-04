<template>
    <div class="listWarp" style="padding: 22px 16px 0;">
        <div class="bgColorF" style="padding: 0 25px;">
            <h3>退款数据检索</h3>
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
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
                <el-form-item>
                    <el-input v-model="formInline.orderNum" placeholder="请输入订单号/订单流水号/关键词">
                        <el-button class="btnColorOrg btnQueryList" slot="append" @click="backQryRefundRecordList">查询</el-button>
                    </el-input>
                </el-form-item>
            </el-form>
            <el-row>
                <el-col>
                    <el-table :data="orderList" class="allTable">
                        <el-table-column  label="ID" type="index"
                              align="center">
                        </el-table-column>
                        <el-table-column  label="退款时间" prop="updateTime"
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
                        <el-table-column  label="交易金额/元" prop="refundPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="退款金额/元" prop="refundPrice"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="申请时间" prop="createTime"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="卖家" prop="sellerName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="买家" prop="buyerName"
                                          align="center">
                        </el-table-column>
                        <el-table-column  label="退款状态"
                                          align="center">
                            <template slot-scope="scope" >
                                <template v-if="scope.row.status==1">
                                    <el-link :underline="false"  @click="toRefundDet(scope.row)"  style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(247,74,13,1);">
                                        退款中
                                    </el-link>
                                </template>
                                <template v-if="scope.row.status==2">
                                    <el-link :underline="false"  @click="toRefundDet(scope.row)"  style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(2,78,204,1);">
                                        退款成功
                                    </el-link>
                                </template>
                                <template v-if="scope.row.status==3">
                                    <el-link :underline="false"  @click="toRefundDet(scope.row)"  style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(202,0,0,1);">
                                        退款失败
                                    </el-link>
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
        <!--<template v-if="showDet">
            <REFUNDDETAIL :refundDetInfo="refundDetInfo" v-on:backlist-event="backList" ></REFUNDDETAIL>
        </template>-->
        <el-dialog
                :visible.sync="showDet"
                width="80%">
            <REFUNDDETAIL :refundDetInfo="refundDetInfo" v-on:backlist-event="backList" ></REFUNDDETAIL>
        </el-dialog>
    </div>
</template>

<script>
    import {backQryRefundRecordList} from "../../../api/financingOrder/refunRecordList";
    import REFUNDDETAIL from "../../../views/financing/refund/refundDetail";
    export default {
        name: "refundRecordList",
        components: {
            REFUNDDETAIL
        },
        data() {
            return {
                statuss:[],
                formInline:{
                    orderNum:null,
                    pageSize:5,
                    curPage:1,
                    status:null,
                    startTime:null,
                    endTime:null
                },
                Time:[],
                orderList:[],
                pageTotal:0,
                showDet:false,
                refundDetInfo:null
            }
        },
        created(){
            this.backQryRefundRecordList();
        },
        methods:{
            backList(){
                this.showDet = false;
            },
            toRefundDet(obj){
                this.showDet = true;
                this.refundDetInfo = obj;
            },
            dateAddDays(dayCount) {
                var tempDate=new Date();//把日期字符串转换成日期格式
                var resultDate=new Date((tempDate/1000+(86400*dayCount))*1000);//增加n天后的日期
                var resultDateStr=resultDate.getFullYear()+"-"+(resultDate.getMonth()+1)+"-"+(resultDate.getDate());//将日期转化为字符串格式
                return resultDateStr;
            },
            backQryRefundRecordList(){
                if(this.statuss.length>0){
                    this.formInline.startTime = this.dateAddDays(0);
                    this.formInline.endTime = this.dateAddDays(this.statuss[0]);
                    if(this.statuss[0]==''){
                        this.formInline.startTime=null;
                        this.formInline.endTime=null;
                    }
                }
                if(this.Time.length>1){
                    this.formInline.startTime=this.Time[0].format("yyyy-MM-dd");
                    this.formInline.endTime=this.Time[1].format("yyyy-MM-dd");
                }
                backQryRefundRecordList(this.formInline).then(response=>{
                    this.orderList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            currentChange(val){
                this.formInline.curPage = val;
                this.backQryRefundRecordList();
            }
        }
    }
</script>

<style scoped>

</style>