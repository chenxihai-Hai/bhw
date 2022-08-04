<template>
    <div>
        <div class="manImg" style="position: relative;">
            <img src="../../images/middleman/img-jjrInfo_banner.png" width="100%">
            <div class="manDetail">
                <el-avatar :src="detailInfo.photo" :fit="fit"></el-avatar>
                <div style="padding-top: 174px;margin-left: 23px;">
                    <div class="manName">
                        <h3>{{detailInfo.name}}</h3>
                        <p>好评度：</p>
                        <el-rate v-model="value"
                            disabled
                            show-score
                            text-color="#ff9900" class="jjrRate">
                        </el-rate>
                    </div>
                    <p style="margin-top: 10px;">{{detailInfo.brokerName}}</p>
                    <p>经验：{{detailInfo.seniority}}</p>
                </div>
            </div>
        </div>
        <div class="manOrtherDetail">
            <el-row>
                <el-col :span="6" style="background: #FFEAE6;color: #CF4F7D;"><span><i class="icon-man"></i></span><span>从事行业领域：</span></el-col>
                <el-col :span="18"><p>{{detailInfo.brokerName}}</p></el-col>
            </el-row>
            <el-row>
                <el-col :span="6"><span><i class="icon-chengj"></i></span><span>主要成就：</span></el-col>
                <el-col :span="18"><p>{{detailInfo.mainAchieve}}</p></el-col>
            </el-row>
            <el-row>
                <el-col :span="6"><span><i class="icon-phone"></i></span><span>手机号码：</span></el-col>
                <el-col :span="18"><p>{{detailInfo.phone}}</p></el-col>
            </el-row>
            <el-row>
                <el-col :span="6"><span><i class="icon-mail"></i></span><span>邮箱：</span></el-col>
                <el-col :span="18"><p>{{detailInfo.email}}</p></el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    export default {
        name: "middlemanDetail",
        data(){
            return{
                value:3.6,
                fit:"cover",
                detailInfo:[]
            }
        },
        created() {
            this.detailInfo =this.$route.query.detailInfo;
            console.log(this.detailInfo);
        }
    }
</script>

<style scoped>
    .manDetail{width: 1240px;display: flex; position: absolute;left: 50%;transform: translateX(-50%);-webkit-transform: translateX(-50%);bottom: -154px;}
    .manDetail /deep/ .el-avatar{width: 300px;height: 300px;box-shadow: 0px 0px 10px 8px rgba(255,255,255,0.5);}
    .manDetail h3{color: #000;font-size: 36px;font-weight:normal;margin-right: 28px;}
    .manDetail p{color: #929292;font-size: 18px;font-weight:normal;line-height: 28px;}
    .manName{display: flex;display: -webkit-flex;align-items: flex-end;-webkit-align-items: flex-end;}
    /deep/ .jjrRate.el-rate{height: 30px;}
    /deep/ .jjrRate .el-rate__icon{font-size: 30px;margin-right: 0;}
    .manOrtherDetail{width: 1240px;margin:240px auto;}
    .manOrtherDetail /deep/ .el-row{width: 100%;height: 67px;line-height: 67px; margin-bottom: 14px; box-shadow:0px 2px 6px 0px rgba(103,12,119,0.23)}
    .manOrtherDetail /deep/ .el-row .el-col:first-child{padding-left: 40px;width: 22%;font-size: 18px;font-weight:normal; display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;}
    .manOrtherDetail /deep/ .el-row .el-col:last-child{width: 78%; padding-left: 46px;}
    .manOrtherDetail /deep/ .el-row+.el-row .el-col:first-child{background: #DEF1FF;color: #2B77AF;}
    .manOrtherDetail /deep/ .el-row .el-col span:first-child{width: 26px;margin-right:14px;display: flex;justify-content: center;-webkit-justify-content: center;}
    .manOrtherDetail /deep/ .el-row .el-col span:last-child{text-align: center;}
    .icon-man{width: 24px;height: 24px;background: url("../../images/middleman/icon-jjr_hangy.png") no-repeat;}
    .icon-chengj{width: 18px;height: 28px;background: url("../../images/middleman/icon-jjr_chengj.png") no-repeat;}
    .icon-phone{width: 16px;height: 25px;background: url("../../images/middleman/icon-jjr_phone.png") no-repeat;}
    .icon-mail{width: 24px;height: 17px;background: url("../../images/middleman/icon-jjr_mail.png") no-repeat;}
</style>