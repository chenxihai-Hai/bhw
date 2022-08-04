<template>
    <div class="msgBox">
        <el-row style="margin-bottom: 4px;">
            <el-menu default-active="-1" class="el-menu-demo" mode="horizontal" @select="menuSelect">
                <el-menu-item index="-1">全部通知</el-menu-item>
                <el-menu-item index="1">个人</el-menu-item>
                <el-menu-item index="2">公告</el-menu-item>
                <el-menu-item index="3">系统</el-menu-item>
            </el-menu>
        </el-row>
        <template v-if="messageList.length>0">
            <el-row type="flex" justify="space-between" class="msgList" v-for="msg in messageList" :key="msg.id">
            <el-col :span="18">
                <el-row type="flex" align="top">
                    <el-col style="width: 60px;">
                        <div class="usrImg">
                            <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt="" style="width: 100%" v-if="activeSubMenu=='0'">
                            <img src="../../../images/user/img-msg_normal.png" style="width: 100%" v-else>
                        </div>
                    </el-col>
                    <el-col style="width: calc(100% - 30px);padding-left: 12px;">
                        <div style="color: #CCC;"><span>
                            <template v-if="msg.msgType==1">
                                个人
                            </template>
                            <template v-if="msg.msgType==2">
                                公告
                            </template>
                            <template v-if="msg.msgType==3">
                                系统
                            </template>
                        </span><span style="font-size: 12px;margin-left: 15px;">{{msg.createTime}}</span></div>
                        <div style="color: #1D1D1D;margin-top: 14px;width: 100%;" v-if="msg.content=='尊敬的用户,您的商家申请已通过！'" >
                            {{msg.content}}可前往"<span @click="toSm" style="color: #024ECC;font-weight:normal;cursor: pointer;">服务管理</span>"上传发布您的产品,技术供需。
                        </div>
                        <div style="color: #1D1D1D;margin-top: 14px;" v-else>{{msg.content}}</div>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="8" v-if=" msg.msgType==1 && undefined != msg.productId && ''!=msg.productId" style="text-align: right;">
                <el-button class="btnColorOrg btnDetail" @click="toDet(msg.productId)">查看详情</el-button>
            </el-col>
        </el-row>
            <el-row style="margin: 70px auto 100px;text-align: center;">
                <el-col>
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :page-size="qryParam.pageSize"
                            :current-page="qryParam.curPage"
                            @current-change="currentChange"
                            :total="pageTotal" class="pageBox">
                    </el-pagination>
                </el-col>
            </el-row>
        </template>
        <template v-else>
            <el-row type="flex" align="middle" style="margin-top: 90px;">
                <el-col style="text-align: right;"><img src="../../../images/user/img-noData.png" alt=""></el-col>
                <el-col style="text-align: left;padding-left: 28px;"><span class="nodata">您还没有收到新的通知哦~</span></el-col>
            </el-row>
        </template>
    </div>
</template>

<script>
    import {qryUserSysMsgList} from "../../../api/user/userSysMsg";
    export default {
        name: "messageIndex",
        data(){
            return{
                activeSubMenu:"-1",
                messageList:[],
                pageTotal:0,
                qryParam:{
                    pageSize:5,
                    curPage:1,
                    userId:this.$store.state.user.userInfo.id,
                    msgType:''
                }
            }
        },
        created(){
            this.qryUserSysMsgList();
        },
        methods:{
            toSm(){
                this.$emit("upMenuAc-event",'intellectual');
            },
            toDet(id){
                this.$router.push({path:"/kjsc/detail",query:{productId:id}});
            },
            currentChange(val){
                this.qryParam.curPage = val;
                this.qryUserSysMsgList();
            },
            qryUserSysMsgList(){
                qryUserSysMsgList(this.qryParam).then(response=>{
                    this.messageList = response.data;
                    this.pageTotal = response.page.counts;
                });
            },
            menuSelect(index,indexPath){
                this.activeSubMenu = index;
                this.qryParam.msgType=index=='-1'?'':index;
                this.qryUserSysMsgList();
            }
        }
    }
</script>

<style scoped>
    @import "../../../styles/userCenter.css";
    .msgBox{padding: 0 20px;}
    /deep/ .el-menu--horizontal > .el-menu-item+.el-menu-item{margin: 0 30px;}
    /deep/ .btnDetail{width: 119px;height: 40px;padding: 0;}
    .msgList{padding: 36px 0;align-items: center;-webkit-align-items: center;border-bottom: 0.5px solid #CCC;}
</style>