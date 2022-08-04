<template>
    <div class="gxIndex">
        <!--   顶部图片搜索     -->
        <div class="tp">
            <el-input placeholder="输入关键字可直接搜索"  style="visibility: hidden;">
                <template slot="append">搜索</template>
            </el-input>
            <el-tabs v-model="activeName" @tab-click="handleClick" class="schoolTab">
                <el-tab-pane label="高校" name="school" class="school">
                    <span slot="label" @click="iconToggle(0)" style="color: #fa84fb;">
                        <img src="../../images/school/xuexiao1.png" style="width: 50px;height: 35px;vertical-align: -8px;"> 高校</span>
                </el-tab-pane>
                <el-tab-pane label="企业" name="enterprise" class="enterprise">
                    <span slot="label" @click="iconToggle(1)" style="color:#f8ff3b; "><img
                        src="../../images/school/qiye1.png" style="width: 50px;height: 35px;vertical-align: -8px;"> 企业</span></el-tab-pane>
                <el-tab-pane label="机构" name="organization" class="organization">
                    <span slot="label" @click="iconToggle(2)" style="color: #95f670;"><img
                        src="../../images/school/jigou1.png" style="width: 50px;height: 35px;vertical-align: -8px;"> 机构</span>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div><component :is="subMenuComponents[iconToggleFlag]" :areaType="areaType"></component></div>
    </div>
</template>

<script>
    import highSchool from "../../views/gx/highSchool";
    import highEnterprise from "../../views/gx/highEnterprise";
    import institutions from "../../views/gx/institutions";
    import {getCollegeCount} from "../../api/school/school";
    import {getResourceCount} from "../../api/resourceArea/resourceArea";
    export default {
        name:"gxIndex",
        components: {
            "highSchool":highSchool,
            "highEnterprise":highEnterprise,
            "institutions":institutions
        },
        data() {
            return {
                collegeCount:"",
                enterpriseCount:"",
                mechanismCount:"",
                activeName: 'school',
                iconToggleFlag:0,
                subMenuComponents:["highSchool","highEnterprise","highEnterprise"],
                areaType:0
            };
        },
        created(){
            getCollegeCount().then(response=>{
                this.collegeCount= response.data;
             });
            getResourceCount({type:1}).then(response=>{
                this.enterpriseCount= response.data;
              });
            getResourceCount({type:2}).then(response=>{
                this.mechanismCount= response.data;
             });
        },
        methods: {
            handleClick(tab, event) {
            },
            iconToggle(val){
                this.areaType = val;
                if(this.iconToggleFlag==0){
                    this.iconToggleFlag = val;
                }else if(val==0){
                    this.iconToggleFlag = val;
                }
            }
        }
    };
</script>

<style>
    .tp {background: url('../../images/img-gx.png') no-repeat center;padding: 0px;height: 811px; text-align: center;position: relative;}
    .tp .el-input-group {width: 974px;height: 96px;margin-top: 335px;}
    .tp .el-input__inner {height: 96px;border-bottom-left-radius: 20px;border-top-left-radius: 20px;font-size: 30px;font-family:'Microsoft YaHei';font-weight: 400;color: rgba(125, 125, 125, 1);}
    .schoolTab{width: 100%;height: 100px;background: rgba(0,0,0,0.5);position: absolute;left: 0;bottom: 0;}
    .schoolTab .el-tabs__nav-scroll {width: 1240px;margin: 0px auto;height: 100px;line-height: 100px;}
    .schoolTab .el-tabs__item {font-size: 30px;font-family:'Microsoft YaHei';font-weight:normal;color: rgba(255, 255, 255, 1);padding: 0;margin: 0 120px;line-height: inherit;}
    .schoolTab .el-tabs__item.is-active::after {content: "";width: 100%;height: 5px;background: white;display: block;position: absolute;left: 0;bottom: -45px;}
    .schoolTab .el-tabs__active-bar {background: none;}
    .schoolTab .el-tabs__nav-wrap::after{content: normal;}
    .schoolTab .el-tabs__item.is-active{background: none;}

    .type {
        width: 128px;
        height: 29px;
        font-size: 30px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(29, 29, 29, 1);
    }

    .schoolInfo {
        width: 278px;
        margin-top: 50px;
        height: 301px;
        background: rgba(255, 255, 255, 1);
        border: 1px solid rgba(204, 204, 204, 1);
        box-shadow: 0px 4px 7px 0px rgba(8, 1, 3, 0.15);
        border-radius: 5px;
        text-align: center;margin-right: 42px;
    }
    .schoolInfo:nth-child(4n){margin-right: 0;}

    .img {
        width: 142px;
        height: 142px;
        margin-top: 21px;
        margin-left: 59px;
    }

    .name {
        margin-top: 10px;
        margin-bottom: 43px;
    }

    .xq {
        /*width: 278px;*/
        border-top: 1px solid rgba(204, 204, 204, 1);
        height: 65px;
        text-align: left;
    }



    .tp .el-input-group__append {
        width: 194px;
        height: 96px;
        background: rgba(0, 102, 181, 1);
        font-size: 30px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(255, 255, 255, 1);
        text-align: center;
        border-top-right-radius: 20px;
        border-bottom-right-radius: 20px;
    }

    .gxfl .el-tabs__nav-scroll {
        margin: 0px auto;
        padding-top: 24px;
        height: 80px;
        background: rgba(238, 238, 238, 1);
        opacity: 0.7;
        /*  padding-left: 800px;*/
    }

    .gxfl .el-tabs__item {
        width: 128px;
        height: 29px;
        font-size: 30px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(29, 29, 29, 1);
        margin: 0 60px;padding: 0;line-height: inherit;
        position: relative;
    }

    .gxfl .el-tabs__item.is-active::after {
        content: "";
        width: 100%;
        height: 5px;
        background: #CA0000;
        display: block;
        position: absolute;
        left: 0;
        bottom: -15px;
    }

    .gxfl .el-tabs__active-bar {
        background: none;
    }

    .gxfl .el-tabs__nav {
        float: inherit;
        margin: 0 auto;
        text-align: center
    }


    .name {
        height: 18px;
        font-size: 18px;
        font-family:'Microsoft YaHei';
        font-weight:normal;
        color: rgba(29, 29, 29, 1);
    }

    .xqbutton {
        margin-top: 20px;
        font-size: 14px;
        width: 61px;
        height: 25px;
        line-height: initial;
        padding: 0;
        border: 1px solid #CA0000;
        color: #CA0000;
        font-weight: 400;
    }

    .dizhi {
        margin-top: 12px;
        margin-left: 23px;
        width: 29px;
        height: 35px;
        float: left
    }

    .address {
        margin-top: 23px;
        margin-left: 16px;
        width: 100px;
        height: 14px;
        float: left;white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        word-break: break-all;
    }

    .shoolphoto {
        width: 1800px;
        margin: 0px auto;
        padding: 30px;
    }



    .schoolTab .el-tabs__nav {
        float: inherit;
        margin: 0 auto;
        text-align: center
    }



</style>