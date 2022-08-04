<template>
    <div class="home-page" style="padding: 22px 16px 18px;">
        <div class="home-top_wlc">
            <div class="wlcBox">欢迎管理员：<em>{{$store.getters.userName}}</em>！当前时间<span>{{new Date().format('yyyy-MM-dd hh:mm:ss')}} </span>
            </div>
        </div>
        <!--    代办事项-->
        <el-row type="flex" justify="space-between">
            <el-col style="width: 58%;">
                <el-row class="home-stat">
                    <el-col style="border-bottom: 1px solid #E4E4E4;"><h4>代办事项</h4></el-col>
                    <el-col>
                        <el-row class="statListBox">
                            <el-col v-for="item in daibanList" :key="item.name"><p>{{item.name}}</p>
                                <p>{{item.num}}</p></el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-col>
            <el-col style="width: 41%;background: #FFF;padding:0 16px;height: 275px;">
                <h4 style="padding-left: 3px;border-bottom: 1px solid #E4E4E4;">系统消息</h4>
                <table class="home-tab sysBox">
                    <tr>
                        <td>当前版本</td>
                        <td>OMS V1.0</td>
                    </tr>
                    <tr>
                        <td>操作系统</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>运行环境</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>服务器地址</td>
                        <td>http://www.traup.net</td>
                    </tr>
                </table>
            </el-col>
        </el-row>
        <!--    数据统计-->
        <el-row class="home-stat">
            <el-col style="border-bottom: 1px solid #E4E4E4;">
                <el-row type="flex" justify="space-between" align="middle">
                    <el-col><h4>数据统计</h4></el-col>
                    <el-col class="dotPage" style="text-align: right;">
                        <el-link :class="isDotCheck==1?'isCheck':''" @click="choiceStat(1)" style="margin-right: 12px;">
                            <i></i></el-link>
                        <el-link :class="isDotCheck==2?'isCheck':''" @click="choiceStat(2)"><i></i></el-link>
                    </el-col>
                </el-row>
            </el-col>
            <el-col>
                <el-row class="statListBox statMoreBox" v-if="isDotCheck==1">
                    <el-col v-for="item in toDoList" :key="item.name"><p>{{item.name}}</p>
                        <p>{{item.num}}</p></el-col>
                </el-row>
                <el-row class="statListBox statMoreBox nextStatBox" v-else>
                    <el-col v-for="nextItem in toDoNextList" :key="nextItem.name"><p>{{nextItem.name}}</p>
                        <p>{{nextItem.num}}</p></el-col>
                </el-row>
            </el-col>
        </el-row>
        <!--    操作轨迹-->
        <el-row class="home-stat" style="margin-bottom: 0px;padding-bottom: 15px; height: auto;">
            <el-col><h4>操作轨迹</h4></el-col>
            <el-col style="padding-left:14px;">
                <el-table :data="dataList" border style="width: 100%" class="home-tab">
                    <el-table-column prop="operateName" label="管理者姓名" width="150"></el-table-column>
                    <el-table-column prop="createTime" label="登录时间" width="200"></el-table-column>
                    <el-table-column prop="content" label="操作记录"></el-table-column>
                </el-table>
            </el-col>
            <el-col style="text-align: center;">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="operatePar.curPage"
                        :page-size="2"
                        layout="prev, pager, next, jumper"
                        :total="pageTotals" class="pageBox">
                </el-pagination>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {qryUser} from "../api/user/userManage";
    import {backQryPayDealList} from "../api/financingOrder/orderList";
    import {backQryBrokerList} from "../api/infoManage/borker";
    import {qryServiceDwList, qryBackOperate} from "../api/admin/index";
    import {qryUserMemberList} from '../api/user/userManage';
    export default {
        name: "home",
        data() {
            return {
                isDotCheck: 1,
                pageTotals: 0,
                currentPage: 5,
                daibanList: [{name: '待审新上传产品', num: 0}, {name: '待审新入驻经纪人', num: 0}, {
                    name: '待审新入驻商家',
                    num: 0
                }, {name: '待审新上传需求', num: 0}],
                toDoList: [{name: '企业注册数量', num: 0}, {name: '个人注册数量', num: 0}, {name: '今日销售总额', num: 0}, {
                    name: '今日订单量',
                    num: 0
                },
                    {name: '今日需求上传量', num: 0}, {name: '平台商家数量(企业)', num: 0}, {name: '平台商家数量(个人)', num: 0},
                    {name: '今日入驻企业量', num: 0}, {name: '今日个人入驻量', num: 0}, {name: '今日新注册用户量', num: 0}],
                toDoNextList: [{name: '今日产品上传量', num: 0}, {name: '今日入驻经纪人量', num: 0}, {
                    name: '今日购买标准会员量',
                    num: 0
                }, {name: '今日购买高级会员量', num: 0}],
                dataList: [], //操作轨迹纪录,
                operatePar: {
                    pageSize: 2,
                    curPage: 1
                }
            }
        },
        mounted() {
            this.daibanSt();
            this.dataOrderSt();
            this.dataUserSt();
            this.qryBackOperate();
        },
        methods: {
            choiceStat(val) {
                this.isDotCheck = val;
            },
            qryBackOperate() {
                qryBackOperate(this.operatePar).then(response => {
                    this.dataList = response.data;
                    this.pageTotals = response.page.counts;
                });
            },
            daibanSt() {
                var promList = [];
                var par = new Promise((resolve, reject) => {
                    qryUser({merchantFlag: 2}).then(response => {
                        resolve(response.page.counts);
                    });
                });
                promList.push(par);
                var par1 = new Promise((resolve, reject) => {
                    qryUser({merchantFlag: 3}).then(response => {
                        resolve(response.page.counts);
                    });
                });
                promList.push(par1);
                Promise.all(promList).then(response => {
                    var counts = 0;
                    response.forEach((item, index) => {
                        counts += item;
                    });
                    this.daibanList[2].num = counts;
                })
                backQryBrokerList({status: 2}).then(response => {
                    this.daibanList[1].num = response.page.counts;
                });
                qryServiceDwList({needType: 1, auditStatus: 0}).then(response => {
                    this.daibanList[0].num = response.page.counts;
                });
                qryServiceDwList({needType: 2, auditStatus: 0}).then(response => {
                    this.daibanList[3].num = response.page.counts;
                });
                qryServiceDwList({needType: 1, time:new Date().format('yyyy-MM-dd')}).then(response => {
                    this.toDoNextList[0].num = response.page.counts;
                });
                backQryBrokerList({pushTime:new Date().format('yyyy-MM-dd')}).then(response=>{
                    this.toDoNextList[1].num = response.page.counts;
                });
                qryUserMemberList({time:new Date().format('yyyy-MM-dd'),type:1}).then(response=>{
                    this.toDoNextList[2].num = response.page.counts;
                });
                qryUserMemberList({time:new Date().format('yyyy-MM-dd'),type:2}).then(response=>{
                    this.toDoNextList[3].num = response.page.counts;
                });
                qryServiceDwList({needType: 2, time:new Date().format('yyyy-MM-dd')}).then(response => {
                    this.toDoList[4].num = response.page.counts;
                });
            },
            dataOrderSt() {
                var qryPar = {
                    createTime: new Date().format('yyyy-MM-dd'),
                    updateTime: new Date().format('yyyy-MM-dd')
                }
                backQryPayDealList(qryPar).then(response => {
                    var totalPrice = 0;
                    response.data.forEach(item => {
                        totalPrice += parseFloat(item.totalPrice);
                    })
                    this.toDoList[2].num = totalPrice;
                    this.toDoList[3].num = response.page.counts;
                });
            },
            dataUserSt() {
                var promList = [];
                var par = new Promise((resolve, reject) => {
                    qryUser({type: 2}).then(response => {
                        this.toDoList[0].num = response.page.counts;
                        resolve(response.page.counts);
                    });
                });
                var par1 = new Promise((resolve, reject) => {
                    qryUser({type: 1}).then(response => {
                        this.toDoList[1].num = response.page.counts;
                        resolve(response.page.counts);
                    });
                });
                var par2 = new Promise((resolve, reject) => {
                    qryUser({type: 2, merchantFlag: 1}).then(response => {
                        this.toDoList[5].num = response.page.counts;
                        resolve(response.page.counts);
                    });
                });
                var par3 = new Promise((resolve, reject) => {
                    qryUser({type: 1, merchantFlag: 1}).then(response => {
                        this.toDoList[6].num = response.page.counts;
                        resolve(response.page.counts);
                    });
                });
                var par4 = new Promise((resolve, reject) => {
                    qryUser({type: 2, createTimeSt: new Date().format('yyyy-MM-dd') + ' 00:00:00'}).then(response => {
                        this.toDoList[7].num = response.page.counts;
                        resolve(response.page.counts);
                    });
                });
                var par5 = new Promise((resolve, reject) => {
                    qryUser({
                        type: 1,
                        createTimeSt: new Date().format('yyyy-MM-dd') + '' + ' 00:00:00'
                    }).then(response => {
                        this.toDoList[8].num = response.page.counts;
                        resolve(response.page.counts);
                    });
                });
                promList.push(par);
                promList.push(par1);
                promList.push(par2);
                promList.push(par3);
                promList.push(par4);
                promList.push(par5);
                Promise.all(promList).then(response => {
                    this.toDoList[9].num = this.toDoList[7].num + this.toDoList[8].num;
                })
            },
            handleSizeChange(val) {
                this.operatePar.curPage = val;
                this.qryBackOperate();
            },
            handleCurrentChange(val) {
                this.operatePar.curPage = val;
                this.qryBackOperate();
            }
        }
    };
</script>
<style scoped>
    .home-top_wlc {
        background: #FFF;
        padding: 10px 26px;
        margin-bottom: 24px;
    }

    .wlcBox {
        background: #F0F0F0;
        padding: 16px 28px;
        position: relative;
    }

    .wlcBox:before {
        content: "";
        width: 4px;
        height: 100%;
        background: #024ECC;
        position: absolute;
        left: 0;
        top: 0;
    }

    .wlcBox em {
        color: #ca0000;
        font-style: normal;
    }

    .home-stat {
        height: 275px;
        background: #FFF;
        padding: 0 13px;
        margin-bottom: 30px;
    }

    .home-page h4 {
        padding: 16px 0px 14px 16px;
    }

    .home-tab {
        width: 99.9%;
        text-align: center;
        margin: 25px auto;
        border-collapse: collapse;
    }

    .home-tab tr td, .home-tab tr th {
        height: 46px;
        line-height: 46px;
        border: 1px solid rgba(134, 134, 134, 1);
    }

    .home-tab.sysBox tr td {
        width: 50%;
        height: 42px;
        line-height: 42px;
    }

    /deep/ .el-table.home-tab, /deep/ .el-table.home-tab thead, /deep/ .el-table__empty-text {
        color: #030303;
        font-weight: 400;
    }

    /deep/ .el-table.home-tab td, /deep/ .el-table.home-tab th {
        text-align: center;
    }

    .statListBox .el-col {
        width: calc((100% - 36px) / 3);
        height: 90px;
        background: #F0F0F0;
        margin-left: 12px;
        margin-top: 14px;
        padding: 18px 24px;
    }

    .statListBox.statMoreBox .el-col {
        width: calc((100% - 60px) / 5);
    }

    .nextStatBox {
        width: 40%;
    }

    .statListBox.nextStatBox .el-col {
        width: calc((100% - 24px) / 2);
    }

    .statListBox p:last-child {
        color: #024ECC;
        font-size: 30px;
        margin-top: 14px;
        margin-left: 21px;
    }

    .dotPage i {
        width: 10px;
        height: 10px;
        background: #E4E4E4;
        border-radius: 50%;
        display: block;
    }

    .dotPage /deep/ .el-link.isCheck i {
        background: #024ECC;
    }

    .dotPage /deep/ .el-link.is-underline:hover::after {
        content: none;
    }
</style>