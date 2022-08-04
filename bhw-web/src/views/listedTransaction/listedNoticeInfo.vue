<template>
    <div class="listedTransaction">
        <div class="breadWrap w1240">
            <span class="breakCurr"><i></i>当前位置：</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/listedTransaction' }">挂牌交易</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/listedTransaction/listedNotice' }">通知公告</el-breadcrumb-item>
                <el-breadcrumb-item>通知公告详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="noticeInfo">
            <h2>{{noticeInfo.infoTitle}}</h2>
            <div class="noticeBox">
                <p>{{noticeInfo.content}} </p>
                <h3>挂牌交易时间: </h3>
                <p>{{noticeInfo.transactionTime}} </p>
                <h3> 拟交易受让方手续办理时间及地点: </h3>
                <p>{{noticeInfo.tptTime}} {{noticeInfo.tptPostion}} </p>
                <h3>联系方式:  </h3>
                <p>{{noticeInfo.contact}} </p>
            </div>
            <div class="noticeMark">
                <p>[相关文章]  </p>
                <p v-html="noticeInfo.remark"></p>
            </div>
        </div>
    </div>
</template>

<script>
    import {noticeInfoReq} from "../../api/listedTranaction/listedTranaction";
    export default {
        name: "listedNoticeInfo",
        data(){
            return{
                info_id:"",
                noticeInfo:{
                    infoTitle:"",
                    content:"",
                    transactionTime:"",
                    tptTime:"",
                    tptPostion:"",
                    contact:"",
                    remark:""
             }
     }
    },
    created(){
            this.info_id=this.$route.query.noticInfo_user_id;
            noticeInfoReq({info_id:this.info_id}).then(response => {
                this.noticeInfo=response.data;
             });
        }
    }
</script>

<style scoped>
    @import "../../styles/listed.css";
</style>