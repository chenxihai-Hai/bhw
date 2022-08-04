<template>
    <div class="news" style="padding-bottom: 100px;" >
        <div style="width:1240px;height:483px;margin: 8px auto 100px;background:rgba(255,255,255,1);box-shadow:0px 4px 8px 1px rgba(0, 0, 0, 0.07);">
            <div style="height:14px;margin-left: 15px;padding-top: 30px" class="font">
                <el-col style="width: 120px;">
                    <span><i class="icon-bread"></i>当前位置：</span>
                </el-col>
                <el-col :span="16">
                    <el-breadcrumb separator-class="el-icon-arrow-right">
                        <el-breadcrumb-item :to="{ path: '/home' }"  class="font">首页</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{path:'/newsList',query:{newsType:queryParam.type}}" class="font">
                            <template v-if="queryParam.type==1">
                                国际动态
                            </template>
                            <template v-if="queryParam.type==2">
                                国家动态
                            </template>
                            <template v-if="queryParam.type==3">
                                地方动态
                            </template>
                            <template v-if="queryParam.type==4">
                                通知公告
                            </template>
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </div>
            <hr style="width:1210px;height:1px;background:linear-gradient(90deg,rgba(7,151,202,1) 0%,rgba(216,81,133,1) 100%);margin-left: 17px;margin-top: 30px">
            <div style="height:314px;font-size:14px;font-family:'Microsoft YaHei';font-weight:400;line-height:34px;margin-top: 33px;margin-left: 73px;padding-right: 70px;">
                    <ul class="newBox">
                        <template v-for="ol in newsList">
                            <router-link :to="{path:'/newsContent',query:{newsId:ol.id,newsType:queryParam.type}}" :key="ol.id" target="_blank">>
                                <li style="list-style-type:disc;display:inline-block;" class="news-ul-li" :key="ol.id">{{ol.subject}}</li>
                                <br/>
                            </router-link>
                        </template>
                    </ul>
            </div>
            <div style="text-align: center;">
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
    import ElContainer from "element-ui/packages/container/src/main";
    import {newsListPageReq} from "../../api/news/newsNotic";
    export default {
        components: {ElContainer},
        name: "news",
        data(){
            return{
                newsList:[{
                    id:"",
                    subject:""
                }],
                queryParam:{
                    pageSize:8,
                    curPage:1,
                    type:""
                },
                pageTotal:0
            }
        },
        created(){
            this.queryParam.type=this.$route.query.newsType;
            this. loadOrderData();
        },
        methods:{
            currentChange(val){
                this.queryParam.curPage = val;
                this.loadOrderData();
            },
            loadOrderData(){
                newsListPageReq(this.queryParam).then(response => {
                     this.newsList = response.data;
                     this.pageTotal = response.page.counts;
                 });
            }
        }
    }
</script>

<style scoped>
    .breadWrap{margin: 20px auto 0;}
    .w1240{width: 1240px;}
    .breakCurr,.el-breadcrumb__item.is-link{color: #ADADAD;font-weight: 400;}
    .font{font-size:14px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(141,141,141,1)}
    .newBox>a{font-size:14px;
        font-family:'Microsoft YaHei';
        font-weight:400;
        color:#1D1D1D;
        line-height:34px;
    ;text-decoration: none;}
    .news-ul-li:hover{
        color: #CA0000;
    }
    /deep/ .el-pagination.is-background .el-pager li:not(.disabled).active {
        background-color: red;
        color: #FFF;
    }
    .newBox li{list-style: disc;}
</style>