<template>
    <div>
        <div style="padding-bottom: 50px;">
            <el-row class="detailTop" >
                <img :src="schoolItem.introduceImg" ref="dImg" style="width: 100%;" alt="" v-if="schoolItem.introduceImg!=''">
                <img src="../../images/school/img-kjdx.png" ref="dImg" style="width: 100%;" alt="" v-else>
                <div class="summaryBox">
                    <div class="schoolTitle"><h2>学院概况</h2><p>COLLEGE OVERVIEW</p></div>
                    <div class="summaryInfo ql-editor" v-html="schoolItem.introduce">
                    </div>
                </div>
            </el-row>
            <el-row style="margin-top: 100px;">
                <div class="schoolTitle otherTitle"><h2>研究成果</h2><p>RESARCH FINDINGS</p></div>
                <div class="otherBox">
                    <el-row type="flex" justify="space-between">
                        <el-col style="width: 489px;">
                            <h3><span>科研公示</span></h3>
                            <ul>
                                <li v-for="temp1 in resultList1" :key="temp1.id">
                                    <router-link :to="{path:'/gx/result',query:{resulitInfo:temp1}}">{{temp1.patentname}}</router-link>
                                </li>
                            </ul>
                        </el-col>
                        <el-col style="width: 489px;">
                            <h3><span>科研公示</span></h3>
                            <ul>
                                <li v-for="temp2 in resultList2" :key="temp2.id">
                                    <router-link :to="{path:'/gx/result',query:{resulitInfo:temp2}}">{{temp2.patentname}}</router-link>
                                </li>
                            </ul>
                        </el-col>
                    </el-row>
                </div>
            </el-row>
            <el-row style="margin-top: 120px;">
                <div class="schoolTitle otherTitle" style="width: 185px;"><h2>相关院士</h2><p>RELATED ACADEMICIANS</p></div>
                <div class="otherBox">
                    <el-row type="flex" justify="space-between" style="color: #040000;">
                        <el-col style="width: 489px;text-align: center;">
                            <div class="margin-top:30px;">
                                <el-avatar :size="138" :src="schoolItem.academicianimg"></el-avatar>
                                <h4 style="margin-top: 40px;">{{schoolItem.academician}}</h4>
                                <h4 style="margin-top: 6px;font-size: 12px;">{{schoolItem.enName}}</h4>
                                <p style="margin-top: 20px;">{{schoolItem.academicianduty}}</p>
                            </div>
                        </el-col>
                        <el-col style="width: 608px;font-family:'Microsoft YaHei'">
                            <h4 style="margin-bottom: 20px;margin-left: 14px;">简介</h4>
                            <div style="line-height: 26px;" class="ql-editor" v-html="schoolItem.academicianremark"></div>
                        </el-col>
                    </el-row>
                </div>
            </el-row>
        </div>
        <FootBar></FootBar>
    </div>
</template>

<script>
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import FootBar from "../layout/FootBar";
    import {getPatentList} from "../../api/school/school";
    export default {
        name: "schoolDetail",
        components: {FootBar},
        data(){
            return{
                imgHeight:"",
                circleUrl: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
                resultList1:[],//成果列表
                resultList2:[],//成果列表
                schoolItem:[],
                row:0
            }
        },
        created() {
            this.schoolItem = this.$route.query.sItem;
            if(undefined == this.schoolItem.id){
                this.schoolItem = JSON.parse(sessionStorage.getItem(`gx-infoDetail`));
            }else{
                sessionStorage.setItem(`gx-infoDetail`,JSON.stringify(this.schoolItem));
            }
            this.queryResultLit();
        },
        methods:{
            queryResultLit(){
                getPatentList({"schoolid":this.schoolItem.id}).then(response => {
                    var count = response.data.length;
                    if(count > 0){
                        if(count == 1){
                            this.resultList1 = response.data;
                        }else{
                            this.row = count/2;
                            this.resultList1 = response.data.slice(0,this.row);
                            this.resultList2 = response.data.slice(this.row,count);
                        }
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .detailTop{width: 100%;position: relative;}
    .summaryBox{width: 1240px;position: absolute;top: 50%;left: 50%;transform: translate(-50%,-50%);}
    .schoolTitle{color: #FFF;width: 150px; font-family: Microsoft YaHei;font-weight: 400; text-align: center;margin: 0 auto;  position: relative;}
    .schoolTitle:before{content: "";width: 107px;height: 2px;background: #FFF; display: block;position: absolute;top: 50%;right: 180px;transform: translateY(-50%);}
    .schoolTitle:after{content: "";width: 107px;height: 2px;background: #FFF; display: block;position: absolute;top:50%;left: 180px;transform: translateY(-50%); }
    .schoolTitle.otherTitle{color: #004D6C;line-height: 28px;}
    .schoolTitle.otherTitle:before{background: #004D6C;right: 190px;}
    .schoolTitle.otherTitle:after{background: #004D6C;left: 190px;}
    .schoolTitle.otherTitle h2{font-size: 30px;}
    .schoolTitle.otherTitle p{font-size: 14px;}
    .summaryBox h2{font-size: 36px;}
    .summaryBox p{font-size: 14px;}
    .summaryInfo{width: 100%;color: #FFF;font-size: 18px;font-family: Microsoft YaHei;font-weight: 400; padding: 34px 24px;margin-top: 50px;background: RGBA(30, 98, 158, 0.8);line-height: 30px;}
    .otherBox{width: 1240px; background:#F9FDFF;padding: 36px 60px 50px;margin: 57px auto 100px;}
    .otherBox h3{border-bottom:1px solid #12335F;margin-bottom: 10px;padding-bottom: 4px;}
    .otherBox h3 span{background: #234E68;color: #FFF;font-family: Microsoft YaHei;font-size: 24px;padding: 4px 20px;}
    .otherBox ul li{width: 489px; border-bottom:2px dashed #ABAAAA;padding: 21px 0 15px 38px;position: relative;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;}
    .otherBox ul li:after{content: "";width: 15px;height: 15px;border: 2px solid #204D67;border-radius: 50%;position: absolute;left: 0px;top:21px;}
    .otherBox ul li a{color: #040000;font-size: 14px;font-family: Microsoft YaHei;text-decoration: none;}
    .otherBox h4{font-size: 18px;}
    @media  screen and (max-width: 1480px)  {
        .summaryBox{top: 40%;}
    }
</style>