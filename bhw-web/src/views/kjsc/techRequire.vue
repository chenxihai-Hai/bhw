<template>
    <div class="requireWrap ">
        <el-row justify="center" type="flex">
            <el-col :span="16" style="min-width: 1240px;">
                <el-tabs v-model="activeName" @tab-click="handleClick" class="tabKjsc">
            <el-tab-pane label="技术需求" name="1" style="margin-top:15px;">
                <el-row class="bgColor" style="height: 47px;" >
                    <el-col :span="18" class="tx-alg_l" style="padding-left: 16px;"><span><i class="icon-xiaoxi"></i>最新动态：</span>
                        <div class="text-container">
                            <div class="inner-container">
                                <p class="text" v-for="item in gdDataList" :key="'gdjs_'+item.requireId" >{{item.requireUserName}}发布了新需求</p>
                            </div>
                        </div>
                    </el-col>
                    <el-col :span="6" class="tx-alg_r" style="padding-right: 60px;">{{allDataCounts}}项需求正在对接中</el-col>
                </el-row>
                <el-row>
                    <el-col :span="18" class="bgColor searchBox" >
                        <dl><dt>需求类型</dt><dd><el-link v-for="rType in requireType" :key="rType.name" @click="pRequireTypeCk(rType.id)">{{rType.name}}</el-link></dd></dl>
                        <dl><dt>行业分类</dt><dd><el-link v-for="hType in industryType" :key="hType.id+hType.name" @click="fwTypeCk(hType.id)">{{hType.name}}</el-link></dd></dl>
                        <dl><dt>所在地</dt><dd>
                            <el-select v-model="qryParam.provinceId" @change="qryCityList" placeholder="省份" style="margin-right: 14px;">
                                <el-option
                                        v-for="item in provinceList"
                                        :key="item.provinceName"
                                        :label="item.provinceName"
                                        :value="item.provinceId">
                                </el-option>
                            </el-select>
                            <el-select v-model="qryParam.cityName" @change="qryRequireList" placeholder="城市">
                                <el-option
                                        v-for="item in cityList"
                                        :key="item.cityName"
                                        :label="item.cityName"
                                        :value="item.cityId">
                                </el-option>
                            </el-select>
                        </dd></dl>
                    </el-col>
                    <el-col :span="6" class="imgGG"><img src="../../images/img-kjsc_ggao.png"></el-col>
                </el-row>
                <el-row>
                    <el-col :span="18">
                        <el-row class="rqList">
                            <el-col class="bgColor shBox">
                                <h4>默认排序</h4>
                                <el-input placeholder="按关键字搜索" v-model="qryParam.requireName" class="keySearch">
                                    <el-button slot="append" @click="qryRequireList" icon="el-icon-search"></el-button>
                                </el-input>
                            </el-col>
                            <el-col class="bgColor" v-for="item in techList" :key="'js_'+item.requireId+'_'+item.projectType">
                                <ul>
                                    <li>
                                        <router-link :to="{path:'/kjsc/requireDetail',query:{requireObj:item}}" tag="a" class="routerLink-a">
                                            <h2>{{item.requireName}}</h2>
                                        </router-link>
                                        <span class="status fontWb">
                                            <template v-if="item.status==0">
                                                待承接
                                            </template>
                                            <template v-if="item.status==1">
                                                已承接
                                            </template>
                                            <template v-if="item.status==2">
                                                已解决
                                            </template>
                                        </span>
                                    </li>
                                    <li><p>{{item.industryName}}</p></li>
                                    <li><span>预算：</span><label style="color: #CA0000;">{{item.unitPrice=="" || undefined == item.unitPrice  ?"面议":item.unitPrice+"元"}}</label></li>
                                    <li class="techIntrod">
                                    <span>简介：</span><label v-html="item.introduce"></label>
                                    </li>
                                    <li style="margin-top: 20px;"><span style="color: #039ACD;">{{item.requireType}}</span>
                                        <span style="margin-left: 100px;">
                                        <i class="el-icon-map-location" style="color: #CC3333;"></i>定位：{{item.provinceName}}  {{item.cityName}}
                                        </span>
                                        <span style="margin-left: 50px;">
                                            数据来源:
                                            <template v-if="item.source==''">
                                                <template v-if="item.userType==1">
                                                    {{item.requireUserName.substring(0,1)+'**'}}
                                                </template>
                                                <template v-else>
                                                    {{item.requireUserName}}
                                                </template>
                                            </template>
                                            <template v-else>
                                                {{item.source}}
                                            </template>
                                        </span>
                                        <template v-if="item.status==0">
                                            <el-button @click="toRequireDetail(item)" class="btnXq">承接需求</el-button>
                                        </template>
                                        <template v-else>
                                            <el-button class="btnXq">已完成</el-button>
                                        </template>
                                    </li>
                                </ul>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 69px; text-align: center;">
                            <el-col>
                                <el-pagination
                                        background
                                        layout="prev, pager, next,total"
                                        :page-size="qryParam.pageSize"
                                        :current-page="qryParam.curPage"
                                        @current-change="currentChange"
                                        :total="pageTotal" class="pageBox">
                                </el-pagination>
                            </el-col>
                        </el-row>
                    </el-col>
                    <el-col :span="6">
                        <p class="titleTj">服务商推荐</p>
                        <ul class="fwsList">
                            <li v-for="item in serveList" :key="item.name"><h3>{{item.name}}</h3><p>{{item.mark}}</p></li>
                        </ul>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane label="产品、服务需求" name="2" style="margin-top:15px;">
                <el-row class="bgColor" style="height: 47px;">
                    <el-col :span="18" class="tx-alg_l" style="padding-left: 16px;"><span><i class="icon-xiaoxi"></i>最新动态：</span>
                        <!--<span>深圳市XXXXXX发布了新的需求</span>-->
                        <div class="text-container">
                            <div class="inner-container">
                                <p class="text" v-for="item in gdDataList" :key="'cpgd_'+item.requireId" >{{item.requireUserName}}发布了新需求</p>
                            </div>
                        </div>
                    </el-col>
                    <el-col :span="6" class="tx-alg_r" style="padding-right: 60px;">{{allDataCounts}}项需求正在对接中</el-col>
                </el-row>
                <el-row>
                    <el-col :span="18" class="bgColor searchBox" >
                        <dl><dt>需求类型</dt><dd><el-link v-for="rType in requireType" :key="rType.name" @click="pRequireTypeCk(rType.id)">{{rType.name}}</el-link></dd></dl>
                        <dl style="line-height: 28px;"><dt>分类</dt><dt style="width: 50px;padding-right: 10px;">
                            <el-link>全部</el-link></dt><dd v-if="qryParam.requireType!=null">
                                <el-link  v-for="fType in fwType" :key="fType.id" @click="fwTypeCk(fType.id)">{{fType.name}}</el-link>
                        </dd></dl>
                        <dl><dt>所在地</dt><dd>
                            <el-select v-model="qryParam.provinceId" @change="qryCityList" placeholder="省份" style="margin-right: 14px;">
                                <el-option
                                        v-for="item in provinceList"
                                        :key="item.provinceName"
                                        :label="item.provinceName"
                                        :value="item.provinceId">
                                </el-option>
                            </el-select>
                            <el-select v-model="qryParam.cityName" @change="qryRequireList" placeholder="城市">
                                <el-option
                                        v-for="item in cityList"
                                        :key="item.cityName"
                                        :label="item.cityName"
                                        :value="item.cityId">
                                </el-option>
                            </el-select>
                        </dd></dl>
                    </el-col>
                    <el-col :span="6" class="imgGG"><img src="../../images/ad/img-cpfw_ad.jpg" width="100%"></el-col>
                </el-row>
                <el-row>
                    <el-col :span="18">
                        <el-row class="rqList">
                            <el-col class="bgColor shBox">
                                <h4>默认排序</h4>
                                <el-input placeholder="按关键字搜索" v-model="qryParam.requireName" class="keySearch">
                                    <el-button slot="append" @click="qryRequireList" icon="el-icon-search"></el-button>
                                </el-input>
                            </el-col>
                            <el-col class="bgColor" v-for="item in techList" :key="item.requireName+'_'+item.requireId+'_'+item.projectType">
                                <ul>
                                    <li>
                                        <router-link :to="{path:'/kjsc/requireDetail',query:{requireObj:item,kjscPageType:pageType}}" tag="a" class="routerLink-a">
                                            <h2>{{item.requireName}}</h2>
                                        </router-link>
                                        <span class="status fontWb">
                                            <template v-if="item.status==0">
                                                待承接
                                            </template>
                                            <template v-if="item.status==1 && item.projectType!=1">
                                                已承接
                                            </template>
                                            <template v-if="item.status==2 && item.projectType!=1">
                                                已解决
                                            </template>
                                            <template v-if="item.status==2 && item.projectType==1">
                                                待承接
                                            </template>
                                            <template v-if="item.status==4 && item.projectType==1">
                                                已承接
                                            </template>
                                            <template v-if="item.status==5 && item.projectType==1">
                                                已解决
                                            </template>
                                        </span>
                                    </li>
                                    <li><p>{{item.industryName}}</p></li>
                                    <li><span>预算：</span><label style="color: #CA0000;">{{item.unitPrice=="" || undefined == item.unitPrice  ?"面议":item.unitPrice+"元"}}</label></li>
                                    <li class="techIntrod"><span>简介：</span><label v-html="item.introduce"></label></li>
                                    <li style="margin-top: 20px;">
                                        <span style="color: #039ACD;">{{item.requireType}}</span>
                                        <span style="margin-left: 100px;">
                                        <i class="el-icon-map-location" style="color: #CC3333;"></i>定位：{{item.provinceName}}  {{item.cityName}}
                                        </span>
                                        <span style="margin-left: 50px;">
                                            数据来源:
                                            <template v-if="item.source==''">
                                                <template v-if="item.userType==1">
                                                    {{item.requireUserName.substring(0,1)+'**'}}
                                                </template>
                                                <template v-else>
                                                    {{item.requireUserName}}
                                                </template>
                                            </template>
                                            <template v-else>
                                                {{item.source}}
                                            </template>
                                        </span>
                                        <template v-if="item.status==0 && item.projectType!=1">
                                            <router-link :to="{path:'/kjsc/requireDetail',query:{requireObj:item,kjscPageType:pageType}}" tag="a" class="routerLink-a">
                                            <el-button class="btnXq" style="vertical-align: inherit;">承接需求</el-button>
                                            </router-link>
                                        </template>
                                        <template v-if="item.status==2 && item.projectType==1">
                                            <router-link :to="{path:'/kjsc/requireDetail',query:{requireObj:item,kjscPageType:pageType}}" tag="a" class="routerLink-a">
                                                <el-button class="btnXq" style="vertical-align: inherit;">承接需求</el-button>
                                            </router-link>
                                        </template>
                                        <template v-if="item.status!=2 && item.projectType==1">
                                            <el-button class="btnXq" style="vertical-align: inherit;">已完成</el-button>
                                        </template>
                                        <template v-if="item.status!=0 && item.projectType!=1">
                                            <el-button class="btnXq" style="vertical-align: inherit;">已完成</el-button>
                                        </template>
                                    </li>
                                </ul>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 69px; text-align: center;">
                            <el-col>
                                <el-pagination
                                        background
                                        layout="prev, pager, next,total"
                                        :page-size="qryParam.pageSize"
                                        :current-page="qryParam.curPage"
                                        @current-change="currentChange"
                                        :total="pageTotal" class="pageBox">
                                </el-pagination>
                            </el-col>
                        </el-row>
                    </el-col>
                    <el-col :span="6">
                        <p class="titleTj">服务商推荐</p>
                        <ul class="fwsList">
                            <li v-for="item in serveList" :key="item.name"><h3>{{item.name}}</h3><p>{{item.mark}}</p></li>
                        </ul>
                    </el-col>
                </el-row>
            </el-tab-pane>
        </el-tabs>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {qryRequireList,qryIntellectualType,qryScienceRequireList,qryRequireTypeList,qryScienceRequireTypeList,qryServerBusiness} from "../../api/projectDw/projectDw";
    import {qryProvinceList,qryCityList} from "../../api/admin/index";
    export default {
        name: "techRequire",
        data(){
            return{
                animate:true,
                activeName:"1",
                allDataCounts:0,
                serveList:[
                    {
                    id:1,
                    name:"北海银河生物产业投资股份有限公司",
                    mark:"计算机模式识别领域"
                },{
                    id:2,
                    name:"北海银河开关设备有限公司",
                    mark:"电子信息"
                },{
                    id:3,
                    name:"广西摇果网络技术有限公司",
                    mark:"物联网技术开发及应用"
                },{
                    id:4,
                    name:"广西云聚合科技发展有限公司",
                    mark:"物联网技术开发及应用"
                },{
                    id:5,
                    name:"北海新宏恒达机械设备有限公司",
                    mark:"成套湿法鱼粉加工设备系统集成技术"
                },{
                    id:6,
                    name:"广西新未来信息产业股份有限公司",
                    mark:"新型电子元器件领域"
                }],
                techList:[],
                requireType:[{id:1,name:"全部"},{id:2,name:"技术难题"},{id:3,name:"新技术开发"},{id:4,name:"技术储备/项目投资"},{id:5,name:"检测检验"},{id:6,name:"政府技术招标需求"},{id:1,name:"其他"}],
                pRequireType:[{id:8,name:"技术合同登记"},{id:9,name:"技术成果认定登记"},{id:1,name:"知识产权"},{id:10,name:"规划设计"},{id:11,name:"法律服务"},{id:12,name:"科技咨询"},{id:13,name:"科技金融"},{id:14,name:"财税服务"}],
                industryType:[/*{id:1,name:"全部"},{id:2,name:"电子信息"},{id:3,name:"生物与新医药"},{id:4,name:"石油化工"},{id:5,name:"新材料"},{id:6,name:"高技术服务"},{id:7,name:"新能源与节能"},{id:8,name:"家居用品"},{id:9,name:"建筑器材"}
                    ,{id:10,name:"资源与环境"},{id:11,name:"资源与环境"},{id:12,name:"先进制造与自动化"},{id:13,name:"教育休闲"},{id:14,name:"包装印刷"},{id:15,name:"电气自动化"},{id:16,name:"食品材料"},{id:17,name:"钒钛产业"},{id:18,name:"采矿治金"}
                    ,{id:19,name:"航天航空"},{id:20,name:"海洋开发"},{id:21,name:"农林牧业"},{id:22,name:"机械"},{id:23,name:"新能源"},{id:24,name:"化学化工"},{id:25,name:"轻工纺织"},{id:26,name:"其他"}*/],
                fwType:[{id:1,name:"01类-化学原料"},{id:2,name:"02类-颜料油漆"},{id:3,name:"03类-日化用品"},{id:4,name:"04类-燃料油脂"},{id:5,name:"05类-医药"},{id:6,name:"06类-金属材料"},{id:7,name:"07类-机械设备"},{id:8,name:"08类-手工器械"}],
                qryParam:{
                    pageSize:5,
                    curPage:1,
                    submitSt:2,
                    requireType:null,
                    projectType:null,
                    industryId:null,
                    requireName:'',
                    cityName:null,
                    provinceId:null
                },
                provinceList:[{id:1,name:'广东省'}],
                cityList:[{id:1,name:'深圳市'}],
                pageTotal:0,
                pageType:1,
                gdDataList:[]
            }
        },
        created(){
            if (this.$route.query.iconToggleFlag) {
                this.activeName = this.$route.query.kjscPageType + '';
                this.pageType = this.$route.query.kjscPageType;
            }
            if (this.$route.query.queryParam) {
                this.qryParam.requireName = this.$route.query.queryParam;
            }
            this.qryRequireList();
            this.fwTypeQry();
            this.qryRequireTypeList();
            this.qryProvinceList();
            this.qryScienceRequireTypeList();
        },
        methods:{
            qryProvinceList(){
                qryProvinceList().then(response => {
                    this.provinceList = response.data;
                });
            },
            qryCityList(val){
                qryCityList({"provinceId":val}).then(response => {
                    this.cityList = response.data;
                });
            },
            toRequireDetail(obj){
                this.$router.push({path:'/kjsc/requireDetail',query:{requireObj:obj,kjscPageType:this.pageType}});
            },
            handleClick(tab, event) {
                this.qryParam = {
                    pageSize:5,
                    curPage:1,
                    submitSt:2,
                    requireType:null,
                    projectType:null,
                    industryId:null,
                    requireName:''
                }
                this.pageType = tab.name;
                this.allDataCounts = 0;
                this.gdDataList=[];
                this.qryRequireList();
                this.qryRequireTypeList();
            },
            currentChange(val){
                this.qryParam.curPage = val;
                this.qryRequireList();
            },
            pRequireTypeCk(val){
                this.qryParam.requireType=val;
                this.qryParam.curPage = 1;
                this.qryRequireList();
                if(val==1){
                    this.fwTypeQry();
                }else{
                    qryServerBusiness({type:val}).then(response=>{
                        this.fwType = response.data;
                    });
                }
            },
            fwTypeCk(val){
                this.qryParam.industryId = val;
                this.qryParam.curPage = 1;
                this.qryRequireList();
            },
            qryRequireList(){
                if(null == this.qryParam.requireName || this.qryParam.requireName.trim()==''){
                    this.qryParam.requireName = null;
                };
                if(this.pageType==2){
                    qryRequireList(this.qryParam).then(response => {
                        this.techList = response.data;
                        this.pageTotal = response.page.counts;
                        if(this.allDataCounts==0){
                            this.allDataCounts = response.page.counts;
                        }
                        if(this.gdDataList.length<1){
                            this.gdDataList = this.techList;
                        }

                    });
                }else{
                    qryScienceRequireList(this.qryParam).then(response => {
                        this.techList = response.data;
                        this.pageTotal = response.page.counts;
                        if(this.allDataCounts==0){
                            this.allDataCounts = response.page.counts;
                        }
                        if(this.gdDataList.length<1){
                            this.gdDataList = this.techList;
                        }
                    });
                }

            },
            qryScienceRequireTypeList(){
                qryScienceRequireTypeList().then(response=>{
                    this.industryType = response.data;
                });
            },
            qryRequireTypeList(){
                qryRequireTypeList({requireType:this.pageType}).then(response=>{
                    this.requireType = response.data;
                });
            },
            fwTypeQry(){
                qryIntellectualType({"intellectualType":1}).then(response => {
                     this.fwType = response.data;
                });
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/market.css";
    /deep/ .tabKjsc .el-tabs__active-bar {
        width: 189px;
        position: absolute;
        bottom: 0;
        left: 0;
        height: 2px;
        background-color: #CA0000;
        z-index: 1;
        -webkit-transition: -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1), -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1),-webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        list-style: none;
    }
    /deep/ .tabKjsc .el-tabs__item {
        padding: 0 20px;
        height: 40px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        line-height: 40px;
        display: inline-block;
        list-style: none;
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(29,29,29,1);
        position: relative;
    }
    /deep/ .tabKjsc .el-tabs__item.is-active {
        font-size: 24px;
        color: rgba(29,29,29,1);
    }
    /deep/ .el-fuwu-col-bg{
        background: url(../../images/fuwujy/fuwulb.png) center no-repeat;
        background-size: contain;
        height: 296px;
        line-height: 296px;
        width: 252px;
    }
    /deep/ .el-row-rzdj-bg{
        background: url(../../images/fuwujy/images/fwjyrzdj.png) center no-repeat;
        background-size: contain;
        height: 74px;
        margin-top: 36px;
    }

    /deep/ .el-row-rzdj2-bg{
        background: url(../../images/fuwujy/images/fwjyrzdj2.png) center no-repeat;
        background-size: contain;
        height: 74px;
        margin-top: 36px;
    }
    /deep/ .el-tabs--bottom .el-tabs__item.is-bottom:nth-child(2),/deep/ .el-tabs--bottom .el-tabs__item.is-top:nth-child(2),
    /deep/ .el-tabs--top .el-tabs__item.is-bottom:nth-child(2),/deep/ .el-tabs--top .el-tabs__item.is-top:nth-child(2){
        padding: 0;margin-right: 100px;
    }
    /deep/ .el-tabs--bottom .el-tabs__item.is-bottom:last-child, /deep/ .el-tabs--bottom .el-tabs__item.is-top:last-child,
    /deep/ .el-tabs--top .el-tabs__item.is-bottom:last-child, /deep/ .el-tabs--top .el-tabs__item.is-top:last-child{
        padding: 0;
    }

    .inner-container {
        animation: myMove 5s linear infinite;
        animation-fill-mode: forwards;
    }
    /*文字无缝滚动*/
    @keyframes myMove {
        0% {
            transform: translateY(0);
        }
        100% {
            transform: translateY(-150px);
        }
    }

    .text-container {
        width: 500px;
        height: 30px;
        line-height: 30px;
        margin: -20px auto;
        overflow: hidden;
        margin-left: 110px;
    }
    /deep/ .techIntrod img{width: 100%;}

</style>