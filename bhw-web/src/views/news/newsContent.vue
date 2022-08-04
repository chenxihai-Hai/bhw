<template>
    <div class="news-content">
        <div style="width:1240px;background:rgba(255,255,255,1);box-shadow:0px 4px 8px 1px rgba(0, 0, 0, 0.07);margin:8px auto 100px;">
            <div style="height:14px;margin-left: 15px;padding-top: 30px" class="font">
                <el-col style="width: 100px;">
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
            <hr style="width:1210px;height:1px;background:linear-gradient(90deg,rgba(7,151,202,1) 0%,rgba(216,81,133,1) 100%);margin: 30px auto 0;">
           <div style="margin-top: 53px;text-align:center">
               <el-row style="font-size: 24px;font-weight:normal;margin-top: 5px">{{cotent.subject}}</el-row>
               <el-row style="font-size: 18px;font-weight:normal;margin-top: 5px;"> {{cotent.subtitle}}</el-row>
               <el-row style="font-size:12px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(141,141,141,1);margin-top: 22px">
                   <span >发布时间：{{undefined==cotent.uploadTime || ''==cotent.uploadTime?cotent.createTime:cotent.uploadTime}}</span>
                   <span style="margin-left: 20px">文章来源：{{cotent.articleSource}}</span>
                   <!--<span style="margin-left: 20px">网络编辑:{{cotent.webEditor}}</span>-->
                   <span style="margin-left: 20px">作者:{{cotent.author}} </span>
               </el-row>
           </div>
            <hr style="width:1210px;height:1px;background:rgba(170,170,170,1);margin:18px auto 2px;">
            <hr style="width:1210px;height:1px;background:rgba(170,170,170,1);margin:0px auto;">
            <div class="newInfo ql-editor" v-html="cotent.html">
            </div>
        </div>
    </div>
</template>

<script>
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import {newsContentPageReq} from "../../api/news/newsNotic";
    export default {
        name: "news-content",
        data(){
            return{
                queryParam:{
                    id:"",
                    type:""
                },
                cotent:{
                    subject:"我市非公经济迈向高质量发展征程",
                    subtitle:"发挥三大职能，取得三大成效",
                    articleSource:"北海日报",
                    webEditor:"程鸿光",
                    author:"程鸿光",
                    createTime:"2019-03-02 01:59",
                    html:"",
                    uploadTime:''
                }
            }
        },
        created(){
            this.queryParam.id=this.$route.query.newsId;
            this.queryParam.type = this.$route.query.newsType;
            newsContentPageReq(this.queryParam).then(response => {
                this.cotent = response.data;
             });
        }
    }
</script>

<style scoped>
    .newInfo{width:1138px;line-height:24px;margin: 44px auto 0;padding-bottom: 50px;}
</style>