<template>
    <div class="home" style="margin-bottom: 100px;">
        <el-row style="height: 504px;position:relative;">
            <el-carousel height="504px" class="img-home-head" style="height: 504px;" >
                <el-carousel-item >
                    <img style="width: 100%; height: 504px;" src="../images/img-home1.png">
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width: 100%; height: 504px;" src="../images/img-home2.png">
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width: 100%; height: 504px;" src="../images/img-home3.png">
                </el-carousel-item>
            </el-carousel>
            <div class="fastCenter">
                <el-row>
                    <el-col class="fastTitle"><h3>快速入口</h3></el-col>
                    <el-col class="fastContent">
                        <el-link @click="toUserCenter"><img src="../images/home/img-home-faster_gxfb.png" alt=""><p>供需发布</p></el-link>
                        <el-link><router-link :to="{path:'/listedTransaction'}" tag="a" style="text-decoration: none;"><img src="../images/home/img-home-faster_gpjy.png" alt=""><p>挂牌交易</p></router-link></el-link>
                        <el-link><router-link :to="{path:'/register'}" tag="a" style="text-decoration: none;"><img src="../images/home/img-home-faster_rzsq.png" alt=""><p>入驻申请</p></router-link></el-link>
                        <el-link><router-link :to="{path:'/serviceIndex'}" tag="a" style="text-decoration: none;"><img src="../images/home/img-home-faster_zzfw.png" alt=""><p>增值服务</p></router-link></el-link>
                        <el-link><router-link :to="{path:'/serviceIndex'}" tag="a" style="text-decoration: none;">
                            <img src="../images/home/img-home-faster_czz.png" alt=""><p>查资助</p>
                        </router-link></el-link>
                        <el-link><router-link :to="{path:'/serviceIndex'}" tag="a" style="text-decoration: none;">
                            <img src="../images/home/img-home-faster_zcpp.png" alt=""><p>政策匹配</p>
                        </router-link></el-link>
                        <el-link><router-link :to="{path:'/serviceIndex'}" tag="a" style="text-decoration: none;">
                            <img src="../images/home/img-home-faster_gxtj.jpg" alt=""><p>高新体验</p>
                        </router-link></el-link>
                    </el-col>
                </el-row>
                <el-row style="width: 246px;height: 161px;margin-top: 13px;"><img src="../images/home/img-home_join.png" alt=""></el-row>
            </div>
        </el-row>
        <!-- 通知/图片轮播-->
        <el-row style="width:1240px;margin: 126px auto 0;">
            <el-col :span="12" style="height:322px;">
                        <div style="height: 35px">
                            <el-row class="btnNotice">
                                <button :class="'newsButton '+[tabID==1?'check':'']" @click="handleClick('1')" style="cursor: pointer;">国际动态</button>
                                <button :class="'newsButton '+[tabID==2?'check':'']" @click="handleClick('2')" style="margin-left: 20px;cursor: pointer;">国家动态</button>
                                <button :class="'newsButton '+[tabID==3?'check':'']" @click="handleClick('3')" style="margin-left: 20px;cursor: pointer;">地方动态</button>
                                <button :class="'newsButton '+[tabID==4?'check':'']" @click="handleClick('4')" style="margin-left: 22px;cursor: pointer;">通知公告</button>
                            </el-row>
                        </div>
                        <div style="height: 288px;border:1px solid rgba(160,160,160,1);">
                            <div>
                                <ul class="noticeList" style="height: 240px;">
                                    <li v-for="(newsItem,newsIndex) in newsList.slice(0,8)" :key="newsIndex+'_news'+newsItem.id">
                                         <router-link :to="{path:'/newsContent',query:{newsId:newsItem.id,newsType:tabID}}"  >
                                            <el-col :span="20">{{newsItem.subject}}</el-col>
                                            <el-col :span="4" style="text-align: right">{{undefined==newsItem.uploadTime || ''==newsItem.uploadTime?newsItem.createtime:newsItem.uploadTime.length>5?newsItem.uploadTime.substring(5,10):newsItem.uploadTime}} </el-col>
                                         </router-link>
                                    </li>
                                </ul>
                                <router-link :to="{path:'/newsList',query:{newsType:tabID}}" style="color: #ca0000;text-align: right;text-decoration: none; display: block; margin-bottom: 1px">查看更多》</router-link>
                            </div>
                        </div>
                    </el-col>
                <el-col :span="11" style="height:322px;margin-left: 22px;width: calc(100% - 50% - 22px);">
                    <div class="imgList" style="height:322px;">
                        <el-carousel height="322px" >
                            <el-carousel-item v-for="(adItem,adIndex) in adList" :key="adIndex+'_adItem'+adItem.id">
                                <img style="width: 100%; height: 100%" :src="adItem.img">
                                <div class="imgMark">{{adItem.subject}}</div>
                            </el-carousel-item>
                        </el-carousel>
                    </div>
                </el-col>
        </el-row>
        <!-- 核心服务 #e35c3d-->
        <el-row style="margin-top: 209px;">
            <el-col style="font-size:40px;font-weight:normal;color:rgba(29,29,29,1);text-align: center;">核心服务</el-col>
            <el-col style="font-size:18px;font-weight:400;color:rgba(125,125,125,1);margin: 13px 0 86px;text-align: center;">CORE SERVICES</el-col>
            <el-col style="width: 1280px;margin: 0 auto;float: inherit;">
                <el-row class="serveList">
                    <el-col v-for="(coreItem,coreIndex)  in coreServerList" :key="coreIndex+'_coreItem'+coreItem.id">
                        <div>
                            <h1 style="text-align: center;margin-top: 50px;">
                                <!--<img style="width: 141px; height: 141px" :src="require('../assets/icon/'+coreItem.icon)">?-->
                                <img style="width: 141px; height: 141px" :src="'http://yhtg.huayunstar.com:8001/jishu/image/'+coreItem.icon">
                            </h1>
                            <h2 style="margin-top:30px;text-align: center;font-size:24px;color:rgba(61,134,227,1);">{{coreItem.title}}</h2>
                            <h3 style="text-align: center;font-size:14px;font-weight:normal;color:rgba(180,180,180,1);margin: 15px 30px 0 30px;line-height: 20px;">{{coreItem.xgms}}</h3>
                            <h4 class="btnFW">
                                <el-button class="btnInfo" @click="btClick(coreItem.url)">查看详情<i class="el-icon-right" style="color: white;margin-left: 5px;"></i></el-button>
                            </h4>
                        </div>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <!-- 挂牌专家 -->
        <el-row style="margin-top: 200px">
            <el-col>
                <div class="homeExpertsBox">
                    <h1>
                        挂牌专家
                    </h1>
                    <p>
                        LISTING EXPERTS
                    </p>
                </div>
            </el-col>
            <el-col style="margin-top: 93px;">
                <!--<img src="../images/img-home_guap.png" style="width: 100%;" alt="">-->
                <!--<img src="../images/zhuanjia-1.png" style="width: 100%;" alt="">-->
                <div class="guaipai-zhuanjia-bg" style="text-align: -webkit-center;">
                    <el-carousel :interval="4000" arrow="always" :autoplay="false" type="card"  height="430px" style="padding-top: 245px;width: 80%;">
                        <el-carousel-item v-for="item in experts" :key="item.username">
                            <div style="width: 286px;height: 342px">
                                <img :src="item.photo" style="width: 100%;height: 100%;">
                            </div>
                            <!--<div style="width: 286px;height: 96px;color: #FFF;background: #024ecc;">
                                <h1 style="text-align: center;font-size: 18px;padding-top: 24px">姓名：{{item.username}}</h1>
                                <h1  style="text-align: center;font-size: 14px;margin-top: 10px;">{{item.duTyName}}</h1>
                            </div>-->
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </el-col>
            <!--<el-col style="margin-top: 244px;background: #F9FDFF;">
                <div style="width: 1240px;padding: 0 20px 70px;margin: 100px auto;position: relative;">
                    <el-row >
                        <el-col :span="10">
                             <h1 style="width:510px;height:24px;font-size:24px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(100,100,100,1);">超强挂牌专家阵容，提供最专业的技术保障</h1>
                        </el-col>
                        <el-col  :span="10" :offset="5" style="margin-top: 19px">
                            <h2 style="width:279px;height:18px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(160,160,160,1);">500+挂牌专家，为您保驾护航</h2>
                        </el-col>
                        <el-col :span="10" style="margin-top: 85px">
                            <h3 style="width:339px;height:23px;font-size:24px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(100,100,100,1);"><span>{{experts.duTyName}}</span>  <span style="margin-left: 30px">{{experts.username}}</span></h3>
                        </el-col>
                        <el-col style="margin-top: 31px">
                            <h4 style="height:39px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(160,160,160,1);">{{experts.remark}} </h4>
                        </el-col>
                    </el-row>
                    <img style="width: 347px; height: 505px;position: absolute;right:76px;top: -320px" :src="require('../images/'+experts.photo)">
                </div>
            </el-col>-->
        </el-row>
        <!-- 经纪人-->
        <el-row style="margin-top: 164px;">
            <el-col>
                <div class="homeBrokerBox">
                    <h1>
                        技术经纪人
                    </h1>
                    <p>
                        TECHNINCAL BROKER
                    </p>
                </div>
            </el-col>
            <el-col style="background: #F9FDFF;margin-top:100px;">
                <el-row class="w1240" style="margin: 0 auto;">
                    <el-col style="margin-top: 50px">
                        <h1 style="text-align: center;font-size:24px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(100,100,100,1);">技术经纪人汇聚，提供最专业的技术服务</h1>
                        <h2 style="text-align: center;margin-top: 24px;font-size:18px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(160,160,160,1);">800+技术经纪人，更有全网技术经纪人可检索</h2>
                    </el-col>
                    <el-col style="margin-top: 97px;width: 1240px">
                        <el-tabs v-model="activeName" @tab-click="handleBrokerClick" class="jjrChoiceBox">
                            <el-tab-pane label="知识产权" name="1" class="fontColor"></el-tab-pane>
                            <el-tab-pane label="财税服务" name="2"></el-tab-pane>
                            <el-tab-pane label="科技咨询" name="3"></el-tab-pane>
                            <el-tab-pane label="科技金融" name="4"></el-tab-pane>
                            <el-tab-pane label="法律服务" name="5"></el-tab-pane>
                            <el-tab-pane label="技术合同登记" name="6"></el-tab-pane>
                            <el-tab-pane label="成果认定" name="7"></el-tab-pane>
                            <el-tab-pane label="商务代写" name="8"></el-tab-pane>
                            <el-tab-pane label="策划设计" name="9"></el-tab-pane>
                            <el-tab-pane label="技术开发" name="10"></el-tab-pane>
                            <el-tab-pane label="教育培训" name="11"></el-tab-pane>
                            <el-tab-pane label="投资孵化" name="12"></el-tab-pane>
                            <el-tab-pane label="更多" name="13"></el-tab-pane>
                        </el-tabs>
                    </el-col>
                    <el-col style="margin-top: 74px;width: 1240px">
                        <el-row class="bList" >
                            <el-col v-for="(brokeItem,brokeIndex) in brokerList.slice(0,3)" :key="brokeIndex">
                                <div style="width: 342px;height: 336px">
                                    <img :src="brokeItem.photo" style="width: 100%;height: 100%;">
                                </div>
                               <!-- <img :src="brokeItem.photo" style="width: 342px;height: 336px">-->
                               <!-- <el-image
                                        style="width: 100%; height: 100%;"
                                        :src="brokeItem.photo"
                                        fit="fill"></el-image>-->
                                <div class="jINfo">
                                    <h1 style="text-align: center;font-size: 18px;padding-top: 24px">姓名：{{brokeItem.name}}</h1>
                                    <h1  style="text-align: center;font-size: 14px;margin-top: 10px;">{{brokeItem.mainAchieve}}</h1>
                                </div>
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    import {getUserManagerReq,coreServerReq,getNewsManager,getListingExpertReq,qryNewsList} from "../api/login";
    import {getToken} from "../utils/auth";
    export default {
        name: "home",
        data() {
            return {
                tabID:1,
                activeName:"1",
                newsList:[
                    {
                        id:"",
                        subject:"1",
                        createtime:"",
                        subTitle:"北海塆项目正式成立"
                    }
                ],
                adList:[],
                coreServerList:[{
                    id:"",
                    title:"",
                    xgms:"",
                    xq:"",
                    icon:"",
                    url:""
                }],
                brokerList:[
                    {photo:"", userName:"", remark:""}
                ],
                experts:{
                    remark:"",
                    username:"",
                    photo:"",
                    duTyName:""
                },
                url:"https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
            }
        },
        mounted(){
            getNewsManager({type:1,pageSize:7}).then(response => {
                this.newsList = response.data;
            });
            qryNewsList({img:1,pageSize:4}).then(response=>{
                this.adList = response.data;
            });
            this.coreServerReq();
            getUserManagerReq({borkerId:"1",status:3}).then(response => {
                this.brokerList = response.data;
            });
            getListingExpertReq({pageSize:4}).then(response=>{
                this.experts= response.data;
             });
        },
        methods:{
            toUserCenter(){
                if(getToken()){
                    this.$router.push({path:"/userCenter",query:{menuActiveFlag:'intellectual'}});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            handleClick(tab) {
                this.tabID = tab;
                var parms={type:tab,pageSize:7};
                getNewsManager(parms).then(response => {
                    this.newsList = response.data;

                  });
            },
            coreServerReq(){
                coreServerReq().then(response => {
                    this.coreServerList = response.data;
                });
            },
            handleBrokerClick(tab) {
                if(tab.name==13){
                    this.$router.push({path:'/middleman'});
                }else{
                    var parms={borkerId:tab.name+"",status:3};
                    getUserManagerReq(parms).then(response => {
                        this.brokerList = response.data;
                    });
                }
            },
            btClick(url){
                if(url.indexOf("http")!=-1) {
                    window.open(url);
                }else{
                    this.$router.push({path:url,query:{chengguoActiveName:1}});
                }

            }

        }
    };
</script>
<style>
    .newsButton{
        width:141px;
        height:35px;
        background:rgba(238,238,238,1);}
    .newsButton.check{color: #fff;background: linear-gradient(90deg,rgba(2,78,204,1) 0%); }
/*    快速入口*/
    .fastCenter{width: 1240px; position: absolute;top: 16px;left: 50%;transform: translateX(-50%);-webkit-transform: translateX(-50%);z-index: 100;}
    .fastCenter .el-row{width: 246px;float: right;clear: both;}
    .fastCenter .fastTitle{height: 47px;line-height: 47px; background:linear-gradient(-90deg,rgba(127,21,181,1) 0%,rgba(84,48,252,1) 100%);
        color: #FFF;font-size: 16px;font-weight:normal;text-align: center;
        box-shadow:0px 1px 15px 0px rgba(0, 0, 0, 0.25);}
    .fastContent{height: 259px;background: #FFF;padding: 24px 0; box-shadow:0px 1px 15px 0px rgba(0, 0, 0, 0.25);
        display: flex;display: -webkit-flex;justify-content: center;-webkit-justify-content: center;flex-wrap: wrap;-webkit-flex-wrap: wrap;}
    .fastContent>a{margin: 5px 15px;text-align: center;color: #1D1D1D;}
    .fastContent>a img{width: 48px;height: 43px;}
    .fastContent>a p{margin-top: 5px;font-size: 12px;color: #1D1D1D}
/*  新闻公告  */
    .btnNotice{display: flex;}
    .noticeList{margin: 10px 20px;}
    .noticeList li{font-size:14px;font-family:'Microsoft YaHei';color:rgba(29,29,29,1);line-height: 30px;list-style:disc;}
    .noticeList li>a{color:#1D1D1D;}
    .noticeList .el-col-20{text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
/*    轮播图*/
    .imgMark{width: 100%;height: 40px;font-size: 20px;background: rgba(29,29,29, 0.6);position: absolute;bottom: 0;color: #FFF;line-height: 40px;padding-left: 10px;}
    img-home-head .el-carousel__container{height: 504px;}
/*    服务类型*/
    .serveList .el-col{width:312px;height:438px;border:1px solid rgba(191,191,191,1);margin-bottom: 64px;margin-right: 10px;position: relative;}
    .serveList .el-col:nth-child(4n){margin-right: 0px;}
    .btnInfo{width:134px;height:39px;background: linear-gradient(90deg,#f74a0d 0%,#fa963d 100%);border-radius:17px;border: none;}
    .btnInfo.el-button.el-button--default span{color: white;}
    .btnFW{text-align: center; position: absolute;left: 50%;transform: translateX(-50%);bottom: 35px;}
/*     经纪人*/
    /*.divider-home.el-divider--horizontal{width: 1240px;margin: 0 auto;text-align: center;}*/
    .jINfo{width: 320px; height: 96px;color: #FFF; background:#024ecc;border-radius:10px;position: absolute;left: 11px;bottom: -70px;}
    .bList .el-col{position: relative; width: 342px; height: 337px;margin: 0 104px 60px 0;}
    .bList .el-col:nth-child(4n){margin: 0 0 60px 0;}
    .bList .el-col:last-child{margin-right: 0;}
    .jjrChoiceBox .el-tabs__item.is-active{background: none;border-bottom: 3px solid red;color: red;}

    .homeBrokerBox{text-align: center;}
    .homeBrokerBox h1{color: #000;font-size: 40px;font-weight:normal;letter-spacing: 4px;display: inline-block; position: relative;}
    .homeBrokerBox h1::before{content: '';width: 610px;height: 20px;background: url("../images/img-line_right.png") no-repeat;background-size: contain; position: absolute;right: 190px;bottom:5px;}
    .homeBrokerBox h1::after{content: '';width: 550px;height: 20px;background: url("../images/img-line_left.png") no-repeat;background-size: contain; position: absolute;left:225px;bottom:5px;}
    .homeBrokerBox p{margin-top: 15px;font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);letter-spacing: 3.5px;}

    .homeExpertsBox{text-align: center;}
    .homeExpertsBox h1{color: #000;font-size: 40px;font-weight:normal;letter-spacing: 4px;display: inline-block; position: relative;}
    .homeExpertsBox h1::before{content: '';width: 550px;height: 20px;background: url("../images/img-line_right.png") no-repeat;background-size: contain; position: absolute;right: 190px;bottom:5px;}
    .homeExpertsBox h1::after{content: '';width: 560px;height: 20px;background: url("../images/img-line_left.png") no-repeat;background-size: contain; position: absolute;left:185px;bottom:5px;}
    .homeExpertsBox p{margin-top: 15px;font-size:18px;font-family:'Microsoft YaHei';font-weight:400;color:rgba(125,125,125,1);letter-spacing: 2px;}
</style>
<style scoped>
    /deep/.is-active{
        color: red;
    }
    /deep/ .el-tabs__active-bar {
        position: absolute;
        bottom: 0;
        left: 0;
        height: 2px;
        /* color: #CA0000; */
        background-color: #CA0000;
        z-index: 1;
        -webkit-transition: -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1), -webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        transition: transform .3s cubic-bezier(.645,.045,.355,1),-webkit-transform .3s cubic-bezier(.645,.045,.355,1);
        list-style: none;
    }
    /deep/ .el-carousel__mask {
        width: auto;
        background-color: #FFF;
        opacity: .24;
        -webkit-transition: .2s;
        transition: .2s;
    }
    /deep/ .el-carousel__indicators{
        display: none;
    }
    .guaipai-zhuanjia-bg{
        background: url("../images/zhuanjia-1.jpg") no-repeat;    background-size: cover;
    }

    /deep/ .el-carousel__arrow i {
        cursor: pointer;
        font-size: x-large;
    }
</style>