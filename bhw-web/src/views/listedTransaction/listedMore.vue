<template>
    <div class="listedTransaction">
        <div class="breadWrap w1240">
            <span class="breakCurr"><i></i>当前位置：</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/listedTransaction' }">挂牌交易</el-breadcrumb-item>
                <el-breadcrumb-item>更多</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="listedBar">
            <div class="listedContent w1240">
                <h3 style="margin-bottom: 50px;">挂牌交易信息</h3>
                <el-row>
                    <template v-for="ol in listedMessage" >
                        <el-col class="f-col" :key="ol">
                            <el-row>
                                <el-col :span="16">
                                    <!--<h4>{{ol.name}}</h4>-->
                                    <router-link style="text-decoration: none;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);" :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:ol.infoId}}">
                                        <h4>{{ol.name}}</h4>
                                    </router-link>
                                    <p>编号：{{ol.number}}委托方：{{ol.client}}</p></el-col>
                                <template v-if="ol.timeLeft==0">
                                    <el-col :span="8"><p>距离截止还有0天</p><h4>已结束</h4></el-col>
                                    <div class="status">终止</div>
                                </template>
                                <template v-else>
                                    <el-col :span="8"><p>距离截止还有{{ol.timeLeft}}天</p><h4>未终止</h4></el-col>
                                </template>
                            </el-row>
                        </el-col>
                    </template>

                    <template v-for="(resultItem,resultIndex) in listedResult">
                        <el-col class="f-col" :key="resultIndex">
                            <el-row>
                                <el-col :span="16">
                                    <router-link style="text-decoration: none;font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(0,0,0,1);" :to="{path:'/listedTransaction/listedNoticeInfo',query:{noticInfo_user_id:resultItem.infoId}}">
                                        <h4>{{resultItem.client}}</h4>
                                    </router-link>
                                    <p><em>摘牌候选人</em>{{resultItem.delister}}</p>
                                </el-col>
                                <template v-if="resultItem.status==0">
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
    </div>
</template>

<script>
    import {listedMessageReq,listedResultReq} from "../../api/listedTranaction/listedTranaction";
    export default {
        name: "listedMore",
        data(){
            return{
                listedMessage:[],
                listedResult:[],
                queryParam:{
                    pageSize:12,
                    curPage:1,
                    status:1
                },
                pageTotal:0
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            loadData(){
                if(this.$route.query.listedMoreStatus){
                    listedResultReq(this.queryParam).then(response => {
                        this.listedResult=response.data;
                        this.pageTotal = response.page.counts;
                    });
                }else{
                    listedMessageReq(this.queryParam).then(response => {
                        this.listedMessage=response.data;
                    });
                }
            },
            currentChange(){
                this.queryParam.curPage = val;
                this.loadData();
            }
        }
    }
</script>

<style scoped>
    @import "../../styles/listed.css";
</style>