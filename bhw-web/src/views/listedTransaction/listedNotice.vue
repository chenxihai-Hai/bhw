<template>
    <div class="listedTransaction">
        <div class="breadWrap w1240">
        <span class="breakCurr"><i></i>当前位置：</span>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/listedTransaction' }">挂牌交易</el-breadcrumb-item>
            <el-breadcrumb-item>通知公告</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
        <div class="noticeWrap">
            <el-row>
                <template v-for="ol in birefNotice">
                    <el-col class="noticeBar" :key="ol">
                        <h3>{{ol.infoTitle}}</h3>
                        <p>
                            <span class="no-info"> {{ol.content}}</span>
                            <router-link :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:ol.infoId}}" class="linkInfo" >「详细信息」</router-link>
                        </p>
                    </el-col>
                </template>
            </el-row>
        </div>
        <div class="pageWrap w1240">
            <el-pagination
                    background
                    layout="prev, pager, next"
                    :page-size="queryParam.pageSize"
                    :current-page="queryParam.curPage"
                    @current-change="currentChange"
                    :total="pageTotal" class="pageBox">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import {birefNoticeReq} from "../../api/listedTranaction/listedTranaction";
    export default {
        name: "listedNotice",
        data(){
            return{
                birefNotice:[{
                    infoId:"",
                    infoTitle:"",
                    content:""
                }],
                queryParam:{
                    pageSize:6,
                    curPage:1
                },
                pageTotal:0,
            }
        },
        created(){
            this. loadOrderData();
        },
        methods: {
            currentChange(val){
                this.queryParam.curPage = val;
                this.loadOrderData();
            },
            loadOrderData(){
                birefNoticeReq(this.queryParam).then(response => {
                    console.log(response.data)
                     this.birefNotice = response.data;
                     this.pageTotal = response.page.counts;
                 });
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/listed.css";
</style>