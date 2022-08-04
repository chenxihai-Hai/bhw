<template>
    <div class="relation">
        <div v-if="messageList.length>0" style="padding: 0 25px;">
            <el-row type="flex" align="middle">
                <el-col><img src="../../../images/property/lianxidianyuan.png"/><span style="margin-left: 10px;vertical-align:8px;">谁联系了我</span></el-col>
                <el-col style="text-align: right;padding-right: 60px"><img src="../../../images/property/yijiejue.png"/></el-col>
            </el-row>
            <div v-if="toggle">
                <el-row v-for="msg in messageList" :key="'serverLemsg_'+msg.id" style="margin-bottom: 60px;">
                    <el-col>
                        <img src="../../../images/property/liaotian.png"/>
                        <span style="margin-left: 10px;vertical-align: 6px;">{{msg.leavemsgName}}<span class="xuqiu">有意向承接</span>您发布的<span
                                class="xuqiu">需求信息</span>【{{msg.serverName}}】</span>
                    </el-col>
                    <el-col style="padding-left: 45px;">
                        <div class="message">
                            <p>{{$store.state.user.userInfo.userName}}，您好： </p>
                            <p>{{msg.leavemsgName}}于{{msg.createTime}}有意向承接您发布的需求【{{msg.serverName}}】,对方联系方式为:{{msg.phoneNo}}</p>
                        </div>
                        <p style="margin-left: 21px">{{msg.createTime}}</p>
                    </el-col>
                </el-row>
            </div>
            <div style="text-align: center;margin: 30px auto 92px"><span @click="shou">{{toggle==true?'收起^':'展开'}}</span>
                <el-divider></el-divider>
            </div>
        </div>
        <div style="padding: 0 65px;" v-else>
            <el-row><el-col><img src="../../../images/property/lianxidianyuan.png"/><span style="margin-left: 8px;vertical-align:8px;">谁联系了我</span></el-col></el-row>
            <el-row type="flex" align="middle" style="margin-top: 52px;">
                <el-col :span="15">
                    <div class="daijiejue">
                        <p>您发布的<span class="xuqiu">需求信息</span>暂时还没有人发现哦！</p>
                        <P>坚持就是胜利！</P>
                        <p>如果您真的很着急，可以联系平台<span class="jinjiren">经纪人</span>协助哦！</p>
                    </div>
                </el-col>
                <el-col :span="9"><img src="../../../images/property/daijiejue.png"/></el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {qryLeaveMsg} from "../../../api/projectDw/projectDw";
    export default {
        name: "relation",
        props:["serverType"],
        data() {
            return {
                toggle: true,
                without: false,
                tobe: true,
                pageTotal:0,
                qryParam:{
                    type:this.serverType,
                    serverUserId:this.$store.state.user.userInfo.id,
                    pageSize:5,
                    curPage:1
                },
                messageList: [],
            }
        },
        methods: {
            shou() {
                this.toggle = !this.toggle;
            },
            qryLeaveMsg(){
                qryLeaveMsg(this.qryParam).then(response=>{
                    this.messageList = response.data;
                    this.pageTotal = response.page.counts;
                    console.log(response.data)
                });
            }
        },
        created() {
          this.qryLeaveMsg();
        }
    }
</script>

<style>
    .shoucang {
        color: #CA0000;
    }

    .xuqiu {
        color: #CA0000;
        font-weight:normal;
    }

    .message {
        margin: 24px auto 12px;
        padding: 16px 22px 30px;
        background: rgba(2,78,204,0.05);line-height: 25px;
    }

    .history {
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight: 400;
        color: rgba(29, 29, 29, 1);
        margin-bottom: 53px;
    }

    .daijiejue {
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight: 400;
        color: rgba(29, 29, 29, 1);
        line-height: 30px;
        margin: 0px auto;
        text-align: center;
    }

    .jinjiren {
        color: #024ECC;
        font-size: 24px;
        font-weight:normal;
    }
</style>