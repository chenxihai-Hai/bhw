<template>
    <!--挂牌交易-->
    <div class="listedTransaction">
        <el-row justify="center" type="flex" style="height:707px;" class="el-row-jiaoyi-statistic">
            <el-col :span="4" :push="4" style="top:24%;">
                <el-row class="showBar">
                    <el-col class="showTitle">
                        正在挂牌交易
                    </el-col>
                    <el-col class="showNum">
                        <template v-for="(numItem,numIndex) in countList">
                            <span :key="numIndex">{{numItem}}</span>
                        </template>
                        <span>宗</span>
                    </el-col>
                    <el-col >
                        <el-button class="el-icon-guapai-statistic" @click="advisoryBroker(broker)">参与挂牌交易</el-button>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <el-row type="flex" class="warmbar" >
            <el-col class="w1240">
                <template v-if="listedMessage.length>0">
                    <img src="../../images/notice-fill.png" style="vertical-align:-2px;"/>
                   <!-- <router-link style="text-decoration: none;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);" :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:listedMessage[0].infoId}}">-->
                    <span style="">挂牌交易公告:</span><!--'{{listedMessage[0].name}}'-->
                        <div class="text-container">
                            <div class="inner-container">
                                <p class="text" v-for="item in listedMessage" :key="'gpjygd_'+item.infoId" >{{item.name}}</p>
                            </div>
                        </div>
                    <!--</router-link>-->
                </template>

            </el-col>
        </el-row>
        <div class="listedBar">
            <div class="listedContent w1240">
                <h3>挂牌交易信息</h3>
                <router-link :to="{path:'/listedTransaction/listedMore'}"  class="more" >查看更多》</router-link>
                <el-row>
                    <template v-for="(messageItem,messageIndex) in listedMessage" >
                        <el-col class="f-col" :key="messageIndex">
                            <el-row>
                                <el-col :span="16">
                                    <router-link style="text-decoration: none;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);" :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:messageItem.infoId}}">
                                        <h4>{{messageItem.name}}</h4>
                                    </router-link>
                                    <p>编号：{{messageItem.number}}委托方：{{messageItem.client}}</p></el-col>
                                <template v-if="messageItem.timeLeft==0 ||messageItem.status ==1 ">
                                    <el-col :span="8"><p>距离截止还有0天</p><h4>已结束</h4></el-col>
                                    <div class="status">终止</div>
                                </template>
                                <template v-else>
                                    <el-col :span="8"><p>距离截止还有{{messageItem.timeLeft}}天</p><h4 style="color: #4A86DD;">进行中</h4></el-col>
                                    <div class="statusUnderway">进行中</div>
                                </template>
                            </el-row>
                        </el-col>
                    </template>
                </el-row>
            </div>
            <div class="listedContent w1240">
                <h3>挂牌结果公示</h3>
                <router-link :to="{path:'/listedTransaction/listedMore',query:{listedMoreStatus:1}}" class="more" >查看更多》</router-link>
                <el-row>
                    <template v-for="(resultItem,resultIndex) in listedResult">
                        <el-col class="f-col" :key="resultIndex">
                            <el-row>
                                <el-col :span="16">
                                    <router-link style="text-decoration: none;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);" :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:resultItem.infoId}}">
                                    <h4>{{resultItem.client}}</h4>
                                    </router-link>
                                    <p><em>摘牌候选人</em>{{resultItem.delister}}</p>
                                </el-col>
                                <template v-if="resultItem.status==1">
                                    <el-col :span="8"><h4>公示期未结束</h4></el-col>
                                </template>
                                <template v-else>
                                    <el-col :span="8"><h4>公示期已结束</h4></el-col>
                                </template>
                            </el-row>
                        </el-col>
                    </template>
                </el-row>
            </div>
            <div class="listedContent w1240">
                <h3>挂牌成交公告</h3>
                <router-link :to="{path:'/listedTransaction/listedNotice'}" class="more" >查看更多》</router-link>
                <el-row>
                    <template v-for="(successItem,successIndex) in listedSuccess">
                        <el-col class="f-col" :key="successIndex">
                            <el-row>
                                <el-col :span="16">
                                    <!--<h4>{{successItem.name}}</h4>-->
                                    <router-link style="text-decoration: none;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);" :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:successItem.infoId}}">
                                        <h4>{{successItem.name}}</h4>
                                    </router-link>
                                    <p><em>摘牌候选人</em>{{successItem.delister}}</p></el-col>
                                <el-col :span="8"><h4>已成交</h4></el-col>
                            </el-row>
                        </el-col>
                    </template>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
    import {listedOnNumberReq,listedMessageReq,listedResultReq,listedSuccessReq} from "../../api/listedTranaction/listedTranaction";
    import {qryBrokerAdvisory,saveBrokerAdvisory} from "../../api/admin/index";
    import {getToken} from "../../utils/auth";
    export default {
        name: "listedTransaction",
        data(){
            return{
                countList:[],
                countNum:"",
                listedMessage:[
                        {name:"",
                        number:"",
                        client:"",
                        status:"",
                        timeLeft:""
                        }
                ],
                listedResult:[
                    {
                        client:"",
                        delister:"",
                        status:"",
                        closeStatus:"",
                        startTime:"",
                        endTime:""
                    }
                ],
                listedSuccess:[
                    {
                        name:"",
                        delister:""
                    }
                ],
                currenBroker:{photo:''}
            }
        },
        created(){
            this.getSucessNum();
            listedMessageReq({pageSize:6,curPage:1}).then(response => {
                this.listedMessage=response.data;
            });
            listedResultReq({status:1,pageSize:6,curPage:1}).then(response => {
                this.listedResult=response.data;

            });
            listedSuccessReq({closeStatus:0,pageSize:6,curPage:1}).then(response => {
                this.listedSuccess=response.data;
            });
        },
        methods:{
            getSucessNum(){
                listedOnNumberReq({status:0}).then(response => {
                    var numList = [];
                    if(response.data > 0){
                        this.countNum = response.data;
                        if(this.countNum<10){
                            this.countList.push(0);
                            this.countList.push(response.data);
                        }else if(this.countNum < 100){
                            this.countList.push("0");
                            numList = this.countNum.split("");
                            for(var i=0;i<numList.length;i++) {
                                this.countList.push(this.countNum[i]);
                            }
                        }else if(this.countNum < 1000){
                            numList = this.countNum.split("");
                            for(var i=0;i<numList.length;i++) {
                                this.countList.push(this.countNum[i]);
                            }
                        }
                    }
                });
            },
            advisoryBroker(broker){
                this.currenBroker = broker;
                if(getToken()){
                    this.$router.push({path: '/listedContent'});
                }else{
                    this.$message.error('您还未登录,请登录后再操作！');
                }
            },
            // qryBrokerAdvisory(){
            //     qryBrokerAdvisory({userId:this.$store.getters.userInfo.id,brokerId: this.currenBroker.id}).then(response=>{
            //         this.brokerAdvList = response.data;
            //     });
            // }
        }
    }
</script>

<style scoped>
    @import "../../styles/listed.css";
    .el-icon-guapai-statistic{
        background: url("../../images/guapai1.png") center no-repeat;
        background-size: 255px 40px;
    }
    .el-icon-guapai-statistic.el-button:focus, .el-icon-guapai-statistic.el-button:hover{color: #FFF;border:none;}
    .el-row-jiaoyi-statistic{
        background: url("../../images/jiaoyi.png") center no-repeat;
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
        margin: -30px auto;
        overflow: hidden;
        margin-left: 130px;
    }

</style>