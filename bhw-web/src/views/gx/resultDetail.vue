<template>
    <div>
        <TopBar></TopBar>
        <el-divider></el-divider>
        <div class="resultInfo">
            <el-row><h2>{{resultItem.patentname}} </h2></el-row>
            <el-row type="flex" justify="center" class="markBox">
                <el-col :span="12">
                    <span>申请号：{{resultItem.patentcode}} </span>
                    <span style="margin-left: 10px;">申请日：{{resultItem.shenqingdate}}</span>
                    <span style="margin-left: 10px;">联系人：{{resultItem.dailiren}}</span>
                    <span style="margin-left: 10px;">联系电话：{{resultItem.dailiJigou}}</span>
                </el-col>
                <!--<el-col style="margin-left: 98px;"></el-col>-->
            </el-row>
            <el-row type="flex" justify="space-between" style="margin-top: 61px;">
                <el-col style="width: 338px;">
                    <img :src="resultItem.patentimg" style="width: 100%;" alt="">
                </el-col>
                <el-col style="width: 844px;">
                    <el-row>
                        <div class="infoBox" v-html="resultItem.content"></div>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <FootBar></FootBar>
    </div>
</template>

<script>
    import TopBar from "../layout/TopBar";
    import FootBar from "../layout/FootBar";
    import {getPatentList} from "../../api/school/school"
    export default {
        name: "resultDetail",
        components: {TopBar, FootBar},
        data(){
            return{
                resultItem:[],
                imgUrl:""
            }
        },
        created() {
            this.resultItem = this.$route.query.resulitInfo;
            if(this.resultItem.patentimg==""){
                this.imgUrl = "http://yhtg.huayunstar.com:8001/jishu/image"+this.resultItem.patentcode+".gif";
            }else{
                this.imgUrl = this.resultItem.patentimg;
            }
            console.log(this.schoolItem);
        },
        methods:{

        }
    }
</script>

<style scoped>
    /deep/ .el-divider--horizontal{background: linear-gradient(90deg,rgba(5,152,203,1) 0%,rgba(216,81,133,1) 99%);height: 3px;margin: 0;}
    .resultInfo{width: 1240px;margin: 0 auto; padding:60px 0 200px;}
    .resultInfo h2{color: #040000;font-size: 24px;text-align: center;}
    .markBox{background: #F8FFD0;padding: 5px 0;margin-top: 24px;}
    .infoBox{}
    .infoBox>>>p strong{color: #040000;font-size: 16px;}
    .infoBox>>>p{color: #7C7B7C;font-size: 14px;line-height: 24px;margin-top: 8px;}
</style>