<template>
    <div class="capitalFlow">
        <el-tabs v-model="activeName" type="card" class="capitalMenu" @tab-click="handleFlowClick">
            <el-tab-pane label="支付明细" name="1"></el-tab-pane>
            <el-tab-pane label="提现记录" name="2"></el-tab-pane>
            <!--<el-tab-pane label="充值记录" name="3"></el-tab-pane>-->
            <el-tab-pane label="T币明细" name="4"></el-tab-pane>
        </el-tabs>
<!--        搜索-->
        <div class="searchBox" v-if="activeName==1" style="padding: 22px;">
            <el-row>
                <el-col :span="6">
                    <span>支付流水号：</span>
                    <el-input v-model="qryParam.orderNum" placeholder="请输入内容"></el-input>
                </el-col>
                <el-col :span="6" style="text-align: right;">
                    <span>订单流水号：</span><el-input v-model="qryParam.orderId" placeholder="请输入内容"></el-input>
                </el-col>
            </el-row>
            <el-row style="margin-top: 26px;">
                <el-col :span="11">
                    <span style="color: #1D1D1D;font-weight:normal;">起止日期：</span>
                    <el-date-picker
                            v-model="time"
                            type="daterange"
                            range-separator="-"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
                </el-col>
                <el-col :span="7" style="text-align: right;">
                    <el-button class="btnColorOrg" @click="qryPayDealList">搜索</el-button>
                    <el-button class="btnColorBlue" @click="cleanQryPar">清空</el-button>
                    <!--<el-button class="btnColorBlue">导出</el-button>-->
                </el-col>
            </el-row>
        </div>
        <div class="searchBox" v-else>
            <el-row>
                <el-col :span="10">
                    <span>起止日期：</span>
                    <el-date-picker
                            v-model="time"
                            type="daterange"
                            range-separator="-"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
                </el-col>
                <el-col :span="6" v-if="activeName==2">
                    <span>状态：</span>
                    <el-select v-model="txQryParam.status">
                        <el-option
                                v-for="sItem in statusList"
                                :key="sItem.statusId"
                                :label="sItem.statusName"
                                :value="sItem.statusName">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="6">
                    <el-button class="btnColorOrg" @click="handleFlowClick">搜索</el-button>
                    <el-button class="btnColorBlue" @click="cleanQryPar">清空</el-button>
                </el-col>
                <el-col style="margin-top: 30px;">
                    <el-link @click="daysBt(0)">今天</el-link><el-link @click="daysBt(7)">最近7天</el-link>
                    <el-link @click="daysBt(30)">一个月</el-link><el-link @click="daysBt(90)">三个月</el-link>
                </el-col>
            </el-row>
        </div>
<!--        列表-->
        <div class="dataListBox">
            <div class="downBox">
                <img src="../../../images/icon-tongj.png" alt="">
                <span>统计金额</span>
                <span>下载账单：<el-link @click="excelBtn">Excel格式</el-link></span>
            </div>
            <template v-if="activeName=='1'">
                <el-table :data="tablePayData" border style="width: 100%;margin-top: 21px" empty-text="没有纪录">
                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                    <el-table-column prop="orderNum" label="支付流水号"></el-table-column>
                    <el-table-column  label="商品名称">
                        <template slot-scope="scope" >
                            <template v-if="scope.row.dealType==1">
                                购买T币
                            </template>
                            <template v-if="scope.row.dealType==2">
                                现金充值
                            </template>
                            <template v-if="scope.row.dealType==3">
                                开通增值服务
                            </template>
                            <template v-if="scope.row.dealType==0">
                                {{scope.row.itemName}}
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column prop="orderId" label="订单流水号"></el-table-column>
                    <el-table-column prop="sellerName" label="交易方"></el-table-column>
                    <el-table-column prop="totalPrice" label="金额（元）"></el-table-column>
                    <el-table-column prop="status" label="完成/退款">
                        <template slot-scope="scope" >
                            <template v-if="scope.row.payStatus==1">
                                进行中
                            </template>
                            <template v-if="scope.row.payStatus==2">
                                已完成
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column prop="remark" label="操作">
                        <template slot-scope="scope" >
                            <router-link :to="{path:'/userCenter/payOrderDetail',query:{orderDetailObj:scope.row}}" tag="a" class="routerLink-a" style="text-decoration: none;color: #024ECC;">
                                查看详情
                            </router-link>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
            <template v-else-if="activeName=='2'">
                <el-table :data="tableTxData" border style="width: 100%;margin-top: 21px" empty-text="没有纪录">
                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                    <el-table-column prop="totalPrice" label="金额(元)"></el-table-column>
                    <el-table-column label="手续费(元)"></el-table-column>
                    <el-table-column label="提现银行">
                        <template slot-scope="scope" >
                            {{bankNameCode[scope.row.bankName]}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="bankCard" label="银行卡号"></el-table-column>
                    <el-table-column prop="userName" label="开户人真实姓名" ></el-table-column>
                    <el-table-column  label="状态">
                        <template slot-scope="scope" >
                            <template v-if="scope.row.status==1">
                                进行中
                            </template>
                            <template v-if="scope.row.status==2">
                                成功
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope" >
                            <router-link :to="{path:'/userCenter/txOrderDetail',query:{orderDetailObj:scope.row}}" tag="a" class="routerLink-a" style="text-decoration: none;color: #024ECC;">
                                查看详情
                            </router-link>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
            <template v-else-if="activeName=='3'">
                <el-table :data="tableCzData" border style="width: 100%;margin-top: 21px" empty-text="没有纪录">
                    <el-table-column prop="orderNo" label="流水号"></el-table-column>
                    <el-table-column prop="createDate" label="创建时间"></el-table-column>
                    <el-table-column prop="price" label="金额（元）"></el-table-column>
                    <el-table-column prop="typeName" label="充值银行"></el-table-column>
                    <el-table-column prop="name" label="充值卡号"></el-table-column>
                    <el-table-column prop="orderNo" label="状态"></el-table-column>
                    <el-table-column prop="remark" label="操作"></el-table-column>
                </el-table>
            </template>
            <template v-else>
                <el-table :data="tableTbData" border style="width: 100%;margin-top: 21px" empty-text="没有纪录">
                    <el-table-column prop="orderNum" label="流水号"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                    <el-table-column prop="payType" label="购买方式">
                        <template slot-scope="scope" >
                            <template v-if="scope.row.payType==1">
                                微信
                            </template>
                            <template v-if="scope.row.payType==2">
                                支付宝
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column prop="totalPrice" label="金额（元）">
                    </el-table-column>
                    <el-table-column  prop="totalPrice" label="T币">
                        <template slot-scope="scope" >
                            {{scope.row.totalPrice}}
                        </template>
                    </el-table-column>
                    <el-table-column  label="状态">
                        <template slot-scope="scope" >
                            <template v-if="scope.row.payStatus==1">
                                未支付
                            </template>
                            <template v-if="scope.row.payStatus==2">
                                已完成
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope" >
                            <router-link :to="{path:'/userCenter/tbOrderDetail',query:{orderDetailObj:scope.row}}" tag="a" class="routerLink-a" style="text-decoration: none;color: #024ECC;">
                                查看详情
                            </router-link>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
            <el-row style="margin: 70px auto 100px;text-align: center;">
                <el-col>
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="pageSize"
                            :current-page="curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>


<script>
    import {qryUserWalletOrderList,qryPayDealList,qryUserWithdrawList} from "../../../api/user/userWallet";
    export default {
        name: "capitalFlow",
        data(){
            return{
                bankNameCode:{'CCB':'中国建设银行','PBC':'中国人民银行','BC':'中国银行','ABC':'中国农业银行','CMB':'中国招商银行'},
                pageSize:5,
                curPage:1,
                pageTotal:0,
                activeName:'1',
                payTypeList:[{typeId:0,typeName:'全部'},{typeId:1,typeName:'即时支付'}],//类型
                typeName:'全部',
                statusList:[{statusId:null,statusName:'全部'},{statusId:2,statusName:'成功'},{statusId:1,statusName:'提现中'}],
                statusName:'全部',
                tablePayData:[],//支付明细
                tableTxData:[],//提现纪录
                tableCzData:[],//充值纪录
                tableTbData:[],//T币明细
                qryParam:{
                    time:[],
                    orderId:'',
                    orderNum:'',
                    pageSize:5,
                    createTime:null,
                    updateTime:null,
                    curPage:1,
                    userId:this.$store.state.user.userInfo.id
                },
                time:[],
                wtQryParam:{
                    createTime:null,
                    updateTime:null,
                    pageSize:5,
                    curPage:1,
                    dealType:1,
                    userId:this.$store.state.user.userInfo.id
                },
                txQryParam:{
                    status:null,
                    startTime:null,
                    endTime:null,
                    pageSize:5,
                    curPage:1,
                    userId:this.$store.state.user.userInfo.id
                },
            }
        },
        created(){
            this.qryPayDealList();
        },
        methods:{
            cleanQryPar(){
                this.qryParam={
                        orderId:'',
                        orderNum:'',
                        pageSize:5,
                        createTime:null,
                        updateTime:null,
                        curPage:1,
                        userId:this.$store.state.user.userInfo.id
                },
                this.time=[],
                this.wtQryParam={
                    createTime:null,
                        updateTime:null,
                        pageSize:5,
                        curPage:1,
                        dealType:'',
                        userId:this.$store.state.user.userInfo.id
                },
                this.txQryParam={
                    status:null,
                        startTime:null,
                        endTime:null,
                        pageSize:5,
                        curPage:1,
                        userId:this.$store.state.user.userInfo.id
                };
                this.handleFlowClick();
            },
            currentChange(val){
                this.curPage = val;
                this.handleFlowClick();
            },
            dateAddDays(dayCount) {
                var tempDate=new Date();//把日期字符串转换成日期格式
                var resultDate=new Date((tempDate/1000+(86400*dayCount))*1000);//增加n天后的日期
                var resultDateStr=resultDate.getFullYear()+"-"+(resultDate.getMonth()+1)+"-"+(resultDate.getDate());//将日期转化为字符串格式
                return resultDateStr;
            },
            excelBtn(){
                if(this.activeName=='1'){
                    qryPayDealList({pageSize:1000000,userId:this.$store.state.user.userInfo.id}).then(response=>{
                        var allData = response.data;
                        allData.forEach(item=>{
                            if(item.payStatus==2){
                                item.payStatus = '已完成';
                            }else if(item.payStatus==1){
                                item.payStatus = '未完成';
                            }
                            if(item.dealType==1) {
                                item.itemName = '购买T币';
                            }else if(item.dealType==2) {
                                item.itemName = '现金充值';
                            }else if(item.dealType==3) {
                                item.itemName = '开通增值服务';
                            }
                        })
                        require.ensure([], () => {
                            const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                            const tHeader = [
                                "创建时间",
                                "支付流水",
                                "商品名称",
                                "订单流水号",
                                "交易方",
                                "金额(元)",
                                "完成/退款"
                            ];
                            const filterVal = [
                                "createTime",
                                "orderNum",
                                "itemName",
                                "orderId",
                                "sellerName",
                                "totalPrice",
                                "payStatus"
                            ];
                            const list = allData;
                            const data = this.formatJson(filterVal, list);
                            export_json_to_excel(tHeader, data, "支付明细");
                        });
                    });
                }else if(this.activeName=='2'){
                    qryUserWithdrawList({pageSize:1000000,userId:this.$store.state.user.userInfo.id}).then(response=>{
                        var allData = response.data;
                        require.ensure([], () => {
                            const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                            const tHeader = [
                                "开户人",
                                "提现账户",
                                "提现金额",
                                "可提现金额",
                                "提现发起时间"
                            ];
                            const filterVal = [
                                "userName",
                                "bankCard",
                                "totalPrice",
                                "totalPrice",
                                "createTime"
                            ];
                            const list = allData;
                            const data = this.formatJson(filterVal, list);
                            export_json_to_excel(tHeader, data, "提现记录明细");
                        });
                    });
                }else if(this.activeName=='4'){
                    qryUserWalletOrderList({pageSize:1000000,userId:this.$store.state.user.userInfo.id,dealType:1}).then(response=>{
                        var allData = response.data;
                        allData.forEach(item=>{
                            if(item.payStatus==2){
                                item.payStatus = '已完成';
                            }else if(item.payStatus==1){
                                item.payStatus = '未完成';
                            }
                            if(item.payType==1) {
                                item.payType = '微信';
                            }else if(item.payType==2) {
                                item.payType = '支付宝';
                            }else if(item.payType==3) {
                                item.payType = '开通增值服务';
                            }
                        })
                        require.ensure([], () => {
                            const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                            const tHeader = [
                                "流水号",
                                "创建时间",
                                "购买方式",
                                "金额",
                                "T币",
                                "状态"
                            ];
                            const filterVal = [
                                "orderNum",
                                "createTime",
                                "payType",
                                "totalPrice",
                                "totalPrice",
                                "payStatus"
                            ];
                            const list = allData;
                            const data = this.formatJson(filterVal, list);
                            export_json_to_excel(tHeader, data, "T币明细");
                        });
                    });
                }
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]));
            },
            daysBt(days){
                if(this.activeName=='2'){
                    this.txQryParam.startTime = this.dateAddDays(0);
                    this.txQryParam.endTime = this.dateAddDays(days);
                    this.qryUserWithdrawList();
                }else if(this.activeName=='4'){
                    this.wtQryParam.createTime = this.dateAddDays(0);
                    this.wtQryParam.updateTime = this.dateAddDays(days);
                    this.qryUserWalletOrderList();
                }
            },
            qryPayDealList(){
                if(this.time.length>1){
                    this.qryParam.createTime=this.time[0].format("yyyy-MM-dd");
                    this.qryParam.updateTime=this.time[1].format("yyyy-MM-dd");
                }
                this.qryParam.curPage = this.curPage;
                qryPayDealList(this.qryParam).then(response=>{
                    this.tablePayData = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            qryUserWalletOrderList(){
                if(this.time.length>1){
                    this.wtQryParam.createTime=this.time[0].format("yyyy-MM-dd");
                    this.wtQryParam.updateTime=this.time[1].format("yyyy-MM-dd");
                }
                this.wtQryParam.curPage = this.curPage;
                qryUserWalletOrderList(this.wtQryParam).then(response=>{
                    this.tableTbData = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            qryUserWithdrawList(){
                if(this.time.length>1){
                    this.txQryParam.startTime=this.time[0].format("yyyy-MM-dd");
                    this.txQryParam.endTime=this.time[1].format("yyyy-MM-dd");
                }
                this.txQryParam.curPage = this.curPage;
                qryUserWithdrawList(this.txQryParam).then(response=>{
                    this.tableTxData = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            handleFlowClick(tabId){
                if(undefined!=tabId.name){
                    this.pageTotal = 0;
                    this.curPage = 1;
                    this.time=[];
                    this.activeName = tabId.name;
                }
                if(this.activeName=='4'){
                    this.wtQryParam.dealType=1;
                    this.qryUserWalletOrderList();
                }else if(this.activeName=='2'){
                    this.qryUserWithdrawList();
                }else if(this.activeName=='1'){
                    this.qryPayDealList();
                }
            }
        }
    }
</script>

<style scoped>
    .capitalMenu{margin-top: 18px;}
    .capitalMenu /deep/ .el-tabs__header{background: #F8FFD0;border-bottom: none;margin: 0;}
    /deep/ .el-tabs--card.capitalMenu > .el-tabs__header .el-tabs__nav{border:none;}
    .capitalMenu /deep/ .el-tabs__item{color: #1D1D1D;height: 50px;line-height: 50px; padding: 0 44px;border-left:none!important;}
    .capitalMenu /deep/ .el-tabs__header .el-tabs__item:nth-child(2){padding-left: 44px;}
    .capitalMenu /deep/ .el-tabs__item.is-active{font-size: 18px;font-weight:normal;}
/*    搜索框*/
    .searchBox{background:rgba(2,78,204,0.05);padding: 24px 28px 35px;margin: 28px 0;}
    .searchBox .el-row .el-col span{color: #1D1D1D;font-weight:normal;}
    .searchBox /deep/ .el-range-editor.el-input__inner{width: 260px;height: 36px;line-height: 36px;}
    .searchBox .el-link{color: #024ECC; margin-right: 82px;}
    .searchBox .el-button{width: 64px;height: 35px;padding: 0;border: none;}
    .searchBox /deep/ .el-select,.searchBox /deep/ .el-input{width: 140px;}
    .searchBox /deep/ .el-input__inner{height: 35px;line-height: 35px;padding-left: 10px;}
/*    列表*/
    .dataListBox{margin-top: 42px;}
    .downBox>span{margin-left: 10px;color: #1D1D1D;}
    .downBox .el-link{color: #024ECC;margin-right: 15px;vertical-align: 1px;}
    /deep/ .el-table th{background: #F8FFD0;color: #1D1D1D;}
    /deep/ .el-table__empty-text{color: #CA0000;}
</style>
