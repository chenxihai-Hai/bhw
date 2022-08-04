<template>
    <div class="vipLogin">
        <TopBar></TopBar>
        <hr class="topLine">
        <div style="width:1243px;height:730px;border:1px solid rgba(204,204,204,1);margin: 20px auto 160px;">
            <h4 class="dlName">会员登陆</h4>
            <hr style="margin-left:12px;width:617px;height:1px;background: #CCC;"/>
            <el-row style="position: relative;">
                <el-col style="width: 742px;">
                    <el-form :model="form" ref="form" style="width: 268px;margin-left: 172px;margin-top: 82px" class="demo-login-ruleForm">
                        <el-form-item :rules="[{required: true, message: '用户名不能为空'}]" prop="name">
                            <el-input v-model="form.name"  autocomplete="off" placeholder="手机（仅个人）登录账号（仅机构）" type="name"  ></el-input>
                        </el-form-item>
                        <el-form-item :rules="[{required: true, message: '密码不能为空'}]" prop="password">
                            <el-input v-model="form.password" autocomplete="off" placeholder="请输入密码" show-password type="password"></el-input>
                        </el-form-item>
                        <router-link :underline="false" :to="{path:'/forgetPassword'}" style="width:100%;height:12px;font-size:12px;color:rgba(204,204,204,1);text-align: right;display: block;text-decoration:none;">忘记密码</router-link>
                        <el-form-item>
                            <el-button class="btnColorBlue" style=" margin-top: 30px;width: 268px;height:46px;padding: 0;border: none;"  @click="submitForm('form',form)">登录</el-button>
                        </el-form-item>
                        <!--<el-divider  content-position="center">微信</el-divider>-->
                        <el-form-item>
                            <el-button class="btnWx" @click="wxLogin" type="primary"></el-button>
                            <el-button class="btnQq" @click="qqLogin" type="primary"></el-button>
                        </el-form-item>

                        <!--<div id="wb_connect_btn"></div>-->
                        <!--<wb:login-button type="3,2" onlogin="wbLogin" >登录</wb:login-button>-->
                       <!-- <span id="qqLoginBtn"></span>-->
                    </el-form>
                </el-col>

                <hr style="width: 1px;height: 471px;background: #CCC;position:absolute;top: 20px;left: 755px;">
                <el-col style="width: 480px;padding-top: 80px;" v-show="!wxLoginFlag">
                        <el-row>
                            <el-col :offset="2">还没有账号</el-col>
                        </el-row>
                        <el-row style="margin-top: 22px">
                            <el-col :offset="2">
                                <router-link to="/register">
                                    <el-button class="btnColorOrg" style="width: 206px;height: 46px;padding: 0;border: none;">立即注册</el-button>
                                </router-link>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 46px">
                            <el-col :offset="2">
                                <p style="width: 368px;"></p>
                            </el-col>
                        </el-row>
                </el-col>
                <div id="login_container" style="float: right;margin-right: 100px;height: 700px;">
                </div>
            </el-row>
        </div>
        <FootBar></FootBar>
    </div>
</template>

<script>
    import TopBar from "../../views/layout/TopBar";
    import FootBar from "../../views/layout/FootBar";
    import {userLoginReq} from "../../api/register/register";
    import {
        setQqToken,getTempInfo
    } from '../../utils/auth';
    export default {
        name: "vip-login",
        components: {FootBar, TopBar},
        data(){
            return{
                form:{
                    name:"", password:""
                },
                wxCode:null,
                wx_access_token:null,
                wxLoginFlag:false
            }
        },
        created(){
            var href = window.location.href;
            var access_token = "";
            if(href.indexOf("access_token=")>0){
                access_token = href.substring(href.indexOf("access_token=")+"access_token=".length,href.indexOf("&expires_in"));
                var that = this;
                QC.Login.getMe(function(openId, accessToken){
                    QC.api("get_user_info", {access_token:access_token,oauth_consumer_key:"101867791",openid:openId})
                        .success(function(s){
                            var qqUserInfo={
                                userName:s.data.nickname,
                                nickName:s.data.nickname,
                                sex:s.data.sex=='男'?1:2,
                                photo:s.data.figureurl_qq,
                                qq:openId
                            };
                            setQqToken(qqUserInfo);
                            if(getTempInfo("QQ_BIND")){
                                that.$router.push({path:"/userCenter",query:{menuActiveFlag:'userManage'}});
                            }else{
                                that.$router.push({path:"/home"});
                            }
                        })
                        .error(function(f){
                        })
                })
            }
          /*  WB2.anyWhere(function (W) {
                console.log(W);
                W.widget.connectButton({
                    id: "wb_connect_btn",
                    type: '3,2',
                    callback: {
                        login: function (o) { //登录后的回调函数
                            alert("login: " + o.screen_name)
                        },
                        logout: function () { //退出后的回调函数
                            alert('logout');
                        }
                    }
                });
            });*/
        },
        methods:{
            wbLogin(o){
                /*WB2.login(function(o){
                    console.log('微博登录',o);
                });*/
            },
            submitForm(formName,form) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var name=form.name;
                        var username;
                        var phone;
                        var pwd;
                        if(!(/^1[3456789]\d{9}$/.test(name))){
                            username=name;
                        }else{
                            phone=name;
                        }
                        pwd=form.password;
                        var parms={
                            username:username,
                            phone:phone,
                            pwd:pwd
                        }
                        this.$store.dispatch('userLoginReq', parms).then(response => {
                            this.$router.push({
                                path: '/home'
                            });
                        }).catch(() => {
                            this.$message({
                                message: "用户名或密码错误",
                                type: 'warning'
                            });
                        });
                    } else {
                        return false;
                     }
                });
            },
            qqLogin(){
                QC.Login.showPopup({
                    appId:this.$store.state.user.qqAppid,
                    redirectURI:this.$store.state.user.qqredirectURI
                });
            },
            wxLogin(){
                this.wxLoginFlag = true;
                var obj = new WxLogin({
                    self_redirect:true,
                    id:"login_container",
                    appid:this.$store.state.user.wxAppid,
                    scope: "snsapi_login",
                    redirect_uri: encodeURI(this.$store.state.user.wx_redirect_uri),
                    state: "",
                    style: "",
                    href: ""
                });
            }
        }
    };
</script>

<style scoped>
    .topLine{width: 100%; height:1px;background:rgba(204,204,204,0.2);box-shadow:0px 2px 5px 1px rgba(8,1,3,0.3);}
    .dlName{font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(202,0,0,1);margin: 38px 0 46px 22px;position: relative;}
    .dlName::before{content: "";width: 3px;height: 29px;background: #CA0000;position: absolute;top:-5px;left: -22px;}
    .btnWx{width: 40px;height: 45px; margin-left: 85px;background: url("../../images/icon_weix.png") no-repeat 100% 100%;background-size: contain;}
    .btnWx.el-button{border: none;}
    .btnQq{width: 40px;height: 45px; margin-left: 15px;background: url("../../images/icon_qq.png") no-repeat 100% 100%;background-size: contain}
    .btnQq.el-button{border: none;}
</style>