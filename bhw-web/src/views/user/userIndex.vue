<template>
    <div class="userIndex">
        <TopBar></TopBar>
        <el-container style="background: #F8F8F8">
            <div class="userContainer">
                <el-aside style="width: 198px;margin-right: 27px;">
                    <div class="userCenterAside">
                        <el-row class="bgColorF" style="padding: 15px 0 10px;">
                            <div>
                                <el-avatar :size="100" :src="userInfo.photo" v-if="userInfo.photo!=null"></el-avatar>
                            </div>
                            <label style="line-height: 30px;">{{undefined==userInfo.nickName || '' == userInfo.nickName? userInfo.userName:userInfo.nickName}}</label>
                            <el-row type="flex" justify="center">
                                <el-button  class="btnColorOrg" style="height: 29px;line-height: 7px;font-size: 10px;" round>{{userWalletInfo.tremaining}} T币</el-button>
                                <el-button class="btnColorBlue" @click="toChongzhi"  style="height: 29px;line-height: 7px;font-size: 10px;" round>T币充值</el-button>
                            </el-row>
                        </el-row>
                        <el-row class="bgColor" style="margin-top: 22px;text-align: left;padding: 0;border: none;">
                            <el-menu ref="userIndexMenu" :default-active="activeMenuPath" class="el-menu-vertical-demo"
                                     @select="userIndexMenuSelect" @open="userIndexMenuOpen" style="border-right: none;">
                                <template v-for="lm in leftMenu">
                                    <template v-if="lm.childM.length>0">
                                        <el-submenu :index="lm.path" :key="lm.name">
                                            <template slot="title">
                                                <i class="icon-user_menu"></i>
                                                <span>{{lm.name}}</span>
                                            </template>
                                            <el-menu-item-group class="sonMenuItem">
                                                <el-menu-item v-for="ch in lm.childM" :key="ch.sort" :index="ch.path" :class="ch.sort==83?'setWord':''">
<!--                                                    <i class="icon-user_ch_menu"></i>-->
                                                    <span v-if="ch.name=='技术合同/科技成果认定登记'" style="display: inline-block;width: 91px;">{{ch.name}}</span>
                                                    <span v-else >{{ch.name}}</span>
                                                </el-menu-item>
                                            </el-menu-item-group>
                                        </el-submenu>
                                    </template>
                                    <template v-else>
                                        <el-menu-item :index="lm.path" :key="lm.name">
                                            <i class="icon-user_menu"></i>
                                            <span slot="title">{{lm.name}}</span>
                                        </el-menu-item>
                                    </template>
                                </template>
                            </el-menu>
                        </el-row>
                    </div>
                </el-aside>
                <el-main v-if="activeMenuPath!=''" class="bgColorF">
                    <component :is="cpActiveMenuPath" v-on:oprVisibleId-event="oprVisible" v-on:upMenuAc-event="upMenuAc"  v-on:upUserInfo-event="upUserInfo" :userInfo="userInfo"></component>
                </el-main>
                <el-dialog title="" :visible.sync="dialogVisibleUser">
                    <div style="text-align: center;">
                        <ul>
                            <li><img src="../../images/user/gantanhao.png"/></li>
                            <li style="margin-top: 49px; font-size:24px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(248,114,22,1);">您需先进行实名认证，方可使用服务管理</li>
                            <li style="margin-top: 44px; font-size:16px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">点击下方按钮，进入实名认证，工作人员将于一个工作日内为您审核。</li>
                            <li style="margin-top: 48px;">
                                <el-button size="medium" v-if="userInfo.type==1" @click="toVisible" type="primary" round>个人实名认证</el-button>
                                <el-button size="medium" v-else @click="toVisible" type="primary" round>企业实名认证</el-button>
                            </li>
                        </ul>
                    </div>
                </el-dialog>
                <el-dialog title="" :visible.sync="dialogVisibleUpload">
                    <div style="text-align: center;">
                        <ul>
                            <li><img width="120" height="120" src="../../images/user/gantanhao.png"/></li>
                            <li style="margin-top: 44px; font-size:16px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                                尊敬的北部湾中心用户，上传发布产品及服务需为平台商家用户 由平台审核通过之后，即可上传发布
                            </li>
                            <li style="margin-top: 48px;">
                                <el-button size="medium" @click="applyId" type="primary" round>申请</el-button>
                                <el-button size="medium" @click="dialogVisibleUpload=false"  type="info" round>取消</el-button>
                            </li>
                        </ul>
                    </div>
                </el-dialog>
                <el-dialog title="" :visible.sync="dialogVisibleApply">
                    <div style="text-align: center;">
                        <ul>
                            <li><img width="107" height="107" src="../../images/user/wancheng.png"/></li>
                            <li style="margin-top: 44px; font-size:16px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(29,29,29,1);">
                                您已成功提交申请 一个工作日内，审核完毕，请您留意系统消息/短信提醒
                            </li>
                        </ul>
                    </div>
                </el-dialog>
            </div>
        </el-container>
        <FootBar></FootBar>
    </div>
</template>

<script>
    import ORDERMANAGE from "../../views/user/order/orderManage";
    import USERMANAGE from "../../views/user/userManage/userManage";
    import SHOPPINGCART from "../../views/user/shoppingCart/shoppingCart";
    import INTELLECTUAL from "../../views/user/serverManage/intellectual";
    import ORDERDETAIL from "../../views/user/order/orderDetail";
    import PAYORDERDETAIL from "../../views/user/myPurse/payOrderDetail";
    import TXORDERDETAIL from "../../views/user/myPurse/txOrderDetail";
    import TBORDERDETAIL from "../../views/user/myPurse/tbOrderDetail";
    import EVALUATEORDER from "../../views/user/order/evaluateOrder";
    import HISTORYINDEX from "../../views/user/historyRecord/historyIndex";
    import ADDSERVICEINDEX from "../../views/user/addedService/addServiceIndex"; //增值服务
    import TECHNOLOGYINDEX from "../../views/user/technology/technologyIndex";
    import CONTRACTINDEX from "../../views/user/contract/contractIndex";
    import PROPERTYINDEX from "../../views/user/property/propertyIndex";
    import LEGALSERVICEINDEX from "../../views/user/legalService/LegalServiceIndex";
    import MANLOGININDEX from "../../views/user/manLogin/manLoginIndex";
    import MESSAGEINDEX from "../../views/user/message/messageIndex"; //系统消息
    import COLLECTION from "../../views/user/collection/myCollection"; //我的收藏
    import MYPURSE from "../../views/user/myPurse/mainPage";
    import FootBar from "../../views/layout/FootBar";
    import TopBar from "../../views/layout/TopBar";
    import {getUserDetailByToken, getUserSubsInfo,upBhwUser} from "../../api/user/user";
    import {qryUserWallet} from "../../api/user/userWallet";
    import {
        getToken, removeToken
    } from '../../utils/auth';
    import {upUserMemberByPay} from '../../api/user/user';
    export default {
        components: {
            TopBar,
            FootBar,
            "orderManage": ORDERMANAGE,
            "userManage": USERMANAGE,
            "shoppingCart": SHOPPINGCART,
            "intellectual": INTELLECTUAL,
            "messageIndex":MESSAGEINDEX,
            "addServiceIndex":ADDSERVICEINDEX,
            "intellectual": INTELLECTUAL,
            "orderDetail":ORDERDETAIL,
            "payOrderDetail":PAYORDERDETAIL,
            "txOrderDetail":TXORDERDETAIL,
            "tbOrderDetail":TBORDERDETAIL,
            "evaluateOrder":EVALUATEORDER,
            "historyIndex":HISTORYINDEX,
            "evaluateOrder":EVALUATEORDER,
            "technologyIndex":TECHNOLOGYINDEX,
            "contractIndex":CONTRACTINDEX,
            "propertyIndex":PROPERTYINDEX,
            "LegalServiceIndex":LEGALSERVICEINDEX,
            "manLoginIndex":MANLOGININDEX,
            "messageIndex":MESSAGEINDEX,
            "myCollection":COLLECTION,
            "myPurse":MYPURSE
        },
        name: "userIndex",
        data() {
            return {
                dialogVisibleApply:false,
                dialogVisibleUser:false,
                dialogVisibleUpload:false,
                userInfo: {
                    photo: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
                    phoneNo: "13850932312"
                },
                defPhoto: require('@/images/defaultHeadPhoto.png'),
                activeMenuPath: "",
                cpActiveMenuPath:"",
                fristActiveMenuPath: "",
                activeMenuFlag: false,
                leftMenu: [
                    {name: "我的订单", path: "orderManage", childM: [], sort: "1"},
                    {name:"我的收藏",path:"myCollection",childM:[],sort:"2"},
                    {name:"我的购物车",path:"shoppingCart",childM:[],sort:"3"},
                    {name:"浏览历史",path:"historyIndex",childM:[],sort:"4"},
                    {name:"我的钱包",path:"myPurse",childM:[],sort:"5"},
                    {name:"我的增值服务",path:"addServiceIndex",childM:[],sort:"6"},
                    {name: "用户管理", path: "userManage", childM: [], sort: "7"},
                    {name: "服务管理", path: "服务管理", childM: [
                            {name: "知识产权", path: "intellectual", sort: "81"},
                            {name: "技术相关供需", path: "technologyIndex", sort: "82"},
                            {name: "技术合同/科技成果认定登记", path: "contractIndex", sort: "83"},
                            {name: "财税服务", path: "propertyIndex", sort: "84"},
                            {name: "法律服务", path: "LegalServiceIndex", sort: "85"},
                            {name: "经纪人注册", path: "manLoginIndex", sort: "86"}
                        ], sort: "8"},
                    {name:"通知消息",path:"messageIndex",childM:[],sort:"9"}
                ],
                userWalletInfo:{tremaining:'0'}
            }
        },
        watch:{
            '$route.path':function (newVal,oldVal) {
                if(newVal!='/userCenter' && newVal!='/'){
                    this.cpActiveMenuPath = newVal.substring(newVal.lastIndexOf("/")+1,newVal.length);
                    if(this.cpActiveMenuPath=='orderDetail'){
                        this.activeMenuPath = 'orderManage';
                    }
                    if(this.cpActiveMenuPath=='payOrderDetail' ||
                        this.cpActiveMenuPath=='txOrderDetail' ||
                        this.cpActiveMenuPath=='tbOrderDetail'){
                        this.activeMenuPath = 'myPurse';
                    }
                }else{
                    if(oldVal.indexOf('/userCenter/')<0){
                        this.activeMenuPath = this.leftMenu[0].path;
                        this.fristActiveMenuPath = this.$route.query.menuActiveFlag;
                        this.cpActiveMenuPath = this.$route.query.menuActiveFlag;
                    }else{
                        if(this.$route.query.menuActiveFlag){
                            this.activeMenuPath = this.$route.query.menuActiveFlag;
                            this.fristActiveMenuPath = this.$route.query.menuActiveFlag;
                            this.cpActiveMenuPath = this.$route.query.menuActiveFlag;
                        }
                    }
                }
            }
        },
        created() {
            console.log('getToken()',getToken());
            getUserDetailByToken({token: getToken()}).then(response => {
                if (response.data) {
                    this.userInfo = response.data;
                    if (undefined == this.userInfo.photo || "" == this.userInfo.photo) {
                        this.userInfo.photo = this.defPhoto;
                    }
                    qryUserWallet({userId:this.$store.state.user.userInfo.id}).then(response=>{
                        if(response.data){
                            this.userWalletInfo = response.data;
                            if(response.data.tremaining>10000){
                                this.userWalletInfo.tremaining = parseInt(response.data.tremaining/10000)+'.'+(response.data.tremaining%10000)+'万';
                            }
                            // this.userWalletInfo = response.data;
                        }

                    });
                    getUserSubsInfo({userId: this.userInfo.id}).then(resp => {
                        if (resp.data) {
                            this.userInfo.subId = resp.data.id;
                            this.userInfo.unitName = resp.data.unitName;
                            this.userInfo.duty = resp.data.duty;
                            this.userInfo.school = resp.data.school;
                            this.userInfo.education = resp.data.education;
                            this.userInfo.qualification = resp.data.qualification;
                        }
                    });
                    if(this.$route.query.walletPayFlag){
                        upUserMemberByPay({userId: this.userInfo.id}).then();
                        this.activeMenuPath = "myPurse";
                        this.fristActiveMenuPath = "myPurse";
                        this.cpActiveMenuPath = "myPurse";
                    }if(this.$route.query.menuActiveFlag){
                        this.activeMenuPath = this.$route.query.menuActiveFlag;
                        this.fristActiveMenuPath = this.$route.query.menuActiveFlag;
                        this.cpActiveMenuPath = this.$route.query.menuActiveFlag;
                    }else{
                        this.activeMenuPath = "orderManage";
                        this.fristActiveMenuPath = "orderManage";
                        this.cpActiveMenuPath = "orderManage";
                    }
                } else {
                    removeToken();
                    this.$message.error('登录失效,请您重新登录!');
                    this.$router.push({
                        path: '/home'
                    });
                }
            });
        },
        methods: {
            toChongzhi(){
                this.activeMenuPath = 'myPurse';
                this.cpActiveMenuPath = "myPurse";
            },
            userIndexMenuSelect(index, indexPath) {
                if(this.$route.path!='/userCenter'){
                    this.$router.push({
                        path: '/userCenter'
                    });
                }
                this.activeMenuPath = index;
                this.cpActiveMenuPath = index;
                if (this.activeMenuFlag) {
                    this.$nextTick(() => {
                        this.activeMenuPath = this.fristActiveMenuPath;
                        this.cpActiveMenuPath = this.fristActiveMenuPath;
                    })
                } else {
                    this.fristActiveMenuPath = index;
                }
            },
            userIndexMenuOpen(index, indexPath) {
                if(index=='服务管理' && this.userInfo.status==2){
                    this.$refs.userIndexMenu.close(index);
                    this.dialogVisibleUser = true;
                }
            },
            toVisible(){
                this.dialogVisibleUser = false;
                this.userInfo.toVisible=true;
                this.activeMenuPath = "userManage";
                this.cpActiveMenuPath = "userManage";
            },
            upMenuAc(ac){
                this.activeMenuPath = ac;
                this.cpActiveMenuPath = ac;
            },
            upUserInfo(userInfo) {
                this.userInfo = userInfo;
            },
            oprVisible(){
                this.dialogVisibleUpload=true;
            },
            applyId(){
                this.dialogVisibleUpload=false;
                upBhwUser({id:this.userInfo.id,merchantFlag:3}).then(response=>{
                    this.dialogVisibleApply = true;
                    var that = this;
                    setTimeout(function(){
                        that.dialogVisibleApply=false;
                    },2000)
                });
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/userCenter.css";

    .userContainer {
        width: 1240px;
        margin: 26px auto;
        display: flex;
        display: -webkit-flex;
    }

    /*/deep/ .el-button.el-button--default span {*/
    /*    color: rgba(29, 29, 29, 1);*/
    /*}*/

    /deep/ .sonMenuItem.el-menu-item-group > ul {
        margin: 0 18px;
        box-shadow: 0px 3px 7px 0px rgba(0, 0, 0, 0.35);
    }

    /deep/ .sonMenuItem.el-menu-item-group ul li {
        padding: 0 35px !important;
        min-width: inherit;
    }
    /deep/ .sonMenuItem.el-menu-item-group ul li.setWord{line-height: inherit; padding: 8px 35px 0!important; white-space:normal;word-break:break-all;word-wrap: break-word;}
    .icon-user_menu{
        width: 16px;height: 16px;background:rgba(255,255,255,1);box-shadow:0px 1px 2px 0px rgba(8,1,3,0.59);border-radius:50%;margin-right: 20px;margin-left: 10px; display: inline-block;}
    .icon-user_ch_menu{
        width: 16px;height: 16px;background:#CCCCCC;box-shadow:0px 1px 2px 0px rgba(8,1,3,0.59);border-radius:50%;margin-right: 20px;margin-left: 10px; display: inline-block;}
   .el-menu-vertical-demo /deep/ .el-submenu__icon-arrow{margin-top: -4px;}
</style>