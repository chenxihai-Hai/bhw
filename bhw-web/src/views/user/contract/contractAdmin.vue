<template>
    <div class="contractAdmin" style="padding: 0 10px 80px 20px;">
        <div style="padding-left: 50px;">
            <el-form ref="form" class="searchFrom">
                <el-form-item prop="name">
                    <el-radio v-model="sousuo.checkType" label=1>供（我要帮办）</el-radio>
                    <el-radio v-model="sousuo.checkType" label=2>需(我要找人代办)</el-radio>
                    <span style="margin:0 24px 0 100px; font-size: 16px;">业务类型</span>
                    <el-radio v-model="sousuo.businessType" label=1>技术合同登记</el-radio>
                    <el-radio v-model="sousuo.businessType" label=2>科技成果认定登记</el-radio>
                </el-form-item>
                <el-form-item label="提交时间">
                    <div class="block">
                        <el-date-picker
                                v-model="Time"
                                type="daterange"
                                range-separator="-"
                                start-placeholder="请选择时间范围起始"
                                end-placeholder="请选择时间范围结束">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item style="margin-top: 43px;">
                    <el-button type="primary" @click="onSubmit" class="btnColorOrg btnSch">搜索</el-button>
                    <el-button class="btnColorBlue daochu" @click="excelBtn">批量导出</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="showOrHide" style="margin: 92px auto 72px;"><span @click="shou" :class="toggle==false?'hideS':''" style="position: relative;">{{toggle==true?'收起':'展开'}}</span></div>
        <div v-if="toggle">
        <div style="margin-bottom: 40px;">
            <el-checkbox class="check" v-model="allCk" @change="allCheck(1)"></el-checkbox>
            <el-button class="btnNoBgd btnMore"  @click="allCheck"  style="margin-left: 20px;">批量标记</el-button>
            <el-button class="btnNoBgd btnMore" @click="xiajia">批量下架</el-button>
            <span style="margin-left: 20px;margin-right:300px;">已读（0）</span>
<!--            <span>共0件</span>-->
<!--            <el-button class="page" style="margin-left: 20px;">上一页</el-button>-->
<!--            <span style="margin:10px">1/1</span>-->
<!--            <el-button class="page">下一页</el-button>-->
        </div>
        <template v-for="temp in xuqiu">
            <div class="gongxu" :key="temp.id">
                <el-row>
                    <el-col :span="12">
                        <el-checkbox class="check" v-model="temp.check"></el-checkbox>
                        <span>选择</span>
                    </el-col>
                    <el-col :span="12"></el-col>
                </el-row>
                <el-row type="flex" justify="space-between" align="middle" class="tInfo" style="margin: 31px 0 38px;">
                    <el-col :span="12"><span class="title">{{temp.serverName}}</span></el-col>
                    <el-col :span="12"  v-if="temp.status!=1">
                        <template v-if="temp.result==0"><span class="jiejue">待解决</span></template>
                        <template v-if="temp.result==1"><span class="jiejue">已承接</span></template>
                        <template v-if="temp.result==2"><span class="jiejue">已解决</span></template>
                    </el-col>
                    <el-col v-if="temp.status==1">
                        <template><span class="jiejue" style="color: red;">草稿</span></template>
                    </el-col>
                </el-row>
                <el-row class="tInfo mark">
                    <el-col>
                        <span>行业类别|<em>{{temp.intellectualName}}</em></span>
                    </el-col>
                    <el-col style="margin-top: 10px;">
                        <span>预算：</span><em style="color: #ca0000;">{{undefined==temp.budget || ''==temp.budget?'面议':temp.budget+'元'}}</em>
                    </el-col>
                    <el-col>
                        <el-divider></el-divider>
                    </el-col>
                    <el-col class="jianjie">
                        <span>简介：</span><span v-html="temp.serverRemark"></span>
                    </el-col>
                </el-row>
                <el-row type="flex" align="middle" class="tInfo" style="margin: 40px 0 0;">
                    <el-col :span="8"><span>需求类型 | {{temp.businessName}}</span></el-col>
                    <el-col :span="8" class="area-col">
                        <img src="../../../images/property/dizhi.png" class="dingwei">
                        <span>定位：{{temp.provinceName}}&nbsp;{{temp.cityName}}</span>
                    </el-col>
                    <!--<el-col :span="8">
                        <el-button class="btnColorOrg" @click="upTechSupplyInfo(temp.id,1)">
                            已承接
                        </el-button>
                        <el-button class="btnColorBlue" @click="upTechSupplyInfo(temp.id,2)">
                            已解决
                        </el-button>
                    </el-col>
                    <el-col :span="8" style="text-align: right;">
                        <el-button class="btnColorOrg  btnAskMe">
                            谁联系了我
                        </el-button>
                    </el-col>-->
                    <el-col :span="8"  v-if="temp.status!=1">
                        <el-button class="btnColorOrg" @click="upTechSupplyInfo(temp.id,1)">
                            已承接
                        </el-button>
                        <el-button class="btnColorBlue" @click="upTechSupplyInfo(temp.id,2)">
                            已解决
                        </el-button>
                    </el-col>
                    <el-col v-if="temp.status==1">
                        <el-button class="btnColorOrg" @click="upProduct(temp)">
                            编辑
                        </el-button>
                        <el-button class="btnColorBlue" @click="delProduct(temp)">
                            删除
                        </el-button>
                    </el-col>
                    <el-col  v-if="temp.status!=1" :span="8" style="text-align: right;">
                        <el-button class="btnColorOrg  btnAskMe">
                            谁联系了我
                        </el-button>
                    </el-col>
                </el-row>
            </div>
        </template>
        <el-row style="text-align: center;margin-top: 50px;">
            <el-col>
                <el-pagination
                        background
                        layout="prev, pager, next"
                        :page-size="sousuo.pageSize"
                        :current-page="sousuo.curPage"
                        @current-change="currentChange"
                        :total="pageTotal" class="pageBox">
                </el-pagination>
            </el-col>
        </el-row>
        </div>
    </div>
</template>

<script>
    import {getBussinessType, getServerListPage, serverTakeOff,upTechSupplyInfo,delTechSupplyInfo} from "../../../api/user/techSupply";

    export default {
        name: "contractAdmin",
        data() {
            return {
                allCk:false,
                radio: 1,
                value: '',
                radioType: 1,
                toggle: true,
                pageTotal:0,
                xuqiu: [{
                    id: "",
                    serverName: "",
                    intellectualName: "",
                    budget: "",
                    serverRemark: "",
                    businessName: "",
                    provinceName: "",
                    cityName: "",
                    checkType: "",
                    result: ""
                }],
                sousuo: {
                    checkType: "",
                    businessType: "",
                    startTime: "",
                    endTime: "",
                    pageSize: 3,
                    curPage: 1,
                    status:-1
                },
                Time: [],
                idList: [1, 2],
            }
        },
        methods: {
            delProduct(obj){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delTechSupplyInfo({ids:obj.id}).then(response=>{
                        this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        this.getServerListPage();
                    });
                });
            },
            upProduct(obj){
                this.$emit("upInfoAc-event",obj);
            },
            excelBtn(){
                getServerListPage({pageSize:1000000}).then(response=>{
                    var allData = response.data;
                    allData.forEach(item=>{
                        if(item.result==0){
                            item.result = '待解决';
                        }else if(item.result==2){
                            item.result = '已承接';
                        }else{
                            item.result = '已解决';
                        }
                        if( undefined == item.budget || item.budget==''){
                            item.budget = '面议';
                        }
                    })
                    require.ensure([], () => {
                        const { export_json_to_excel } = require("../../../vendor/Export2Excel");
                        const tHeader = [
                            "技术名称",
                            "行业类型",
                            "需求类型",
                            "预算",
                            "状态"
                        ];
                        const filterVal = [
                            "serverName",
                            "intellectualName",
                            "businessName",
                            "budget",
                            "result"
                        ];
                        const list = allData;
                        const data = this.formatJson(filterVal, list);
                        export_json_to_excel(tHeader, data, "技术合同登记明细");
                    });
                });
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]));
            },
            shou() {
                this.toggle = !this.toggle;
            },
            upTechSupplyInfo(id,st){
                upTechSupplyInfo({id:id,result:1,st}).then(response=>{
                    this.$message({
                        type: 'success',
                        message: '操作成功!'
                    });
                    this.getServerListPage();
                });
            },
            onSubmit() {
                if(this.Time.length>1){
                    this.sousuo.startTime=this.Time[0].format("yyyy-MM-dd");
                    this.sousuo.endTime=this.Time[1].format("yyyy-MM-dd");
                }
                this.getServerListPage();
            },
            allCheck(flag){
                if(flag!=1){
                    this.allCk = !this.allCk;
                }
                this.xuqiu.forEach(row => {
                    row.check = !row.check;
                });
            },
            xiajia() {
                this.idList = [];
                this.xuqiu.forEach(item=>{
                    if(item.check){
                        this.idList.push(item.id);
                    }
                })
                if(this.idList.length<1){
                    this.$message.warning("请勾选后再操作!");
                    return;
                }
                serverTakeOff({idList: this.idList.join(",")}).then(response => {
                    this.flag = response.data;
                })
            },
            getServerListPage(){
                getServerListPage(this.sousuo).then(response => {
                    this.xuqiu = response.data;
                    this.pageTotal = response.page.counts;
                })
            },
            currentChange(val){
                this.sousuo.curPage = val;
                this.getServerListPage();
            },
        },
        created() {
            this.getServerListPage();
        }
    }
</script>

<style scoped>
    .searchFrom /deep/ .el-radio{margin-right: 22px;}
    .searchFrom /deep/ .el-radio__label{font-size: 16px;}
    .btnSch,.daochu{width: 132px;height: 37px;padding:0;border: none;}
    .btnNoBgd.btnMore{width: 70px;height: 29px;padding: 0;}
    /deep/ .btnNoBgd.el-button.el-button--default span {font-weight: 400;color: #1D1D1D;}
    .btnAskMe {width: 139px;height: 45px;padding: 0;border: none;}

    .check {
        width: 17px;
        height: 17px;
        background: rgba(248, 248, 248, 1);
        border: 0px solid rgba(29, 29, 29, 1);
        border-radius: 3px;
    }
    .gongxu {background: rgba(255, 255, 255, 1);padding: 20px 20px 50px;margin-bottom:50px;box-shadow: 0px 1px 17px 1px rgba(0, 0, 0, 0.14);}
    .tInfo{padding: 0 50px;}
    .mark{color: #797979;font-size: 14px;}
    .mark em{font-style: normal;}
    .jiejue {font-size: 14px;font-weight:normal;color: #000;}
    .title {font-size: 24px;font-weight:normal;color: #000;}
    /deep/ .el-divider--horizontal{margin: 34px 0 20px;}
</style>