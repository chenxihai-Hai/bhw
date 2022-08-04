<template>
    <div class="detailWrap">
        <div class="breadWrap w1240">
            <span class="breakCurr"><i></i>当前位置：</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/kjsc' }">科技市场</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/kjsc' }">资产交易</el-breadcrumb-item>
                <el-breadcrumb-item>产品详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="detailBox w1240" style="padding: 30px 30px 10px 30px;">
            <el-row>
                <el-col :span="8">
                    <div class="productImg">
                        <img :src="projectDetail.homeImg" v-if="projectDetail.homeImg !=''" class="productImg"/>
                        <img src="../../images/img-zhuanl.png" v-else class="productImg"/>
                    </div>
                    <div class="productImg-Samll">
                        <template v-if="undefined!=projectDetail.imgs && ''!=projectDetail.imgs">
                            <img style="width: 81px;height: 81px;" v-for="s in projectDetail.imgs.split(';')" :src="s" :key="s"/>
                        </template>
                        <template v-else>
                            <img style="width: 81px;height: 81px;"  :src="projectDetail.homeImg" />
                        </template>
                    </div>
                </el-col>
                <el-col :span="9" class="proInfo">
                    <div style="height: 374px;">
                        <div class="proTitle">
                            <h3 :title="projectDetail.projectName">{{projectDetail.projectName.substring(0,12)+".."}}</h3>
                            <el-dropdown placement="bottom-start">
                                <span class="el-dropdown-link">
                                    <i class="icon-kefu"></i>推荐经纪人<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown" class="jjrChoice" style="width: 200px;" v-if="brokerList.length>0">
                                    <el-dropdown-item style="padding:0 10px;border-bottom: none;">
                                        <div class="jjrInfo">
                                            <div class="img-head"><el-avatar :size="size" :src="brokerList[0].photo"></el-avatar></div>
                                            <div class="jjrName"><h3>{{brokerList[0].name}}</h3>
                                                <template v-if="undefined!=brokerList[0].grade">
                                                    <p>好评度{{(((brokerList[0].grade*2)*10)/brokerList[0].gradeCounts).toFixed(0)}}%</p>
                                                </template>
                                            </div>
                                        </div>
                                        <el-button @click="advisoryBroker(brokerList[0])" class="btnColorBlue btnAsk">咨询</el-button>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                        <dl><dd>{{projectDetail.projectType==1?'注册号':'专利号'}}：{{projectDetail.codeNumber}}</dd></dl>
                        <dl>
                            <dd v-if="projectDetail.projectType==1">有效期：{{projectDetail.indate}}</dd>
                            <dd v-if="projectDetail.projectType==2">专利分类：{{patentType[projectDetail.patentType]}}</dd>
                            <dd>交易类型:{{projectDetail.dealType==1?'转让':'许可'}}</dd>
                        </dl>
                        <dl><dd>行业分类：{{projectDetail.industryName}}</dd><dd>权利状态：有效</dd></dl>
                        <dl class="introduct"><dd class="introductInfo">简介：<span>{{projectDetail.introduce}}</span></dd></dl>
                        <dl><dd>产品价格：<span style="color: #CA0000;font-size: 24px;font-weight:normal">
                            <template v-if="undefined==projectDetail.unitPrice||''==projectDetail.unitPrice">
                                面议
                            </template>
                            <template v-else>
                                ￥{{projectDetail.unitPrice}}
                            </template>
                        </span></dd></dl>
                    </div>
                    <div class="proBtn">
                        <el-button @click="ordersClick" class="btnColorOrg btnMaket" style="margin-right: 26px;">立即下单</el-button>
                        <el-button class="btnColorBlue btnMaket" @click="joinCart">加入购物车</el-button>
                    </div>
                </el-col>
                <el-col :span="5.5" class="choice" style="margin-left: 30px;">
                    <el-dropdown split-button type="primary" placement="bottom-start">
                        <span ref="qttj">其他知识产权类经纪人推荐</span>
                        <el-dropdown-menu slot="dropdown" class="jjrChoice">
                            <el-dropdown-item v-for="broker in brokerList" :key="broker.id+'_broker'">
                                <div class="jjrInfo">
                                    <div class="img-head"><el-avatar :size="size" :src="broker.photo"></el-avatar></div>
                                    <div class="jjrName"><h3>{{broker.name}}</h3>
                                        <template v-if="undefined!=broker.grade">
                                            <p>好评度{{(((broker.grade*2)*10)/broker.gradeCounts).toFixed(0)}}%</p>
                                        </template>
                                    </div>
                                </div>
                                <el-button class="btnColorBlue btnAsk"  @click="advisoryBroker(broker)">咨询</el-button>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <div></div>
                </el-col>
            </el-row>
            <div class="btnColl"><i :class="collectionFlag?'el-icon-star-on':'el-icon-star-off'" style="color:#CA0000; "></i>
                <el-button type="text" @click="saveRecord(2)" v-if="collectionFlag==0">加入收藏</el-button>
                <el-button type="text" v-if="collectionFlag>0" @click="unCn">已收藏</el-button>
            </div>
        </div>
        <div class="detailBox w1240" style="height: 679px;margin-bottom: 50px;">
            <el-tabs class="kjscDetailTabs" @tab-click="kjscDetailTabsCk">
                <el-tab-pane label="产品详情">
                    <div class="productD" v-html="projectDetail.details"></div>
                </el-tab-pane>
                <el-tab-pane label="机构信息">
                    <template v-if="undefined!=compayCard">
                        <el-image :src="compayCard.businesslicenseurl" style="width: 100%;"></el-image>
                    </template>
                </el-tab-pane>
                <el-tab-pane label="服务评价">
                    <div class="evalBox">
                        <h3>产品评价</h3>
                        <el-row type="flex" align="middle" class="elMark" style="padding: 34px 43px 32px">
                            <el-col :span="4"><p>好评度</p><p>
                                <template v-if="evaluatePageTotal>0">
                                    {{(evaluatePageTotal/statisticsEvaluateObj.praise)*100}}%
                                </template>
                                <template v-if="evaluatePageTotal==0">
                                    100%
                                </template>
                            </p></el-col>
                            <el-col :span="14">
                                <span class="check">与描述一致</span><span>提供的资料详细</span><span>效率高</span><span>服务态度好</span>
                            </el-col>
                        </el-row>
                        <el-row type="flex" align="middle" class="elChoice">
                            <el-col :span="15">
                                <el-link class="pjCheck">全部评价({{evaluatePageTotal}})</el-link>
                                <el-link>晒图({{undefined==statisticsEvaluateObj.imgnum?0:statisticsEvaluateObj.imgnum}})</el-link>
                                <el-link>好评({{undefined==statisticsEvaluateObj.praise?0:statisticsEvaluateObj.praise}})</el-link>
                                <el-link>中评({{undefined==statisticsEvaluateObj.commMiddle?0:statisticsEvaluateObj.commMiddle}})</el-link>
                                <el-link>差评({{undefined==statisticsEvaluateObj.negative?0:statisticsEvaluateObj.negative}})</el-link>
                            </el-col>
                            <el-col :span="3"><el-checkbox >只看当前产品图片</el-checkbox></el-col>
                            <el-col :span="6"></el-col>
                        </el-row>
                        <div style="padding: 25px 30px;">
                            <el-row v-for="emsg in evaluateMsgList" :key="'evmsg_'+emsg.id">
                                <el-col :span="4" class="elUser">
                                    <el-avatar v-if="undefined==emsg.userPhoto" :size="41" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                                    <el-avatar v-else :size="41" :src="emsg.userPhoto"></el-avatar>
                                    <span>{{emsg.userName}}</span>
                                </el-col>
                                <el-col :span="20">
                                    <el-rate class="rateComplate"
                                            v-model="emsg.productGrade"
                                            disabled
                                            :colors="['#CA0000', '#CA0000', '#CA0000']">
                                    </el-rate>
                                    <div><span>评价内容：</span><span>
                                        {{emsg.remark}}
                                    </span></div>
                                    <div class="elImgList" style="margin-top: 27px;display: flex;">
                                        <template v-if="undefined!=emsg.imgs && ''!=emsg.imgs">
                                            <div  v-for="s in emsg.imgs.split(';')"  :key="s">
                                                <img style="width: 81px;height: 81px;" v-if="''!=s" :src="s"/>
                                            </div>
                                        </template>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="相关协议下载">
                    <div class="dowmBox">
                        <!--<i class="el-icon-document"></i>-->
                        <img style="vertical-align: -8px;" src="../../images/zichanjy/yasuobao.png"/>
                        <span style="font-size:16px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);margin-left: 5px;">相关协议附件</span>
                        <span v-if="undefined == projectDetail.protocolUrl ||''==projectDetail.protocolUrl" style="text-decoration:none;margin-left: 51px;color:rgba(3,154,205,1);font-size:16px;font-family:'Microsoft YaHei';font-weight:400;">暂未上传</span>
                        <a  v-if="undefined != projectDetail.protocolUrl && ''!=projectDetail.protocolUrl"  :href="projectDetail.protocolUrl" download="协议附件" style="text-decoration:none;margin-left: 51px;color:rgba(3,154,205,1);font-size:16px;font-family:'Microsoft YaHei';font-weight:400;">下载</a>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
        <el-dialog :visible.sync="outerVisible" class="chatBox">
            <div slot="title"><span style="font-size: 18px;">您当前正在给专属经纪人留言中</span><span class="dotting">...</span></div>
            <el-dialog
                    width="30%"
                    :visible.sync="showRate" class="rateBox"
                    append-to-body>
                <div style="text-align: center;padding: 20px 0;">
                    <p>请您为我的服务评分：<em style="color: #ca0000;font-style: normal">{{textVal[rateVal - 1]}}</em></p>
                    <el-rate v-model="rateVal" class="btnRate" :texts="textVal"></el-rate>
                </div>
                <div style="text-align: center;border-top: 0.1px solid #CCC;"><el-button type="text" style="font-weight: 400;" @click="saveEvaluate">发送提交</el-button></div>
            </el-dialog>
            <div class="chatList" style="overflow-x:auto;" ref="chatList">
                <ul>
                    <li>
                        <div class="chatImg"><el-avatar :size="size" :src="currenBroker.photo"></el-avatar></div>
                        <div class="chatInfo">
                            <h4>{{currenBroker.name}}经纪人 {{currenTime}}</h4>
                            <p>欢迎您来咨询！我将成为您的专属顾问，有什么问题、不了解的地方，我将会一一为您答疑解惑，请问有什么地方可以帮助您的呢？</p>
                        </div>
                    </li>
                    <template v-for="brokerAdv in brokerAdvList" >
                        <li v-if="brokerAdv.sendType==2" :key="brokerAdv.id+'_brokerAdv'">
                            <div class="chatImg"><el-avatar :size="size" :src="brokerAdv.brokerPhoto"></el-avatar></div>
                            <div class="chatInfo">
                                <h4>{{brokerAdv.brokerName}}经纪人 {{brokerAdv.createTime}}</h4>
                                <p>{{brokerAdv.content}}</p>
                            </div>
                        </li>
                        <li class="right" :key="brokerAdv.id+'_brokerAdv'" v-else>
                            <div class="chatInfo">
                                <h4>{{brokerAdv.createTime}}</h4>
                                <p>{{brokerAdv.content}}</p>
                            </div>
                            <div class="chatImg"><el-avatar :size="size" :src="brokerAdv.userPhoto"></el-avatar></div>
                        </li>
                    </template>
                </ul>
            </div>
            <div class="chatBtn">
                <el-button class="el-icon-aim"></el-button>
                <el-button class="el-icon-scissors"></el-button>
                <el-button class="el-icon-bangzhu" style=""></el-button>
                <el-button class="el-icon-document-copy"></el-button>
                <el-button class="el-icon-bottom"></el-button>
                <el-button class="el-icon-star-off" @click="showRate=true"></el-button>
            </div>
            <div class="setChart">
                <el-form :model="form">
                    <el-form-item>
                        <el-input type="textarea" v-model="form.msg" :rows="3"></el-input>
                    </el-form-item>
                </el-form>
                <el-button type="primary" style="background: linear-gradient(90deg,#f74a0d 0%,#fa963d 100%);border: none;" class="setBtn" @click="sendBrokerAdv">发送</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {qryProjectList} from "../../api/projectDw/projectDw";
    import {queryManList,saveBrokerEvaluate} from "../../api/middleman/middelman";
    import {qryBrokerAdvisory,saveBrokerAdvisory} from "../../api/admin/index";
    import {getToken} from '../../utils/auth';
    import {saveUserShopping,qryUserShoppingCartList} from "../../api/user/shoppingCart";
    import {qryEvaluateOrderList,statisticsEvaluate} from "../../api/user/evaluateOrder";
    import {saveRecord,qryRecordList,deleteRecordByIds} from "../../api/user/serverOperate";
    import {getBhwCompanyCardInfo} from "../../api/user/user";
    export default {
        name: "kjscDetail",
        data() {
            return{
                circleUrl:"https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
                outerVisible: false,
                form: {
                    msg: ''
                },
                size:"large",
                projectDetail:{
                    projectCode:"",
                    projectName:"",
                    homeImg:"",
                    introduce:'',
                    details:'',
                    unitPrice:'',
                    industryName:'',
                    userId:''
                },
                evaluatePageTotal:0,
                evaluateOrderParam:{
                    productId:'',
                    pageSize:5,
                    curPage:1
                },
                statisticsEvaluateObj:{},
                evaluateMsgList:[],
                brokerList:[],
                brokerAdvList:[],
                currenBroker:{photo:''},
                currenTime:'',
                elValue:"5",
                showRate:false,
                rateVal:'',
                textVal:['2分','4分','6分','8分','10分'],
                patentType:{1:"发明专利",2:"实用新型专利",3:"外观专利"},
                compayCard:{},
                collectionFlag:0
            }
        },
        created(){
            if(this.$route.query.shareId){
                qryProjectList({"id":this.$route.query.shareId}).then(response => {
                    this.projectDetail = response.data[0];
                    this.getBhwCompanyCardInfo();
                });
            }else if(this.$route.query.productId){
                qryProjectList({"id":this.$route.query.productId}).then(response => {
                    this.projectDetail = response.data[0];
                    this.getBhwCompanyCardInfo();
                });
            }else if(this.$route.query.hisProductId) {
                qryProjectList({"id": this.$route.query.hisProductId}).then(response => {
                    this.projectDetail = response.data[0];
                    this.getBhwCompanyCardInfo();
                });
            }else{
                this.projectDetail = this.$route.query.projectObj;
                if(undefined == this.projectDetail.userId){
                    this.projectDetail = JSON.parse(sessionStorage.getItem(`kjsc-projectDetail`));
                }else{
                    sessionStorage.setItem(`kjsc-projectDetail`,JSON.stringify(this.projectDetail));
                }
                this.getBhwCompanyCardInfo();
            }
            this.getUserManagerReq();
            this.statisticsEvaluate();
            this.saveRecord(1);

        },
        methods:{
            getBhwCompanyCardInfo(){
                if(undefined != this.projectDetail.userId){
                    getBhwCompanyCardInfo({userId:this.projectDetail.userId}).then(response=>{
                        this.compayCard = response.data;
                    });
                    this.$nextTick(function(){
                        if(this.$store.state.user.userInfo){
                            this.qryUserRecord();
                        }
                    })
                }
            },
            qryUserRecord(){
                var par ={
                    userId:this.$store.state.user.userInfo.id,
                    operateType:2,
                    serverId:this.projectDetail.id,
                    type:this.projectDetail.projectType
                }
                qryRecordList(par).then(response=>{
                    if(response.data.length>0){
                        this.collectionFlag = response.data[0].id;
                    }else{
                        this.collectionFlag =0;
                    }
                });
            },
            statisticsEvaluate(){
                statisticsEvaluate({productId:this.projectDetail.id}).then(response=>{
                    if(response.data){
                        this.statisticsEvaluateObj = response.data;
                    }
                });
            },
            saveEvaluate(){
                var par = {
                    userId:this.$store.state.user.userInfo.id,
                    borkerId:this.currenBroker.id,
                    grade:this.rateVal
                };
                saveBrokerEvaluate(par).then(response=>{
                    this.showRate = false;
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                });
            },
            unCn(){
                deleteRecordByIds({ids:this.collectionFlag}).then(response=>{
                    this.$message({
                        message: '您已取消收藏',
                        type: 'success'
                    });
                    this.qryUserRecord();
                });
            },
            saveRecord(operateType){
                if(getToken()) {
                    var par ={
                        userId:this.$store.state.user.userInfo.id,
                        operateType:operateType,
                        serverId:this.projectDetail.id,
                        serverName:this.projectDetail.projectName,
                        img:this.projectDetail.homeImg,
                        price:this.projectDetail.unitPrice,
                        type:this.projectDetail.projectType,
                        path:this.$route.path
                    }
                    saveRecord(par).then(response => {
                        if (operateType == 2) {
                            this.$message({
                                message: '收藏成功',
                                type: 'success'
                            });
                            this.qryUserRecord();
                        }
                    });
                }else{
                    if (operateType == 2) {
                        this.$message.error('您还未登录,请登录后再操作！');
                    }
                }
            },
            donwLoadFile(){

            },
            getTime:function(){
                var _this = this;
                let yy = new Date().getFullYear();
                let mm = new Date().getMonth()+1;
                let dd = new Date().getDate();
                let hh = new Date().getHours();
                let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
                let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
                _this.currenTime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
            },
            advisoryBroker(broker){
                this.currenBroker = broker;
                if(getToken()){
                    this.getTime();
                    this.outerVisible = true;
                    this.qryBrokerAdvisory();
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            kjscDetailTabsCk(tab, event){
                if('服务评价'==tab.label){
                    this.evaluateOrderParam.productId = this.projectDetail.id;
                    qryEvaluateOrderList(this.evaluateOrderParam).then(response=>{
                        this.evaluateMsgList = response.data;
                        this.evaluatePageTotal = response.page.counts;
                    });
                }
            },
            qryBrokerAdvisory(){
                qryBrokerAdvisory({userId:this.$store.getters.userInfo.id,brokerId: this.currenBroker.id,pageSize:100000}).then(response=>{
                    this.brokerAdvList = response.data;
                    this.$nextTick(function () {
                        this.$refs.chatList.scrollTop =  this.$refs.chatList.scrollHeight;
                    })
                });
            },
            sendBrokerAdv(){
                if(this.form.msg.trim()==''){
                    this.$message.error('不能发送为空！');
                    return;
                }
                var par ={
                    userId:this.$store.getters.userInfo.id,
                    brokerId:this.currenBroker.id,
                    content:this.form.msg,
                    sendType:1
                };
                saveBrokerAdvisory(par).then(response=>{
                    this.form.msg = '';
                    this.qryBrokerAdvisory();
                    /*this.$message({
                        message: '发送成功!',
                        type: 'success'
                    });*/
                });
            },
            getUserManagerReq(){
                queryManList({pageSize:6,borkerId:1,status:3}).then(response=>{
                    if(response.data){
                        this.brokerList = response.data;
                        if(this.brokerList.length>0){
                            this.currenBroker = this.brokerList[0];
                            this.$nextTick(function () {
                                if(getToken()){
                                    this.advisoryBroker(this.brokerList[0]);
                                }
                            })
                        }
                    }
                });
            },
            ordersClick(){
                if(getToken()){
                    const order={
                        itemName:this.projectDetail.projectName,
                        itemLogo:this.projectDetail.homeImg,
                        unitPrice:this.projectDetail.unitPrice,
                        itemNumber:1,
                        totalPrice:this.projectDetail.unitPrice,
                        projectId:this.projectDetail.id,
                        sellerName:this.projectDetail.userName,
                        userId:this.projectDetail.userId
                    };
                    this.$router.push({path:"/orderDetail",query:{orderProject:order}});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            joinCart(){
                if(getToken()){
                    const order={
                        userId:this.$store.state.user.userInfo.id,
                        itemName:this.projectDetail.projectName,
                        itemLogo:this.projectDetail.homeImg,
                        unitPrice:this.projectDetail.unitPrice,
                        sellerName:this.projectDetail.userName,
                        itemNumber:1,
                        totalPrice:this.projectDetail.unitPrice,
                        projectId:this.projectDetail.id
                    };
                    qryUserShoppingCartList({userId:this.$store.state.user.userInfo.id, projectId:this.projectDetail.id})
                        .then(response=>{
                            if(response.data.length>0){
                                this.$message.error('该产品您已加入购物车！');
                            }else{
                                saveUserShopping(order).then(response=>{
                                    this.$message({
                                        message: '加入成功',
                                        type: 'success'
                                    });
                                });
                            }
                    });
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/market.css";
    .kjscDetailTabs /deep/ .el-tabs__nav-wrap::after {height: 1px;}
    /deep/ .el-tabs__content{height: 622px;overflow-y: scroll;}
    .proBtn /deep/.el-button.el-button--default {
        padding: 8px 39px;
        border-radius: 5px;
        font-size:18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color:rgba(255,255,255,1);
    }
    .kjscDetailTabs /deep/ .el-tabs__item{
        font-family:'Microsoft YaHei';
        padding: 0 20px;
        height: 40px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        line-height: 40px;
        display: inline-block;
        list-style: none;
        font-size: 18px;
        font-weight: 400;
        color:rgba(29,29,29,1);
        position: relative;
        border-top: 0px solid;
    }
    .kjscDetailTabs /deep/.is-active{
        font-size:18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color:rgba(202,0,0,1);
        border-top: 3px solid;vertical-align:-3px;
    }
    /deep/ .el-tabs--bottom .el-tabs__item.is-bottom:nth-child(2), /deep/ .el-tabs--bottom .el-tabs__item.is-top:nth-child(2),
    /deep/ .el-tabs--top .el-tabs__item.is-bottom:nth-child(2), /deep/ .el-tabs--top .el-tabs__item.is-top:nth-child(2){padding-left: 20px;}
    .chatList::-webkit-scrollbar {
        /*滚动条整体样式*/
        width : 10px;  /*高宽分别对应横竖滚动条的尺寸*/
        height: 1px;
    }
    .chatList::-webkit-scrollbar-thumb {
        /*滚动条里面小方块*/
        border-radius: 10px;
        box-shadow   : inset 0 0 5px rgba(0, 0, 0, 0.2);
        background   : #024ecc;
    }
    .chatList::-webkit-scrollbar-track {
        /*滚动条里面轨道*/
        box-shadow   : inset 0 0 5px rgba(0, 0, 0, 0.2);
        border-radius: 10px;
        background   : #ededed;
    }
    .elUser{display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;}
    .elUser span{color: #7D7D7D;margin-left: 16px;}
    /deep/ .rateComplate.el-rate{height: 24px;margin:12px 0 29px;}
    .rateComplate /deep/ .el-rate__icon{font-size: 24px;margin-right: 0;}
    .elImgList>img{width: 58px;height: 58px;margin-right: 10px;}
    .rateBox /deep/ .el-dialog__header{display: none;}
    .rateBox /deep/ .el-dialog__body{padding: 0;}
    /deep/ .btnRate.el-rate{height: 40px;margin-top:20px;}
    .btnRate /deep/ .el-rate__icon{font-size: 40px;margin-right: 0;}

    /deep/ .btnColorBlue.btnMaket,/deep/ .btnColorOrg.btnMaket{width: 152px;height: 36px;padding: 0;border: none;}
    /deep/ .el-button.el-button--default.btnMaket span{font-size: 18px;font-weight:normal;}
    /deep/ .el-button.el-button--default.btnAsk{width:44px;height:25px;font-size: 12px;font-weight: 400;padding: 0;border: none;}
    /deep/ .el-button.el-button--default.btnAsk span{font-size: 12px;}
    .introduct .introductInfo{overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 5;line-clamp: 5;-webkit-box-orient: vertical;}

</style>