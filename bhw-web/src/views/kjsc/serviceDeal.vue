<template>
    <div class="requireWrap ">
        <el-row justify="center" type="flex">
            <el-col :span="16" style="min-width: 1240px;">
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
                            <el-select v-model="qryParam.cityName" placeholder="城市">
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
                            <el-col class="bgColor" v-for="item in techList" :key="item.requireId+'_serdl'+'_'+item.projectType">
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
                                    <li><p>业务类型：{{item.industryName}}</p></li>
                                    <li><span>预算：</span><label style="color: #CA0000;">{{item.unitPrice=="" || undefined == item.unitPrice  ?"面议":item.unitPrice+"元"}}</label></li>
                                    <li>
                                        <span>简介：</span><label v-html="item.introduce"></label>
                                    </li>
                                    <li style="margin-top: 20px;"><span>
                                        <i class="el-icon-map-location" style="color: #CC3333;"></i>定位：{{item.provinceName}}  {{item.cityName}}</span>
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
                                        <template v-if="item.status!=2">
                                        <el-button class="btnXq" @click="advisoryRequire(item)">联系咨询</el-button>
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
                </el-row>
            </el-col>
        </el-row>
        <el-dialog title="服务交易" :visible.sync="outerVisible" class="allDialog requireAskBox">
            <el-row type="flex" style="margin-top: 10px;">
                <el-col :span="4" style="margin-top: 10px;">
                    服务名称:
                </el-col>
                <el-col :span="20">
                    <input type="text" v-model="serverForm.serverName" class="el-input__inner" style="border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    服务类型:
                </el-col>
                <el-col :span="20">
                    <el-select v-model="serverForm.type" placeholder="请选择" style="width: 100%;">
                        <el-option
                                v-for="item in requireType"
                                :key="item.name"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-col>
            </el-row>
            <el-row type="flex" style="height: 80px" >
                <el-col :span="4" style="margin-top: 10px;">
                    服务需求:
                </el-col>
                <el-col :span="20">
                    <el-input type="textarea" :row="4" v-model="serverForm.msg" class="el-input__inner" style="width: 100%;height: 100%; border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    姓名:
                </el-col>
                <el-col :span="20">
                    <input type="text" v-model="serverForm.leavemsgName" placeholder="您的姓名" class="el-input__inner" style="border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    手机号码:
                </el-col>
                <el-col :span="14">
                    <input type="text" v-model="serverForm.phoneNo" class="el-input__inner" placeholder="您的手机号吗" style="font-size: 12px; border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
                <el-col :span="6" style="text-align:right;margin-top: 3px;">
                    <el-button class="btnNoBgdBule sendCode" @click="pushCode" size="small" v-if="disabledFlag==false">发送验证码</el-button>
                    <el-button class="btnColorBlue" v-if="disabledFlag">{{yzmTitle}}</el-button>
                </el-col>
            </el-row>
            <el-row type="flex" >
                <el-col :span="4" style="margin-top: 10px;">
                    验证码:
                </el-col>
                <el-col :span="20">
                    <input type="text" v-model="serverForm.verifyCode" placeholder="请输入验证码" class="el-input__inner" style="border: 0 none;border-bottom: 0px solid #ccc;border-radius: 0px;" />
                </el-col>
            </el-row>
            <div slot="footer" class="dialog-footer" style="text-align: center">
                <el-button type="warning" @click="submitServerAdv" class="btnColorOrg" style="width: 199px;font-size: 16px;">确 定</el-button>
                <el-row style="margin-top: 20px;border-bottom: 0;text-align: center;">
                    <el-col style="text-align: center;"><i class="icon-shield"></i>信息保护中,请放心填写</el-col>
                </el-row>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import {qryServiceDealList,qryServerBusiness,saveLeaveMsg} from "../../api/projectDw/projectDw";
    import {qryProvinceList,qryCityList,sendPhoneCode} from "../../api/admin/index";
    import {getToken} from '../../utils/auth';
    export default {
        name: "techRequire",
        data(){
            return{
                activeName:"1",
                serveList:[],
                techList:[],
                requireType:[{id:null,name:"全部"},{id:16,name:"技术供需"},{id:8,name:"技术合同登记"},{id:9,name:"技术成果认定登记"},{id:11,name:"法律服务"},{id:14,name:"财税服务"}],
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
                    provinceId:null,
                    cityName:null
                },
                serverForm:{
                    serverId:'',
                    serverName:"",
                    serverUserId:'',
                    type:"",
                    msg:"",
                    leavemsgName:"",
                    userId:"",
                    phoneNo:"",
                    verifyCode:''
                },
                provinceList:[{id:1,name:'广东省'}],
                cityList:[{id:1,name:'深圳市'}],
                pageTotal:0,
                pageType:1,
                outerVisible:false,
                yzmTime:120,
                yzmTimer:null,
                yzmTitle:"发送验证码",
                disabledFlag:false
            }
        },
        created(){
          this.qryServiceDealList();
          this.qryServerBusiness();
          this.qryProvinceList();
        },
        methods:{
            pushCode(){
                if(!(/^1[3456789]\d{9}$/.test(this.serverForm.phoneNo))){
                    this.$message.error('手机号格式错误！')
                    return;
                }
                sendPhoneCode(this.serverForm).then(response => {
                    if(response.data==0){
                        var that = this;
                        this.yzmTimer = setInterval(function(){
                            that.disabledFlag = true;
                            if(that.yzmTime==1){
                                that.yzmTitle="发送验证码";
                                that.disabledFlag = false;
                                window.clearInterval(that.yzmTimer);
                                that.yzmTimer = null;
                                that.yzmTime = 120;
                            }else{
                                that.yzmTitle="请在"+that.yzmTime+"秒内输入";
                                that.yzmTime= that.yzmTime -1;
                            }
                        },1000);
                    }else{
                        this.$message({
                            message: '验证码发送失败!',
                            type: 'warning'
                        });
                    }
                });
            },
            submitServerAdv(){
                if(this.serverForm.verifyCode.trim()==''){
                    this.$message({
                        message: '请输入验证码!',
                        type: 'warning'
                    });
                    return;
                }
                this.serverForm.userId=this.$store.state.user.userInfo.id;
                saveLeaveMsg(this.serverForm).then(response => {
                    if(response.retCode==0){
                        this.$confirm('<div><p>请注意接听北部湾国际技术转移转化中心专属顾问电话</p>' +
                            '<p>400-123-123</p></div>', '您的资料已提交!', {
                            confirmButtonText: '确定',
                            type: 'success',
                            dangerouslyUseHTMLString: true,
                            showCancelButton:false,
                            center: true
                        }).then(() => {
                            this.serverForm={
                                serverId:"",
                                serverName:"",
                                type:"",
                                msg:"",
                                leavemsgName:"",
                                phoneNo:"",
                                verifyCode:'',
                                userId:""
                            };
                            this.outerVisible = false;
                        }).catch(() => {
                        });
                    }else{
                        this.$message({
                            message: response.message,
                            type: 'warning'
                        });
                    }
                });
            },
            advisoryRequire(item){
                if(getToken()){
                    this.outerVisible = true;
                    this.serverForm.serverName = item.requireName;
                    this.serverForm.type = item.projectType;
                    this.serverForm.serverId = item.requireId;
                    this.serverForm.serverUserId = item.userId;
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
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
            currentChange(val){
                this.qryParam.curPage = val;
                this.qryServiceDealList();
            },
            pRequireTypeCk(val){
                this.qryParam.projectType=val;
                this.qryServiceDealList();
            },
            fwTypeCk(val){
                this.qryParam.industryId = val;
                this.qryServiceDealList();
            },
            qryServiceDealList(){
                qryServiceDealList(this.qryParam).then(response => {
                    this.techList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            qryServerBusiness(){
                qryServerBusiness().then(response=>{
                    this.industryType = response.data;
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
</style>