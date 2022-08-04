<template>
    <div>
        <div class="topbar">
            <div class="topLink">
                <el-row type="flex" class="row-bg" justify="space-between">
                    <el-col style="display: flex;align-items: center;-webkit-align-items: center;">
                      <template v-if="loginFlag">
                            <span>欢迎用户:</span>
                            <el-link ><router-link :to="{path:'/userCenter'}" tag="a" class="routerLink-a" style="color: #CA0000">{{loginUserName}}</router-link></el-link>
                            <el-link @click="exitWeb">退出</el-link>
                        </template>
                         <template v-else>
                           <el-link><router-link :to="{path:'/viplogin'}" tag="a" class="routerLink-a">请登录</router-link></el-link>
                           <el-link><router-link :to="{path:'/register'}" tag="a" class="routerLink-a">免费注册</router-link></el-link>
                       </template>
                        <label><i class="i-phone"></i>4000112931</label>
                        <el-badge :value="msgCount" class="item">
                            <el-link class="btn-mail" type="primary" @click="btnMsg"></el-link>
                        </el-badge>
                    </el-col>
                    <el-col class="tx-alg_r">
                        <el-link @click="toHome">首页</el-link>
                        <el-link @click="myChat">我的购物车</el-link>
                        <el-link @click="toUserCenter">用户中心</el-link>
                        <el-link @click="toAbout">关于我们</el-link>
                       <!-- 北部灣 <el-link><router-link :to="{path:'/guide'}" target="_blank" tag="a" class="routerLink-a">操作指引</router-link></el-link>-->
                        <el-link style="display: none;">语言切换</el-link>
                        <el-link style="margin-right:0; display: none;">app</el-link>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div class="topContent">
            <el-row type="flex" class="row-bg">
                <el-col>
                    <el-link >
                         <!--北部湾样式-->
                       <router-link :to="{path:'/home'}" tag="a" class="routerLink-a">
                        <img class="img-logo" :src="require('@/images/'+logoImg.src)"/>
                        </router-link>
                        <!--<router-link :to="{path:'/home'}" tag="a" class="routerLink-a">
                            <div style="font-weight: bold;font-size: 58px;color: #0b0306;display: inline-block;">Traup</div>
                            <div style="display: inline-block;">
                                <div style="background-color: #0B6DB9;margin-left: 5px; width: 8px;height: 68px;position: relative;top:12px;"></div>
                            </div>
                        </router-link>-->
                    </el-link>
                    <div class="lg-title">
                        <h3>北部湾国际技术转移转化中心公共服务平台</h3>
                        <p>国家技术转移示范机构-对接平台建设运营</p>
                       <!-- <p>国家技术转移大数据平台</p>
                        <p>国家产学研融合示范平台</p>
                        <p>国际成果转化综合生态体系</p>-->
                    </div>
                </el-col>
                <el-col style="width: 60%;">
                    <div class="search-mark" style="margin-left: 2px;">
                        <el-link>
                            <router-link :to="{path:'/kjsc'}" tag="a" class="routerLink-a">科技成果</router-link>
                        </el-link>
                        <el-link>
                            <router-link tag="a"  class="routerLink-a" :to="{path:'/kjsc',query:{chengguoActiveName:'1'}}">商标注册</router-link>
                        </el-link>
                        <el-link>
                            <router-link tag="a"  class="routerLink-a" :to="{path:'/kjsc',query:{chengguoActiveName:'1'}}">专利申请</router-link>
                        </el-link>
                        <el-link>
                            <router-link :to="{path:'/middleman'}" tag="a" class="routerLink-a">技术经纪人</router-link>
                        </el-link><el-link>
                            <router-link :to="{path:'/gxIndex'}" tag="a" class="routerLink-a">高校</router-link></el-link></div>
                    <div class="ipt-search">
                        <!--<el-input placeholder="请输入搜索内容的关键词" v-model="input2">
                            <el-button slot="append" class="btnSearch">搜索</el-button>
                        </el-input>-->
                        <el-autocomplete
                                v-model="state" style="width: 100%;"
                                :fetch-suggestions="querySearchAsync"
                                @select="handleSelect"
                                placeholder="请输入你想检索的内容">
                            <el-button slot="append" class="btnSearch">AI搜索</el-button>
                        </el-autocomplete>
                    </div>
                </el-col>
                <el-col style="width: 138px;display: none; "><div class="top-img-adtv"><img :src="require('@/images/'+adtiveInfo.src)" class="top-img-adtv"/> <!--<el-image :src="adtiveInfo.src"></el-image>--><p>{{adtiveInfo.msg}}</p></div></el-col>
                <!--<el-col v-if="$route.path=='/userCenter' && userAccountType==1" style="text-align: center;">
                    <div style="cursor: pointer;" @click="switchoverUser">
                        <img :src="require('@/images/yonghuqiehuan.png')" style="margin-left: 30px;" />
                        <p style="font-size:14px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);">切换为商家版</p>
                    </div>
                </el-col>-->
            </el-row>
        </div>
    </div>
</template>

<script>
    import {
        getToken,
        removeToken,
        setWxToken,
        getWxToken,
        setToken,
        removeQqToken,
        getQqToken,
        removeWxToken,
        getTempInfo,
        removeTempInfo
    } from '../../utils/auth';
    import {qryWxAccessTokenApi,bindOpenId,saveUserInfo,qryQqUserInfo} from "../../api/thirdparty/thirdparty";
    import {qryServiceDwList} from "../../api/projectDw/projectDw";
    import {qryUserSysMsgCounts} from "../../api/user/userSysMsg";
    export default {
        name: "topBar",
        data() {
            return {
                restaurants: [],
                state: '',
                dataType:{1:"知识产权",16:"技术供需",8:"技术合同登记",9:"技术成果认定登记",11:"法律服务",14:"财税服务"},
                adtiveInfo:{src: 'logo_02.png',msg:'和合创优'},
                logoImg:{src:'logo_01.png'},
                input2:null,
                loginFlag:false,
                loginUserName:'',
                userAccountType:1,
                msgCount:'',
                msgInterval:null
            }
        },
        created(){
            var href = window.location.href;
            var code = "";
            if(href.indexOf("code=")>0){
                var parent = window.parent.document.getElementById('login_container');
                parent.style.display="none";
                code = href.substring(href.indexOf("code=")+"code=".length,href.indexOf("&state"));
                setWxToken(code);
                window.parent.location.href = href.substring(0,href.indexOf("/home/")+"/home/".length)+"/home";
            }
            if((this.$route.path!='/viplogin' && this.$route.path!='/register')){
                if(getTempInfo("WX_BIND")){
                    this.$router.push({path:"/userCenter",query:{menuActiveFlag:'userManage'}});
                }
                if(getToken()){
                    this.refreshUserInfo();
                }else{
                    if(getWxToken()){
                        this.getAccessToken();
                    }
                    if(getQqToken()){
                        this.qryQqInfoApi();
                    }
                }
            }
        },
        methods:{
            qryUserSysMsgCounts(){
                qryUserSysMsgCounts({ userId:this.$store.state.user.userInfo.id,status:0}).then(response=>{
                    this.msgCount = response.data==0?'':response.data;
                    this.$forceUpdate();
                });
            },
            querySearchAsync(queryString, cb) {
                if(queryString.trim()){
                    this.restaurants=[];
                    qryServiceDwList({title:queryString,auditStatus:null,pageSize:20}).then(response=>{
                        response.data.forEach(item=>{
                            var vl = {value:this.dataType[item.dataType]+'-'+item.title+'('+(item.needType==1?'供':'需')+')',
                                id:item.id,dataType:item.dataType,needType:item.needType
                            };
                            this.restaurants.push(vl);
                        })
                        cb(this.restaurants);
                    });
                }
            },
            handleSelect(item) {
                if(item.needType==1 && item.dataType==1){
                    this.$router.push({path:"/kjsc/detail",query:{productId:item.id}});
                }else if(item.needType==2){
                    this.$router.push({path:"/kjsc",query:{iconToggleFlag:1}});
                }else if(item.needType==1 && item.dataType!=1){
                    this.$router.push({path:"/kjsc",query:{chengguoActiveName:1,aiServiceDetFlag:1}});
                }
            },
            refreshUserInfo(){
                if(undefined!= getToken() && null != getToken() && '' != getToken().trim()){
                    if(null == this.$store.state.user.userInfo || !Number.isInteger(this.$store.state.user.userInfo.id)){
                        this.$store.dispatch('GetInfo').then(response => {
                            this.loginFlag = true;
                            this.loginUserName = response.userName!='' && undefined!=response.userName ?response.userName:response.phoneNo;
                            this.userAccountType = response.type;
                            var that = this;
                            if(this.msgInterval==null){
                                that.qryUserSysMsgCounts();
                                this.msgInterval = setInterval(function(){
                                    if(getToken()){
                                        that.qryUserSysMsgCounts();
                                    }else{
                                        clearInterval(this.msgInterval);
                                        this.msgInterval = null;
                                    }
                                },60000);
                            }
                        }).catch(() => {
                            removeToken();
                        });
                    }else{
                        this.loginFlag = true;
                        this.loginUserName = this.$store.state.user.userInfo.userName!='' && undefined!=this.$store.state.user.userInfo.userName ?this.$store.state.user.userInfo.userName:this.$store.state.user.userInfo.phoneNo;
                        this.userAccountType = this.$store.state.user.userInfo.type;
                        var that = this;
                        if(this.msgInterval==null){
                            that.qryUserSysMsgCounts();
                            this.msgInterval = setInterval(function(){
                                if(getToken()){
                                    that.qryUserSysMsgCounts();
                                }else{
                                    clearInterval(this.msgInterval);
                                    this.msgInterval = null;
                                }
                            },60000);
                        }
                    }
                }
            },
            qryQqInfoApi(){
                var data = JSON.parse(getQqToken());
                qryQqUserInfo({qq:data.qq}).then(response=>{
                    if(response.data){
                        setToken(response.data.token);
                        removeQqToken();
                        this.refreshUserInfo();
                    }else{
                        this.bindOpt(data);
                    }
                })
            },
            bindOpt(data){
                this.$prompt('是否绑定平台已有账号?', '温馨提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    closeOnClickModal:false
                }).then(({ value }) => {
                    data.account = value;
                    bindOpenId(data).then(resp=>{
                        if(resp.retCode==-1){
                            this.$message.error('该账号'+value+"不存在!");
                            return false;
                        }else{
                            setToken(resp.data.token);
                            removeWxToken();
                            removeQqToken();
                            this.refreshUserInfo();
                            this.$message({
                                message: '绑定成功',
                                type: 'success'
                            });
                        }
                    });
                }).catch(() => {
                    data.phoneNo = data.account;
                    saveUserInfo(data).then(resp=>{
                        setToken(resp.data.token);
                        removeWxToken();
                        this.refreshUserInfo();
                    });
                });
            },
            getAccessToken(){
                var par = {code:getWxToken()};
                qryWxAccessTokenApi(par).then(response=>{
                    if(undefined == response.data.id){
                        this.bindOpt(response.data);
                    }else{
                        setToken(response.data.token);
                        removeWxToken();
                        this.refreshUserInfo();
                    }
                });
            },
            toUserCenter(){
                if(getToken()){
                    this.$router.push({path:"/userCenter"});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            toHome(){
                this.$router.push({
                    path: '/home'
                });
            },
            toAbout(){
                this.$router.push({
                    path: '/about'
                });
            },
            myChat(){
                if(getToken()){
                    this.$router.push({path:"/userCenter",query:{menuActiveFlag:'shoppingCart'}});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            btnMsg(){
                if(getToken()){
                    this.$router.push({path:"/userCenter",query:{menuActiveFlag:'messageIndex'}});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            switchoverUser(){
                this.$confirm('确认切换当前帐号吗?', '温馨提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    removeToken();
                    this.loginFlag = false;
                    this.$router.push({
                        path: '/viplogin'
                    });
                }).catch(() => {
                });
            },
            exitWeb(){
                this.$confirm('确认退出吗?', '温馨提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    cancelButtonClass:'btnColorOrg',
                    confirmButtonClass:'btnColorBlue',
                    type: 'warning'
                }).then(() => {
                    removeToken();
                    removeWxToken();
                    removeQqToken();
                    removeTempInfo("WX_BIND");
                    removeTempInfo("QQ_BIND");
                    sessionStorage.removeItem("WebUserSion");
                    this.$store.state.user.userInfo = null;
                    this.loginFlag = false;
                    if(null != this.msgInterval){
                        clearInterval(this.msgInterval);
                        this.msgInterval = null;
                    }
                    this.$router.push({
                        path: '/home'
                    });
                }).catch(() => {
                });
            }
        }
    }
</script>
<style scoped>
    .ipt-search /deep/ .el-input__inner{border: 1px solid #fa963d;border-right: none;}
    /deep/ .el-button.el-button--default.btnSearch{background: linear-gradient(90deg,#f74a0d 0%,#fa963d 100%);border: none;padding: 13px 20px;border-radius: 2px;}
    /deep/ .el-button.el-button--default span{color: #fff!important;}
    /deep/.routerLink-a{
        text-decoration:none;
    }
    /deep/ .topbar .topLink a, .topbar .topLink label {
        color: #6C6C6C;
        font-weight: 400;
        margin-left: 20px;
        margin-right: 0px;
    }
</style>