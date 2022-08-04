<template >
    <div style="text-align: center;">
        <div :class="kjscBgImgClassName" style="height: 448px;"></div>
        <el-row class="kjscIndex" justify="center" type="flex">
            <el-col :span="24">
                <el-row justify="center" type="flex" class="tabType" style="margin-top: 59px;" v-show="showflag">
                    <el-col :span="14">
                        <el-button @click="iconToggle(0)" icon="el-icon-chengguo-statistic" class="marketMenu">成果库</el-button>
                        <el-button @click="iconToggle(1)" icon="el-icon-xuqiuku2-statistic" class="marketMenu" style="margin-left:1px;">需求库</el-button>
                    </el-col>
                    <!--<el-col :span="14" v-else>
                        <el-button @click="iconToggle(0)"  icon="el-icon-chengguo-statistic" style="line-height:36px;width: 233px;height: 65px;background:#024ECC;font-size:24px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,255,255,1);">成果库</el-button>
                        <el-button @click="iconToggle(1)" icon="el-icon-xuqiuku2-statistic" style="margin-left:0px;line-height:36px;width: 233px;height: 65px;background:#024ECC;border:0px solid rgba(204,204,204,1);font-size:24px;font-family:'Microsoft YaHei';font-weight:normal;color:rgba(255,255,255,1);">需求库</el-button>
                    </el-col>-->
                </el-row>
                <el-row style="margin-top: 89px;">
                    <component :is="subMenuComponents[iconToggleFlag]" v-on:swBgImg-event="swBgImg"></component>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    import {testPay} from "../../api/pay/pay";
    import CHENGGUO from "../../views/kjsc/chengguo";
    import TechRequire from "../../views/kjsc/techRequire";
    export default {
        name:"kjscIndex",
        components: {
            "chengguo":CHENGGUO,
            "techRequire":TechRequire
        },
        data() {
            return {
                src: '../kjsc/images/1.png',
                iconToggleFlag:0,
                subMenuComponents:["chengguo","techRequire"],
                infoid:5,
                kjscBgImgClassName:"el-row-kjsc-statistic",
                showflag:true
            };
        },
        created(){
            if(this.$route.query.iconToggleFlag){
                this.iconToggleFlag = this.$route.query.iconToggleFlag;
            }
            if(this.$route.query.chengguoActiveName){
                var nameId= this.$route.query.chengguoActiveName+''
                if(nameId == "0"){
                    this.kjscBgImgClassName = "el-row-kjsc-statistic"
                    this.showflag = true;
                }else if(nameId == "1"){
                    // this.showflag = false;
                    this.kjscBgImgClassName = "el-row-kjsc-fwjy-statistic"
                }else if(nameId == "2"){
                    this.kjscBgImgClassName = "el-row-kjsc-zcbt-statistic"
                    // this.showflag = false;
                }else if(nameId == "3"){
                    this.kjscBgImgClassName = "el-row-kjsc-jscg-statistic"
                    this.showflag = false;
                }
            }
        },
        methods: {
            iconToggle(val){
                this.iconToggleFlag = val;
                this.kjscBgImgClassName ="el-row-kjsc-statistic";
            },
            swBgImg(className){
                this.kjscBgImgClassName = className;
              /*  if(this.kjscBgImgClassName != "el-row-kjsc-statistic"){
                    // this.showflag = false;
                }else{
                    // this.showflag = true;
                }*/
            }
        }
    };
</script>
<style scoped>
    .jp {
        width: 216px;
        height: 240px;
        background: rgba(255, 255, 255, 1);
        box-shadow: 0px 2px 6px 0px rgba(8, 1, 3, 0.5);
        border: 1px solid black;
    }

    .zl {
        width: 239px;
        height: 331px;
        background: rgba(255, 255, 255, 1);
        box-shadow: 0px 2px 6px 0px rgba(8, 1, 3, 0.5);
    }
    /deep/ .marketMenu{width: 233px;height: 65px;background:#024ECC;font-size:24px;font-weight:normal;font-family:'Microsoft YaHei';color:rgba(255,255,255,1);border:none;}
    .el-row-kjsc-statistic{
        background: url("../../images/kjsc.png") center no-repeat;
        background-size: 100% 100%;
    }
    .el-row-kjsc-fwjy-statistic{
        background: url("../../images/fuwujy/images/fwjybg.png") center no-repeat;
        background-size: 100% 100%;
    }
    .el-row-kjsc-zcbt-statistic{
        background: url("../../images/fuwujy/images/zcbtbg.png") center no-repeat;
        background-size: 100% 100%;
    }
    .el-row-kjsc-jscg-statistic{background: url("../../images/zichanjy/jscg.png") center no-repeat;background-size: 100% 100%;}
    /deep/ .tabType .el-col{display: flex;display: -webkit-flex;justify-content: center;-webkit-justify-content: center;}
    /deep/ .tabType .el-col .el-button{display: flex;display: -webkit-flex;justify-content: center;-webkit-justify-content: center;align-items: center;-webkit-align-items: center;}
    /deep/ .el-icon-chengguo-statistic{
        background: url("../../images/zichanjy/chengguo@2x.png") center no-repeat;
        background-size: contain;
        width: 38px;
        height: 36px;margin-right: 10px;
    }

    /deep/ .el-icon-chengguo-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }

    /deep/ .el-icon-chengguo2-statistic{
        background: url("../../images/zichanjy/chengguo@2x.png") center no-repeat;
        background-size: contain;
        width: 38px;
        height: 36px;margin-right: 10px;
    }

    /deep/ .el-icon-chengguo2-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }

    /deep/ .el-icon-xuqiuku1-statistic{
        background: url("../../images/zichanjy/xuqiuku1@2x.png") center no-repeat;
        background-size: contain;
        width: 53px;
        height: 52px;margin-right: 10px;
    }

    /deep/ .el-icon-xuqiuku1-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }

    /deep/ .el-icon-xuqiuku2-statistic{
        background: url("../../images/zichanjy/xuqiuku1@2x.png") center no-repeat;
        background-size: contain;
        width: 53px;
        height: 52px;margin-right: 10px;
    }

    /deep/ .el-icon-xuqiuku2-statistic:before{
        content: "\8d3a";
        font-size: 14px;
        visibility: hidden;
    }
    /deep/.btnNoCheck.el-button.el-button--default span{color:#7D7D7D;}
</style>