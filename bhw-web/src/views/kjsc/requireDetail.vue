<template>
    <div>
        <div class="">
            <div class="breadWrap w1240">
                <span class="breakCurr"><i></i>当前位置：</span>
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/ksjc' }">科技市场</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/kjsc' }" v-if="this.requireObj.projectType>15">技术需求</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/kjsc' }" v-if="this.requireObj.projectType<16">产品、服务需求</el-breadcrumb-item>
                    <el-breadcrumb-item>详情</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="rqDtail">
                <el-row style="padding-bottom: 28px;" v-if="statusId==1">
                    <el-col class="rqTitle"><span style="color: #CA0000;">【
                        <!--<template v-if="requireObj.status==0">
                            待承接
                        </template>
                        <template v-if="requireObj.status==1">
                            已承接
                        </template>
                        <template v-if="requireObj.status==2">
                            已解决
                        </template>-->
                        <template v-if="requireObj.status==0">
                            待承接
                        </template>
                        <template v-if="requireObj.status==1 && requireObj.projectType!=1">
                            已承接
                        </template>
                        <template v-if="requireObj.status==2 && requireObj.projectType!=1">
                            已解决
                        </template>
                        <template v-if="requireObj.status==2 && requireObj.projectType==1">
                            待承接
                        </template>
                        <template v-if="requireObj.status==4 && requireObj.projectType==1">
                            已承接
                        </template>
                        <template v-if="requireObj.status==5 && requireObj.projectType==1">
                            已解决
                        </template>
                        】</span><span>{{requireObj.requireName}}</span>
                        <el-button class="btnRqColl" @click="saveRecord(2)" v-if="collectionFlag==0">
                            <i class="el-icon-star-off" style="color:#CA0000; "></i>收藏
                        </el-button>
                        <el-button class="btnRqColl" @click="unCn" v-if="collectionFlag>0"><i class="el-icon-star-on" style="color:#CA0000; "></i>已收藏</el-button>
                    </el-col>
                    <el-col>
                        <el-row class="rqInfo">
                            <el-col :span="10">
                                <p><i class="el-icon-menu"></i>行业类别 | {{requireObj.industryName}} </p>
                                <p><i class="el-icon-edit-outline"></i>需求类型 | {{requireObj.requireType}}</p>
                                <p><i class="el-icon-user"></i>需求方：{{requireObj.requireUserName}}</p>
                            </el-col>
                            <el-col :span="14">
                                <p style="visibility: hidden;">00</p>
                                <p><i class="el-icon-money"></i>预算：<em style="color: #CE2626">{{requireObj.unitPrice=="" || undefined == requireObj.unitPrice  ?"面议":requireObj.unitPrice+"元"}} </em></p>
                                <p><i class="el-icon-map-location"></i>定位: {{requireObj.provinceName}}  {{requireObj.cityName}}</p>
                            </el-col>
                        </el-row>
                    </el-col>
                    <el-col style="margin:35px 0 23px;" v-if="requireObj.status!=2 && requireObj.projectType!=1">
                        <el-button class="btnColorOrg btnRq" @click="advisoryRequire">我要承接</el-button>
                    </el-col>
                    <el-col style="margin:35px 0 23px;" v-if="requireObj.status!=4 && requireObj.projectType==1">
                        <el-button class="btnColorOrg btnRq" @click="advisoryRequire">我要承接</el-button>
                    </el-col>
                    <el-col class="rqMark"><div style="width: 150px;">需求描述:</div><div v-html="requireObj.introduce"></div></el-col>
                    <el-col style="margin-top: 35px;"><el-button @click="backRequireList" class="btnColorOrg btnRq">其他需求</el-button></el-col>
                </el-row>
                <el-row v-else>
                    <el-col style="padding-bottom: 28px;">
                        <el-row>
                            <el-col :span="10" class="rqInfo">
                                <div class="rqTitle"><span style="color: #CA0000;">【已解决】</span><span>ITTS 国际技术转移峰会</span></div>
                                <p><i class="el-icon-menu"></i> 行业类别 | 互联网 </p>
                                <p><i class="el-icon-edit-outline"></i>需求类型 | LOGO设计</p>
                                <p><i class="el-icon-user"></i>需求方：张**</p>
                                <p><i class="el-icon-money"></i>预算：<em>7万元 </em></p>
                                <p><i class="el-icon-map-location"></i>定位: 山东 济南</p>
                            </el-col>
                            <el-col :span="14" class="rqMore">
                                <div><el-button class="btn-tj">同类型需求推荐</el-button></div>
                                <el-row class="rqInfo">
                                    <el-col :span="11" style="margin-right: 20px;">
                                        <h3>一种高分子新型材料的生产工艺</h3>
                                        <p><i class="el-icon-menu"></i>行业:互联网 </p>
                                        <p><i class="el-icon-edit-outline"></i>类型:LOGO设计</p>
                                        <p><i class="el-icon-money"></i>预算：<em>7万元 </em></p>
                                        <p><i class="el-icon-map-location"></i>定位: 山东 济南</p>
                                    </el-col>
                                    <el-col :span="11">
                                        <h3>一种高分子新型材料的生产工艺</h3>
                                        <p><i class="el-icon-menu"></i> 行业:互联网 </p>
                                        <p><i class="el-icon-edit-outline"></i>类型:LOGO设计</p>
                                        <p><i class="el-icon-money"></i>预算：<em>7万元 </em></p>
                                        <p><i class="el-icon-map-location"></i>定位: 山东 济南</p>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                    </el-col>
                    <el-col class="rqMark"><div>需求描述：</div><div><p> 超薄铜箔压延技术、铜箔气垫式连续退火技术、铜箔去离子水高温高压清洗</p><p>厚度低于0.05mm，宽度突破200mm，成材率高达99%以上，耐弯曲性为同厚度电解铜箔的5—10倍</p></div></el-col>
                </el-row>
            </div>
        </div>
<!--        咨询弹框-->
        <el-dialog :visible.sync="outerVisible" class="chatBox">
            <div slot="title"><span style="font-size: 18px;">您当前正在给专属经纪人留言中</span><span class="dotting">...</span></div>
            <div class="chatList">
                <ul>
                    <li>
                        <div class="chatImg"><el-avatar :size="size" :src="requireObj.requireUserPhoto"></el-avatar></div>
                        <div class="chatInfo">
                            <h4>{{requireObj.requireUserName}}经纪人 {{currenTime}}</h4>
                            <p>欢迎您来咨询！我将成为您的专属顾问，有什么问题、不了解的地方，我将会一一为您答疑解惑，请问有什么地方可以帮助您的呢？</p>
                        </div>
                    </li>
                    <template v-for="brokerAdv in brokerAdvList" >
                        <li v-if="brokerAdv.sendType==2" :key="brokerAdv.id+'_brokerAdv'">
                            <div class="chatImg"><el-avatar :size="size" :src="brokerAdv.merchantPhoto"></el-avatar></div>
                            <div class="chatInfo">
                                <h4>{{brokerAdv.merchantName}}经纪人 {{brokerAdv.createTime}}</h4>
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
            </div>
            <div class="setChart">
                <el-form :model="form">
                    <el-form-item>
                        <el-input type="textarea" v-model="form.msg" :rows="3"></el-input>
                    </el-form-item>
                </el-form>
                <el-button type="primary" class="btnColorOrg setBtn" @click="saveRequireAdvisory">发送</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {qryRequireAdvisory,saveRequireAdvisory,qryRequireList,qryScienceRequireList,saveLeaveMsg} from "../../api/projectDw/projectDw";
    import {getToken} from '../../utils/auth';
    import {saveRecord,qryRecordList,deleteRecordByIds} from "../../api/user/serverOperate";

    export default {
        name: "requireDetail",
        data(){
            return{
                statusId:1,
                size:"large",
                form:{msg:""},
                requireObj:{
                    requireId:2,
                    requireName:"餐饮业大数据分析及运营辅助系统的开发",
                    industryName:"电子信息技术|计算机网络",
                    unitPrice:"7万元",
                    introduce:"数据化运营，已经是餐饮业可以预见的一大趋势，很多餐厅已经引进了扫码点餐的模块，有些餐厅的线上自助点餐率甚至达到了半数以上。那么，按照雅座为餐企铺设的路径，用支付即会员来沉淀用户，再通过自助点餐等模块收集数据，再加上支付宝所提供的各种用户标签，让餐厅掌握了顾客喜好、会员画像等数据，而这一系列的数据就会为菜品迭代、营销策划，甚至人员管理提供精准的依据。",
                    status:"待解决",
                    provinceName:"山东",
                    cityName:"济南",
                    requireType:"设计",
                    requireUserName:"张**",
                    requireUserPhoto:""
                },
                defPhoto:require('@/images/defaultHeadPhoto.png'),
                outerVisible:false,
                currenTime:null,
                brokerAdvList:[],
                collectionFlag:0
            }
        },
        created(){
            if(this.$route.query.requireObj){
                this.requireObj = this.$route.query.requireObj;
            }
            if(undefined == this.requireObj.requireUserPhoto || this.requireObj.requireUserPhoto==''){
                this.requireObj.requireUserPhoto = this.defPhoto;
            }
            if(this.$route.query.hisProductId) {
                if(this.$route.query.hisProductType>15){
                    qryScienceRequireList({"requireId": this.$route.query.hisProductId,userType:this.$route.query.hisProductType}).then(response => {
                        this.requireObj = response.data[0];
                        this.qryCollection();
                    });
                }else{
                    qryRequireList({"requireId": this.$route.query.hisProductId,requireType:this.$route.query.hisProductType}).then(response => {
                        this.requireObj = response.data[0];
                        this.qryCollection();
                    });
                }
            }else{
                this.qryCollection();
            }
            this.saveRecord(1);
        },
        methods:{
            backRequireList(){
                this.$router.push({path:'/kjsc',query:{iconToggleFlag:1,kjscPageType:this.$route.query.kjscPageType}});
            },
            qryCollection(){
                var par ={
                    userId:this.$store.state.user.userInfo.id,
                    operateType:2,
                    serverId:this.requireObj.requireId,
                    type:this.requireObj.projectType
                }
                qryRecordList(par).then(response=>{
                    if(response.data.length>0){
                        this.collectionFlag = response.data[0].id;
                    }else{
                        this.collectionFlag =0;
                    }
                });
            },
            unCn(){
                deleteRecordByIds({ids:this.collectionFlag}).then(response=>{
                    this.$message({
                        message: '您已取消收藏',
                        type: 'success'
                    });
                    this.qryCollection();
                });
            },
            saveRecord(val){
                if(getToken()) {
                    var par ={
                        userId:this.$store.state.user.userInfo.id,
                        operateType:val,
                        serverId:this.requireObj.requireId,
                        serverName:this.requireObj.requireName,
                        img:'',
                        price:this.requireObj.unitPrice,
                        type:this.requireObj.projectType,
                        path:this.$route.path
                    }
                    saveRecord(par).then(response => {
                        if (val == 2) {
                            this.$message({
                                message: '收藏成功',
                                type: 'success'
                            });
                            this.qryCollection();
                        }
                    });
                }else{
                    if (val == 2) {
                        this.$message.error('您还未登录,请登录后再操作！');
                    }
                }
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
            advisoryRequire(){
                if(getToken()){
                    this.getTime();
                    this.outerVisible = true;
                    this.qryRequireAdvisory();
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            saveRequireAdvisory(){
                if(this.form.msg.trim()==''){
                    this.$message.error('不能发送为空！');
                    return;
                }
                var par ={
                    userId:this.$store.getters.userInfo.id,
                    merchantId:this.requireObj.userId,
                    content:this.form.msg,
                    sendType:1
                };
                saveRequireAdvisory(par).then(response=>{
                    this.form.msg = '';
                    this.qryRequireAdvisory();
                });
                var serverForm={
                        serverId:this.requireObj.requireId,
                        serverName:this.requireObj.requireName,
                        serverUserId:this.requireObj.userId,
                        type:this.requireObj.projectType,
                        msg:this.form.msg,
                        leavemsgName:this.$store.getters.userInfo.userName,
                        userId:this.$store.getters.userInfo.id,
                        phoneNo:this.$store.getters.userInfo.phoneNo
                };
                saveLeaveMsg(serverForm).then(response => {
                });
            },
            qryRequireAdvisory(){
                qryRequireAdvisory({userId:this.$store.getters.userInfo.id,merchantId: this.requireObj.userId}).then(response=>{
                    this.brokerAdvList = response.data;
                });
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/market.css";
</style>